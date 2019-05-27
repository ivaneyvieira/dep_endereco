package br.com.astrosoft.model.enderecamento.domain.query.assoc

import br.com.astrosoft.model.enderecamento.domain.User
import io.ebean.typequery.PInteger
import io.ebean.typequery.PLocalDateTime
import io.ebean.typequery.PLong
import io.ebean.typequery.PString
import io.ebean.typequery.TQAssocBean
import io.ebean.typequery.TypeQueryBean

/**
 * Association query bean for AssocUser.
 * 
 * THIS IS A GENERATED OBJECT, DO NOT MODIFY THIS CLASS.
 */
@TypeQueryBean
class QAssocUser<R>(name: String, root: R) : TQAssocBean<User,R>(name, root) {

  lateinit var id: PLong<R>
  lateinit var createdAt: PLocalDateTime<R>
  lateinit var updatedAt: PLocalDateTime<R>
  lateinit var version: PInteger<R>
  lateinit var userName: PString<R>
  lateinit var roles: QAssocRole<R>
  lateinit var chapa: PString<R>
  lateinit var userSaci: PString<R>
  lateinit var firstName: PString<R>
  lateinit var lastName: PString<R>
  lateinit var title: PString<R>
  lateinit var passw: PString<R>

  // type safe fetch(properties) using varargs not supported yet ...
}
