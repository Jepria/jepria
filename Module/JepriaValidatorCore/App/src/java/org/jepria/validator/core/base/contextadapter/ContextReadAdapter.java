package org.jepria.validator.core.base.contextadapter;

import java.util.List;

import org.jepria.validator.core.base.resource.Directory;
import org.jepria.validator.core.base.resource.PlainResource;

/**
 * Интерфейс-адаптер чтения ресурсов из внешнего контекста (интерфейс доступен в прикладных валидационных правилах).
 * <br><br>
 * <i>
 * <b>Конвенция:</b> Реализации данного интерфейса вместе с реализацией {@link ContextWriteAdapter} должны обеспечивать
 * <b>персистентность</b> дерева ресурсов в рамках валидации множества ресурсов одним валидационным правилом.
 * Это означает, что при валидации нескольких ресурсов одним правилом, доступный для чтения контекст в оном правиле 
 * должен быть одинаковым по составу независимо от валидируемого ресурса 
 * и от произведенных ранее этим же правилом трансформаций контекста.
 * Другими словами, коммит любых записей в контекст должен происходить после валидации всех ресурсов одним правилом.
 * <br><br>
 * Для повышения производительности, реализации могут кешировать ресурсы,
 * используя принцип персистентности валидационных правил (см. ниже).
 * </i>
 */
//TODO интерфейс содержит параллельные пары методов (с одинаковым смыслом) для директорий и ресурсов. Оптимизировать?
public interface ContextReadAdapter {
  /**
   * Получение <b>существующего</b> ресурса (не директории) из внешнего контекста, по заданному относительному пути.
   * <br>
   * 
   * @param pathName относительный контекстный путь
   * @return ресурс
   * 
   * @throws {@link org.jepria.validator.core.base.exception.ResourceDoesNotExistException} если такого ресурса не существует
   */
  PlainResource getPlainResource(String pathName);
  
  /**
   * Получение <b>существующей</b> директории из внешнего контекста, по заданному относительному пути.
   * <br>
   * 
   * @param pathName относительный контекстный путь
   * @return ресурс
   * 
   * @throws {@link org.jepria.validator.core.base.exception.ResourceDoesNotExistException} если такой директории не существует
   */
  Directory getDirectory(String pathName);
  
  /**
   * Проверяет, существует ли во внешнем контексте ресурс (не директория) с заданным относительным путём.
   * 
   * @param pathName относительный контекстный путь
   */
  boolean plainResourceExists(String pathName);
  
  /**
   * Проверяет, существует ли во внешнем контексте директория с заданным относительным путём.
   * 
   * @param pathName относительный контекстный путь
   */
  boolean directoryExists(String pathName);
  
  /**
   * Поиск ресурсов (не директорий) во внешнем контексте по имени (с расширением)
   * 
   * @param nameRegex регулярное выражение для имени ресурса (с расширением)
   * @return список найденных ресурсов или пустой список, если ресурсов не найдено
   */
  List<PlainResource> listPlainResourcesByName(String nameRegex);
  
  /**
   * Поиск ресурсов (не директорий) из внешнего контекста по относительному пути (с именем и расширением)
   * 
   * @param pathNameRegex регулярное выражение для относительного контекстного пути ресурса (с именем и расширением)
   * @return список найденных ресурсов или пустой список, если ресурсов не найдено
   */
  List<PlainResource> listPlainResourcesByPathName(String pathNameRegex);
  
  /**
   * Поиск директорий во внешнем контексте по имени
   * 
   * @param nameRegex регулярное выражение для имени директории
   * @return список найденных директорий или пустой список, если директорий не найдено
   */
  List<Directory> listDirectoriesByName(String nameRegex);
  
  /**
   * Поиск ресурсов (не директорий) из внешнего контекста по относительному пути (с именем и расширением)
   * 
   * @param pathNameRegex регулярное выражение для относительного контекстного пути директории (с именем)
   * @return список найденных директорий или пустой список, если директорий не найдено
   */
  List<Directory> listDirectoriesByPathName(String pathNameRegex);
}