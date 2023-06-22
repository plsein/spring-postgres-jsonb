package com.app.model;

import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

/**
 * PersonInfo Class.
 */
@Entity
@Table(name = "person")
public class PersonInfo extends BasePerson {

  /**
   * property serialVersionUID long.
   */
  private static final long serialVersionUID = -5338134188704910866L;

  /**
   * property additionalData Map.
   */
  @SuppressWarnings("squid:S1948")
  @Type(type = "JsonDataUserType")
  private Map<String, Object> additionalData;

  /**
   * method getAdditionalData.
   * @return Map
   */
  public Map<String, Object> getAdditionalData() {
    return additionalData;
  }

  /**
   * method setAdditionalData.
   * @param additionalDataValue Map
   */
  public void setAdditionalData(final Map<String, Object> additionalDataValue) {
    this.additionalData = additionalDataValue;
  }

}
