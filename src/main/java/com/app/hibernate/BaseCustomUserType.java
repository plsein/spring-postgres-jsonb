package com.app.hibernate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.UserType;
import org.postgresql.util.PGobject;
import org.springframework.util.ObjectUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.StringWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

/**
 * BaseCustomUserType Class.
 */
public class BaseCustomUserType implements UserType, Serializable {

  /**
   * property serialVersionUID long.
   */
  private static final long serialVersionUID = 2430606532098078683L;
  /**
   * property LOGGER Logger.
   */
  private static final Logger LOGGER = LogManager.getLogger("Logger");

  /* (non-Javadoc)
   * @see org.hibernate.usertype.UserType#sqlTypes()
   */
  @Override
  public int[] sqlTypes() {
    return new int[] {Types.JAVA_OBJECT};
  }

  /* (non-Javadoc)
   * @see org.hibernate.usertype.UserType#returnedClass()
   */
  @Override
  public Class<?> returnedClass() {
    return Map.class;
  }

  /* (non-Javadoc)
   * @see org.hibernate.usertype.UserType#isMutable()
   */
  @Override
  public boolean isMutable() {
    return true;
  }

  /* (non-Javadoc)
   * @see org.hibernate.usertype.UserType#nullSafeSet(java.sql.PreparedStatement, java.lang.Object,
   *  int, org.hibernate.engine.spi.SessionImplementor)
   */
  @Override
  public void nullSafeSet(final PreparedStatement st, final Object value, final int index,
      final SessionImplementor session) {
    if (value == null) {
      try {
        st.setNull(index, Types.OTHER);
      } catch (SQLException e) {
        LOGGER.debug(e);
      }
    } else {
      final ObjectMapper mapper = new ObjectMapper();
      final StringWriter w = new StringWriter();
      try {
        mapper.writeValue(w, value);
        w.flush();
        st.setObject(index, w.toString(), Types.OTHER);
      } catch (final IOException | SQLException ex) {
        LOGGER.debug(ex);
      }
    }
  }

  /* (non-Javadoc)
   * @see org.hibernate.usertype.UserType#deepCopy(java.lang.Object)
   */
  @Override
  public Object deepCopy(final Object value) {
    try {
      ByteArrayOutputStream bos = new ByteArrayOutputStream();
      ObjectOutputStream oos = new ObjectOutputStream(bos);
      oos.writeObject(value);
      oos.flush();
      oos.close();
      bos.close();
      ByteArrayInputStream bais = new ByteArrayInputStream(bos.toByteArray());
      return new ObjectInputStream(bais).readObject();
    } catch (final ClassNotFoundException | IOException ex) {
      LOGGER.debug(ex);
      return value;
    }
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
        return mapper.readValue(o.getValue().getBytes("UTF-8"),
            new TypeReference<Map<String, Object>>() {
          });
      }
    } catch (final IOException | SQLException ex) {
      LOGGER.debug(ex);
    }
    return new HashMap<String, Object>();
  }

  /* (non-Javadoc)
   * @see org.hibernate.usertype.UserType#disassemble(java.lang.Object)
   */
  @Override
  public Serializable disassemble(final Object value) {
    return (Serializable) this.deepCopy(value);
  }

  /* (non-Javadoc)
   * @see org.hibernate.usertype.UserType#assemble(java.io.Serializable, java.lang.Object)
   */
  @Override
  public Object assemble(final Serializable cached, final Object owner) {
    return deepCopy(cached);
  }

  /* (non-Javadoc)
   * @see org.hibernate.usertype.UserType#replace(java.lang.Object,
   *  java.lang.Object, java.lang.Object)
   */
  @Override
  public Object replace(final Object original, final Object target, final Object owner) {
    return deepCopy(original);
  }

  /* (non-Javadoc)
   * @see org.hibernate.usertype.UserType#hashCode(java.lang.Object)
   */
  @Override
  public int hashCode(final Object x) {
    if (x == null) {
      return 0;
    }
    return x.hashCode();
  }

  /* (non-Javadoc)
   * @see org.hibernate.usertype.UserType#equals(java.lang.Object, java.lang.Object)
   */
  @Override
  public boolean equals(final Object x, final Object y) {
    return ObjectUtils.nullSafeEquals(x, y);
  }

}
