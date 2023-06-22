/**
 * package info.
 */

@TypeDefs({
    @TypeDef(name = "BaseCustomUserType", typeClass = JsonDataUserType.class),
    @TypeDef(name = "JsonDataUserType", typeClass = JsonDataUserType.class),
    @TypeDef(name = "AdditionalDataJsonUserType", typeClass = AdditionalDataJsonUserType.class)
  })

package com.app.hibernate;

import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
