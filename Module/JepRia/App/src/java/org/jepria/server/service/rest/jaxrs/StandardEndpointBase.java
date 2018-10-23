package org.jepria.server.service.rest.jaxrs;

import java.util.function.Supplier;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import org.jepria.server.security.Credential;
import org.jepria.server.service.rest.ResourceController;
import org.jepria.server.service.rest.ResourceControllerImpl;
import org.jepria.server.service.rest.ResourceDescription;

import com.technology.jep.jepria.server.dao.JepDataStandard;

public abstract class StandardEndpointBase<D extends JepDataStandard> {

  /**
   * Provides application resource description
   * @return
   */
  protected abstract ResourceDescription<D> getResourceDescription();
  
  /**
   * Injectable field
   */
  @Context 
  protected HttpServletRequest request;
  
  /**
   * Get credential from the injected request
   * @return
   */
  protected Credential getCredential() {
    return new Credential() {
      @Override
      public int getOperatorId() {
        return 1;
        // TODO
//        return (int)request.getAttribute("org.jepria.auth.jwt.OperatorId");
      }
    };
  }
  
  /**
   * Supplier protects the internal field from direct access from within the class members,
   * and initializes the field lazily (due to the DI: the injectable fields are being injected after the object construction)
   */
  protected final Supplier<ResourceController> resourceController = new Supplier<ResourceController>() {
    private ResourceController instance = null;
    @Override
    public ResourceController get() {
      if (instance == null) {
        instance = createResourceController();
      }
      return instance;
    }
  };
  
  protected ResourceController createResourceController() {
    return new ResourceControllerImpl(getResourceDescription());
  }
}
