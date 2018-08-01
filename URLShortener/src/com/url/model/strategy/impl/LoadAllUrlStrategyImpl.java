package com.url.model.strategy.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.LoggerFactory;

import com.url.db.inmemory.UrlMap;
import com.url.model.beans.StrategyResponseBean;
import com.url.model.beans.URLBean;
import com.url.model.commons.CommonsUtils;
import com.url.model.strategy.interfaces.IStrategy;

import ch.qos.logback.classic.Logger;

public class LoadAllUrlStrategyImpl implements IStrategy {

	private final static Logger log = (Logger) LoggerFactory.getLogger(LoadAllUrlStrategyImpl.class);
	
	public StrategyResponseBean execute(Object obj) {

		log.info("Executing LoadAll Url Strategy.");
		List<URLBean> copy = new ArrayList<>();
		
		for(URLBean bean : UrlMap.loadAllUrls()) {
			
			bean = bean.copyUrlBean(bean);
			bean.setUrlKey(CommonsUtils.URL_PREFIX+bean.getUrlKey());
			copy.add(bean);
		}
		log.debug("Total URL-Loaded. Size: {}", copy.size());

		return new StrategyResponseBean(200, "Urls Loaded Successfully", copy);
	}

}
