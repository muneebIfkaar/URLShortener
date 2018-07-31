package com.urls.model.strategy.impl;

import java.util.Calendar;

import org.slf4j.LoggerFactory;

import com.urls.db.URLBean;
import com.urls.db.inmemory.UrlMap;
import com.urls.enums.URLStatus;
import com.urls.model.beans.StrategyResponseBean;
import com.urls.model.commons.CommonsUtils;
import com.urls.model.strategy.interfaces.IStrategy;

import ch.qos.logback.classic.Logger;

public class UrlAddStrategyImpl implements IStrategy {

	private final static Logger log = (Logger) LoggerFactory.getLogger(UrlAddStrategyImpl.class);
	
	public StrategyResponseBean execute(Object obj) {

		String url = (String) obj;
		URLBean bean = null;
		try {
			
			if(UrlMap.hasValue(url)) {
				bean = UrlMap.getUrlByValue(url);
				if(bean.getStatus().equals(URLStatus.EXPIRED)) {
					Calendar cal = Calendar.getInstance();
					cal.add(Calendar.YEAR, 1);
					bean.setExpireDate(cal.getTimeInMillis());
					
					bean.setStatus(URLStatus.ACTIVE);
					
					UrlMap.updateUrl(bean);
				}
				bean = bean.copyUrlBean(bean);
				bean.setUrlKey(CommonsUtils.URL_PREFIX+bean.getUrlKey());
				return new StrategyResponseBean(200, "Urls Successfully Added", bean);
			}
			
			bean = parseBean(url);
			bean = UrlMap.addUrl(bean);
			
		} catch (Exception e) {
			log.error("Error while inserting. Error-Msg: {}", e.getMessage(), e);
			new StrategyResponseBean(500, "ERROR: Internal-server Error. " + e.getMessage(), null);
		}
		bean = bean.copyUrlBean(bean);
		bean.setUrlKey(CommonsUtils.URL_PREFIX+bean.getUrlKey());
		return	new StrategyResponseBean(200, "Urls Successfully Added", bean);
	}
	
	private URLBean parseBean(String url) {
		
		URLBean bean = new URLBean();
		
		bean.setUrl(url);
		bean.setStatus(URLStatus.ACTIVE);
		bean.setCreationDate(Calendar.getInstance().getTimeInMillis());
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, 1);
		
		bean.setExpireDate(cal.getTimeInMillis());
		
		
		return bean;
	} 

}
