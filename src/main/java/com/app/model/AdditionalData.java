package com.app.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * AdditionalData Class.
 */
public class AdditionalData implements Serializable {

  /**
   * property serialVersionUID long.
   */
  private static final long serialVersionUID = 3973878124379818979L;

  /**
   * property regDate Date.
   */
  private Date regDate;

  /**
   * property renewalDate Date.
   */
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
  private Date renewalDate;

  /**
   * property eyeColor String.
   */
  private String eyeColor;

  /**
   * property favoriteColors List.
   */
  private List<String> favoriteColors;

  /**
   * property contact List.
   */
  private List<Contact> contact;

  /**
   * property hobbies Hobbies.
   */
  private Hobbies hobbies;

  /**
   * method getContact.
   * @return List
   */
  public List<Contact> getContact() {
    return contact;
  }

  /**
   * method setContact.
   * @param contactValue List
   */
  public void setContact(final List<Contact> contactValue) {
    this.contact = contactValue;
  }

  /**
   * method getHobbies.
   * @return Hobbies
   */
  public Hobbies getHobbies() {
    return hobbies;
  }

  /**
   * method setHobbies.
   * @param hobbiesValue Hobbies
   */
  public void setHobbies(final Hobbies hobbiesValue) {
    this.hobbies = hobbiesValue;
  }

  /**
   * method getRegDate.
   * @return Date
   */
  public Date getRegDate() {
    if (regDate != null) {
      return new Date(regDate.getTime());
    } else {
      return null;
    }
  }

  /**
   * method setRegDate.
   * @param regDateValue Date
   */
  public void setRegDate(final Date regDateValue) {
    this.regDate = new Date(regDateValue.getTime());
  }

  /**
   * method getRenewalDate.
   * @return Date
   */
  public Date getRenewalDate() {
    if (renewalDate != null) {
      return new Date(renewalDate.getTime());
    } else {
      return null;
    }
  }

  /**
   * methos setRenewalDate.
   * @param renewalDateValue Date
   */
  public void setRenewalDate(final Date renewalDateValue) {
    this.renewalDate = new Date(renewalDateValue.getTime());
  }

  /**
   * method getEyeColor.
   * @return String
   */
  public String getEyeColor() {
    return eyeColor;
  }

  /**
   * method setEyeColor.
   * @param eyeColorValue String
   */
  public void setEyeColor(final String eyeColorValue) {
    this.eyeColor = eyeColorValue;
  }

  /**
   * method getFavoriteColors.
   * @return List
   */
  public List<String> getFavoriteColors() {
    return favoriteColors;
  }

  /**
   * method setFavoriteColors.
   * @param favoriteColorsValue
   *          the favorite_colors to set
   */
  public void setFavoriteColors(final List<String> favoriteColorsValue) {
    this.favoriteColors = favoriteColorsValue;
  }

}
