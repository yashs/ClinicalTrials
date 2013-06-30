package com.test.rest.pkg.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.test.rest.pkg.clinicaltrials.ClinicalTrials;
import com.test.rest.pkg.misc.UserInfo;
import com.test.rest.pkg.ws.DefaultParam;

public class PersistanceActions {


	public String userAuthenticate(Connection connection, String email, String pwd) throws Exception
	{
		try
		{
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM Users Where email='"+email+"'");
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				if(rs.getString("password").equals(pwd))
					if(!rs.getBoolean("user_status"))
						return "Please Activate Your Account";
					else{
								return "AUTHENTICATED";
					}
			}
		}
		catch(Exception e)
		{
			throw e;
		}
		return "Authentication Failed";
	}

	public void getDBRecords(Connection connection) throws Exception
	{
		try
		{
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM Users ORDER BY ssn DESC");
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				UserInfo feedObject = new UserInfo();
				feedObject.setId(rs.getString("ssn"));
				feedObject.setName(rs.getString("name"));
				feedObject.setDateOfBirth(rs.getString("dateofbirth"));
				feedObject.setGender(rs.getString("gender"));
				feedObject.setEmailID(rs.getString("email"));
				feedObject.setpassword(rs.getString("password"));
				DefaultParam.instance.getModel().put(feedObject.getId(),feedObject);    
			}
		}
		catch(Exception e)
		{
			throw e;
		}
	}

	public void setDBRecords(Connection connection, UserInfo perm)
	{
		try
		{
			String query="INSERT into Users values (?,?,?,?,?,?,?)";
			System.out.println(query);
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setInt(1,Integer.parseInt(perm.getId()));
			ps.setString(2,perm.getName());
			ps.setString(3,perm.getDateOfBirth());
			ps.setString(5,perm.getGender());
			ps.setString(4,perm.getEmailID());
			ps.setString(6,perm.getpassword());
			ps.setBoolean(7,false);
			ps.executeUpdate();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void setPrefs(Connection connection, List<String> perm, List<String> gender, List<String> status, String userEmail)
	{
		try
		{
			String query="INSERT into Preferences values (?,?,?,?)";
			System.out.println(query);
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1,userEmail);
			ps.setString(2,perm.toString());
			ps.setString(3,gender.toString());
			ps.setString(4,status.toString());
			ps.executeUpdate();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public List<ClinicalTrials> getTrialRecords(Connection connection, String query) throws Exception
	{
		if(query==null || query.equals(""))
			query = "SELECT * FROM clinical_trials ORDER BY trial_id ASC";
		
		List<ClinicalTrials> trials = new ArrayList<ClinicalTrials>();
		PreparedStatement ps = connection.prepareStatement(query);
		ResultSet rs = ps.executeQuery();
		while(rs.next())
		{
			ClinicalTrials trial = new ClinicalTrials();
			trial.setTrialId(rs.getString("trial_id"));
			trial.setBriefTitle(rs.getString("brief_title"));
			trial.setOfficialTitle(rs.getString("official_title"));
			trial.setSponsors(rs.getString("sponsors"));
			trial.setAuthority(rs.getString("authority"));
			trial.setStudyType(rs.getString("study_type"));
			trial.setStudyDesign(rs.getString("study_design"));
			trial.setSummary(rs.getString("summary"));
			trial.setStatus(rs.getString("status"));
			trial.setStDate(rs.getString("start_date"));
			trial.setEndDate(rs.getString("completion_date"));
			trial.setPhase(rs.getString("phase"));
			trial.setCriteria(rs.getString("criteria"));
			trial.setGender(rs.getString("gender"));
			trial.setMinAge(rs.getInt("min_age"));
			trial.setMaxAge(rs.getInt("max_age"));
			trial.setOfficialLastName(rs.getString("official_last_name"));
			trial.setOfficialRole(rs.getString("official_role"));
			trial.setOfficialAffiliation(rs.getString("official_affiliation"));
			trial.setRetDate(rs.getString("ret_date"));
			trial.setTags(rs.getString("tags"));
			trials.add(trial);
		}
		return trials;
	}


	public void setTrialRecords(Connection connection, ClinicalTrials trial)
	{
		try
		{
			String query="INSERT into clinical_trials values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1,trial.getTrialId());
			ps.setString(2,trial.getBriefTitle());
			ps.setString(3,trial.getOfficialTitle());
			ps.setString(4,trial.getSponsors());
			ps.setString(5,trial.getAuthority());
			ps.setString(6,trial.getStudyType());
			ps.setString(7,trial.getStudyDesign());
			ps.setString(8,trial.getSummary());
			ps.setString(9,trial.getStatus());
			ps.setString(10,trial.getStDate());
			ps.setString(11,trial.getEndDate());
			ps.setString(12,trial.getPhase());
			ps.setString(13,trial.getCriteria());
			ps.setString(14,trial.getGender());
			ps.setInt(15,trial.getMinAge());
			ps.setInt(16,trial.getMaxAge());
			ps.setString(17,trial.getOfficialLastName());
			ps.setString(18,trial.getOfficialRole());
			ps.setString(19,trial.getOfficialAffiliation());
			ps.setString(20,trial.getRetDate());
			ps.setString(21,trial.getTags());
			ps.executeUpdate();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public static boolean validateConfirmationLink(String hashCode) {
		// TODO Auto-generated method stub

		hashCode=hashCode.replace(" ", "+");
		String [] urlParams = hashCode.split(";");
		Database database= new Database();
		Connection connection;
		try {
			connection = database.Get_Connection();
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM users where name = '"+urlParams[0].split("\\[")[1]+"'");

			ResultSet rs = ps.executeQuery();

			while(rs.next()){

				String ssnDB = rs.getString("ssn");
				ssnDB = ssnDB.replace(" ", "+");
				System.out.println(hashCode+"\n"+urlParams[0]+"\n"+urlParams[1]+"\n"+Encrypt.encrypt(ssnDB));

				if(urlParams[1].split("]")[0].equals(Encrypt.encrypt(ssnDB))){
					String psString = "UPDATE users SET user_status = true where name = '"+urlParams[0].split("\\[")[1]+"'";
					ps = connection.prepareStatement(psString);
					ps.executeUpdate();
					return true;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public List<ClinicalTrials> getSearchedTrials(Connection connection, String status,
			String result, String studyType, String ageGroup, String phase1, String phaseII, String phaseIII,
			String phaseIV, String nih, String industry, String federal,
			String university, String tags) {
		
		boolean flag = false;
		List<ClinicalTrials> trials = new ArrayList<ClinicalTrials>();
		
		String query = "select * from clinical_trials where ";
		
		if(!status.equalsIgnoreCase("Open & Closed")){
			flag = true;
			if(status.equalsIgnoreCase("Open"))
				query = query + "status != 'Completed' AND ";
			else
				query = query + "status = 'Completed' AND ";
		}
		
		if(!studyType.equalsIgnoreCase("All Studies")){
			flag = true;
			if(studyType.equalsIgnoreCase("Interventional"))
				query = query + "study_type = 'Interventional' AND ";
			else if(studyType.contains("Observational"))
				query = query + "study_type = 'Observational' AND ";
			else
				query = query + "study_type = 'Expanded Access' AND ";
		}
		
		if(!ageGroup.equalsIgnoreCase("all")){
			flag = true;
			if(ageGroup.equalsIgnoreCase("Child"))
				query = query + "min_age > 0 AND max_age < 18 AND ";
			else if(ageGroup.contains("Adults"))
				query = query + "min_age > 17 AND max_age < 65 AND ";
			else
				query = query + "max_age > 64 AND ";
		}
		
		if (phase1.equalsIgnoreCase("Yes")){
			flag=true;
			query = query + "phase = 'Phase 1' AND ";
		}
		
		if (phaseII.equalsIgnoreCase("Yes")){
			flag=true;
			query = query + "phase = 'Phase 2' AND ";
		}
		
		if (phaseIII.equalsIgnoreCase("Yes")){
			flag=true;
			query = query + "phase = 'Phase 3' AND ";
		}
		
		if (phaseIV.equalsIgnoreCase("Yes")){
			flag=true;
			query = query + "phase = 'Phase 4' AND ";
		}
		
		if (nih.equalsIgnoreCase("Yes")){
			flag=true;
			query = query + "official_affiliation like '%NIH%' AND official_affiliation like '%nhlbi%' AND ";
		}
		
		if (university.equalsIgnoreCase("Yes")){
			flag=true;
			query = query + "official_affiliation like '%university%' AND ";
		}
		
		if (federal.equalsIgnoreCase("Yes")){
			flag=true;
			query = query + "official_affiliation like '%federal%' AND ";
		}
		
		if (industry.equalsIgnoreCase("Yes")){
			flag=true;
			query = query + "official_affiliation not like '%fed%' and official_affiliation not like '%university%' and official_affiliation not like '%nih%' and official_affiliation not like '%nhlbi%' AND ";
		}
		
		if(tags!=null){
			flag=true;
				if(!tags.equals(""))
					query = query + "tags like'%"+tags+"%' AND ";
		}
		if(flag)
			query = query.substring(0,query.length()-5);
		else
			query = query.substring(0,query.length()-8);
		
		System.out.println("This is the final query\n" + query);
		
		try {
			trials = getTrialRecords(connection, query);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return trials;
	}

}
