package com.technology.jep.jepriashowcase.allshopgoods.server.ejb;
 
import static com.technology.jep.jepriashowcase.allshopgoods.server.AllShopGoodsServerConstant.DATA_SOURCE_JNDI_NAME;
import static com.technology.jep.jepriashowcase.allshopgoods.server.AllShopGoodsServerConstant.RESOURCE_BUNDLE_NAME;
import static com.technology.jep.jepriashowcase.allshopgoods.shared.field.AllShopGoodsFieldNames.*;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import oracle.j2ee.ejb.StatelessDeployment;
import com.technology.jep.jepria.server.ejb.JepDataStandardBean;
import com.technology.jep.jepria.shared.exceptions.ApplicationException;
import com.technology.jep.jepria.shared.record.JepRecord;
import com.technology.jep.jepria.shared.util.Mutable;
import com.technology.jep.jepriashowcase.allshopgoods.server.ejb.AllShopGoods;
import com.technology.jep.jepria.server.dao.ResultSetMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
 
@Local( { AllShopGoodsLocal.class })
@Remote( { AllShopGoodsRemote.class })
@StatelessDeployment
@Stateless
public class AllShopGoodsBean extends JepDataStandardBean implements AllShopGoods {
 
	public AllShopGoodsBean() {
		super(DATA_SOURCE_JNDI_NAME, RESOURCE_BUNDLE_NAME);
	}
 
	public List<JepRecord> find( JepRecord templateRecord, Mutable<Boolean> autoRefreshFlag, Integer maxRowCount, Integer operatorId) throws ApplicationException {
		String sqlQuery = 
			"begin  " 
			  +	"? := pkg_jepriashowcase.findShopGoods(" 
				  	+ "shopGoodsId => ? " 
				  	+ ", shopId => ? " 
				  	+ ", goodsId => ? " 
					+ ", maxRowCount => ? " 
					+ ", operatorId => ? " 
			  + ");"
		 + " end;";
		return super.find( sqlQuery,
				new ResultSetMapper<JepRecord>() {
					public void map(ResultSet rs, JepRecord record) throws SQLException {
						record.set(ALL_SHOP_GOODS_ID, getInteger(rs, SHOP_GOODS_ID));
						record.set(SHOP_ID, getInteger(rs, SHOP_ID));
						record.set(SHOP_NAME, rs.getString(SHOP_NAME));
						record.set(GOODS_ID, getInteger(rs, GOODS_ID));
						record.set(GOODS_NAME, rs.getString(GOODS_NAME));
					}
				}
				, templateRecord.get(ALL_SHOP_GOODS_ID)
				, templateRecord.get(SHOP_ID)
				, templateRecord.get(GOODS_ID)
				, maxRowCount 
				, operatorId);
	}
	public void delete(JepRecord record, Integer operatorId) throws ApplicationException {
		String sqlQuery = 
			"begin " 
			  + "pkg_jepriashowcase.deleteShopGoods(" 
				  	+ "goodsId => ? " 
					+ ", operatorId => ? " 
			  + ");"
		  + "end;";
		super.delete(sqlQuery 
				, record.get(GOODS_ID) 
				, operatorId);
	}
 
	public void update(JepRecord record, Integer operatorId) throws ApplicationException {
		String sqlQuery = 
			"begin " 
			+	"pkg_jepriashowcase.updateShopGoods(" 
				  	+ "shopGoodsId => ? " 
				  	+ ", goodsQuantity => ? " 
				  	+ ", sellPrice => ? " 
					+ ", operatorId => ? " 
			+ ");"
		 + "end;";
		super.update(sqlQuery 
				, record.get(SHOP_GOODS_ID)
				, record.get(GOODS_QUANTITY)
				, record.get(SELL_PRICE)
				, operatorId);
	}
 
	public Integer create(JepRecord record, Integer operatorId) throws ApplicationException {
		String sqlQuery = 
			"begin " 
			  + "? := pkg_jepriashowcase.createShopGoods(" 
				  	+ "shopId => ? " 
				  	+ ", goodsId => ? " 
				  	+ ", goodsQuantity => ? " 
				  	+ ", sellPrice => ? " 
					+ ", operatorId => ? " 
			  + ");"
			+ "end;";
		return super.create(sqlQuery, 
				Integer.class 
				, record.get(SHOP_ID)
				, record.get(GOODS_ID)
				, record.get(GOODS_QUANTITY)
				, record.get(SELL_PRICE)
				, operatorId);
	}
 
}