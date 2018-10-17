package org.jepria.server.service.rest;

import java.util.List;
import java.util.Map;

import org.jepria.server.load.rest.SearchEntity;
import org.jepria.server.load.rest.SearchParamsDto;

public interface StandardResourceController {

  //////////// CRUD ///////////////////

  /**
   * @param recordId
   * @param credential
   * @return instance normally; {@code null} to indicate 'not found' status
   */
  Object getResourceById(String recordId, Credential credential);
  
  /**
   * @param instance
   * @param credential
   * @return created instance ID normally; never {@code null}
   */
  Object create(Map<String, Object> instance, Credential credential);
  
  void deleteResourceById(String recordId, Credential credential);
  
  void update(String recordId, Map<String, Object> fields, Credential credential);
  
  /////////////////////////// OPTIONS RESOURCE //////////////////////////
  
  /**
   * 
   * @param optionEntityName
   * @param credential
   * @return non-empty list normally; {@code null} or empty list to indicate 'not found' status
   */
  List<?> listOptions(String optionEntityName, Credential credential);
  
  
  
  /////////////////////////// SEARCH ////////////////////////// 
  
  /**
   * @param searchParamsDto
   * @param searchState search state corresponding to the particular invocation; not {@code null}
   * @param credential
   * @return searchId non-null
   */
  String postSearch(SearchParamsDto searchParamsDto, Credential credential);
  
  /**
   * 
   * @param searchState search state corresponding to the particular invocation; not {@code null}
   * @param credential
   * @return search entity normally; {@code null} to indicate 'not found' status
   */
  SearchEntity getSearchEntity(String searchId, Credential credential);
  
  void updateSearchEntity(String searchId, SearchParamsDto searchParamsDto, Credential credential);
  
  void deleteSearchEntity(String searchId, Credential credential);
  
  /**
   * @param searchState search state corresponding to the particular invocation; not {@code null}
   * @param pageSize
   * @param page
   * @param credential
   * @return non-empty list normally; {@code null} or empty list to indicate 'not found' status
   */
  List<?> fetchData(String searchId,
      Integer pageSize, 
      Integer page, 
      Credential credential);
}
