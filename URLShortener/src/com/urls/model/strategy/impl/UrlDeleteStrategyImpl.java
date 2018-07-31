package com.urls.model.strategy.impl;

import org.slf4j.LoggerFactory;

import com.urls.db.inmemory.UrlMap;
import com.urls.model.beans.StrategyResponseBean;
import com.urls.model.strategy.interfaces.IStrategy;

import ch.qos.logback.classic.Logger;

public class UrlDeleteStrategyImpl implements IStrategy {

	private final static Logger log = (Logger) LoggerFactory.getLogger(UrlDeleteStrategyImpl.class);

	public StrategyResponseBean execute(Object obj) {

		log.info("Executing Delete-Url startegy");

		String key = (String) obj;
		try {
			UrlMap.deleteUrl(key);
			new StrategyResponseBean(200, "Successfully Deleted Url", null);

		} catch (Exception e) {
			log.error("Error: while deleteing Url :{}, ErrorMsg: {}", key, e.getMessage(), e);
			new StrategyResponseBean(500, "ERROR: Internal-server Error. " + e.getMessage(), null);
		}
		return null;
	}

}
