package com.test.rest.pkg.clinicaltrials;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement (name = "ClinicalTrials")
public class ClinicalTrials {

	private String trialId=null;
	private String briefTitle=null;
	private String officialTitle=null;
	private String sponsors=null;
	private String authority=null;
	private String studyType=null;
	private String studyDesign=null;
	private String summary=null;
	private String status=null;
	private String stDate=null;
	private String endDate=null;
	private String phase=null;
	private String criteria=null;
	private String gender=null;
	private int minAge=00;
	private int maxAge=00;
	private String officialLastName=null;
	private String officialRole=null;
	private String officialAffiliation=null;
	private String retDate=null;
	private String tags=null;

	public ClinicalTrials(){
		
	}
	
	public ClinicalTrials(String trialId, String briefTitle, String officialTitle,
			String sponsors, String authority, String studyType,
			String studyDesign, String summary, String status, String stDate,
			String endDate, String phase, String criteria, String gender,
			int minAge, int maxAge, String officialLastName,
			String officialRole, String officialAffiliation, String retDate, String tags) {
		super();
		this.trialId = trialId;
		this.briefTitle = briefTitle;
		this.officialTitle = officialTitle;
		this.sponsors = sponsors;
		this.authority = authority;
		this.studyType = studyType;
		this.studyDesign = studyDesign;
		this.summary = summary;
		this.status = status;
		this.stDate = stDate;
		this.endDate = endDate;
		this.phase = phase;
		this.criteria = criteria;
		this.gender = gender;
		this.minAge = minAge;
		this.maxAge = maxAge;
		this.officialLastName = officialLastName;
		this.officialRole = officialRole;
		this.officialAffiliation = officialAffiliation;
		this.retDate=retDate;
		this.tags=tags;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getTrialId() {
		return trialId;
	}

	public void setTrialId(String trialId) {
		this.trialId = trialId;
	}


	public String getBriefTitle() {
		return briefTitle;
	}


	public void setBriefTitle(String briefTitle) {
		this.briefTitle = briefTitle;
	}


	public String getOfficialTitle() {
		return officialTitle;
	}


	public void setOfficialTitle(String officialTitle) {
		this.officialTitle = officialTitle;
	}


	public String getSponsors() {
		return sponsors;
	}


	public void setSponsors(String sponsors) {
		this.sponsors = sponsors;
	}


	public String getAuthority() {
		return authority;
	}


	public void setAuthority(String authority) {
		this.authority = authority;
	}


	public String getStudyType() {
		return studyType;
	}


	public void setStudyType(String studyType) {
		this.studyType = studyType;
	}


	public String getStudyDesign() {
		return studyDesign;
	}


	public void setStudyDesign(String studyDesign) {
		this.studyDesign = studyDesign;
	}


	public String getSummary() {
		return summary;
	}


	public void setSummary(String summary) {
		this.summary = summary;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getStDate() {
		return stDate;
	}


	public void setStDate(String stDate) {
		this.stDate = stDate;
	}


	public String getEndDate() {
		return endDate;
	}


	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}


	public String getPhase() {
		return phase;
	}


	public void setPhase(String phase) {
		this.phase = phase;
	}


	public String getCriteria() {
		return criteria;
	}


	public void setCriteria(String criteria) {
		this.criteria = criteria;
	}


	public String getGender() {
		return gender;
	}


	public void setGender(String gender) {
		this.gender = gender;
	}


	public int getMinAge() {
		return minAge;
	}


	public void setMinAge(int minAge) {
		this.minAge = minAge;
	}


	public int getMaxAge() {
		return maxAge;
	}


	public void setMaxAge(int maxAge) {
		this.maxAge = maxAge;
	}


	public String getOfficialLastName() {
		return officialLastName;
	}


	public void setOfficialLastName(String officialLastName) {
		this.officialLastName = officialLastName;
	}


	public String getOfficialRole() {
		return officialRole;
	}


	public void setOfficialRole(String officialRole) {
		this.officialRole = officialRole;
	}


	public String getOfficialAffiliation() {
		return officialAffiliation;
	}


	public void setOfficialAffiliation(String officialAffiliation) {
		this.officialAffiliation = officialAffiliation;
	}

	public String getRetDate() {
		return retDate;
	}


	public void setRetDate(String retDate) {
		this.retDate = retDate;
	}
}
