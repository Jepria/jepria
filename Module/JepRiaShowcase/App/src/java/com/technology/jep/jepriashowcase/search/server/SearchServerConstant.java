package com.technology.jep.jepriashowcase.search.server;

import com.technology.jep.jepria.server.JepRiaServerConstant;
/**
 * Константы для Search.
 */
public class SearchServerConstant extends JepRiaServerConstant {
	/**
	 * JNDI-имя EJB модуля.
	 */
	public static final String BEAN_JNDI_NAME = "SearchBean";
	
	/**
	 * JNDI-имя источника данных модуля.
	 */
	public static final String DATA_SOURCE_JNDI_NAME = "jdbc/RFInfoDS";

	/**
	 * Идентификатор файла ресурсов.
	 */
	public static final String RESOURCE_BUNDLE_NAME = "com.technology.jep.jepriashowcase.search.shared.text.SearchText";
	
}