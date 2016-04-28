package com.technology.jep.jepriatoolkit.creator.module;

import static com.technology.jep.jepriatoolkit.JepRiaToolkitConstant.*;
import static com.technology.jep.jepriatoolkit.util.JepRiaToolkitUtil.*;

import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;

public class ModuleDeclaration {

	private List<FieldDeclaration> fields = new ArrayList<FieldDeclaration>();
	private List<MethodDeclaration> methods = new ArrayList<MethodDeclaration>();
	private List<ConstructorDeclaration> constructors = new ArrayList<ConstructorDeclaration>();
	
	public List<FieldDeclaration> getFields() {
		return fields;
	}
	
	public List<MethodDeclaration> getMethods() {
		return methods;
	}
	
	public List<ConstructorDeclaration> getConstructors() {
		return constructors;
	}
	
	private String getFieldsAsString(){
		StringBuilder sb = new StringBuilder();
		for (FieldDeclaration field : fields){
			sb.append(sb.toString().isEmpty() ? "" : END_OF_LINE).append(field.toStringWithoutComments());
		}
		return sb.toString();
	}
	
	private String getMethodsAsString(){
		StringBuilder sb = new StringBuilder();
		for (MethodDeclaration method : methods){
			sb.append(sb.toString().isEmpty() ? "" : END_OF_LINE).append(method.toStringWithoutComments());
		}
		return sb.toString();
	}
	
	public String getBusinessLogic(){
		String fields = getFieldsAsString();
		if (!isEmpty(fields)){
			fields = multipleConcat(fields, END_OF_LINE);
		}
		return multipleConcat(fields, getMethodsAsString());
	}
}
