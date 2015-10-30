package com.technology.jep.jepriashowcase.auto;

import com.technology.jep.jepria.auto.entrance.ApplicationEntranceAppAuto;
import com.technology.jep.jepriashowcase.custom.auto.CustomAuto;
import com.technology.jep.jepriashowcase.custom.auto.CustomAutoImpl;
import com.technology.jep.jepriashowcase.goods.auto.GoodsAuto;
import com.technology.jep.jepriashowcase.goods.auto.GoodsAutoImpl;

public class JepRiaShowcaseAutoImpl extends ApplicationEntranceAppAuto implements JepRiaShowcaseAuto {
	private CustomAuto customAuto;
	private GoodsAuto goodsAuto;

	public JepRiaShowcaseAutoImpl(String baseUrl,
			String browserName,
			String browserVersion,
			String browserPlatform,
			String jepriaVersion,
			String username,
			String password) {
		super(baseUrl, browserName, browserVersion, browserPlatform, jepriaVersion, username, password);
		
		customAuto = new CustomAutoImpl<JepRiaShowcaseAuto>(this, new JepRiaShowcasePageManager());
		goodsAuto = getGoodsAuto(true);
	}
	
	@Override
	public void start(String baseUrl) {
		super.start(baseUrl);
		this.getCustomAuto().openMainPage(baseUrl); // TODO MainPage ?
	}


	@Override
	public CustomAuto getCustomAuto() {
		return customAuto;
	}

	@Override
	public GoodsAuto getGoodsAuto(boolean newInstance) {
		if(goodsAuto == null || newInstance) {
			goodsAuto = new GoodsAutoImpl<JepRiaShowcaseAuto, JepRiaShowcasePageManager>(this, new JepRiaShowcasePageManager());
		}
		return goodsAuto;
	}
}