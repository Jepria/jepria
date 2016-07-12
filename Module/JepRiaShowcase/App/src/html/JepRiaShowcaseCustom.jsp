<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.technology.jep.jepriashowcase.custom.shared.ui.JepRiaShowcaseCustomPageElements"%>
<%@ page import="com.technology.jep.jepriashowcase.main.shared.JepRiaShowcaseConstant" %>

<html style="width: 100%; height: 100%;">
  <head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    
    <!--                                           -->
    <!-- Any title is fine                         -->
    <!--                                           -->
    <title>JepRiaShowcase Module</title>
    
    <!--                                           -->
    <!-- This script loads your compiled module.   -->
    <!-- If you add any GWT meta tags, they must   -->
    <!-- be added before this line.                 -->
    <!--                                           -->
    <script type="text/javascript" language="javascript" src="JepRiaShowcase/JepRiaShowcase.nocache.js"></script>
  </head>

  <!--                                           -->
  <!-- The body can have arbitrary html, or       -->
  <!-- you can leave the body empty if you want   -->
  <!-- to create a completely dynamic UI.         -->
  <!--                                           -->
  <body style="margin: 0px; padding: 0px; width: 100%; height: 100%;">
  
    <!-- OPTIONAL: include this if you want history support -->
    <iframe src="javascript:''" id="__gwt_historyFrame" tabIndex='-1' style="position: absolute; width: 0; height: 0; border: 0;"></iframe>
    
    <!-- RECOMMENDED if your web app will not function without JavaScript enabled -->
    <noscript>
      <div style="width: 22em; position: absolute; left: 50%; margin-left: -11em; color: red; background-color: white; border: 1px solid red; padding: 4px; font-family: sans-serif">
        Your web browser must have JavaScript enabled
        in order for this application to display correctly.
      </div>
    </noscript>
        <div id="testBuildMessage" class="jepRia-testBuildMessage"> 
            <div class="jepRia-testBuildMessageNotification error"> 
                <div class="jepRia-testBuildMessageClose" onclick="document.getElementById('testBuildMessage').style.display = 'none';">
                    X
                </div> 
                <div class="jepRia-testBuildMessageHeader">
                    Attention please!
                </div> 
                <div class="jepRia-testBuildMessageInfo">
                    This is test build!
                </div> 
            </div> 
        </div> 
    
    <div id="loadingProgress" class="jepRia-loadingProgress">
      <div class="jepRia-loadingIndicator">
        <img src="images/loading.gif" width="32" height="32" alt="Loading..."/>
          <div>
            <p>
              <span id="loadingHeader">JepRiaShowcase</span>
            </p>
          <span id="loadingMessage" class="jepRia-loadingMessage">Loading&nbsp;Application,&nbsp;please&nbsp;wait...</span>
        </div>
      </div>
    </div>
    
    <table style="border: 0px; table-layout: fixed; border-collapse: collapse; margin: 0px; padding: 0px; width: 100%; height: 100%;">
      <colgroup>
        <col style="width: 10%; ">
        <col style="width: 70%; ">
        <col style="width: 20%; ">
      </colgroup>
      <tr>
        <td colspan="3" style="height: 10%; padding: 5px;">
          <table style="width: 100%; height: 100%;">
            <colgroup>
              <col style="width: 200px; ">
              <col>
              <col style="width: 200px; ">
            </colgroup>
            <tr>
              <td style="height: 100%; text-align: left;">
                <div id="<%= JepRiaShowcaseCustomPageElements.SEARCH_ELEMENT %>" style="width: 100%; height: 100%; position: relative;"></div>
              </td>
              <td style="height: 100%; text-align: left;">
                <div style="width: 100%; height: 100%; position: relative;"></div>
              </td>
              <td style="height: 100%; text-align: right;">
                <div id="<%= JepRiaShowcaseCustomPageElements.FULL_SCREEN_ELEMENT %>" style="width: 100%; height: 100%; position: relative;"></div>
              </td>
            </tr>
          </table>
        </td>
      </tr>
      <tr>
        <td style="height: 70%;">
          <div id="<%= JepRiaShowcaseCustomPageElements.TOP_ELEMENT %>" style="width: 100%; height: 100%; position: relative;"></div>
        </td>
        <td style="height: 70%;">
          <table style="width: 100%; height: 100%;">
            <tr>
              <td style="height: 30px; text-align: right;">
                <div id="<%= JepRiaShowcaseCustomPageElements.EMBEDDED_ELEMENT %>" style="width: 100%; height: 100%; position: relative;"></div>
              </td>
            </tr>
            <tr>
              <td style="vertical-align: top; padding: 5px;">
                Модуль с Gwt-компонентами встроенными в произвольную Html-верстку.<br/>
                Привязка компонента в верстку осуществляется по <i>id</i>&nbsp;&nbsp;Html-элемента.<br/>
                <br/>
                Данный модуль - пример "привязки" именно Gwt-<b><u>компонентов</u></b> к произвольной Html-разметке.<br/>
                Пример встраивания <b><u>модулей</u></b> в Html-верстку можно посмотреть по 
                <a href="<%= JepRiaShowcaseConstant.URL_EMBEDDED %>">ссылке</a> (или по соответствующей кнопке).
              </td>
            </tr>
          </table>
        </td>
        <td style="height: 70%;">
          <div id="<%= JepRiaShowcaseCustomPageElements.RIGHT_ELEMENT %>" style="width: 100%; height: 100%; position: relative;"></div>
        </td>
      </tr>
      <tr>
        <td colspan="3" style="height: 20%; padding: 5px;">
          <table style="width: 100%; height: 100%;">
            <colgroup>
              <col style="width: 50%; ">
              <col style="width: 50%; ">
            </colgroup>
            <tr>
              <td style="height: 100%; text-align: left;">
                <div id="<%= JepRiaShowcaseCustomPageElements.CURRENT_USER_ELEMENT %>" style="width: 100%; height: 100%; position: relative;"></div>
              </td>
              <td style="height: 100%; text-align: right;">
                <div id="<%= JepRiaShowcaseCustomPageElements.BOTTOM_ELEMENT %>" style="width: 100%; height: 100%; position: relative;"></div>
              </td>
            </tr>
          </table>
        </td>
      </tr>
    </table>
    
    <!-- According to HTML5 Specification we can place link and style tags in any place inside <BODY> -->
    <!-- For that purpose we should use attribute 'property' -->
    <!-- It allows us to guarantee that all our styles will be applied in correct order without replacing GWT styles-->
    <link type="text/css" rel="stylesheet" property="stylesheet" href="css/JepRia.css" />
    <link type="text/css" rel="stylesheet" property="stylesheet" href="css/JepRiaShowcase.css" />
  </body>
</html>
