package br.com.astrosoft.model.enderecamento.domain.query

import br.com.astrosoft.model.enderecamento.domain.Rua
import br.com.astrosoft.model.enderecamento.domain.query.assoc.QAssocPredio
import io.ebean.EbeanServer
import io.ebean.typequery.PInteger
import io.ebean.typequery.PLocalDateTime
import io.ebean.typequery.PLong
import io.ebean.typequery.PString
import io.ebean.typequery.TQRootBean
import io.ebean.typequery.TypeQueryBean

/**
 * Query bean for Rua.
 * 
 * THIS IS A GENERATED OBJECT, DO NOT MODIFY THIS CLASS.
 */
@TypeQueryBean
class QRua : TQRootBean<Rua, QRua> {

  companion object {
    /**
     * shared 'Alias' instance used to provide
     * properties to select and fetch clauses
     */
    val _alias = QRua(true)
  }

  lateinit var id: PLong<QRua>
  lateinit var createdAt: PLocalDateTime<QRua>
  lateinit var updatedAt: PLocalDateTime<QRua>
  lateinit var version: PInteger<QRua>
  lateinit var numero: PString<QRua>
  lateinit var predios: QAssocPredio<QRua>


  /**
   * Construct with a given EbeanServer.
   */
  constructor(server: EbeanServer) : super(Rua::class.java, server)

  /**
   * Construct using the default EbeanServer.
   */
  constructor() : super(Rua::class.java)

  /**
   * Construct for Alias.
   */
  private constructor(dummy: Boolean) : super(dummy)
}
