package com.app;

import static org.junit.Assert.assertNotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

// import com.app.dao.PersonDao;
import com.app.model.PersonInfo;
import com.app.repositories.PersonInfoRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PgJsonbHibernateApplicationTests {

	@Autowired
	private PersonInfoRepository repository;
	
	// @Autowired
  // private PersonDao personDao;

	@SuppressWarnings("unchecked")
  @Test
	public void insertTest() {
		// repository.deleteAll();

		PersonInfo personInfo = new PersonInfo();
		personInfo.setFirstName("Json");
		personInfo.setLastName("Jackson");

		Date date = new Date();
		Map<String, Object> additionalData = new HashMap<>();
		additionalData.put("favoriteColors", Arrays.asList("red","green","yellow","blue"));
		additionalData.put("regDate", date);
		additionalData.put("renewalDate", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
		additionalData.put("eyeColor", "blue");
		Map<String,Object> hobbies = new HashMap<>();
		hobbies.put("sports", Arrays.asList("cricket","football"));
		hobbies.put("arts", Arrays.asList("drawing","painting"));
		additionalData.put("hobbies", hobbies);
		Map<String,Object> phone = new HashMap<>();
		phone.put("home", "123-4567-8910");
		phone.put("work", "012-3456-7891");
		Map<String,Object> email = new HashMap<>();
		email.put("home", "home@email.com");
		email.put("work", "work@email.com");
		Map<String,Object> contact = new HashMap<>();
		contact.put("phone", phone);
		contact.put("email", email);
		List<Map<String,Object>> contacts = new ArrayList<>();
		contacts.add(contact);
		Map<String,Object> phone1 = new HashMap<>();
    phone1.put("home", "1123-4567-891");
    phone1.put("work", "112-3456-7891");
    Map<String,Object> email1 = new HashMap<>();
    email1.put("home", "home@mail.com");
    email1.put("work", "work@mail.com");
    Map<String,Object> contact1 = new HashMap<>();
    contact1.put("phone", phone1);
    contact1.put("email", email1);
		contacts.add(contact1);
		additionalData.put("contact", contacts);
		personInfo.setAdditionalData(additionalData);
		repository.save(personInfo);
		PersonInfo p = repository.findOne(personInfo.getId());
		System.out.println("PersonInfo Reg Date : " + new Date(Long.valueOf(p.getAdditionalData().get("regDate").toString())));
		try {
        System.out.println("PersonInfo Renewal Date : " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(p.getAdditionalData().get("renewalDate").toString()));
    } catch (ParseException e) {
        System.out.println("PersonInfo renewal date :- ");
        e.printStackTrace();
    }
		System.out.println("PersonInfo hobbies : " + p.getAdditionalData().get("hobbies").toString());
		Map<String,Object> hobby = (Map<String,Object>) p.getAdditionalData().get("hobbies");
		System.out.println("PersonInfo hobby : " + hobby);
		List<String> favcolors = (List<String>) p.getAdditionalData().get("favoriteColors");
		System.out.println("PersonInfo fav colors : " + favcolors);
		List<Map<String, Object>> contactinfo = (List<Map<String, Object>>) p.getAdditionalData().get("contact");
		System.out.println("PersonInfo contact info : " + contactinfo);
		assertNotNull(p.getId());
	}
	
	/*@SuppressWarnings("unchecked")
  @Test
  public void searchTest() {
	  System.out.println("Inside Search Test : ");
	  PersonInfo p = repository.getPersonsByQuery("{\"hobbies\":{\"arts\":[\"painting\"]}}");
	  System.out.println("PersonInfo : " + p);
	  List<Map<String, Object>> contactinfo = (List<Map<String, Object>>) p.getAdditionalData().get("contact");
    System.out.println("PersonInfo contact info : " + contactinfo);
    assertNotNull(p.getId());
	}*/

}
