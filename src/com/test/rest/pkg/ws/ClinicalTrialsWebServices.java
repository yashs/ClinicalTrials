package com.test.rest.pkg.ws;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import com.test.rest.pkg.clinicaltrials.ClinicalTrials;
import com.test.rest.pkg.database.Database;
import com.test.rest.pkg.database.Encrypt;
import com.test.rest.pkg.database.PersistanceActions;
import com.test.rest.pkg.misc.SendEmail;
import com.test.rest.pkg.misc.UserInfo;

@Path("/params")
public class ClinicalTrialsWebServices {

  // Allows to insert contextual objects into the class, 
  // e.g. ServletContext, Request, Response, UriInfo
  @Context
  UriInfo uriInfo;
  @Context
  Request request;


  // Return the list of parameters to the user in the browser
  @GET
  @Path("/xmlcount")
  @Produces(MediaType.TEXT_XML)
  public List<UserInfo> getParametersBrowser() {
    List<UserInfo> params = new ArrayList<UserInfo>();
    params.addAll(DefaultParam.instance.getModel().values());
    return params; 
  }
  
  // Return the list of todos for applications
  @GET
  @Path("xmlGet")
  @Produces({MediaType.APPLICATION_JSON})
  public List<UserInfo> getParameters() throws Exception {
    List<UserInfo> params = new ArrayList<UserInfo>();
    Database database= new Database();
    Connection connection = database.Get_Connection();
	PersistanceActions project= new PersistanceActions();
	project.getDBRecords(connection);
    params.addAll(DefaultParam.instance.getModel().values());	  	
    return params; 
  }
  
  @GET
  @Path("getTrials")
  @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
  public List<ClinicalTrials> getClinicalTrials() throws Exception {
    List<ClinicalTrials> trials = new ArrayList<ClinicalTrials>();
    Database database= new Database();
    Connection connection = database.Get_Connection();
	PersistanceActions project= new PersistanceActions();
	trials=project.getTrialRecords(connection);
    return trials; 
  }
  
  // to get the total number of records
  @GET
  @Path("/count")
  @Produces(MediaType.TEXT_PLAIN)
  public String getCount() {
    int count = DefaultParam.instance.getModel().size();
    return String.valueOf(count);
  }
  
  @GET
  @Path("/confirmRegistration")
  @Produces(MediaType.TEXT_PLAIN)
  public String confirmRegistration() {
   // MultivaluedMap<String,String> urlParameters = uriInfo.getQueryParameters();
    String hashCode = uriInfo.getQueryParameters().toString();
	boolean valid = PersistanceActions.validateConfirmationLink(hashCode); 
	if (valid) 
		return String.valueOf("Thanks for validating the email. Registraiton Successful.");
	else
		return String.valueOf("Could not Validate. Registration Failed.");
  }
  
  @POST
  @Path("/add")
  @Produces(MediaType.TEXT_HTML)
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  public void newParameters(@FormParam("id") String id,
	      @FormParam("name") String name,
	      @FormParam("dob") String dob,
	      @FormParam("email") String email,
	      @FormParam("pwd") String pwd,
	      @Context HttpServletResponse servletResponse) throws Exception {
	  	pwd = Encrypt.encrypt(pwd);
	  	System.out.println(name);
	  	name=name.replace(" ", "_");
	  	System.out.println(name);
	  	//System.out.println("This is the Encrypted Password:     "+pwd);
		UserInfo parm = new UserInfo(id,name,dob,email,"male",pwd);
		Database database= new Database();
	    Connection connection = database.Get_Connection();
		PersistanceActions project= new PersistanceActions();
		project.setDBRecords(connection,parm);

		SendEmail.send(email, "http://localhost:8080/ClinicalTrials/rest/params/confirmRegistration?hashCode="+name+";"+Encrypt.encrypt(id));
		
	    DefaultParam.instance.getModel().put(id, parm);    
	    try {
			servletResponse.sendRedirect("../../register.html");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }
  
  @POST
  @Path("/login")
  @Produces(MediaType.TEXT_HTML)
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  public String login(
	      @FormParam("email") String email,
	      @FormParam("pwd") String pwd,
	      @Context HttpServletResponse servletResponse) throws Exception {
	  	pwd = Encrypt.encrypt(pwd);		
		Database database= new Database();
	    Connection connection = database.Get_Connection();
		PersistanceActions project= new PersistanceActions();
		String isAuth = project.userAuthenticate(connection, email, pwd);
	    try {
			//servletResponse.sendRedirect("../../register.html");
	    	return isAuth;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	  }
  
  // Defines that the next path parameter after todos is
  // treated as a parameter and passed to the TodoResources
  // Allows to type http://localhost:8080/RestWS/rest/todos/1
  // 1 will be treaded as parameter todo and passed to TodoResource
  @Path("{todo}")
  public ParamResource getParameters(@PathParam("todo") String id) {
    return new ParamResource(uriInfo, request, id);
  }
  
} 