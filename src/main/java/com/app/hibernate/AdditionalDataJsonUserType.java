package com.app.hibernate;

import com.app.model.AdditionalData;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.engine.spi.SessionImplementor;
import org.postgresql.util.PGobject;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * AdditionalDataJsonUserType Class.
 */
public class AdditionalDataJsonUserType extends BaseCustomUserType {

  /**
   * property serialVersionUID long.
   */
  private static final long serialVersionUID = -5849904638253184627L;
  /**
   * property LOGGER Logger.
   */
  private static final Logger LOGGER = LogManager.getLogger("Logger");

  /* (non-Javadoc)
   * @see org.hibernate.usertype.UserType#returnedClass()
   */
  @Override
  public Class<?> returnedClass() {
    return AdditionalData.class;
  }

  /* (non-Javadoc)
   * @see org.hibernate.usertype.UserType#nullSafeGet(java.sql.ResultSet, java.lang.String[],
   *  org.hibernate.engine.spi.SessionImplementor, java.lang.Object)
   */
  @Override
  public Object nullSafeGet(final ResultSet rs, final String[] names,
      final SessionImplementor session, final Object owner) {
    try {
      PGobject o = (PGobject) rs.getObject(names[0]);
      if (o != null && o.getValue() != null) {
        final ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(o.getValue().getBytes("UTF-8"), returnedClass());
      }
    } catch (final SQLException | IOException e) {
      LOGGER.debug(e);
    }
    try {
      return returnedClass().newInstance();
    } catch (final InstantiationException | IllegalAccessException e) {
      LOGGER.debug(e);
    }
    return null;
  }

}
