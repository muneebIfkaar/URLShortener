package com.url.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.slf4j.LoggerFactory;

import com.url.request.UrlShortenerRequestBean;
import com.urls.model.strategy.impl.LoadAllUrlStrategyImpl;

import ch.qos.logback.classic.Logger;

//http://localhost:8080/RESTfulExample/rest/message/hello%20world
@Path("/message")
public class MessageRestService {

	private final static Logger log = (Logger) LoggerFactory.getLogger(MessageRestService.class);
	
	@GET
	@Path("/{param}")
	public Response printMessage(@PathParam("param") String msg) {

		String result = "Restful example : " + msg;

		return Response.status(200).entity(result).build();

	}
	
	

}