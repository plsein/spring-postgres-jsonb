package com.app;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * PostgresqlJsonbHibernateApplication Main Class.
 */
@SpringBootApplication
public class PostgresqlJsonbHibernateApplication {

  /**
   * property LOGGER Logger.
   */
  private static final Logger LOGGER = LogManager.getLogger("Logger");

  /**
   * @param args String[].
   */
  @SuppressWarnings("squid:S2095")
  public static void main(final String[] args) {
    LOGGER.info("Application Started : ");
    SpringApplication.run(PostgresqlJsonbHibernateApplication.class, args);
  }

}
