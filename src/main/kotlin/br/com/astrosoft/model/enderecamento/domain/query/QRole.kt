package br.com.astrosoft.model.enderecamento.domain.query

import br.com.astrosoft.model.enderecamento.domain.Role
import br.com.astrosoft.model.enderecamento.domain.query.assoc.QAssocUser
import io.ebean.Database
import io.ebean.typequery.PInteger
import io.ebean.typequery.PLocalDateTime
import io.ebean.typequery.PLong
import io.ebean.typequery.PString
import io.ebean.typequery.TQRootBean
import io.ebean.typequery.TypeQueryBean

/**
 * Query bean for Role.
 * 
 * THIS IS A GENERATED OBJECT, DO NOT MODIFY THIS CLASS.
 */
@TypeQueryBean
class QRole : TQRootBean<Role, QRole> {

  companion object {
    /**
     * shared 'Alias' instance used to provide
     * properties to select and fetch clauses
     */
    val _alias = QRole(true)
  }

  lateinit var id: PLong<QRole>
  lateinit var createdAt: PLocalDateTime<QRole>
  lateinit var updatedAt: PLocalDateTime<QRole>
  lateinit var version: PInteger<QRole>
  lateinit var name: PString<QRole>
  lateinit var tag: PString<QRole>
  lateinit var users: QAssocUser<QRole>


  /**
   * Construct with a given Database.
   */
  constructor(database: Database) : super(Role::class.java, database)

  /**
   * Construct using the default Database.
   */
  constructor() : super(Role::class.java)

  /**
   * Construct for Alias.
   */
  private constructor(dummy: Boolean) : super(dummy)
}
