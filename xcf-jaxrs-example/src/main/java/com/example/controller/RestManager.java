package com.example.controller;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.model.Account;
import com.example.model.domain.Greeting;



@Produces(MediaType.APPLICATION_JSON)
public interface RestManager {
	
	@GET
	String root();
	
	@GET
	@Path("/ehlo/")
	String helloWorld();

	@POST
	@Path("/ehlo/{greeting}")
	String helloWorld(@PathParam("greeting") String string);
	
	@POST
	@Path("/get/account/{id}")
	Response getAccountById(@PathParam("id") String id);
	
	@POST
	@Path("/get/resources/account/{id}")
	Response getAccountByIdResources(@PathParam("id") String id); 
	
	@GET
	@Path("/get/accounts")
	Response getAllAccount();
	
	@POST
	@Path("/query")
	Response demoQueryParam(@DefaultValue("queryData") @QueryParam("queryparam") List<String> paramList);
	
	@POST
	@Path("/matrix/{id}")
	Response demoMatrixParam(@PathParam("id") String id,@MatrixParam("name") String name,@MatrixParam("county") 
							String country, @MatrixParam("zipcode") String zipCode);
	
	@POST
	@Path("/save/account")
	@Consumes(MediaType.APPLICATION_JSON)
	Response postObject(Account account);
	
	@GET
	@Path("/get/useragent")
	Response demoHttpHeader(@HeaderParam("user-agent") String userAgent);

	@GET
	@Path("/get/context/httpheader")
	Response demoContext(@Context HttpHeaders httpHeaders);
	
	@GET
	@Path("/resources/helper")
	Response demoResourceHelper();
	
	
	
	
}
