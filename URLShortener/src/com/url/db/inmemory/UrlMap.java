package com.url.db.inmemory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.url.db.URLDBImpl;
import com.url.model.beans.URLBean;

public class UrlMap {

	private static Map<String, URLBean> URL_MAP = new HashMap<String, URLBean>();
	private static URLDBImpl DB = new URLDBImpl();

	public static void loadMapEnteries() throws Exception {
		
		List<URLBean> list = DB.loadAllURl();
		for(URLBean bean : list) {
			URL_MAP.put(bean.getUrlKey(), bean);
		}
	}

	/**
	 * check if bean exists against provided URL
	 * 
	 * @param urlKey:
	 *            String
	 * @return
	 */
	public static boolean hasUrl(String url) {
		return URL_MAP.containsKey(url);
	}

	/**
	 * Will check if domain name of the site already exists.
	 * 
	 * @param url
	 * @return
	 */
	public static boolean hasValue(String url) {

		for (URLBean bean : URL_MAP.values()) {
			if (bean.getUrl().equals(url))
				return true;
		}
		return false;
	}

	public static URLBean getUrlByKey(String key) {

		return URL_MAP.get(key);
	}

	public static URLBean getUrlByValue(String urlValue) {

		for (URLBean bean : URL_MAP.values()) {
			if (bean.getUrl().equals(urlValue))
				return bean;
		}
		return null;
	}

	public static URLBean addUrl(URLBean bean) throws Exception {

		DB.insertUrl(bean);
		URL_MAP.put(bean.getUrlKey(), bean);
		return bean;
	}

	public static void updateUrl(URLBean bean) throws Exception {

		DB.updateUrl(bean);
		URL_MAP.put(bean.getUrlKey(), bean);

	}

	public static void deleteUrl(URLBean bean) throws Exception {
		DB.deleteUrl(bean);
		URL_MAP.remove(bean.getUrlKey());
	}
	
	public static void deleteUrl(String key) throws Exception {
		URLBean bean = URL_MAP.get(key);
		if(bean != null) {
			DB.deleteUrl(bean);
			URL_MAP.remove(bean.getUrlKey());
		}
	}

	/**
	 * this function will
	 *  check, if URl exists in map, if not then it cross
	 * verify from DB and return value
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static URLBean getUrl(String key) throws Exception {

		if (!URL_MAP.containsKey(key)) {
			URLBean bean = DB.loadUrl(key);
			if (bean != null) {
				URL_MAP.put(key, bean);
				return bean;
			} else
				return null;
		}

		return URL_MAP.get(key);

	}
	
	public static List<URLBean> loadAllUrls() {
		
		return new ArrayList<URLBean>(URL_MAP.values());
		
	}

}
