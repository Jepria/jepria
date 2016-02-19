package com.technology.jep.jepriatoolkit.creator.module;

import static com.technology.jep.jepria.shared.field.JepTypeEnum.BIGDECIMAL;
import static com.technology.jep.jepria.shared.field.JepTypeEnum.BINARY_FILE;
import static com.technology.jep.jepria.shared.field.JepTypeEnum.BOOLEAN;
import static com.technology.jep.jepria.shared.field.JepTypeEnum.CLOB;
import static com.technology.jep.jepria.shared.field.JepTypeEnum.DATE;
import static com.technology.jep.jepria.shared.field.JepTypeEnum.DATE_TIME;
import static com.technology.jep.jepria.shared.field.JepTypeEnum.INTEGER;
import static com.technology.jep.jepria.shared.field.JepTypeEnum.TEXT_FILE;
import static com.technology.jep.jepria.shared.field.JepTypeEnum.TIME;
import static com.technology.jep.jepriatoolkit.util.JepRiaToolkitUtil.getAppropriateFieldType;
import static com.technology.jep.jepriatoolkit.util.JepRiaToolkitUtil.getFieldTypeAsEnum;
import static com.technology.jep.jepriatoolkit.util.JepRiaToolkitUtil.getOptionField;
import static com.technology.jep.jepriatoolkit.util.JepRiaToolkitUtil.getWorkStateAsString;
import static com.technology.jep.jepriatoolkit.util.JepRiaToolkitUtil.getWorkStates;
import static com.technology.jep.jepriatoolkit.util.JepRiaToolkitUtil.initCap;
import static com.technology.jep.jepriatoolkit.util.JepRiaToolkitUtil.isEditFormState;
import static com.technology.jep.jepriatoolkit.util.JepRiaToolkitUtil.isEmpty;
import static com.technology.jep.jepriatoolkit.util.JepRiaToolkitUtil.multipleConcat;
import static com.technology.jep.jepriatoolkit.util.JepRiaToolkitUtil.subtractFieldSuffix;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.technology.jep.jepria.client.ui.WorkstateEnum;
import com.technology.jep.jepriatoolkit.JepRiaToolkitConstant;
import com.technology.jep.jepriatoolkit.util.JepRiaToolkitUtil;

public class ModuleField implements JepRiaToolkitConstant {
	
	private String moduleId;
	private String fieldId;
	private String fieldType;
	private String fieldListFormName;
	private String fieldListFormNameEn;
	private String fieldDetailFormName;
	private String fieldDetailFormNameEn;
	private String fieldLike;
	private String fieldWidget;
	private String fieldMaxLength;
	private String fieldWidth;
	private String columnWidth;
	private String fieldHeight;
	private String labelWidth;
	private Integer listFormIndex;
	private Integer detailFormIndex;
	private boolean isEditable = true;
	private boolean isPrimaryKey = false;
	private boolean isListFormField = false;
	private boolean isDetailFormField = false;	
	private boolean isFindParameter = false;	
	private boolean isCreateParameter = false;	
	private boolean isUpdateParameter = false;
	private boolean isDeleteParameter = false;
	private boolean isGroupFormField = false;	
	private List<WorkstateEnum> visibleWorkStates;
	private List<WorkstateEnum> mandatoryWorkStates;
	private List<WorkstateEnum> editableWorkStates;
	private List<WorkstateEnum> enableWorkStates;
	
	public static final Map<String, String> FIELD_WIDGET = new HashMap<String, String>();
	static {
		// Список предопределенных полей и их соответствующих классов
		// (наименование с пакетом)
		FIELD_WIDGET.put(JEP_DATE_FIELD, multipleConcat(FIELD_PREFIX, JEP_DATE_FIELD));
		FIELD_WIDGET.put(JEP_MONEY_FIELD, multipleConcat(FIELD_PREFIX, JEP_MONEY_FIELD));
		FIELD_WIDGET.put(JEP_NUMBER_FIELD, multipleConcat(FIELD_PREFIX, JEP_NUMBER_FIELD));
		FIELD_WIDGET.put(JEP_INTEGER_FIELD, multipleConcat(FIELD_PREFIX, JEP_INTEGER_FIELD));
		FIELD_WIDGET.put(JEP_LONG_FIELD, multipleConcat(FIELD_PREFIX, JEP_LONG_FIELD));
		FIELD_WIDGET.put(JEP_TEXT_AREA_FIELD, multipleConcat(FIELD_PREFIX, JEP_TEXT_AREA_FIELD));
		FIELD_WIDGET.put(JEP_TEXT_FIELD, multipleConcat(FIELD_PREFIX, JEP_TEXT_FIELD));
		FIELD_WIDGET.put(JEP_MASKED_TEXT_FIELD, multipleConcat(FIELD_PREFIX, JEP_MASKED_TEXT_FIELD));
		FIELD_WIDGET.put(JEP_TIME_FIELD, multipleConcat(FIELD_PREFIX, JEP_TIME_FIELD));
		FIELD_WIDGET.put(JEP_FILE_FIELD, multipleConcat(FIELD_PREFIX, "large.", JEP_FILE_FIELD));
		FIELD_WIDGET.put(JEP_IMAGE_FIELD, multipleConcat(FIELD_PREFIX, "large.", JEP_IMAGE_FIELD));
		FIELD_WIDGET.put(JEP_CHECKBOX_FIELD, multipleConcat(FIELD_PREFIX, JEP_CHECKBOX_FIELD));
		FIELD_WIDGET.put(JEP_COMBOBOX_FIELD, multipleConcat(FIELD_PREFIX, JEP_COMBOBOX_FIELD));
		FIELD_WIDGET.put(JEP_LIST_FIELD, multipleConcat(FIELD_PREFIX, JEP_LIST_FIELD));
		FIELD_WIDGET.put(JEP_TREE_FIELD, multipleConcat(FIELD_PREFIX, JEP_TREE_FIELD));
		FIELD_WIDGET.put(JEP_DUAL_LIST_FIELD, multipleConcat(FIELD_PREFIX, JEP_DUAL_LIST_FIELD));
	}
	
	public ModuleField(){}
	
	public ModuleField(String moduleId, String fieldId, String fieldType, String fieldName, String fieldNameEn, String fieldLike, String fieldWidget, String fieldMaxLength, String fieldWidth, String labelWidth, String fieldHeight, String visibleWorkstates, String mandatoryWorkstates, String editableWorkstates, String enableWorkstates){
		setModuleId(moduleId);
		setFieldId(fieldId);
		setFieldType(fieldType);
		setFieldListFormName(fieldName);
		setFieldListFormNameEn(fieldNameEn);
		setFieldDetailFormName(fieldName);
		setFieldDetailFormNameEn(fieldNameEn);
		setFieldLike(fieldLike);
		setFieldWidget(fieldWidget);
		setFieldMaxLength(fieldMaxLength);
		setFieldWidth(fieldWidth);
		setColumnWidth(fieldWidth);
		setLabelWidth(labelWidth);
		setFieldHeight(fieldHeight);
		setVisibleWorkStates(visibleWorkstates);
		setMandatoryWorkStates(mandatoryWorkstates);
		setEditableWorkStates(editableWorkstates);
		setEnableWorkStates(enableWorkstates);
	}	
	public String getModuleId() {
		return moduleId;
	}
	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}
	public String getFieldId() {
		return fieldId;
	}
	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
	}
	public String getFieldIdAsParameter() {
		return initCap(JepRiaToolkitUtil.getFieldIdAsParameter(
				subtractFieldSuffix(fieldId), null));
	}
	public String getFieldIdWithPrefix(String prefix){
		return JepRiaToolkitUtil.getFieldIdAsParameter(fieldId, prefix);
	}
	public String getFieldIdWithPrefix(String id, String prefix){
		return JepRiaToolkitUtil.getFieldIdAsParameter(id, prefix);
	}
	public String getFieldName() {
		return multipleConcat(JepRiaToolkitUtil.getFieldIdAsParameter(fieldId, null),
				fieldWidget.substring(JEP_FIELD_PREFIX.length()));
	}
	public String getFieldType() {
		return isEmpty(fieldType) ? getAppropriateFieldType(fieldWidget) : fieldType;
	}
	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}
	public String getFieldListFormName() {
		return fieldListFormName;
	}
	public void setFieldListFormName(String fieldListFormName) {
		this.fieldListFormName = fieldListFormName;
	}
	public String getFieldListFormNameEn() {
		return fieldListFormNameEn;
	}
	public void setFieldListFormNameEn(String fieldListFormNameEn) {
		this.fieldListFormNameEn = fieldListFormNameEn;
	}
	public String getFieldDetailFormName() {
		return fieldDetailFormName;
	}
	public void setFieldDetailFormName(String fieldDetailFormName) {
		this.fieldDetailFormName = fieldDetailFormName;
	}
	public String getFieldDetailFormNameEn() {
		return fieldDetailFormNameEn;
	}
	public void setFieldDetailFormNameEn(String fieldDetailFormNameEn) {
		this.fieldDetailFormNameEn = fieldDetailFormNameEn;
	}
	public String getFieldLike() {
		return fieldLike;
	}
	public void setFieldLike(String fieldLike) {
		if (!isEmpty(fieldLike)){
			this.fieldLike = fieldLike;
		}
	}
	public String getFieldWidget() {
		return fieldWidget;
	}
	public void setFieldWidget(String fieldWidget) {
		this.fieldWidget = fieldWidget;
	}
	public boolean getIsPrimaryKey() {
		return isPrimaryKey;
	}
	public void setPrimaryKey(boolean isPrimaryKey) {
		this.isPrimaryKey = isPrimaryKey;
	}
	public boolean getIsListFormField() {
		return isListFormField;
	}
	public void setListFormField(boolean isListFormField) {
		this.isListFormField = isListFormField;
	}
	public boolean getIsDetailFormField() {
		return isDetailFormField;
	}
	public void setDetailFormField(boolean isDetailFormField) {
		this.isDetailFormField = isDetailFormField;
	}
	public boolean getIsFindParameter() {
		return isFindParameter;
	}
	public void setFindParameter(boolean isFindParameter) {
		this.isFindParameter = isFindParameter;
	}
	public boolean getIsCreateParameter() {
		return isCreateParameter;
	}
	public void setCreateParameter(boolean isCreateParameter) {
		this.isCreateParameter = isCreateParameter;
	}
	public boolean getIsUpdateParameter() {
		return isUpdateParameter;
	}
	public void setUpdateParameter(boolean isUpdateParameter) {
		this.isUpdateParameter = isUpdateParameter;
	}
	public boolean getIsDeleteParameter() {
		return isDeleteParameter;
	}
	public void setDeleteParameter(boolean isDeleteParameter) {
		this.isDeleteParameter = isDeleteParameter;
	}
	public List<WorkstateEnum> getVisibleWorkStates() {
		return visibleWorkStates;
	}	
	public void setVisibleWorkStates(String workstates) {
		visibleWorkStates = getWorkStates(workstates);
	}
	public List<WorkstateEnum> getMandatoryWorkStates() {
		return mandatoryWorkStates;
	}	
	public void setMandatoryWorkStates(String workstates) {
		mandatoryWorkStates = getWorkStates(workstates);
	}	
	public List<WorkstateEnum> getEditableWorkStates() {
		return editableWorkStates;
	}
	public void setEditableWorkStates(String workstates) {
		List<WorkstateEnum> editWorkStates = getWorkStates(workstates);
		if (!isEmpty(editWorkStates)){
			editableWorkStates = new ArrayList<WorkstateEnum>();
			for (WorkstateEnum workstate : editWorkStates){
				if (WorkstateEnum.isEditableState(workstate)){
					editableWorkStates.add(workstate);
				}
			}
		} 
	}	
	public List<WorkstateEnum> getEnableWorkStates() {
		return enableWorkStates;
	}
	public void setEnableWorkStates(String workstates) {
		List<WorkstateEnum> enabWorkStates = getWorkStates(workstates);
		if (!isEmpty(enabWorkStates)){
			enableWorkStates = new ArrayList<WorkstateEnum>();
			for (WorkstateEnum workstate : enabWorkStates){
				if (isEditFormState(workstate)){
					enableWorkStates.add(workstate);
				}
			}
		}
	}
	public boolean isGroupFormField() {
		return isGroupFormField;
	}
	public void setGroupFormField(boolean isGroupFormField) {
		this.isGroupFormField = isGroupFormField;
	}
	public String getFieldMaxLength() {
		return fieldMaxLength;
	}
	public void setFieldMaxLength(String fieldMaxLength) {
		if (!isEmpty(fieldMaxLength)) {
			this.fieldMaxLength = fieldMaxLength;
		}
	}
	public String getFieldWidth() {
		return fieldWidth;
	}
	public void setFieldWidth(String fieldWidth) {
		if (!isEmpty(fieldWidth)) {
			this.fieldWidth = fieldWidth;
		}
	}
	public String getColumnWidth() {
		return isEmpty(columnWidth) ? Integer.toString(150) : columnWidth;
	}
	public void setColumnWidth(String columnWidth) {
		this.columnWidth = columnWidth;
	}
	public String getLabelWidth() {
		return labelWidth;
	}
	public void setLabelWidth(String labelWidth) {
		if (!isEmpty(labelWidth)) {
			this.labelWidth = labelWidth;
		}
	}
	public boolean isCLOB(){
		return (isEmpty(fieldType) ? 
					(isEmpty(fieldWidget) ? false :
						JEP_FILE_FIELD.equalsIgnoreCase(fieldWidget)) : 
							(TEXT_FILE.name().equalsIgnoreCase(fieldType) ||
									CLOB.name().equalsIgnoreCase(fieldType)));
	}
	public boolean isBLOB(){
		return (isEmpty(fieldType) ? 
					(isEmpty(fieldWidget) ? false : 
						(JEP_FILE_FIELD.equalsIgnoreCase(fieldWidget) ||
							JEP_IMAGE_FIELD.equalsIgnoreCase(fieldWidget))) :
					BINARY_FILE.name().equalsIgnoreCase(fieldType));
	}
	public boolean getIsLOB(){
		return isCLOB() || isBLOB();
	}
	public boolean getIsComboBoxField(){
		return JEP_COMBOBOX_FIELD.equalsIgnoreCase(fieldWidget);
	}
	public boolean getIsOptionField(){
		return getIsComboBoxField()
				|| JEP_LIST_FIELD.equalsIgnoreCase(fieldWidget)
				|| JEP_DUAL_LIST_FIELD.equalsIgnoreCase(fieldWidget)
				|| JEP_TREE_FIELD.equalsIgnoreCase(fieldWidget);
	}
	public String getFieldHeight() {
		return fieldHeight;
	}
	public void setFieldHeight(String fieldHeight) {
		this.fieldHeight = fieldHeight;
	}
	public Integer getListFormIndex() {
		return listFormIndex;
	}
	public void setListFormIndex(Integer listFormIndex) {
		this.listFormIndex = listFormIndex;
	}
	public Integer getDetailFormIndex() {
		return detailFormIndex;
	}
	public void setDetailFormIndex(Integer detailFormIndex) {
		this.detailFormIndex = detailFormIndex;
	}
	public Integer getFormIndex(boolean isDetailForm){
		return isDetailForm ? 
				(getIsDetailFormField() ? getDetailFormIndex() : null) : 
				(getIsListFormField() ? getListFormIndex() : null);
	}
	public boolean getIsEditable() {
		return isEditable;
	}
	public void setIsEditable(boolean isEditable) {
		this.isEditable = isEditable;
	}
	private static final String orWithDoubleWhiteSpace = multipleConcat(WHITE_SPACE, OR, WHITE_SPACE);
	public String getVisibility(){
		if (isEmpty(visibleWorkStates)) return null;
		String visibleWorkstateOr = new String();
		for (WorkstateEnum state : visibleWorkStates) {
			visibleWorkstateOr += multipleConcat((isEmpty(visibleWorkstateOr) ? "" : orWithDoubleWhiteSpace),
					getWorkStateAsString(state), ".equals(workstate)");
		}
		return multipleConcat("fields.setFieldVisible(", fieldId, ", ", visibleWorkstateOr, ");", END_OF_LINE);
	}
	public String getMandatory(){
		if (isEmpty(mandatoryWorkStates)) return null;
		String mandatoryWorkstateOr = new String();
		for (WorkstateEnum state : mandatoryWorkStates) {
			mandatoryWorkstateOr += multipleConcat((isEmpty(mandatoryWorkstateOr) ? "" : orWithDoubleWhiteSpace),
					getWorkStateAsString(state), ".equals(workstate)");
		}
		return multipleConcat("fields.setFieldAllowBlank(", fieldId, ", !",
				(mandatoryWorkstateOr.contains(orWithDoubleWhiteSpace) ? "(" : ""), mandatoryWorkstateOr,
				(mandatoryWorkstateOr.contains(orWithDoubleWhiteSpace) ? ")" : ""), ");", END_OF_LINE);
	}
	public String getEditable(){
		if (getIsDetailFormField() && !getIsEditable()) {
			return multipleConcat("fields.setFieldEditable(", fieldId, ", false);", END_OF_LINE);
		}
		else {
			if (isEmpty(editableWorkStates)) return null;
			String editableWorkstateOr = new String();
			for (WorkstateEnum state : editableWorkStates){
				editableWorkstateOr += multipleConcat((isEmpty(editableWorkstateOr) ? "" : orWithDoubleWhiteSpace),
						getWorkStateAsString(state), ".equals(workstate)");
			}
			return multipleConcat("fields.setFieldEditable(", fieldId, ", ", editableWorkstateOr, ");", END_OF_LINE);
		}
	}
	public String getEnabled(){
		if (isEmpty(enableWorkStates)) return null;
		String enableWorkstateOr = new String();
		if (!isEmpty(enableWorkStates)) {
			for (WorkstateEnum state : enableWorkStates) {
				enableWorkstateOr += multipleConcat((isEmpty(enableWorkstateOr) ? "" : orWithDoubleWhiteSpace),
						getWorkStateAsString(state), ".equals(workstate)");
			}
			return multipleConcat("fields.setFieldEnabled(", fieldId, ", ", enableWorkstateOr, ");",  END_OF_LINE);
		}
		return null;
	}
	public String getHeight(){
		if (!isEmpty(fieldHeight)) {
			return multipleConcat("fields.setFieldHeight(", fieldId, ", ", fieldHeight, ");", END_OF_LINE);
		}
		return null;
	}
	public String getImportString(){
		return FIELD_WIDGET.get(fieldWidget);
	}
	public boolean getIsBigDecimalNumberField(){
		return JEP_NUMBER_FIELD.equalsIgnoreCase(fieldWidget) && BIGDECIMAL.name().equalsIgnoreCase(fieldType);
	}
	public boolean getIsMaskedTextField(){
		return JEP_MASKED_TEXT_FIELD.equalsIgnoreCase(fieldWidget);
	}
	public boolean getIsIntegerType(){
		return INTEGER.name().equalsIgnoreCase(fieldType);
	}
	public boolean getIsBigDecimalType(){
		return BIGDECIMAL.name().equalsIgnoreCase(fieldType);
	}
	public boolean getIsBooleanType(){
		return BOOLEAN.name().equalsIgnoreCase(fieldType);
	}
	public boolean getIsDateType(){
		return DATE.name().equalsIgnoreCase(fieldType);
	}
	public boolean getIsTimeType(){
		return TIME.name().equalsIgnoreCase(fieldType);
	}
	public boolean getIsDateTimeType(){
		return DATE_TIME.name().equalsIgnoreCase(fieldType);
	}
	public String getDisplayValueForComboBox(){
		return JepRiaToolkitUtil.getDisplayValueForComboBox(fieldId);
	}
	public String getResultSet(boolean hasFileNameField, String primaryKey){
		String resultSet = "null";
		switch (getFieldTypeAsEnum(fieldType)) {
			case STRING:
				resultSet = multipleConcat("rs.getString(", fieldId, ")");
				break;
			case INTEGER:
				resultSet = multipleConcat("getInteger(rs, ", fieldId, ")");
				break;
			case FLOAT:
				resultSet = multipleConcat("rs.getFloat(", fieldId, ")");
				break;
			case DOUBLE:
				resultSet = multipleConcat("rs.getDouble(", fieldId, ")");
				break;
			case BIGDECIMAL:
				resultSet = multipleConcat("rs.getBigDecimal(", fieldId, ")");
				break;
			case BOOLEAN:
				resultSet = multipleConcat("rs.getBoolean(", fieldId, ")");
				break;
			case DATE:
			case DATE_TIME:
				resultSet = multipleConcat("getDate(rs, ", fieldId, ")");
				break;
			case TIME:
				resultSet = multipleConcat("getTimestamp(rs, ", fieldId, ")");
				break;
			case OPTION:
				String optionNameFieldId = fieldId.endsWith("CODE") ? fieldId.replace("CODE", "NAME") : fieldId.replace("ID", "NAME"); 
				resultSet = multipleConcat("getOption(rs, ", fieldId, ", ", optionNameFieldId, ")");
				break;
			case BINARY_FILE:
			case TEXT_FILE:
			case CLOB:
				resultSet = multipleConcat(
						"getFileReference(rs, ",
							(hasFileNameField ? FILE_NAME_FIELD_ID : "null"),
								", ", primaryKey, ", ", EXTENSION_FIELD_ID, ", ", MIME_TYPE_FIELD_ID, ")");
				break;
			default:
				resultSet = multipleConcat("rs.getString(", fieldId, ")");
				break;
		}
		return resultSet;
	}
	
	public String getOption(){
		String bufferFieldId = new String(this.fieldId);
		this.fieldId = multipleConcat(getFieldIdAsParameter(), "Options.", fieldId);
		String result = getResultSet(false, null);
		this.fieldId = bufferFieldId;
		return result;
	}
	
	public boolean getEnabledOnViewDetails(boolean hasViewDetailsWS){
		return !isEmpty(visibleWorkStates) ? 
				visibleWorkStates.contains(WorkstateEnum.VIEW_DETAILS) : 
					isDetailFormField && hasViewDetailsWS;
	}
	
	public boolean getIsKeyOption(List<ModuleField> moduleFields, boolean hasViewDetailsWS){
		boolean isKeyOption = false;
		if (!getIsComboBoxField()) {
			for (ModuleField optionField : getOptionField(moduleFields)) {
				if (fieldId.equalsIgnoreCase(JepRiaToolkitUtil.getDisplayValueForComboBox(optionField.getFieldId())))
					isKeyOption = getIsListFormField() || getEnabledOnViewDetails(hasViewDetailsWS);
			}
		}
		return isKeyOption;
	}
	
	public String getParameter(boolean isFind){
		boolean isOptionField = getIsComboBoxField() ;
		boolean isOptionListField = getIsOptionField() && !isOptionField; 
		return 
			multipleConcat((isOptionField ? "JepOption.<String>getValue(" : ""),
					(isOptionListField ? "JepOption.getOptionValuesAsString((List<JepOption>)" : ""),
						(isFind ? "templateRecord" : "record"), ".get(", fieldId.toUpperCase(), ")", (isOptionField || isOptionListField ? ")" : ""));
	}
}