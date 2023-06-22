package com.app.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.model.Person;

/**
 * PersonRepository Interface.
 */
@Repository
@Transactional
public interface PersonRepository extends JpaRepository<Person, Long> {

  /**
   * method getPersonsByQuery.
   * @param condition String
   * @return Person
   */
  @Query(value = "SELECT p.id, p.first_name, p.last_name, p.additional_data"
      + ", p.additional_data #>> '{contact,0,email,home}' as home_email"
      + ", p.additional_data #>> '{eyeColor}' as eye_color"
      + ", p.additional_data #>> '{renewalDate}' as renewal_date"
      + " FROM Person p"
      + " WHERE p.additional_data @> CAST(:con as jsonb)"
      + " Group by p.id, p.first_name, p.additional_data"
      + " ORDER BY renewal_date DESC",
      nativeQuery = true)
  List<Person> getPersonsByQuery(@Param("con") String condition);

  /**
   * method getCountPerIdByQuery.
   * @param path String
   * @param condition String
   * @return List
   */
  @Query(value = "SELECT p.id, jsonb_array_length(additional_data #> :path) as count"
      + " FROM Person p"
      + " WHERE p.additional_data @> :con"
      + " Group by p.id, p.first_name"
      + " ORDER BY count DESC",
      nativeQuery = true)
  List<Object[]> getCountPerIdByQuery(@Param("path") String path, @Param("con") String condition);

  /**
   * method countByCondition.
   * @param condition String
   * @return Long
   */
  @Query(value = "SELECT count(p.id)"
      + " FROM Person p"
      + " WHERE p.additional_data @> :con",
      nativeQuery = true)
  Long countByCondition(@Param("con") String condition);

  /**
   * method updatePersonsByQuery.
   * @param path String
   * @param value String
   * @param condition1 String
   * @param condition2 String
   * @return Integer
   */
  @Modifying
  @Query(value = "Update Person SET"
      + " additional_data = jsonb_set(additional_data, :path"
      + ", additional_data#>:path || :value)"
      + " WHERE additional_data @> :con1"
      + " AND NOT additional_data @> :con2",
      nativeQuery = true)
  Integer updatePersonsByQuery(@Param("path") String path, @Param("value") String value,
      @Param("con1") String condition1, @Param("con2") String condition2);

  /**
   * method deletePersonsByQuery.
   * @param path String
   * @param condition1 String
   * @param condition2 String
   * @return Integer
   */
  @Modifying
  @Query(value = "Update Person SET"
      + " additional_data = additional_data #- string_to_array(:path, ',')"
      + " WHERE additional_data @> CAST(:con1 as jsonb)"
      + " AND NOT additional_data @> CAST(:con2 as jsonb)",
      nativeQuery = true)
  Integer deletePersonsByQuery(@Param("path") String path,
      @Param("con1") String condition1, @Param("con2") String condition2);

}
