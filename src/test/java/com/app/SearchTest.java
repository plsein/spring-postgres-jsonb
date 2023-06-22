package com.app;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
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

import com.app.dao.PersonDao;
import com.app.model.AdditionalData;
import com.app.model.Contact;
import com.app.model.Person;
import com.app.repositories.PersonRepository;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SearchTest {

  @Autowired
  private PersonDao personDao;
  
	@Autowired
	private PersonRepository personRepository;

  @Test
  public void searchTest() {
    System.out.println("Inside Search Test : ");
    String eyeColor = "blue";
    final ObjectMapper mapper = new ObjectMapper();
    mapper.setSerializationInclusion(Include.NON_NULL);
    AdditionalData data = new AdditionalData();
    data.setEyeColor(eyeColor);
    /*Hobbies hobbies = new Hobbies();
    hobbies.setArts(Arrays.asList("painting"));
    data.setHobbies(hobbies);*/
    String condition;
    try {
      condition = mapper.writeValueAsString(data);
    } catch (JsonProcessingException e) {
      condition = "";
      e.printStackTrace();
    }
    System.out.println("Condition : " + condition);
    // List<Person> resp = personRepository.getPersonsByQuery("{\"hobbies\":{\"arts\":[\"painting\"]}}");
    List<Person> resp = personRepository.getPersonsByQuery(condition);
    for(Person person : resp) {
      List<Contact> contactinfo = person.getAdditionalData().getContact();
      try {
        System.out.println("Person : " + mapper.writeValueAsString(person));
        System.out.println("Person contact info : " + mapper.writeValueAsString(contactinfo));
      } catch (JsonProcessingException e) {
        e.printStackTrace();
      }
    }
    assertNotNull(resp);
  }
  
  @SuppressWarnings("unchecked")
  @Test
  public void getTest() {
    final ObjectMapper mapper = new ObjectMapper();
    /*HashMap<String, String> conditions = new HashMap<>();
    conditions.put("con1", "{\"hobbies\":{\"arts\":[\"painting\"]}}");
    conditions.put("con2", "{\"contact\":[{\"email\":{\"home\":\"home@email.com\"}}]}");*/
    Map<String, Object> con1 = new HashMap<>();
    Map<String, Object> con2 = new HashMap<>();
    List<String> conditions = new ArrayList<>();
    /*conditions.add("{\"hobbies\":{\"arts\":[\"painting\"]}}");
    conditions.add("{\"contact\":[{\"email\":{\"home\":\"home@email.com\"}}]}");*/
    //
    Map<String,Object> hobbies = new HashMap<>();
    hobbies.put("sports", Arrays.asList("cricket"));
    con1.put("hobbies", hobbies);
    //
    Map<String,Object> email = new HashMap<>();
    email.put("home", "home@email.com");
    Map<String,Object> contact = new HashMap<>();
    contact.put("email", email);
    List<Map<String,Object>> contacts = new ArrayList<>();
    contacts.add(contact);
    con2.put("contact", contacts);
    //
    try {
      conditions.add(mapper.writeValueAsString(con1));
      conditions.add(mapper.writeValueAsString(con2));
    } catch (JsonProcessingException e1) {
      e1.printStackTrace();
    }
    //
    List<String> resp = personDao.getData(conditions);
        // Arrays.asList("id","first_name","last_name","home_email","eye_color","renewal_date","additional_data")
      //);
    System.out.println("Response : " + resp);
    System.out.println("PersonInfo : " + resp.get(0));
    Map<String, Object> person = null;
    try {
      person = mapper.readValue(resp.get(0).getBytes("UTF-8"), new TypeReference<Map<String, Object>>() {});
    } catch (IOException e) {
      e.printStackTrace();
    }
    System.out.println("PersonInfo : " + person);
    System.out.println("PersonInfo Reg Date : " + new Date(Long.valueOf(person.get("regDate").toString())));
    List<String> favColors = (List<String>) person.get("favoriteColors");
    System.out.println("PersonInfo fav color : " + favColors.get(0));
    List<Map<String, Object>> contactinfo = (List<Map<String, Object>>) person.get("contact");
    System.out.println("Contact info : " + contactinfo);
    System.out.println("Home Email : " + ((Map<String, Object>) contactinfo.get(0).get("email")).get("home").toString());
    assertNotNull(person);
  }
  
  @Test
  public void fetchTest() {
    System.out.println("Inside Fetch Test : ");
    List<Object[]> resp = personDao.fetchData();
    System.out.println("Response : " + resp);
    Object[] p = resp.get(0);
    System.out.println("PersonInfo : " + p);
    System.err.println("Home Email : " + p[3]);
    assertNotNull(p[0]);
  }
  
  @Test
  public void countTest() {
    String eyeColor = "blue";
    final ObjectMapper mapper = new ObjectMapper();
    mapper.setSerializationInclusion(Include.NON_NULL);
    String path = "{contact}";
    AdditionalData data = new AdditionalData();
    data.setEyeColor(eyeColor);
    String condition;
    try {
      condition = mapper.writeValueAsString(data);
    } catch (JsonProcessingException e) {
      condition = "";
      e.printStackTrace();
    }
    System.out.println("Condition : " + condition);
    List<Object[]> resp = personRepository.getCountPerIdByQuery(path, condition);
    for(Object[] rec : resp) {
        System.out.println("Count : " + rec[0] + " => " + rec[1]);
    }
    Long count = personRepository.countByCondition(condition);
    System.out.println("Count : " + count);
    assertNotNull(resp);
  }

}
