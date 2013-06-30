package com.test.rest.pkg.clinicaltrials;

import java.io.File;
import java.sql.Connection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.test.rest.pkg.database.Database;
import com.test.rest.pkg.database.PersistanceActions;

public class ClinicalTrialsLoader {

	public static void main(String argv[]) {

		try {

			File fXmlFile = new File("/Users/yasshrivastava/Downloads/ClinicalTrials");
			File[] xmlFiles = fXmlFile.listFiles();
			int i=0;
			for(File file:xmlFiles){
				i++;
				String trialId=null;
				String briefTitle=null;
				String officialTitle=null;
				String sponsors=null;
				String authority=null;
				String studyType=null;
				String studyDesign=null;
				String summary=null;
				String status=null;
				String stDate=null;
				String endDate=null;
				String phase=null;
				String criteria=null;
				String gender=null;
				int minAge=00;
				int maxAge=00;
				String officialLastName=null;
				String officialRole=null;
				String officialAffiliation=null;
				String retDate= null;
				String tags = "";
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(file);

				doc.getDocumentElement().normalize();


				Node node = doc.getDocumentElement(); 

				if (node.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) node;
					try{
						//retDate = (eElement.getElementsByTagName("download_date").item(0).getTextContent()).split(" on ")[1].trim();
						NodeList keywords = eElement.getElementsByTagName("keyword");
						for(int j=0;j<keywords.getLength();j++){
							tags = tags+keywords.item(j).getTextContent()+" ";
						}
						if(tags.equals(""))
							tags = "NULL";
						System.out.println(tags);
					}catch(Exception e){
						tags="NA";
					}
					try{
						retDate = (eElement.getElementsByTagName("download_date").item(0).getTextContent()).split(" on ")[1].trim();
					}catch(Exception e){
						retDate="NA";
					}
					try{
						trialId = eElement.getElementsByTagName("nct_id").item(0).getTextContent();
					}catch(Exception e){
						trialId="NA";
					}
					try{
						briefTitle = eElement.getElementsByTagName("brief_title").item(0).getTextContent();
					}catch(Exception e){
						briefTitle ="NA";
					}
					try{
						officialTitle = eElement.getElementsByTagName("official_title").item(0).getTextContent();
					}catch(Exception e){
						officialTitle="NA";
					}
					try{
						sponsors = eElement.getElementsByTagName("agency").item(0).getTextContent();
					}catch(Exception e){
						sponsors="NA";
					}
					try{
						authority = eElement.getElementsByTagName("authority").item(0).getTextContent();
					}catch(Exception e){
						authority ="NA";
					}
					try{
						studyType = eElement.getElementsByTagName("study_type").item(0).getTextContent();
					}catch(Exception e){
						studyType ="NA";
					}
					try{
						studyDesign = eElement.getElementsByTagName("study_design").item(0).getTextContent();
					}catch(Exception e){
						studyDesign ="NA";
					}
					try{
						summary = ((Element) eElement.getElementsByTagName("brief_summary").item(0)).getElementsByTagName("textblock").item(0).getTextContent();
					}catch(Exception e){
						summary ="NA";
					}
					try{
						status = eElement.getElementsByTagName("overall_status").item(0).getTextContent();
					}catch(Exception e){
						status ="NA";
					}
					try{
						stDate = eElement.getElementsByTagName("start_date").item(0).getTextContent();
					}catch(Exception e){
						stDate ="NA";
					}
					try{
						endDate = eElement.getElementsByTagName("completion_date").item(0).getTextContent();
					}catch(Exception e){
						endDate ="NA";
					}
					try{
						phase = eElement.getElementsByTagName("phase").item(0).getTextContent();
					}catch(Exception e){
						phase ="NA";
					}
					try{
						criteria = ((Element) eElement.getElementsByTagName("criteria").item(0)).getElementsByTagName("textblock").item(0).getTextContent();
					}catch(Exception e){
						criteria="NA";
					}
					try{
						gender = eElement.getElementsByTagName("gender").item(0).getTextContent();
					}catch(Exception e){
						gender="NA";
					}
					try{
						minAge = Integer.parseInt(eElement.getElementsByTagName("minimum_age").item(0).getTextContent().split(" ")[0]);
					}catch(Exception e){
						minAge=00;
					}
					try{
						maxAge = Integer.parseInt(eElement.getElementsByTagName("maximum_age").item(0).getTextContent().split(" ")[0]);
					}catch(Exception e){
						maxAge =00;
					}
					try{
						officialLastName = eElement.getElementsByTagName("last_name").item(0).getTextContent();
					}catch(Exception e){
						officialLastName ="NA";
					}
					try{
						officialRole = eElement.getElementsByTagName("role").item(0).getTextContent();
					}catch(Exception e){
						officialRole ="NA";
					}
					try{
						officialAffiliation = eElement.getElementsByTagName("affiliation").item(0).getTextContent();
					}catch(Exception e){
						officialAffiliation ="NA";
					}
					ClinicalTrials record = new ClinicalTrials(trialId, briefTitle, officialTitle, sponsors, authority, studyType, studyDesign, 
							summary, status, stDate, endDate, phase, criteria, gender, minAge, maxAge, officialLastName, officialRole, officialAffiliation, retDate, tags);
					Database database= new Database();
					Connection connection = database.Get_Connection();
					PersistanceActions project= new PersistanceActions();
					project.setTrialRecords(connection, record);
				}
			}
			System.out.println("\n\n\nCOUNT:\t"+i);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}