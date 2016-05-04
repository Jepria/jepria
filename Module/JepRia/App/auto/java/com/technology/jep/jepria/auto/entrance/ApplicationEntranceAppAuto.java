package com.technology.jep.jepria.auto.entrance;

import com.technology.jep.jepria.auto.entrance.pages.ApplicationEntrancePageManager;
import com.technology.jep.jepria.auto.manager.WDAutoAbstract;

public class ApplicationEntranceAppAuto extends WDAutoAbstract implements EntranceAppAuto {
	private EntranceAuto entranceAuto;
	
	public ApplicationEntranceAppAuto(String baseUrl,
			String browserName,
			String browserVersion,
			String browserPlatform,
			String browserPath,
			String jepriaVersion,
			String username,
			String password) {
		super(baseUrl, browserName, browserVersion, browserPlatform, browserPath, jepriaVersion, username, password);
		entranceAuto = new ApplicationEntranceAuto<EntranceAppAuto, ApplicationEntrancePageManager>(this, new ApplicationEntrancePageManager());
	}

	@Override
	public void start(String baseUrl) {
		super.start(baseUrl);
		getEntranceAuto().openMainPage(baseUrl);
	}
	

	@Override
    public EntranceAuto getEntranceAuto() {
        return entranceAuto;
    }
}
