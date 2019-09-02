package br.com.astrosoft.model.enderecamento.domain.query

import br.com.astrosoft.model.enderecamento.domain.ETipoNivel
import br.com.astrosoft.model.enderecamento.domain.Nivel
import br.com.astrosoft.model.enderecamento.domain.query.assoc.QAssocApto
import br.com.astrosoft.model.enderecamento.domain.query.assoc.QAssocLayoutNivel
import br.com.astrosoft.model.enderecamento.domain.query.assoc.QAssocPredio
import io.ebean.Database
import io.ebean.typequery.PBigDecimal
import io.ebean.typequery.PEnum
import io.ebean.typequery.PInteger
import io.ebean.typequery.PLocalDateTime
import io.ebean.typequery.PLong
import io.ebean.typequery.PString
import io.ebean.typequery.TQRootBean
import io.ebean.typequery.TypeQueryBean

/**
 * Query bean for Nivel.
 * 
 * THIS IS A GENERATED OBJECT, DO NOT MODIFY THIS CLASS.
 */
@TypeQueryBean
class QNivel : TQRootBean<Nivel, QNivel> {

  companion object {
    /**
     * shared 'Alias' instance used to provide
     * properties to select and fetch clauses
     */
    val _alias = QNivel(true)
  }

  lateinit var id: PLong<QNivel>
  lateinit var createdAt: PLocalDateTime<QNivel>
  lateinit var updatedAt: PLocalDateTime<QNivel>
  lateinit var version: PInteger<QNivel>
  lateinit var numero: PString<QNivel>
  lateinit var altura: PBigDecimal<QNivel>
  lateinit var tipoNivel: PEnum<QNivel,ETipoNivel>
  lateinit var predio: QAssocPredio<QNivel>
  lateinit var aptos: QAssocApto<QNivel>
  lateinit var layout: QAssocLayoutNivel<QNivel>


  /**
   * Construct with a given Database.
   */
  constructor(database: Database) : super(Nivel::class.java, database)

  /**
   * Construct using the default Database.
   */
  constructor() : super(Nivel::class.java)

  /**
   * Construct for Alias.
   */
  private constructor(dummy: Boolean) : super(dummy)
}
