package com.test.rest.pkg.ws;

import java.util.HashMap;

import com.test.rest.pkg.misc.UserInfo;



public enum DefaultParam {
  instance;
  
  public HashMap<String, UserInfo> contentProvider = new HashMap<String, UserInfo>();
  
  private DefaultParam() {
    
    UserInfo parm = new UserInfo();
    parm.setDateOfBirth("21/03/1988");
    parm.setEmailID("abc@emaildid1.com");
    parm.setGender("Male");
    parm.setpassword("4085953762");
    contentProvider.put("1", parm);
    parm = new UserInfo();
    parm.setDateOfBirth("26/12/2001");
    parm.setEmailID("pqr@emaildid1.com");
    parm.setGender("Female");
    parm.setpassword("4085839242");
    contentProvider.put("2", parm);
    
  }
  public HashMap<String, UserInfo> getModel(){
    return contentProvider;
  }
  
} 