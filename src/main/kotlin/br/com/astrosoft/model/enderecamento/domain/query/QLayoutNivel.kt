package br.com.astrosoft.model.enderecamento.domain.query

import br.com.astrosoft.model.enderecamento.domain.LayoutNivel
import br.com.astrosoft.model.enderecamento.domain.query.assoc.QAssocNivel
import io.ebean.Database
import io.ebean.typequery.PInteger
import io.ebean.typequery.PLong
import io.ebean.typequery.PString
import io.ebean.typequery.TQRootBean
import io.ebean.typequery.TypeQueryBean

/**
 * Query bean for LayoutNivel.
 * 
 * THIS IS A GENERATED OBJECT, DO NOT MODIFY THIS CLASS.
 */
@TypeQueryBean
class QLayoutNivel : TQRootBean<LayoutNivel, QLayoutNivel> {

  companion object {
    /**
     * shared 'Alias' instance used to provide
     * properties to select and fetch clauses
     */
    val _alias = QLayoutNivel(true)
  }

  lateinit var id: PLong<QLayoutNivel>
  lateinit var layout: PString<QLayoutNivel>
  lateinit var total: PInteger<QLayoutNivel>
  lateinit var nivel: QAssocNivel<QLayoutNivel>


  /**
   * Construct with a given Database.
   */
  constructor(database: Database) : super(LayoutNivel::class.java, database)

  /**
   * Construct using the default Database.
   */
  constructor() : super(LayoutNivel::class.java)

  /**
   * Construct for Alias.
   */
  private constructor(dummy: Boolean) : super(dummy)
}
