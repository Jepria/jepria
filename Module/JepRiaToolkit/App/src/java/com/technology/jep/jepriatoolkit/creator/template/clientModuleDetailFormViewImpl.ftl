package com.technology.${packageName?lower_case}.${moduleName?lower_case}.${form.formName?lower_case}.client.ui.form.detail;
 
import static com.technology.${packageName?lower_case}.${moduleName?lower_case}.${form.formName?lower_case}.shared.field.${form.formName}FieldNames.*;
import static com.technology.${packageName?lower_case}.${moduleName?lower_case}.${form.formName?lower_case}.client.${form.formName}ClientConstant.${form.formName?uncap_first}Text;
<#assign import = "">
<#if !form.isDependent>
<#assign import = import + "com.technology.jep.jepria.client.widget.field.multistate.JepIntegerField">
import static com.technology.jep.jepria.shared.field.JepFieldNames.MAX_ROW_COUNT;
import com.technology.jep.jepria.client.widget.field.multistate.JepIntegerField;
</#if>
<#assign hasFieldMaxLength = false, hasBigDecimalNumberField = false>
<#list form.sortDetailFormFields as field>
<#if field.isBigDecimalNumberField>
<#assign hasBigDecimalNumberField = true>
</#if>
<#if field.fieldMaxLength??>
<#assign hasFieldMaxLength = true>
</#if>
<#if field.isDetailFormField>
<#if !import?contains(field.importString)>
<#assign import = import + field.importString>
import ${field.importString};
</#if>
</#if>
</#list>
<#if hasFieldMaxLength>
import com.technology.jep.jepria.client.widget.field.multistate.JepMultiStateField;
<#if !import?contains("com.technology.jep.jepria.client.widget.field.multistate.JepTextField")>
import com.technology.jep.jepria.client.widget.field.multistate.JepTextField;
</#if>
</#if>
<#if hasBigDecimalNumberField>
import java.math.BigDecimal;
<#if !import?contains("com.technology.jep.jepria.client.widget.field.multistate.JepNumberField")>
import com.technology.jep.jepria.client.widget.field.multistate.JepNumberField;
</#if>
</#if>
import com.technology.jep.jepria.client.ui.form.detail.DetailFormViewImpl;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.technology.jep.jepria.client.widget.field.FieldManager;

public class ${form.formName}DetailFormViewImpl extends DetailFormViewImpl
	implements ${form.formName}DetailFormView { 
	
 	public ${form.formName}DetailFormViewImpl() {
		super(new FieldManager());
		
		ScrollPanel scrollPanel = new ScrollPanel();
		scrollPanel.setSize("100%", "100%");
		VerticalPanel panel = new VerticalPanel();
		panel.getElement().getStyle().setMarginTop(5, Unit.PX);
		scrollPanel.add(panel);
		
		<#list form.sortDetailFormFields as field>
		${field.fieldWidget} ${field.fieldName} = new ${field.fieldWidget}(${form.formName?uncap_first}Text.${form.formName?uncap_first}_detail_${field.fieldId?lower_case}()<#if field.isBigDecimalNumberField>, BigDecimal.class<#elseif field.isMaskedTextField>, "cccccccccc"</#if>);
		<#if field.fieldWidth??>
		${field.fieldName}.setFieldWidth(${field.fieldWidth});
		</#if><#rt>
		<#if field.labelWidth??>
		${field.fieldName}.setLabelWidth(${field.labelWidth});
		</#if><#rt>
		</#list>
		<#if !form.isDependent>
		JepIntegerField maxRowCountField = new JepIntegerField(${form.formName?uncap_first}Text.${form.formName?uncap_first}_detail_row_count());
		maxRowCountField.setMaxLength(4);
		maxRowCountField.setFieldWidth(55);
		<#if form.fieldLabelWidth??>
		maxRowCountField.setLabelWidth(${form.fieldLabelWidth});
		</#if>
		</#if>
		<#list form.sortDetailFormFields as field>
		panel.add(${field.fieldName});
		</#list>
		<#if !form.isDependent>
		panel.add(maxRowCountField);
		</#if>
		setWidget(scrollPanel);
		<#list form.sortDetailFormFields as field>
		fields.put(${field.fieldId?upper_case}, ${field.fieldName});
		</#list>
		<#if !form.isDependent>
		fields.put(MAX_ROW_COUNT, maxRowCountField);
		</#if>
		<#list form.sortDetailFormFields as field>
		<#if field.fieldMaxLength??>
		setFieldMaxLength(${field.fieldId}, ${field.fieldMaxLength});
		</#if>
		</#list>
	}
	
	<#if hasFieldMaxLength>
	public void setFieldMaxLength(String fieldId, Integer maxLength) {
		JepMultiStateField field = fields.get(fieldId);
		if(field instanceof JepTextField) {
			((JepTextField)field).setMaxLength(maxLength);
		}
	}
	</#if>
}