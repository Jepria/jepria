package org.jepria.server.service.rest.jaxrs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

/**
 * Класс для создания расширенных ответов на основе прикладного обработчика
 */
public class ExtendedResponse {
  
  public static final String HEADER_NAME__EXTENDED_RESPONSE = "extended-response";
  
  /**
   * Конфигуратор расширения ответа 
   */
  public static interface Configurator {
    /**
     * Конфигурирует источник значений: запрос.
     * @param request запрос, из заголовка которого берутся значения для обработки
     */
    Configurator valuesFrom(HttpServletRequest request);
    
    /**
     * Конфигурирует обработчик значений
     * @param handler обработчик значений
     */
    Configurator handler(Handler handler);
    
    /**
     * Завершает конфигурирование расширения ответа и производит его расширение
     * @return расширенный ответ
     */
    Response create();
  }

  /**
   * Создаёт конфигуратор
   * @param response ответ, подлежащий расширению
   * @return экземпляр конфигуратора
   */
  public static Configurator extend(Response response) {
    if (response == null) {
      throw new IllegalStateException("Cannot extend null response");
    }
    return new ConfiguratorImpl(response);
  }
  
  private static final class ConfiguratorImpl implements Configurator {
    private final Response response;
    
    private List<String> values;
    
    private Handler handler;
    
    public ConfiguratorImpl(Response response) {
      this.response = response;
    }

    @Override
    public Configurator valuesFrom(HttpServletRequest request) {
      final String header = request.getHeader(HEADER_NAME__EXTENDED_RESPONSE);
      if (header != null) {
        values = new ArrayList<>();
        String[] headerValues = header.split("\\s*,\\s*");
        for (String headerValue: headerValues) {
          if (headerValue != null) {
            values.add(headerValue);
          }
        }
      }
      
      return this;
    }

    @Override
    public Configurator handler(Handler handler) {
      this.handler = handler;
      return this;
    }

    @Override
    public Response create() {
      
      if (values == null) {
        // do not extend the response
        return response;
      }
      
      
      final Map<String, Object> extendedResponses = new HashMap<>();
      
      if (handler != null) {
        for (String value: values) {
          Object extendedEntityPart = handler.handle(value);
          
          if (extendedEntityPart != null) {
            extendedResponses.put(value, extendedEntityPart);
          } else {
            // null means that the particular value is not supported by the handler 
          }
        }
      }
      
      
      ResponseBuilder extendedResponseBuilder = Response.fromResponse(response);
      Map<String, Object> extendedEntity = new HashMap<>();
      
      Object basicEntity = response.getEntity();
      if (basicEntity == null) {
        // even if basic entity is null, make it present in the extended response
        basicEntity = Collections.emptyMap();
      }
      
      extendedEntity.put("basic-response", basicEntity);
      extendedEntity.put("extended-response", extendedResponses);
      extendedResponseBuilder.entity(extendedEntity);
      
      
      return extendedResponseBuilder.build();
    }
    
    
  }
  
  
  public static interface Handler {
    /**
     * @param headerValue not null
     * @return null to indicate unsupported headerValue
     */
    Object handle(String value);
  }
}