package com.owb.playhelp.server.utils;

import java.util.Properties;

import javax.mail.Message;
//import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
//import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.owb.playhelp.server.domain.DBRecord;
import com.owb.playhelp.server.domain.Orphanage;
import com.owb.playhelp.server.domain.Ngo;
import com.owb.playhelp.server.domain.Volunteer;


public class EmailHelper {

	  private static String systemEmail = "owb@orphanagewithoutborders.org";
	  private static String myEmail = "mvcharcos@orphanagewithoutborders.org";
	  private static String eol = System.getProperty("line.separator");  
	
	  public EmailHelper(){
		  
	  }
	  
	  static public String sendMail(String to, String subject, String message) {
	        String output=null;
	        Properties props = new Properties();
	        Session session = Session.getDefaultInstance(props, null);

	        /*
	        props.setProperty("mail.transport.protocol", "smpt");
	        props.setProperty("mail.smtp.port", "25");
	        props.setProperty("mail.host", "smtp.random.com");
	        props.setProperty("mail.user", "user@random.com");
	        props.setProperty("mail.password", "passwd");
	        */
	        
	        try {
	            Message msg = new MimeMessage(session);
	            msg.setFrom(new InternetAddress("mvcharcos@gmail.com", "Gmail.com Admin"));
	            msg.addRecipient(Message.RecipientType.TO,
	                             new InternetAddress(to, "Orphanage Without Borders App User"));
	            msg.setSubject(subject);
	            msg.setText(message);
	            msg.setReplyTo(new InternetAddress[]{new InternetAddress("mvcharcos@orphanagewithoutborders.org")});
	            Transport.send(msg);
	          
	            /*
	        } catch (AddressException e) {
	            // ...
	        } catch (MessagingException e) {
	            // ...
	        }*/
	        } catch (Exception e) {
	            output=e.toString();                
	        }   
	        
	        return output;
	    }

	  static public String sendMail(DBRecord record) {
	        
		  String subject = "DB record was added";
		  String message = "DB Record " + record.getName() + " was created with the following information " + eol + eol +
	                  		"   - Email: "+record.getEmail()+ eol +
	                  		"   - Address: "+record.getAddress()+ eol +
	                  		"   - Phone: "+record.getPhone()+ eol +
	                  		"   - Web: "+record.getWebsite()+ eol +
	                  		"   - Coordinates: "+record.getLongitude()+","+record.getLatitude()+ eol +
	                  		"   - Description: "+record.getDescription()+ eol;

		  sendMail(myEmail,subject,message);
		  return sendMail(systemEmail,subject,message);
	    }

	  static public String sendMail(Ngo ngo) {
	        
		  String subject = "Ngo request";
		  String message = "NGO " + ngo.getName() + " was created with the following information " + eol + eol +
	                  		"   - Email: "+ngo.getEmail()+ eol +
	                  		"   - Address: "+ngo.getAddress()+ eol +
	                  		"   - Phone: "+ngo.getPhone()+ eol +
	                  		"   - Web: "+ngo.getWebsite()+ eol +
	                  		"   - Coordinates: "+ngo.getLongitude()+","+ngo.getLatitude()+ eol +
	                  		"   - Description: "+ngo.getDescription()+ eol;

		  sendMail(myEmail,subject,message);
		  return sendMail(systemEmail,subject,message);
	    }
	  static public String sendMail(Orphanage orphanage) {
	        
		  String subject = "Orphanage request";
		  String message = "Orphanage " + orphanage.getName() + " was created with the following information " + eol + eol +
	                  		"   - Email: "+orphanage.getEmail()+ eol +
	                  		"   - Address: "+orphanage.getAddress()+ eol +
	                  		"   - Phone: "+orphanage.getPhone()+ eol +
	                  		"   - Web: "+orphanage.getWebsite()+ eol +
	                  		"   - Coordinates: "+orphanage.getLongitude()+","+orphanage.getLatitude()+ eol +
	                  		"   - Description: "+orphanage.getDescription()+ eol;

		  sendMail(myEmail,subject,message);
		  return sendMail(systemEmail,subject,message);
	    }
	  static public String sendMail(Volunteer volunteer) {
	        
		  String subject = "Volunteer request";
		  String message = "Volunteer " + volunteer.getName() + " was created with the following information " + eol + eol +
	                  		"   - Email: "+volunteer.getEmail()+ eol +
	                  		"   - Address: "+volunteer.getAddress()+ eol +
	                  		"   - Phone: "+volunteer.getPhone()+ eol +
	                  		"   - Web: "+volunteer.getWebsite()+ eol +
	                  		"   - Interest: "+eol;
		  
		  String interests = volunteer.getDescription().getValue();
		  if (interests.contains("||")) {
			  String[] parts = interests.split("[|]+");
			  for (String p: parts){
				  if (p.contains("Yes")){
					  message = message + p + eol; //.substring(4, p.length());
				  }
			  }
			  //message = message + interests.replace("||",eol+"   + ");
			} else {
			  message = message + interests;
			}
		  		  
		  sendMail(myEmail,subject,message);
		  return sendMail(systemEmail,subject,message);
	    }
}
