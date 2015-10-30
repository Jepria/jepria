package com.technology.jep.jepriashowcase.supplier.server.service;
 
import java.util.List;

import com.technology.jep.jepriashowcase.supplier.server.ejb.Supplier;
import com.technology.jep.jepriashowcase.supplier.shared.service.SupplierService;
import com.technology.jep.jepria.server.service.JepDataServiceServlet;
import com.technology.jep.jepria.server.util.JepServerUtil;
import com.technology.jep.jepria.shared.exceptions.ApplicationException;
import com.technology.jep.jepria.shared.field.option.JepOption;
import com.technology.jep.jepriashowcase.supplier.shared.record.SupplierRecordDefinition;

import static com.technology.jep.jepriashowcase.supplier.server.SupplierServerConstant.BEAN_JNDI_NAME;

import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
 
@RemoteServiceRelativePath("SupplierService")
public class SupplierServiceImpl extends JepDataServiceServlet implements SupplierService  {
 
	private static final long serialVersionUID = 1L;
 
	public SupplierServiceImpl() {
		super(SupplierRecordDefinition.instance, BEAN_JNDI_NAME);
	}

	@Override
	public List<JepOption> getBank(String bankBic, Integer maxRowCount) throws ApplicationException {
		List<JepOption> result = null;
		try {
			Supplier supplier = (Supplier)JepServerUtil.ejbLookup(ejbName);
			result = supplier.getBank(bankBic, maxRowCount);					
		} catch (Throwable th) {
			throw new ApplicationException(th.getLocalizedMessage(), th);
		}
		return result;
	}
}