package com.app;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.app.dao.PersonDao;
import com.app.model.PersonInfo;
import com.app.repositories.PersonInfoRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PgJsonbHibernateUpdateTests {

  @Autowired
  private PersonInfoRepository personInfoRepository;
  
	@Autowired
  private PersonDao personDao;
	
	private static final Long ID = 1L;
  
  @SuppressWarnings("unchecked")
  @Test
  public void updateTest() {
    System.out.println("Inside Update Test : ");
    // String path = "hobbies,sports";
    String path = "{hobbies,sports}";
    final ObjectMapper mapper = new ObjectMapper();
    String new_value_to_append = "baseball";
    List<String> value = Arrays.asList(new_value_to_append);
    String val = "[]";
    try {
      val = mapper.writeValueAsString(value);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    List<String> conditions = new ArrayList<>();
    Map<String, Object> con1 = new HashMap<>();
    Map<String, Object> con2 = new HashMap<>();
    //
    Map<String,Object> hobbies1 = new HashMap<>();
    hobbies1.put("sports", Arrays.asList());
    con1.put("hobbies", hobbies1);
    //
    Map<String,Object> hobbies2 = new HashMap<>();
    hobbies2.put("sports", Arrays.asList(new_value_to_append));
    con2.put("hobbies", hobbies2);
    //
    try {
      System.out.println("con1 : " + mapper.writeValueAsString(con1));
      System.out.println("con2 : " + mapper.writeValueAsString(con2));
    } catch (JsonProcessingException e1) {
      e1.printStackTrace();
    }
    try {
      conditions.add(mapper.writeValueAsString(con1));
      conditions.add(mapper.writeValueAsString(con2));
    } catch (JsonProcessingException e1) {
      e1.printStackTrace();
    }
    int resp = personDao.updateData(
        /*Arrays.asList(
            "{\"hobbies\":{\"sports\":[]}}",
            "{\"hobbies\":{\"sports\":[\"baseball\"]}}"
          ),*/
        conditions,
        path,
        path,
        val
      );
    PersonInfo p = personInfoRepository.findOne(ID);
    System.out.println("Return response : " + resp);
    System.out.println("PersonInfo : " + p.getAdditionalData());
    Map<String,Object> hobby = (Map<String,Object>) p.getAdditionalData().get("hobbies");
    List<String> sports = (List<String>) hobby.get("sports");
    assertTrue(sports.contains(new_value_to_append));
  }
  
  @SuppressWarnings("unchecked")
  @Test
  public void updateByRepoTest() {
    System.out.println("Inside Update By Repository Test : ");
    // String path = "hobbies,sports";
    String path = "{hobbies,sports}";
    final ObjectMapper mapper = new ObjectMapper();
    String new_value_to_append = "baseball";
    List<String> value = Arrays.asList(new_value_to_append);
    String val = "[]";
    try {
      val = mapper.writeValueAsString(value);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    Map<String, Object> con1 = new HashMap<>();
    Map<String, Object> con2 = new HashMap<>();
    //
    Map<String,Object> hobbies1 = new HashMap<>();
    hobbies1.put("sports", Arrays.asList());
    con1.put("hobbies", hobbies1);
    //
    Map<String,Object> hobbies2 = new HashMap<>();
    hobbies2.put("sports", Arrays.asList(new_value_to_append));
    con2.put("hobbies", hobbies2);
    //
    String condition1 = "";
    String condition2 = "";
    try {
      condition1 = mapper.writeValueAsString(con1);
      condition2 = mapper.writeValueAsString(con2);
      System.out.println("con1 : " + condition1);
      System.out.println("con2 : " + condition2);
    } catch (JsonProcessingException e1) {
      e1.printStackTrace();
    }
    System.out.println("Path : " + path);
    System.out.println("Value : " + val);
    System.out.println("Condition1 : " + condition1);
    System.out.println("Condition2 : " + condition2);
    Integer resp = personInfoRepository.updatePersonsByQuery(path, val, condition1, condition2);
    System.out.println("Return response : " + resp);
    PersonInfo p = personInfoRepository.findOne(ID);
    System.out.println("PersonInfo : " + p.getAdditionalData());
    Map<String,Object> hobby = (Map<String,Object>) p.getAdditionalData().get("hobbies");
    List<String> sports = (List<String>) hobby.get("sports");
    assertTrue(sports.contains(new_value_to_append));
  }

}
