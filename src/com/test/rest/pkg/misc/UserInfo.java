package com.test.rest.pkg.misc;


import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UserInfo {
	
  private String id=null;
  private String name=null;
  private String dateofbirth=null;
  private String gender=null;
  private String emailId=null;
  private String password=null;
  
  
  public UserInfo(){
    
  }
  public UserInfo (String id, String name, String dob, String gender, String email, String phone){
	    this.id = id;
	    this.name = name;
	    this.dateofbirth = dob;
	    this.gender= gender;
	    this.emailId = email;
	    this.password = phone;
	  }
  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getDateOfBirth() {
    return dateofbirth;
  }
  public void setDateOfBirth(String dateofbirth) {
    this.dateofbirth = dateofbirth;
  }
  
  public String getGender() {
	    return gender;
  }
  public void setGender(String gender) {
    this.gender = gender;
  }
  public String getEmailID() {
    return emailId;
  }
  public void setEmailID(String emailid) {
    this.emailId = emailid;
  }
  
  public String getpassword() {
	    return password;
  }
  public void setpassword(String password) {
    this.password = password;
  }
} 