package com.app;

import static org.junit.Assert.assertEquals;

import java.util.List;

// import org.apache.log4j.LogManager;
// import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.app.model.AdditionalData;
// import com.app.model.Contact;
import com.app.model.Hobbies;
import com.app.model.Person;
import com.app.repositories.PersonRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UpdateTest {
  
  // private static final Logger LOGGER = LogManager.getLogger("Logger");
  
  private static final Long ID = 1L;

	@Autowired
	private PersonRepository personRepository;
	
	private static final ObjectMapper OBJ_MAPPER = new ObjectMapper();

  @Test
	public void updateTest() {
    String newSportsValue = "baseball";
    String eyeColor = "brown";
    System.out.println("Inside Update Test : ");
    // LOGGER.info("Inside Update Test : ");
		Person person = personRepository.findOne(ID);
		AdditionalData additionalData = person.getAdditionalData();
		Hobbies hobbies = additionalData.getHobbies();
		List<String> sports = hobbies.getSports();
		if(sports.contains(newSportsValue)) {
		  sports.remove(newSportsValue);
		} else {
		  sports.add(newSportsValue);
		}
		additionalData.setEyeColor(eyeColor);
		/*additionalData.setContact(new ArrayList<Contact>());
		List<Contact> contactList = additionalData.getContact();
		Contact contact = new Contact();
		contact.setEmail(new HashMap<String,String>());
		// contact.setPhone(new HashMap<String,String>());
		contactList.add(contact);*/
		personRepository.save(person);
		try {
		  System.out.println("Person : " + OBJ_MAPPER.writeValueAsString(person));
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
		assertEquals(additionalData.getEyeColor(), eyeColor);
	}

}
