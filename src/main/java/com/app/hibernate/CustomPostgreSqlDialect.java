package com.app.hibernate;

import org.hibernate.dialect.PostgreSQL94Dialect;
import java.sql.Types;

/**
 * CustomPostgreSqlDialect Class.
 */
public class CustomPostgreSqlDialect extends PostgreSQL94Dialect {

  /**
   * constructor method CustomPostgreSqlDialect.
   */
  public CustomPostgreSqlDialect() {
    super();
    this.registerColumnType(Types.JAVA_OBJECT, "jsonb");
  }
}
