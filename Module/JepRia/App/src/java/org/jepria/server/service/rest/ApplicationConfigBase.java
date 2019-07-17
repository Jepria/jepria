package org.jepria.server.service.rest;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.ext.ExceptionMapper;

import org.glassfish.jersey.server.ResourceConfig;
import org.jepria.CastMap;

public class ApplicationConfigBase extends ResourceConfig {
  
  public ApplicationConfigBase() {
    register(new QueryParamsParamValueFactoryProvider.Binder());
    register(new BodyParamsParamValueFactoryProvider.Binder());
    register(new ConfiguredValidatorImpl.Binder());
    
    
    
    // register exception mappers
    ExceptionMapper<? extends Throwable> em;
    if ((em = createExceptionMapper(ConstraintViolationException.class)) != null) {
      register(em);
    }
    if ((em = createExceptionMapper(JsonParseException.class)) != null) {
      register(em);
    }
    if ((em = createExceptionMapper(CastMap.CastOnGetException.class)) != null) {
      register(em);
    }
  }
  
  /**
   * @param exceptionClass
   * @return {@code null} if no need to register such exception class
   */
  @SuppressWarnings("unchecked")
  protected <T extends Throwable> ExceptionMapper<T> createExceptionMapper(Class<T> exceptionClass) {
    if (exceptionClass == ConstraintViolationException.class) {
      return (ExceptionMapper<T>) new ExceptionMappers.ConstraintViolationMapper();
    }
    if (exceptionClass == JsonParseException.class) {
      return (ExceptionMapper<T>) new ExceptionMappers.JsonParse();
    }
    if (exceptionClass == CastMap.CastOnGetException.class) {
      return (ExceptionMapper<T>) new ExceptionMappers.CastOnGet();
    }
    return null;
  }
}