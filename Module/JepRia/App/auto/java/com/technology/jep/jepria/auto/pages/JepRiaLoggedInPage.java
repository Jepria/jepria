package com.technology.jep.jepria.auto.pages;

import static com.technology.jep.jepria.auto.util.WebDriverFactory.getWait;
import static com.technology.jep.jepria.client.JepRiaAutomationConstant.LOGGED_IN_USER_ID;
import static com.technology.jep.jepria.client.JepRiaAutomationConstant.LOGOUT_BUTTON_ID;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Реализация некоторой (любой) страницы приложения, в которое был выполнен вход
 */
public class JepRiaLoggedInPage extends PlainPage implements LoggedInPage {
  private static Logger logger = Logger.getLogger(JepRiaLoggedInPage.class.getName());
    
  // TODO Разобраться с тем, что сейчас страница используется и для незалогиненных состояний приложения
  @FindBy(id = LOGGED_IN_USER_ID)
  private WebElement userName;

  @FindBy(id = LOGOUT_BUTTON_ID)
  private WebElement logoutButton;

  @Override
  public JepRiaLoggedInPage ensurePageLoaded() {
    super.ensurePageLoaded();
    
    getWait().until(visibilityOfElementLocated(By.id(LOGGED_IN_USER_ID)));
    
    return this;
  }
  
  @Override  
  public String getLoggedInUsername() {
    return userName.getText();
  }

  @Override
  public void clickLogoutButton() {
    logoutButton.click();
    logger.trace(this.getClass() + "clickLogoutButton()");
  }
  
}