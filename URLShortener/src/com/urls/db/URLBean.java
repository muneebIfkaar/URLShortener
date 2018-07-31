package com.urls.db;

import com.urls.enums.URLStatus;

public class URLBean {

	private int id;
	private String urlKey;
	private String url;
	private URLStatus status;
	private long creationDate;
	private long expireDate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUrlKey() {
		return urlKey;
	}

	public void setUrlKey(String urlKey) {
		this.urlKey = urlKey;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public URLStatus getStatus() {
		return status;
	}

	public void setStatus(URLStatus status) {
		this.status = status;
	}

	public long getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(long creationDate) {
		this.creationDate = creationDate;
	}

	public long getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(long expireDate) {
		this.expireDate = expireDate;
	}
	
	public URLBean copyUrlBean(URLBean bean) {
		
		URLBean copyBean = new URLBean();
		copyBean.setId(bean.getId());
		copyBean.setCreationDate(bean.getCreationDate());
		copyBean.setExpireDate(bean.getExpireDate());
		copyBean.setStatus(bean.getStatus());
		copyBean.setUrl(bean.getUrl());
		copyBean.setUrlKey(bean.getUrlKey());
		
		return copyBean;
	}

}
