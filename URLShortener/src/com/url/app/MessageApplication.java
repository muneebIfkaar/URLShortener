package com.url.app;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import com.url.db.inmemory.UrlMap;
import com.url.rest.MessageRestService;
import com.url.rest.URLShortenerService;

public class MessageApplication extends Application {
	private Set<Object> singletons = new HashSet<Object>();

	public MessageApplication() {

		singletons.add(new MessageRestService());
		singletons.add(new URLShortenerService());
		
		try {
			UrlMap.loadMapEnteries();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}
}
