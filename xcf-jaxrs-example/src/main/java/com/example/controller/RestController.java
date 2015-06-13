package com.example.controller;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.jaxrs.JaxRsLinkBuilder;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.model.Account;
import com.example.model.domain.Greeting;
import com.example.service.BusinessService;

@Component
@Path("/")
public class RestController implements RestManager {
	
	@Autowired
	private BusinessService businessService;
	
	public RestController() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * {@link http://localhost:8080/xcf-jaxrs-example2/rest/api/}
	 * @return Response code
	 * 
	 * */
	@Override
	public String root() {
		return "rootPage";
	}
	
	/**
	 * {@link http://localhost:8080/xcf-jaxrs-example2/rest/api/ehlo/}
	 * @return Response code
	 * 
	 * */
	@Override
	public String helloWorld() {
		return "hello World";
	}
	
	/**
	 * {@link http://localhost:8080/xcf-jaxrs-example2/rest/api/ehlo/{greeting}}
	 * @return Response code
	 * 
	 * */
	@Override
	public String helloWorld(String string) {
		return businessService.ehloFromDao(string);
	}
	
	
	/**
	 * {@link http://localhost:8080/xcf-jaxrs-example2/rest/api/get/account/2}
	 * @return Response code
	 * 
	 * */
	@Override
	public Response getAccountById(String id) {
		Account account =  businessService.getAccountbyId(id);
		return Response.status(Status.OK).entity(account).build();
	}
	
	@Override
	public Response getAccountByIdResources(String id) {
		Account account =  businessService.getAccountbyId(id);
		
		Link link = JaxRsLinkBuilder.linkTo(RestController.class).withRel("/get/resources/account/{id}");
		List<Account> listAccount = new ArrayList<>();
		listAccount.add(account);
		Resources resources = new Resources<Account>(listAccount, link);
		
		return Response.status(Status.OK).entity(resources).build();
	}
	
	/**
	 * {@link http://localhost:8080/xcf-jaxrs-example2/rest/api/get/accounts}
	 * @return Response code
	 * 
	 * */
	@Override
	public Response getAllAccount() {
		List<Account> accounts =  new ArrayList<>();
		try{
			accounts = businessService.getAccounts();
			
		}catch(Exception ex){
			ex.printStackTrace();
			return Response.status(Status.EXPECTATION_FAILED).build();
		}
		
		return Response.status(Status.OK).entity(accounts).build();
	}

	/**
	 * {@link http://localhost:8080/xcf-jaxrs-example2/rest/api/query?queryparam=oncom&queryparam=combro&queryparam=misro&queryparam=tempe}
	 * 
	 * @return Response Object with Json string
	 * @param ?queryparam=value
	 * 
	 * 
	 * */
	@Override
	public Response demoQueryParam(List<String> paramList) {
		return Response.status(Status.OK).entity("Gorengan : "+paramList.toString()).build();
	}

	/**
	 * {@link http://localhost:8080/xcf-jaxrs-example2/rest/api/matrix/1;name=budi;country=indonesia;zipcode=16610}
	 * 
	 * @return Response Object with Json string
	 * @param
	 * 
	 * */
	@Override
	public Response demoMatrixParam(String id, String name, String country,String zipCode) {
		String stringParam = "id : "+id+ ", name : "+name +", country : "+country+ ", zipCode :"+zipCode;
		return Response.status(Status.OK).entity("address : "+stringParam).build();
	}

	/**
	 * {@link http://localhost:8080/xcf-jaxrs-example2/rest/api/save/account}
	 * 
	 * @return Response code
	 * @param {"accountNo" : "987654321", "balance":"900000", "lastPaidOn": "2015-05-28"}
	 * 
	 * */
	@Override
	public Response postObject(Account account) {
		try{

			businessService.create(account);
			
		}catch(Exception ex){
			ex.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		
		return Response.status(Status.ACCEPTED).build();
	}

	/**
	 * {@link http://localhost:8080/xcf-jaxrs-example2/rest/api/get/useragent}
	 * 
	 * @return Response code
	 * 
	 * */
	@Override
	public Response demoHttpHeader(String userAgent) {
		return Response.status(Status.OK).entity("userAgent : "+userAgent).build();
	}

	/**
	 * {@link http://localhost:8080/xcf-jaxrs-example2/rest/api/get/context/httpheader}
	 * 
	 * @return Response code
	 * 
	 * */
	@Override
	public Response demoContext(HttpHeaders httpHeaders) {
		String userAgent = httpHeaders.getHeaderString("user-agent");
		return Response.status(Status.OK).entity("userAgent : "+userAgent).build();
	}

	/**
	 * {@link http://localhost:8080/xcf-jaxrs-example2/rest/api/resources/helper}
	 * 
	 * @return Response code
	 * 
	 * */
	@Override
	public Response demoResourceHelper() {
		
		Link link = JaxRsLinkBuilder.linkTo(RestController.class).withRel("/resources/helper");

		Resources<Account> resources = null;
		List<Account> accounts = new ArrayList<>();
		try {
			
			accounts.add(businessService.getAccounts().get(1));		
			resources = new Resources<Account>(accounts, link);
		
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
		
		return Response.status(Status.OK).entity(resources).build();
	}
	
	@GET
	@Path("/greeting")
	public HttpEntity<Greeting> greeting(@DefaultValue("demoHateoas") @PathParam("msg") String hai){
		
		Link link = JaxRsLinkBuilder.linkTo(RestController.class).withRel("/greeting");
		
		Greeting greeting = new Greeting(hai);
//		greeting.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(RestController.class).greeting(hai)).withSelfRel());
		greeting.add(link);
		
		return new ResponseEntity<Greeting>(greeting, HttpStatus.OK);
	}
}
