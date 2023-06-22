package com.app;

import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// import org.apache.log4j.LogManager;
// import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.app.model.AdditionalData;
import com.app.model.Contact;
import com.app.model.Hobbies;
import com.app.model.Person;
import com.app.repositories.PersonRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InsertTest {

  // private static final Logger LOGGER = LogManager.getLogger("Logger");
  
  private static final ObjectMapper OBJ_MAPPER = new ObjectMapper();
  
	@Autowired
	private PersonRepository personRepository;

  @Test
	public void insertTest() {
	  // personRepository.deleteAll();

		Date date1 = new Date();
		Date date2 = new Date();
		
		Map<String,String> email1 = new HashMap<>();
    email1.put("home", "home@email.com");
    email1.put("work", "work@email.com");
    Map<String,String> phone1 = new HashMap<>();
    phone1.put("home", "123-4567-8910");
    phone1.put("work", "012-3456-7891");
    
    Map<String,String> email2 = new HashMap<>();
    email2.put("home", "home@mail.com");
    email2.put("work", "work@mail.com");
    Map<String,String> phone2 = new HashMap<>();
    phone2.put("home", "1123-4567-891");
    phone2.put("work", "112-3456-7891");
    
		Contact contact1 = new Contact();
		contact1.setEmail(email1);
		contact1.setPhone(phone1);
		Contact contact2 = new Contact();
    contact2.setEmail(email2);
    contact2.setPhone(phone2);
    List<Contact> contact = Arrays.asList(contact1, contact2);
    
    Hobbies hobbies = new Hobbies();
    hobbies.setArts(Arrays.asList("drawing","painting"));
    hobbies.setSports(Arrays.asList("cricket","football"));
    
    AdditionalData additionalData = new AdditionalData();
    additionalData.setEyeColor("blue");
    additionalData.setRegDate(date1);
    additionalData.setRenewalDate(date2);
    additionalData.setContact(contact);
    additionalData.setHobbies(hobbies);
    additionalData.setFavoriteColors(Arrays.asList("red","green","yellow","blue"));

    Person person = new Person();
    person.setFirstName("Jack");
    person.setLastName("Jackson");
		person.setAdditionalData(additionalData);

		try {
      System.out.println("Person : " + OBJ_MAPPER.writeValueAsString(person));
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
		
		personRepository.save(person);
		assertNotNull(person.getId());
	}

}
