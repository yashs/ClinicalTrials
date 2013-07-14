package com.test.rest.pkg.ws;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import com.sun.jersey.api.json.JSONWithPadding;
import com.test.rest.pkg.clinicaltrials.ClinicalTrials;
import com.test.rest.pkg.clinicaltrials.ClinicalTrialsLoader;
import com.test.rest.pkg.database.Database;
import com.test.rest.pkg.database.Encrypt;
import com.test.rest.pkg.database.PersistanceActions;
import com.test.rest.pkg.misc.SendEmail;
import com.test.rest.pkg.misc.UserInfo;

import com.google.gson.Gson;


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
	@Path("advSearchRes")
	@Produces({MediaType.APPLICATION_JSON})
	public List<ClinicalTrials> getSearchedTrials(@Context HttpServletResponse servletResponse,@Context HttpServletRequest servletRequest) throws Exception {
		List<ClinicalTrials> trials = new ArrayList<ClinicalTrials>();
		HttpSession session = servletRequest.getSession(true);
		trials = (List<ClinicalTrials>) session.getAttribute("advSearchedTrials");
		return trials; 
	}

	@POST
	@Path("advSearch")
	@Produces({MediaType.APPLICATION_JSON})
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public List<ClinicalTrials> populateSearchedTrials(@FormParam("status") String status,
			@FormParam("result") String result,
			@FormParam("studyType") String studyType,
			@FormParam("ageGroup") String ageGroup,
			@FormParam("Phase1") String phase1,
			@FormParam("PhaseII") String phaseII,
			@FormParam("PhaseIII") String phaseIII,
			@FormParam("PhaseIV") String phaseIV,
			@FormParam("NIH") String NIH,
			@FormParam("Industry") String industry,
			@FormParam("federal") String federal,
			@FormParam("University") String university,
			@FormParam("tags") String tags,
			@Context HttpServletResponse servletResponse,
			@Context HttpServletRequest servletRequest) throws Exception {

		List<ClinicalTrials> trials = new ArrayList<ClinicalTrials>();
		Database database= new Database();
		Connection connection = null;
		try{
			connection = database.Get_Connection();
			PersistanceActions project= new PersistanceActions();
			trials = project.getSearchedTrials(connection,status,result,studyType,ageGroup,phase1,phaseII,phaseIII,phaseIV,NIH,industry,federal,university,tags);

			HttpSession session = servletRequest.getSession(true);
			session.setAttribute("advSearchedTrials", trials);
			servletResponse.sendRedirect("../../getTrials.html");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(connection!=null)
				connection.close();
		}

		return trials; 
	}

	@POST
	@Path("basicSearch")
	@Produces({MediaType.APPLICATION_JSON})
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public List<ClinicalTrials> populateBasicSearchedTrials(@FormParam("searchString") String searchString,
			@Context HttpServletResponse servletResponse,
			@Context HttpServletRequest servletRequest) throws Exception {

		List<ClinicalTrials> trials = new ArrayList<ClinicalTrials>();
		Database database= new Database();
		Connection connection = null;
		try{
			connection = database.Get_Connection();
			PersistanceActions project= new PersistanceActions();
			trials = project.getSearchedTrials(connection,searchString);

			HttpSession session = servletRequest.getSession(true);
			session.setAttribute("advSearchedTrials", trials);

			servletResponse.sendRedirect("../../getTrials.html");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(connection!=null)
				connection.close();
		}

		return trials; 
	}

	@GET
	@Path("load")
	//@Produces({MediaType.APPLICATION_JSON})
	//@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void loadClinicalTrials(
			@Context HttpServletResponse servletResponse,
			@Context HttpServletRequest servletRequest) throws Exception {

		ClinicalTrialsLoader.loadTrials();

		try {
			servletResponse.sendRedirect("../../loginPage.html");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}


	@GET
	@Path("xmlGetJsonp")
	@Produces({"application/x-javascript"})
	public JSONWithPadding getParametersjsonp(@QueryParam("callback") String callback) throws Exception{
		List<UserInfo> params = new ArrayList<UserInfo>();
		Database database= new Database();
		Connection connection = null;
		try{
			connection = database.Get_Connection();
			PersistanceActions project= new PersistanceActions();

			project.getDBRecords(connection);
			params.addAll(DefaultParam.instance.getModel().values());
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(connection!=null)
				connection.close();
		}
		return new JSONWithPadding(params, callback);
		//return (JSONWithPadding) params;
	}

	@GET
	@Path("getTrials")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public List<ClinicalTrials> getClinicalTrials() throws Exception {
		List<ClinicalTrials> trials = new ArrayList<ClinicalTrials>();
		Database database= new Database();
		Connection connection = null;
		try{
			connection = database.Get_Connection();
			PersistanceActions project= new PersistanceActions();
			trials=project.getTrialRecords(connection,null);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(connection!=null)
				connection.close();
		}
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

		SendEmail.send(email, "http://clinictrials.cloudapp.net/ClinicalTrials/rest/params/confirmRegistration?hashCode="+name+";"+Encrypt.encrypt(id));

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
			@Context HttpServletResponse servletResponse,
			@Context HttpServletRequest servletRequest) throws Exception {
		pwd = Encrypt.encrypt(pwd);		
		Database database= new Database();
		Connection connection = null;
		try{
			connection = database.Get_Connection();
			PersistanceActions project= new PersistanceActions();
			String isAuth = project.userAuthenticate(connection, email, pwd);
			if (isAuth.equals("AUTHENTICATED") || isAuth.equals("Please Activate Your Account")){
				HttpSession session = servletRequest.getSession(true);
				session.setAttribute("user_email", email);
				servletResponse.sendRedirect("../../landing.html");
			}
			else
				servletResponse.sendRedirect("../../loginFailed.html");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(connection!=null)
				connection.close();
		}
		return null;
	}

	@POST
	@Path("/pref")
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String preferences(
			@FormParam("mcname") List<String> studyType,
			@FormParam("Gender") List<String> gender,
			@FormParam("Status") List<String> status,
			@Context HttpServletResponse servletResponse,
			@Context HttpServletRequest servletRequest) throws Exception {

		HttpSession session = servletRequest.getSession(true);
		Database database= new Database();
		Connection connection = null;
		try{
			connection = database.Get_Connection();
		PersistanceActions project= new PersistanceActions();
		System.out.println(session.getAttribute("user_email"));
		System.out.println(studyType.toString());

		project.setPrefs(connection, studyType, gender, status, session.getAttribute("user_email").toString());
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(connection!=null)
				connection.close();
		}
		return "Your Preferences have been set";
	}

	// Defines that the next path parameter after todos is
	// treated as a parameter and passed to the TodoResources
	// Allows to type http://clinictrials.cloudapp.net/RestWS/rest/todos/1
	// 1 will be treaded as parameter todo and passed to TodoResource
	@Path("{todo}")
	public ParamResource getParameters(@PathParam("todo") String id) {
		return new ParamResource(uriInfo, request, id);
	}

} 