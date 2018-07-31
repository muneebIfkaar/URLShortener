package com.url.rest;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.spi.HttpResponse;
import org.slf4j.LoggerFactory;

import com.sun.glass.ui.View;
import com.url.request.UrlShortenerRequestBean;
import com.urls.concurrent.AsyncProcessingHandler;
import com.urls.db.URLBean;
import com.urls.enums.URLStatus;
import com.urls.model.ControllerTaskContext;
import com.urls.model.beans.StrategyResponseBean;
import com.urls.model.strategy.StrategyContext;
import com.urls.model.strategy.impl.LoadAllUrlStrategyImpl;
import com.urls.model.strategy.impl.LoadUrlStrategy;
import com.urls.model.strategy.impl.UrlAddStrategyImpl;

import ch.qos.logback.classic.Logger;

@Path("/url")
public class URLShortenerService {
	
	private final static Logger log = (Logger) LoggerFactory.getLogger(URLShortenerService.class);
	
	
	@GET
	@Path("/index")
	@Produces({MediaType.TEXT_HTML})
	public InputStream getIndexPage() throws Exception {
		
		ClassLoader loader = getClass().getClassLoader();
		File file1 = new File(loader.getResource("index.html").getFile());
		return new FileInputStream(file1);
		//return Response.seeOther(new URI("index.html")).build();
	}
	
	@GET
	@Path("/{url}")
	//@Consumes("application/json")
	//@Produces("application/json")
	public Response getJson(@PathParam("url") String url) throws URISyntaxException {
		
		StrategyContext context = new StrategyContext(new LoadUrlStrategy());
		
		ControllerTaskContext controller = new ControllerTaskContext(context, url);
		
		try {
			StrategyResponseBean response = (StrategyResponseBean) AsyncProcessingHandler.getProcessor(5, TimeUnit.SECONDS,
					controller);
			URLBean urlBean = (URLBean) response.getResponseData();
			if(urlBean.getStatus().equals(URLStatus.EXPIRED))
				return Response.status(response.getStatusCode()).entity("Url is Expired").build();
				
			return Response.seeOther(new URI(urlBean.getUrl())).build();// .status(response.getStatusCode()).entity(response).build();
		} catch (Exception e) {
			log.error("Error while processing User registration request. ErrorMessage: {}", e.getMessage(), e);
			Response.status(500).entity("Internal Server-Error: "+e.getMessage()).build();
		}
		
		
		return Response.status(200).entity("error").build();
	}
	
	@POST
	@Path("/short")
	@Consumes("application/json")
	@Produces("application/json")
	public Response shortUrl(UrlShortenerRequestBean req) throws URISyntaxException {
		
		StrategyContext context = new StrategyContext(new UrlAddStrategyImpl());
		
		ControllerTaskContext controller = new ControllerTaskContext(context, req.getUrl());
		
		try {
			StrategyResponseBean response = (StrategyResponseBean) AsyncProcessingHandler.getProcessor(5, TimeUnit.SECONDS,
					controller);
			return Response.status(response.getStatusCode()).entity(response).build();
		} catch (Exception e) {
			log.error("Error while processing User registration request. ErrorMessage: {}", e.getMessage(), e);
			Response.status(500).entity("Internal Server-Error: "+e.getMessage()).build();
		}
		
		
		return Response.status(200).entity("error").build();// status(200).entity(bean).build();
	}
	
	@GET
	@Path("/load/all/urls")
	@Produces("application/json")
	public Response getJson() throws URISyntaxException {
		
		StrategyContext context = new StrategyContext(new LoadAllUrlStrategyImpl());
		
		ControllerTaskContext controller = new ControllerTaskContext(context, null);
		
		try {
			StrategyResponseBean response = (StrategyResponseBean) AsyncProcessingHandler.getProcessor(5, TimeUnit.SECONDS,
					controller);
			return Response.status(response.getStatusCode()).entity(response).build();
		} catch (Exception e) {
			log.error("Error while processing User registration request. ErrorMessage: {}", e.getMessage(), e);
			Response.status(500).entity("Internal Server-Error: "+e.getMessage()).build();
		}
		
		
		return Response.status(200).entity("error").build();// status(200).entity(bean).build();
	}
	
}
