# EnvironmentPropertySupport: настройка переменных окружения

*Статья-руководство по настройке переменных окружения для приложений*
### Подход
Гибкий и универсальный подход к настройке переменных окружения предусматривает несколько *уровней размещения* переменных окружения (в порядке приоритета):
* [Контекстные переменные Томката](https://tomcat.apache.org/tomcat-7.0-doc/config/context.html#Environment_Entries) —
	к ним имеют доступ только приложения, развёрнутые на данном экземпляре. Подход родной для Томката, но имеющий слишком узкую область действия.
* Параметры-опции запуска `JVM` —
	к ним тоже имеют доступ только приложения, развёрнутые на конкретном экземпляре. Подход  родной для `java`, но неудобный на практике.
* Системные переменные окружения —
	к ним имеют доступ приложения на всех экземплярах внутри системы.
* Внешние конфигурационные файлы —
	имеют гибкую настройку, широкую область действия и позволяют не загромождать служебные ресурсы.
* Внутренние настройки приложения — 
	к ним имеет доступ только само приложение, пригодны только для хранения настроек по умолчанию.

### Выбор уровня размещения настройки
* Если настройка должна действовать только для приложений, развёрнутых на одном экземпляре Томката, её следует объявить среди контекстных переменных Томката, в файле `conf/context.xml`:
	```
	<Context>
		...
		<Environment name="name" value="value" type="java.lang.String" override="true"/>
	</Context>
	```
	Для исключения разночтения типов и для поддержания единого стандарта, используется только **`type="java.lang.String"`**.
	Все приложения экземпляра будут получать значение `value` по запросу переменной с именем `name`.
	
	* Если значения одноимённой настройки должны различаться для разных приложений, используется *контекстное имя* настройки:
		```
		<Context>
			...
			<Environment name="name" value="value" type="java.lang.String" override="true"/>
			<Environment name="foo/name" value="value-foo" type="java.lang.String" override="true"/>
		</Context>
		```
		Приложение `/foo` будет получать значение `value-foo` по запросу переменной с именем `name`, все остальные приложения экземпляра будут получать значение `value` по запросу переменной с именем `name`.
		
	* Если настроек много, можно вынести их во внешний `.properties` файл, указав ссылку на файл в переменной с ключевым именем **`app-conf.file`** (можно создать по отдельному файлу для разных контекстов):
		```
		<Context>
			...
			<Environment name="app-conf.file" value="C:\conf\common.properties" type="java.lang.String" override="true"/>
			<Environment name="foo/app-conf.file" value="C:\conf\foo.properties" type="java.lang.String" override="true"/>
		</Context>
		```
		Содержимое файла `C:\conf\common.properties`:
		```
		name=value
		```
		Содержимое файла `C:\conf\foo.properties`:
		```
		name=value-foo
		```
		Приложение `/foo` будет получать значение `value-foo` по запросу переменной с именем `name`, все остальные приложения экземпляра будут получать значение `value` по запросу переменной с именем `name`.
		
		Среди одноимённых настроек приоритет имеют определённые непосредственно в `context.xml`, по сравнению с теми, что вынесены в файл.
* Можно (но не предпочтительно) разместить настройки аналогичным образом среди опций запуска `JVM`:
	```
	start-tomcat
	-Dname=value
	-Dapp-conf.file=C:\conf\common.properties
	-Dfoo/app-conf.file=C:\conf\foo.properties
	```
	Значения настроек, размещённых здесь, обновляются только при перезапуске `JVM`.
	
	Среди одноимённых настроек приоритет имеют определённые среди контекстных переменных Томката, по сравнению с теми, что определены среди опций запуска `JVM`.
* Если настройка должна действовать для приложений всех экземпляров Томката в системе, её можно определить в системных переменных окружения (с аналогичной возможностью переопределения значений и вынесения настроек во внешний файл).
	
	Среди одноимённых настроек первый приоритет имеют настройки, определённые среди контекстных переменных Томката, второй приоритет — настройки, определённые среди опций запуска  `JVM`, и третий — настройки в системных переменных окружения.
	
* Если настройка должна действовать только для одного приложения, в качестве значения по умолчанию (при отсутствии переопределения на других уровнях), её следует поместить в файл с ключевым именем `app-conf.default.properties` в папку `/WEB-INF`. 

### Отказ от размещения настроек окружения:
* в `web.xml` сервера
* в `web.xml` приложения