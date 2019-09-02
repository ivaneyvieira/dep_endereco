package br.com.astrosoft.model.enderecamento.domain.query

import br.com.astrosoft.model.enderecamento.domain.User
import br.com.astrosoft.model.enderecamento.domain.query.assoc.QAssocRole
import io.ebean.Database
import io.ebean.typequery.PInteger
import io.ebean.typequery.PLocalDateTime
import io.ebean.typequery.PLong
import io.ebean.typequery.PString
import io.ebean.typequery.TQRootBean
import io.ebean.typequery.TypeQueryBean

/**
 * Query bean for User.
 * 
 * THIS IS A GENERATED OBJECT, DO NOT MODIFY THIS CLASS.
 */
@TypeQueryBean
class QUser : TQRootBean<User, QUser> {

  companion object {
    /**
     * shared 'Alias' instance used to provide
     * properties to select and fetch clauses
     */
    val _alias = QUser(true)
  }

  lateinit var id: PLong<QUser>
  lateinit var createdAt: PLocalDateTime<QUser>
  lateinit var updatedAt: PLocalDateTime<QUser>
  lateinit var version: PInteger<QUser>
  lateinit var userName: PString<QUser>
  lateinit var roles: QAssocRole<QUser>
  lateinit var chapa: PString<QUser>
  lateinit var userSaci: PString<QUser>
  lateinit var firstName: PString<QUser>
  lateinit var lastName: PString<QUser>
  lateinit var title: PString<QUser>
  lateinit var passw: PString<QUser>


  /**
   * Construct with a given Database.
   */
  constructor(database: Database) : super(User::class.java, database)

  /**
   * Construct using the default Database.
   */
  constructor() : super(User::class.java)

  /**
   * Construct for Alias.
   */
  private constructor(dummy: Boolean) : super(dummy)
}
