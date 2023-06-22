package com.app;

import static org.junit.Assert.assertFalse;

import java.util.Arrays;
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

@RunWith(SpringRunner.class)
@SpringBootTest
public class PgJsonbHibernateDeleteTests {

  @Autowired
  private PersonInfoRepository personInfoRepository;
  
	@Autowired
  private PersonDao personDao;
	
	private static final Long ID = 2L;
  
  @SuppressWarnings("unchecked")
  @Test
  public void deleteTest() {
    System.out.println("Inside Delete Test : ");
    String value_to_remove = "baseball";
    String keyPath = "hobbies,sports,-1";
    personDao.deletePath(
        Arrays.asList(
            "{\"hobbies\":{\"sports\":[\"baseball\"]}}",
            "{\"hobbies\":{\"sports\":[\"\"]}}"
          ),
        keyPath
      );
    PersonInfo p = personInfoRepository.findOne(ID);
    System.out.println("PersonInfo : " + p.getAdditionalData());
    Map<String,Object> hobby = (Map<String,Object>) p.getAdditionalData().get("hobbies");
    List<String> sports = (List<String>) hobby.get("sports");
    assertFalse(sports.contains(value_to_remove));
  }

}
