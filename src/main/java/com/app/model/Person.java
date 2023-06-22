package com.app.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

/**
 * Person Class.
 */
@Entity
@Table(name = "person")
public class Person extends BasePerson {

  /**
   * property serialVersionUID long.
   */
  private static final long serialVersionUID = -3869938133247054734L;

  /**
   * property additionalData AdditionalData.
   */
  @Type(type = "AdditionalDataJsonUserType")
  private AdditionalData additionalData;

  /**
   * method getAdditionalData.
   * @return AdditionalData
   */
  public AdditionalData getAdditionalData() {
    return additionalData;
  }

  /**
   * method setAdditionalData.
   * @param additionalDataValue AdditionalData
   */
  public void setAdditionalData(final AdditionalData additionalDataValue) {
    this.additionalData = additionalDataValue;
  }

}
