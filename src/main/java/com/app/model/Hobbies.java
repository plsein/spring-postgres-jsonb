package com.app.model;

import java.io.Serializable;
import java.util.List;

/**
 * Hobbies Class.
 */
public class Hobbies implements Serializable {

  /**
   * property serialVersionUID long.
   */
  private static final long serialVersionUID = 4531340415408528300L;

  /**
   * property arts List.
   */
  private List<String> arts;

  /**
   * property sports List.
   */
  private List<String> sports;

  /**
   * method getArts.
   * @return List
   */
  public List<String> getArts() {
    return arts;
  }

  /**
   * method setArts.
   * @param artsValue List
   */
  public void setArts(final List<String> artsValue) {
    this.arts = artsValue;
  }

  /**
   * method getSports.
   * @return List
   */
  public List<String> getSports() {
    return sports;
  }

  /**
   * method setSports.
   * @param sportsValue List
   */
  public void setSports(final List<String> sportsValue) {
    this.sports = sportsValue;
  }

}
