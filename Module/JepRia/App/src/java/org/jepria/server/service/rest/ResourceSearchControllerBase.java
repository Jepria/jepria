package org.jepria.server.service.rest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Supplier;

import javax.servlet.http.HttpSession;

import org.jepria.compat.CoreCompat;
import org.jepria.server.dao.Dao;
import org.jepria.server.load.ColumnSortConfiguration;
import org.jepria.server.load.ListSorter;
import org.jepria.server.load.rest.ColumnSortConfigurationDto;
import org.jepria.server.load.rest.SearchParamsDto;
import org.jepria.server.security.Credential;

import com.technology.jep.jepria.shared.field.JepLikeEnum;
import com.technology.jep.jepria.shared.field.JepTypeEnum;

/**
 * Реализация поискового контроллера, состоящего на HTTP сессиях.
 */
// TODO отразить в названии класса тот факт, что это именно сессионная реализация (добавлением слова Session)
public class ResourceSearchControllerBase<T> implements ResourceSearchController<T> {

  // нет необходимости параметризовать, так как механизм поиска не специфицируется на прикладном уровне 
  protected final ResourceDescription<?, T> resourceDescription;

  protected final Supplier<HttpSession> session;

  private final String searchUID;
 
  public ResourceSearchControllerBase(ResourceDescription<?, T> resourceDescription,
      Supplier<HttpSession> session) {
    this.resourceDescription = resourceDescription;
    this.session = session;
    
    // create single searchUID for a tuple {session,resource} 
    searchUID = Integer.toHexString(Objects.hash(session.get(), resourceDescription.getResourceName()));
  }
  
  /**
   * В сессионной реализации контроллера поиска, обращение клиента возможно только с searchId равным значению поля searchUID
   * @param searchId
   * @throws NoSuchElementException в случае несовпадающего searchId
   */
  private void checkSearchIdOrElseThrow(String searchId) throws NoSuchElementException {
    if (!searchUID.equals(searchId)) {
      throw new NoSuchElementException(searchId);
    }
  }

  
  /**
   * @return сохранённый атрибут сессии: клиентские поисковые параметры
   */
  private SearchParamsDto getSessionSearchParams() {
    final String key = "SearchController:ResourceName=" + resourceDescription.getResourceName() + ";SearchId=" + searchUID + ";Key=SearchParams;";
    return (SearchParamsDto)session.get().getAttribute(key);
  }
  /**
   * Сохраняет атрибут сессии: клиентские поисковые параметры 
   * @param searchParams
   */
  private void setSessionSearchParams(SearchParamsDto searchParams) {
    final String key = "SearchController:ResourceName=" + resourceDescription.getResourceName() + ";SearchId=" + searchUID + ";Key=SearchParams;";
    if (searchParams == null) {
      session.get().removeAttribute(key);
    } else {
      session.get().setAttribute(key, searchParams);
    }
  }
  
  /**
   * @return сохранённый атрибут сессии: результирующий список
   * в соответствии с последним клиентским запросом 
   */
  private List<T> getSessionResultset() {
    final String key = "SearchController:ResourceName=" + resourceDescription.getResourceName() + ";SearchId=" + searchUID + ";Key=SearchResultset;";
    return (List<T>)session.get().getAttribute(key);
  }
  /**
   * Сохраняет атрибут сессии: результирующий список
   * в соответствии с последним клиентским запросом
   * @param resultset
   */
  private void setSessionResultset(List<T> resultset) {
    final String key = "SearchController:ResourceName=" + resourceDescription.getResourceName() + ";SearchId=" + searchUID + ";Key=SearchResultset;";
    if (resultset == null) {
      session.get().removeAttribute(key);
    } else {
      session.get().setAttribute(key, resultset);
    }
  }
  
  /**
   * @return сохранённый атрибут сессии: является ли сессионный результирующий список {@link #getSessionResultset()} 
   * отсортированным в соответствии с последним клиентским запросом
   */
  private boolean getSessionResultsetSortValid() {
    final String key = "SearchController:ResourceName=" + resourceDescription.getResourceName() + ";SearchId=" + searchUID + ";Key=SearchResultsetSortValid;";
    return Boolean.TRUE.equals(session.get().getAttribute(key));
  }
  /**
   * Сохраняет атрибут сессии: является ли сессионный результирующий список {@link #getSessionResultset()}
   * отсортированным в соответствии с последним клиентским запросом
   * @param resultsetSortValid
   */
  private void setSessionResultsetSortValid(boolean resultsetSortValid) {
    final String key = "SearchController:ResourceName=" + resourceDescription.getResourceName() + ";SearchId=" + searchUID + ";Key=SearchResultsetSortValid;";
    if (!resultsetSortValid) {
      session.get().removeAttribute(key);
    } else {
      session.get().setAttribute(key, true);
    }
  }
  

  
  @Override
  public String postSearchRequest(SearchParamsDto searchParams, Credential credential) {

    // В зависимости от существующих и новых поисковых параметров инвалидируем результирующий список и/или его сортировку
    final SearchParamsDto existingParams = getSessionSearchParams();
    
    boolean invalidateResultset = true;
    boolean invalidateResultsetSort = true;
    
    if (existingParams != null && searchParams != null) {
      if (Objects.equals(existingParams.getTemplate(), searchParams.getTemplate())) {
        if (!Objects.equals(existingParams.getListSortConfiguration(), searchParams.getListSortConfiguration())) {
          // не инвалидируем результирующий список, если изменились только параметры сортировки
          invalidateResultset = false;
        }
      }
    }
    
    if (invalidateResultset) {
      setSessionResultset(null);
    }
    if (invalidateResultsetSort) {
      setSessionResultsetSortValid(false);
    }
    
    
    // сохраняем новые поисковые параметры
    setSessionSearchParams(searchParams);
    
    return searchUID;
  }
  
  /**
   * Специфичная для DAO-поиска поисковая модель, формируемая на основе пользовательских поисковых параметров 
   *
   */
  private class SearchModel {
    public Map<String, Object> preparedTemplate;
    public int maxRowCount;
  }

  /**
   * Преобразует клиентские поисковые параметры в специфичную для DAO-поиска поисковую модель
   * @param searchParams
   * @return
   */
  protected SearchModel createSearchModel(SearchParamsDto searchParams) {

    SearchModel searchModel = new SearchModel();

    Map<String, Object> template = searchParams.getTemplate();
    if (template == null) {
      // search with no params
      template = Collections.emptyMap();
    }

    
    // maxRowCount: если не задано в клиентских параметрах, то дефолтное значение 
    Integer maxRowCount = (Integer)template.get(CoreCompat.MAX_ROW_COUNT__FIELD_NAME);
    searchModel.maxRowCount = maxRowCount != null ? maxRowCount : CoreCompat.DEFAULT_MAX_ROW_COUNT;

    
    Map<String, Object> preparedTemplate = new HashMap<>(template);

    // применяем MatchType к каждому полю
    preparedTemplate.entrySet().forEach(entry -> {

      final String key = entry.getKey();
      final Object value = entry.getValue();

      JepTypeEnum valueType = resourceDescription.getRecordDefinition().getFieldType(key);
      JepLikeEnum matchType = resourceDescription.getRecordDefinition().getFieldMatch(key);

      if (valueType == JepTypeEnum.STRING && value instanceof String) {
        String valueStr = (String)value;
        if (matchType != null) {
          switch(matchType) {
          case FIRST:
            entry.setValue(valueStr + "%");
            break;
          case CONTAINS:
            entry.setValue("%" + valueStr + "%");
            break;
          case LAST:
            entry.setValue("%" + valueStr);
            break;
          case EXACT:
            // the value is already EXACT
            break;
          }
        }
      }
    });

    searchModel.preparedTemplate = preparedTemplate;

    return searchModel;
  }

  /**
   * Осуществляет DAO-поиск и сохраняет результирующий список в сессию
   * @param searchId
   */
  protected void doSearch(Credential credential) {

    final SearchParamsDto searchParams = getSessionSearchParams();
    if (searchParams == null) {
      throw new IllegalStateException("The session attribute must have already been set at this point");
    }
    
    SearchModel searchModel = createSearchModel(searchParams);
    
    List<T> resultset;
    
    try {
      // TODO remove backward compatibility: resourceDescription.getDao() must return org.jepria.server.dao.JepDataStandard
      Dao<T> dao = resourceDescription.getDao();
      resultset = dao.find(
              searchModel.preparedTemplate,
              searchModel.maxRowCount,
              credential.getOperatorId());
    } catch (Throwable e) {
      //TODO
      throw new RuntimeException(e);
    }

    if (resultset == null) {
      resultset = new ArrayList<>();
    }
    
    setSessionResultset(resultset);
  }
   
  /**
   * Осуществляет сортировку результирующего списка, сохранённого в сессию, и выставляет сессионный признак валидности его сортировки
   * @param searchId
   */
  protected void doSort() {
    
    final SearchParamsDto searchParams = getSessionSearchParams();
    if (searchParams == null) {
      throw new IllegalStateException("The session attribute must have already been set at this point");
    }
    
    // collect columnSortConfigurations
    final List<ColumnSortConfiguration> columnSortConfigurations = convertColumnSortConfigurations(searchParams.getListSortConfiguration());
    
    // collect field comparators      
    final Map<String, Comparator<Object>> fieldComparators = new HashMap<>();
    if (resourceDescription.getRecordDefinition().getFieldNames() != null) {
      resourceDescription.getRecordDefinition().getFieldNames().forEach(fieldName -> fieldComparators.put(fieldName,
          resourceDescription.getRecordDefinition().getFieldComparator(fieldName)));
    }
    
    
    
    final List<T> resultset = getSessionResultset();
    
    if (resultset == null) {
      throw new IllegalStateException("The session attribute must have already been set at this point");
    }

    
    Collections.sort((List<T>)resultset, new ListSorter<T>(columnSortConfigurations, fieldComparators));
    // sorting affects the session attribute as well 
    
    setSessionResultsetSortValid(true);
  }
  
  /**
   * Преобразует конфигурацию сортировки столбцов списка из {@link ColumnSortConfigurationDto} в {@link ColumnSortConfiguration}
   * @param list
   * @return or null if the input is null
   */
  protected static List<ColumnSortConfiguration> convertColumnSortConfigurations(List<ColumnSortConfigurationDto> list) {
    if (list == null) {
      return null;
    }
    
    List<ColumnSortConfiguration> ret = new ArrayList<>();
    for (ColumnSortConfigurationDto item: list) {
      ColumnSortConfiguration columnSortConfiguration = new ColumnSortConfiguration(
          item.getColumnName(),
          "desc".equals(item.getSortOrder()) ? -1 : 1);
      ret.add(columnSortConfiguration);
    }
    return ret;
  }
  
  @Override
  public SearchParamsDto getSearchParams(String searchId, Credential credential) throws NoSuchElementException {
    checkSearchIdOrElseThrow(searchId);
    
    SearchParamsDto searchParams = getSessionSearchParams();
    if (searchParams == null) {
      throw new IllegalStateException("The session attribute must have already been set at this point");
    }
    
    return searchParams;
  }
  
  @Override
  public int getResultsetSize(String searchId, Credential credential) throws NoSuchElementException {
    checkSearchIdOrElseThrow(searchId);
    
    return getResultsetLocal(credential).size();
  }
  
  /**
   * 
   * @param searchId
   * @return non-null
   * @throws NoSuchSearchIdException
   */
  protected List<T> getResultsetLocal(Credential credential) throws NoSuchElementException {
    
    // поиск (если необходимо)
    List<T> resultset = getSessionResultset();
    
    if (resultset == null) {
      // поиск не осуществлялся или был инвалидирован
      doSearch(credential);
      
      resultset = getSessionResultset();
      
      if (resultset == null) {
        throw new IllegalStateException("The session attribute must have already been set at this point");
      }
    }
    
    
    // сортировка (если необходимо)
    boolean resultsetSortValid = getSessionResultsetSortValid();
    
    if (!resultsetSortValid) {
      // сортировка не осуществлялась или была инвалидирована
      doSort();
      
      resultset = getSessionResultset();
      
      if (resultset == null) {
        throw new IllegalStateException("The session attribute must have already been set at this point");
      }
    }
    
    
    return resultset;
  }
  
  @Override
  public List<T> getResultset(String searchId, Credential credential) throws NoSuchElementException {
    checkSearchIdOrElseThrow(searchId);
    
    return getResultsetLocal(credential);
  }
  
  @Override
  public List<T> getResultsetPaged(String searchId, int pageSize, int page, Credential credential) throws NoSuchElementException {
    checkSearchIdOrElseThrow(searchId);
    
    List<T> resultset = getResultsetLocal(credential);
    
    return paging(resultset, pageSize, page);
  }
  
  private static <T> List<T> paging(List<T> resultset, int pageSize, int page) {
    if (resultset == null) {
      return null;
    }
    
    final int fromIndex = pageSize * (page - 1);
    
    if (fromIndex >= resultset.size()) {
      return null;
    }
    
    final int toIndex = Math.min(resultset.size(), fromIndex + pageSize);
    
    if (fromIndex < toIndex) {
      
      List<T> pageRecords = Collections.unmodifiableList(resultset.subList(fromIndex, toIndex));
      return pageRecords;
      
    } else {
      return null;
    }
  }
}