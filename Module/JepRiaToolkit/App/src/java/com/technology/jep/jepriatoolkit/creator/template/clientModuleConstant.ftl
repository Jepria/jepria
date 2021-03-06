package ${packagePrefix?lower_case}.${packageName?lower_case}.${moduleName?lower_case}.${form.formName?lower_case}.client;

import static ${packagePrefix?lower_case}.${packageName?lower_case}.${moduleName?lower_case}.main.client.${moduleName}ClientConstant.*; 
 
import com.google.gwt.core.client.GWT;
import ${packagePrefix?lower_case}.${packageName?lower_case}.${moduleName?lower_case}.${form.formName?lower_case}.shared.${form.formName}Constant;
import ${packagePrefix?lower_case}.${packageName?lower_case}.${moduleName?lower_case}.${form.formName?lower_case}.shared.text.${form.formName}Text;
 
public class ${form.formName}ClientConstant extends ${form.formName}Constant {
 
  /**
   * Префикс для автоматической генерации web-ID полей. 
   */
  public static final String ${form.formName?upper_case}_FIELDS_AUTO_GENERATE_WEB_ID_PREFIX = "${form.formName?upper_case}_";
  
  public static ${form.formName}Text ${form.formName?uncap_first}Text = (${form.formName}Text) GWT.create(${form.formName}Text.class);
  
  <#list form.scopeModuleIds as moduleId><#if moduleId_index == 0>public static String[] scopeModuleIds = {${form.formName?upper_case}_MODULE_ID, </#if>${moduleId?upper_case}_MODULE_ID<#if moduleId_has_next>, </#if><#if (moduleId_index + 1) == form.scopeModuleIds?size>};</#if></#list>
}
