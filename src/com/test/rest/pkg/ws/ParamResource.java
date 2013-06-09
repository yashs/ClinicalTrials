package com.test.rest.pkg.ws;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBElement;

import com.test.rest.pkg.misc.UserInfo;


public class ParamResource {
  @Context
  UriInfo uriInfo;
  @Context
  Request request;
  String id;
  public ParamResource(UriInfo uriInfo, Request request, String id) {
    this.uriInfo = uriInfo;
    this.request = request;
    this.id = id;
  }
  
  //Application integration     
  @GET
  @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
  public UserInfo getParameters() {
	UserInfo param = DefaultParam.instance.getModel().get(id);
    if(param==null)
      throw new RuntimeException("Get: Parameters with " + id +  " not found");
    return param;
  }
  
  // For the browser
  @GET
  @Produces(MediaType.TEXT_XML)
  public UserInfo getParametersHTML() {
	UserInfo param = DefaultParam.instance.getModel().get(id);
    if(param==null)
      throw new RuntimeException("Get: Parameters with " + id +  " not found");
    return param;
  }
  
  @PUT
  @Consumes(MediaType.APPLICATION_XML)
  public Response putParameters(JAXBElement<UserInfo> param) {
	UserInfo c = param.getValue();
    return putAndGetResponse(c);
  }
  
  @DELETE
  public void deleteParameters() {
	UserInfo c = DefaultParam.instance.getModel().remove(id);
    if(c==null)
      throw new RuntimeException("Delete: Parameters with " + id +  " not found");
  }
  
  private Response putAndGetResponse(UserInfo param) {
    Response res;
    if(DefaultParam.instance.getModel().containsKey(param.getId())) {
      res = Response.noContent().build();
    } else {
      res = Response.created(uriInfo.getAbsolutePath()).build();
    }
    DefaultParam.instance.getModel().put(param.getId(), param);
    return res;
  }
  
  

} 