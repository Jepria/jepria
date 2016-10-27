package com.technology.jep.jepria.auto.model.user.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import oracle.jdbc.pool.OracleDataSource;

import com.technology.jep.jepria.auto.model.user.User;
import com.technology.jep.jepria.server.dao.DaoSupport;
import com.technology.jep.jepria.shared.exceptions.ApplicationException;

/**
 * Класс получает из базы пользователя с нужными правами для теста. 
 */
public class UserDao implements UserData {
  
  /**
   * Конструктор. Создает UserDao.
   * 
   * @param dbURL URL from which connections have to be obtained.
   * @param dbUser User name with which connections have to be obtained.
   * @param dbPassword Password name with which connections have to be obtained.
   */
  public UserDao(String dbURL, String dbUser, String dbPassword) {
    
    this.dbURL = dbURL;
    this.dbUser = dbUser;
    this.dbPassword = dbPassword;
  }

  private String dbURL;
  private String dbUser;
  private String dbPassword;
  
  @Override
  public User createUser(String login, List<String> roleSNameList) throws Exception {
    
    Integer operatorId = getTestOperatorId(login, roleSNameList);
    
    if(operatorId == null){
      throw new ApplicationException("Can't create operator. OperatorId is null", null);
    }
    
    return User.fromDB(
        (Integer) operatorId,
        login,
        roleSNameList,
        login,
        login);
  }
  
  /**
   * Получает пользователя для теста. <br/>
   * После создания пароль и логин пользователя совпадают.
   * Имя оператора начинается с логина (подробнее см. реализацию DB).
   * 
   * @param login - Логин.
   * @param roleSNameList - Список ролей.
   * @return Id созданного оператора.
   * @throws Exception
   */
  private Integer getTestOperatorId(String login, List<String> roleSNameList) throws Exception {

    OracleDataSource ods = new OracleDataSource();
    ods.setURL(dbURL);
    ods.setUser(dbUser);
    ods.setPassword(dbPassword);
    Connection conn = ods.getConnection();

    Integer result = null;
    try {

      //Генерируем вопросы для параметризованного вызова cmn_string_table_t
      //Количество вопросов равно количеству ролей.
      StringBuilder subQuery = new StringBuilder();
      for(int i = 0; i < roleSNameList.size(); i++){
        subQuery.append("?,");
      }
      
      //Удаляем последнюю запятую.
      String cmnStringTableTSubQuery = subQuery.length() > 0 ? 
          subQuery.substring(0, subQuery.length() - 1) : "";

      String query = 
          "begin ? := pkg_AccessOperatorTest.getTestOperatorId("
              + "login => ?"
              + ", roleSNameList => cmn_string_table_t( " + cmnStringTableTSubQuery + " ) "
            + ");"
          + " end;";
      
      CallableStatement callableStatement = conn.prepareCall(query);

      //Устанавливаем тип выходного параметра
      callableStatement.registerOutParameter(1, Types.INTEGER);
      
      //Устанавливаем логин
      callableStatement.setString(2, login);
      
      //Устанавливаем роли
      DaoSupport.setInputParamsToStatement(callableStatement, 3, 
          roleSNameList.toArray(new String[roleSNameList.size()]));
      
      // Выполнение запроса.
      callableStatement.execute();
  
      //Получим набор.
      result = (Integer) callableStatement.getObject(1);
      if (callableStatement.wasNull()) result = null;
      
    } catch (Throwable th) {
      conn.rollback();
      throw new ApplicationException(th.getMessage(), th);
    } finally {
      try {
        conn.commit();
      } catch (SQLException e) {
        throw new ApplicationException(e.getMessage(), e);
      }
    }
    
    return result;
  }
}