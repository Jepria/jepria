package com.technology.jep.jepriashowcase.goods.server.service;
 
import static com.technology.jep.jepriashowcase.goods.server.GoodsServerConstant.BEAN_JNDI_NAME;
import static com.technology.jep.jepriashowcase.goods.server.GoodsServerConstant.DATA_SOURCE_JNDI_NAME;
import static com.technology.jep.jepriashowcase.goods.server.GoodsServerConstant.RESOURCE_BUNDLE_NAME;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.technology.jep.jepria.server.service.JepDataServiceServlet;
import com.technology.jep.jepria.server.util.JepServerUtil;
import com.technology.jep.jepria.shared.exceptions.ApplicationException;
import com.technology.jep.jepria.shared.field.option.JepOption;
import com.technology.jep.jepria.shared.util.DefaultComparator;
import com.technology.jep.jepriashowcase.goods.server.ejb.Goods;
import com.technology.jep.jepriashowcase.goods.shared.record.GoodsRecordDefinition;
import com.technology.jep.jepriashowcase.goods.shared.service.GoodsService;
 
@RemoteServiceRelativePath("GoodsService")
public class GoodsServiceImpl extends JepDataServiceServlet implements GoodsService  {
 
	private static final long serialVersionUID = 1L;
	
	/**
	 * Схема сравнения.
	 */
	protected Comparator<Object> comparator;
 
	public GoodsServiceImpl() {
		super(GoodsRecordDefinition.instance, BEAN_JNDI_NAME, DATA_SOURCE_JNDI_NAME, RESOURCE_BUNDLE_NAME);
		this.comparator = DefaultComparator.instance;
	}
	
	public List<JepOption> getGoodsType() throws ApplicationException {
		List<JepOption> result = null;
		try {
			Goods goods = (Goods) JepServerUtil.ejbLookup(ejbName);
			result = goods.getGoodsType();
		} catch (Throwable th) {
			throw new ApplicationException(th.getLocalizedMessage(), th);
		}
		return result;
	}
	
	public List<JepOption> getUnit() throws ApplicationException {
		List<JepOption> result = null;
		try {
			Goods goods = (Goods) JepServerUtil.ejbLookup(ejbName);
			result = goods.getUnit();
			// To sort our list we should use comparator with some business logic
			// in compare method
			Collections.sort(result, new Comparator<JepOption>() {
				@Override
				public int compare(JepOption o1, JepOption o2) {
					// In that case we sort list according to name option
					return comparator.compare(o1.getName(), o2.getName());
				}
			});
		} catch (Throwable th) {
			throw new ApplicationException(th.getLocalizedMessage(), th);
		}
		return result;
	}
	
	public List<JepOption> getMotivationType() throws ApplicationException {
		List<JepOption> result = null;
		try {
			Goods goods = (Goods) JepServerUtil.ejbLookup(ejbName);
			result = goods.getMotivationType();
		} catch (Throwable th) {
			throw new ApplicationException(th.getLocalizedMessage(), th);
		}
		return result;
	}
	
	public List<JepOption> getGoodsCatalog(Integer parentGoodsCatalogId, Integer goodsId) throws ApplicationException {
		List<JepOption> result = null;

		try {
			Goods goods = (Goods) JepServerUtil.ejbLookup(ejbName);
			result = goods.getGoodsCatalog(parentGoodsCatalogId, goodsId);
		} catch (Throwable th) {
			throw new ApplicationException(th.getLocalizedMessage(), th);
		}
		return result;
	}
	
	public List<JepOption> getGoodsSegment() throws ApplicationException {
		List<JepOption> result = null;

		try {
			Goods goods = (Goods) JepServerUtil.ejbLookup(ejbName);
			result = goods.getGoodsSegment();
		} catch (Throwable th) {
			throw new ApplicationException(th.getLocalizedMessage(), th);
		}
		return result;
	}
}