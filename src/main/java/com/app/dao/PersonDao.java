
package com.app.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * PersonDao Class.
 */
@Repository
@Transactional
public class PersonDao {

  /**
   * property entityManager EntityManager.
   */
  @Autowired
  private EntityManager entityManager;

  /**
   * method fetchData.
   * @return List
   */
  @SuppressWarnings("unchecked")
  public List<Object[]> fetchData() {
    Query query = entityManager.createNativeQuery("SELECT p.id, p.first_name, p.last_name"
        + ", p.additional_data #>> '{contact,0,email,home}' as home_email"
        + ", p.additional_data #>> '{eyeColor}' as eye_color"
        + ", p.additional_data #>> '{renewalDate}' as renewal_date"
        // + ", p.additional_data "   // won't work
        + " FROM Person p"
        + " WHERE p.additional_data @> '{\"hobbies\":{\"arts\":[\"painting\"]}}'"
        + " Group by p.id, p.first_name, p.additional_data"
        + " ORDER BY 2");
    return query.getResultList();
  }

  /**
   *  method getData.
   * @param conditions List
   * @return List
   */
  @SuppressWarnings({ "unchecked" })
  public List<String> getData(final List<String> conditions) {
    Session session = entityManager.unwrap(Session.class);
    SQLQuery q = session.createSQLQuery("SELECT p.id, p.first_name, p.last_name"
        + ", p.additional_data #>> '{contact,0,email,home}' as home_email"
        + ", p.additional_data #>> '{eyeColor}' as eye_color"
        + ", p.additional_data #>> '{renewalDate}' as renewal_date"
        + ", p.additional_data"
        + " FROM Person p"
        + " WHERE p.additional_data @> CAST(? AS jsonb)"
        + " AND additional_data @> CAST(? AS jsonb)"
        + " Group by p.id, p.first_name, p.additional_data"
        + " ORDER BY 2")
        .addScalar("additional_data", StandardBasicTypes.STRING);
    Integer i = 0;
    for (String condition : conditions) {
      q.setString(i, condition);
      i++;
    }
    return q.list();
  }

  /**
   * method updateData.
   * @param conditions List
   * @param path1 String
   * @param path2 String
   * @param value String
   * @return Integer
   */
  public Integer updateData(final List<String> conditions, final String path1,
      final String path2, final String value) {
    Query query = entityManager.createNativeQuery(
        "Update Person SET"
        + " additional_data = jsonb_set(additional_data, ?, additional_data#>? || ?)"
        + " WHERE additional_data @> ?"
        + " AND NOT additional_data @> ?"
      );
    Integer i = 1;
    query.setParameter(i, path1);
    i++;
    query.setParameter(i, path2);
    i++;
    query.setParameter(i, value);
    i++;
    for (String condition : conditions) {
      query.setParameter(i, condition);
      i++;
    }
    return query.executeUpdate();
  }

  /**
   * method deletePath.
   * @param conditions List
   * @param path String
   * @return Integer
   */
  public Integer deletePath(final List<String> conditions, final String path) {
    Query query = entityManager.createNativeQuery(
        "Update Person SET additional_data = additional_data #- string_to_array(?, ',')"
        + " WHERE additional_data @> CAST(? as jsonb) AND NOT additional_data @> CAST(? as jsonb)"
      );
    query.setParameter(1, path);
    Integer i = 2;
    for (String condition : conditions) {
      query.setParameter(i, condition);
      i++;
    }
    return query.executeUpdate();
  }

}
