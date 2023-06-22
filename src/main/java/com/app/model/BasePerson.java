package com.app.model;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * BasePerson Class.
 */
@MappedSuperclass
public class BasePerson implements Serializable {

  /**
   * property serialVersionUID long.
   */
  private static final long serialVersionUID = -8048006690732427086L;

  /**
   * property id Long.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /**
   * property firstName String.
   */
  private String firstName;

  /**
   * property LastName String.
   */
  private String lastName;

  /**
   * method getId.
   * @return Long
   */
  public Long getId() {
    return id;
  }

  /**
   * method getFirstName.
   * @return String
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * method setFirstName.
   * @param firstNameValue String
   */
  public void setFirstName(final String firstNameValue) {
    this.firstName = firstNameValue;
  }

  /**
   * method getLastName.
   * @return String
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * method setLastName.
   * @param lastNameValue String
   */
  public void setLastName(final String lastNameValue) {
    this.lastName = lastNameValue;
  }

}
