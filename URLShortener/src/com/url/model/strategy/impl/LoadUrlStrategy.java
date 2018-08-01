package com.url.model.strategy.impl;

import java.util.Calendar;

import org.slf4j.LoggerFactory;

import com.url.db.inmemory.UrlMap;
import com.url.enums.URLStatus;
import com.url.model.beans.StrategyResponseBean;
import com.url.model.beans.URLBean;
import com.url.model.commons.CommonsUtils;
import com.url.model.strategy.interfaces.IStrategy;

import ch.qos.logback.classic.Logger;

public class LoadUrlStrategy implements IStrategy {

	private final static Logger log = (Logger) LoggerFactory.getLogger(LoadUrlStrategy.class);
		
	public StrategyResponseBean execute(Object obj) {
		String urlKey = (String) obj;
		log.info(" Execiting LoadUrl Strategy. UrlKey: {} ", urlKey);
		URLBean bean;
		try {
		if(!UrlMap.hasUrl(urlKey)) {
			return new StrategyResponseBean(404, "URL Does not exist", null);
		}
		
		bean = UrlMap.getUrlByKey(urlKey);
		if(bean.getStatus().equals(URLStatus.EXPIRED))
			return new StrategyResponseBean(406, "Requested Url has been expired", bean);
		
		long currentDateLong = Calendar.getInstance().getTimeInMillis();
		
		if(bean.getExpireDate() < currentDateLong) {
			bean.setStatus(URLStatus.EXPIRED);
			UrlMap.updateUrl(bean);
			return new StrategyResponseBean(406, "Requested Url has been expired",bean);
		}
		
		
		
		}catch(Exception e) {
			log.error("loading Error. Error-Msg: {}",e.getMessage(),e);
			return new StrategyResponseBean(500, "Internal Server Error. "+e.getMessage(), null);
		}
		bean = bean.copyUrlBean(bean);
		bean.setUrlKey(CommonsUtils.URL_PREFIX+bean.getUrlKey());
		return new StrategyResponseBean(200, "loaded Successfully", bean);
	}

}
