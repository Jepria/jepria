package org.jepria.server.service;

import org.jepria.server.service.rest.ResourceBasicController;
import org.jepria.server.service.rest.ResourceSearchController;

import javax.servlet.http.HttpSession;
import java.util.function.Supplier;

public interface ServiceProvider {
  /**
   * @return сервис, воплощающий логику entity-операций (create, get-by-id, update, delete)
   */
  ResourceBasicController getEntityService();

  /**
   * @return сервис, воплощающий логику session-stateful поиска со страничным листанием и сортировкой
   */
  ResourceSearchController getSearchService(Supplier<HttpSession> session);
}
