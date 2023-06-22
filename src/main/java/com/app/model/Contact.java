package com.app.model;

import java.io.Serializable;
import java.util.Map;

/**
 * Contact Class.
 */
public class Contact implements Serializable {

  /**
   * property serialVersionUID long.
   */
  private static final long serialVersionUID = -2890097929188631901L;

  /**
   * property email Map.
   */
  private Map<String, String> email;

  /**
   * property phone Map.
   */
  private Map<String, String> phone;

  /**
   * method getEmail.
   * @return Map
   */
  public Map<String, String> getEmail() {
    return email;
  }

  /**
   * method setEmail.
   * @param emailValue Map
   */
  public void setEmail(final Map<String, String> emailValue) {
    this.email = emailValue;
  }

  /**
   * method getPhone.
   * @return Map
   */
  public Map<String, String> getPhone() {
    return phone;
  }

  /**
   * method setPhone.
   * @param phoneValue Map
   */
  public void setPhone(final Map<String, String> phoneValue) {
    this.phone = phoneValue;
  }

}
