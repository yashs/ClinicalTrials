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

	public List<ClinicalTrials> getTrialRecords(Connection connection) throws Exception
	{
		List<ClinicalTrials> trials = new ArrayList<ClinicalTrials>();
		PreparedStatement ps = connection.prepareStatement("SELECT * FROM clinical_trials ORDER BY trial_id ASC");
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
			trial.setMinAge(rs.getString("min_age"));
			trial.setMaxAge(rs.getString("max_age"));
			trial.setOfficialLastName(rs.getString("official_last_name"));
			trial.setOfficialRole(rs.getString("official_role"));
			trial.setOfficialAffiliation(rs.getString("official_affiliation"));
			trial.setRetDate(rs.getString("ret_date"));
			trials.add(trial);
		}
		return trials;
	}


	public void setTrialRecords(Connection connection, ClinicalTrials trial)
	{
		try
		{
			String query="INSERT into clinical_trials values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			System.out.println(query);
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
			ps.setString(15,trial.getMinAge());
			ps.setString(16,trial.getMaxAge());
			ps.setString(17,trial.getOfficialLastName());
			ps.setString(18,trial.getOfficialRole());
			ps.setString(19,trial.getOfficialAffiliation());
			ps.setString(20,trial.getRetDate());
			ps.executeUpdate();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public static boolean validate(String hashCode) {
		// TODO Auto-generated method stub

		String [] urlParams = hashCode.split(";");
//		System.out.println(urlParams[0].split("\\[")[1]+"\n"+urlParams[1].split("]")[0]);
//		System.out.println("SELECT * FROM users where name = '"+urlParams[0].split("\\[")[1]+"'");
		Database database= new Database();
		Connection connection;
		try {
			connection = database.Get_Connection();
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM users where name = '"+urlParams[0].split("\\[")[1]+"'");

			ResultSet rs = ps.executeQuery();

			while(rs.next()){

				String ssnDB = rs.getString("ssn");

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

}
