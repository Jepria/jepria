package com.technology.jep.jepriashowcase.auto;

import com.technology.jep.jepria.auto.entrance.EntranceAppAuto;
import com.technology.jep.jepria.auto.manager.JepRiaAuto;
import com.technology.jep.jepriashowcase.arsenic.auto.ArsenicAuto;
import com.technology.jep.jepriashowcase.custom.auto.CustomAuto;
import com.technology.jep.jepriashowcase.goods.auto.GoodsAuto;
import com.technology.jep.jepriashowcase.feature.auto.FeatureAuto;

public interface JepRiaShowcaseAuto extends JepRiaAuto, EntranceAppAuto {
	CustomAuto getCustomAuto();
	
	GoodsAuto getGoodsAuto(boolean newInstance);
	ArsenicAuto getArsenicAuto(boolean newInstance);
	FeatureAuto getFeatureAuto(boolean newInstance);
}
