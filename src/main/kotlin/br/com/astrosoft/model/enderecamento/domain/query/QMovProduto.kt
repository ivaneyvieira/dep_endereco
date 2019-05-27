package br.com.astrosoft.model.enderecamento.domain.query

import br.com.astrosoft.model.enderecamento.domain.MovProduto
import br.com.astrosoft.model.enderecamento.domain.query.assoc.QAssocMovimentacao
import br.com.astrosoft.model.enderecamento.domain.query.assoc.QAssocProduto
import br.com.astrosoft.model.enderecamento.domain.query.assoc.QAssocTransferencia
import io.ebean.EbeanServer
import io.ebean.typequery.PBigDecimal
import io.ebean.typequery.PInteger
import io.ebean.typequery.PLocalDateTime
import io.ebean.typequery.PLong
import io.ebean.typequery.TQRootBean
import io.ebean.typequery.TypeQueryBean

/**
 * Query bean for MovProduto.
 * 
 * THIS IS A GENERATED OBJECT, DO NOT MODIFY THIS CLASS.
 */
@TypeQueryBean
class QMovProduto : TQRootBean<MovProduto, QMovProduto> {

  companion object {
    /**
     * shared 'Alias' instance used to provide
     * properties to select and fetch clauses
     */
    val _alias = QMovProduto(true)
  }

  lateinit var id: PLong<QMovProduto>
  lateinit var createdAt: PLocalDateTime<QMovProduto>
  lateinit var updatedAt: PLocalDateTime<QMovProduto>
  lateinit var version: PInteger<QMovProduto>
  lateinit var quantCan: PBigDecimal<QMovProduto>
  lateinit var quantMov: PBigDecimal<QMovProduto>
  lateinit var quantPalete: PBigDecimal<QMovProduto>
  lateinit var movimentacao: QAssocMovimentacao<QMovProduto>
  lateinit var produto: QAssocProduto<QMovProduto>
  lateinit var transferencias: QAssocTransferencia<QMovProduto>
  lateinit var sumQuantTransf: PBigDecimal<QMovProduto>


  /**
   * Construct with a given EbeanServer.
   */
  constructor(server: EbeanServer) : super(MovProduto::class.java, server)

  /**
   * Construct using the default EbeanServer.
   */
  constructor() : super(MovProduto::class.java)

  /**
   * Construct for Alias.
   */
  private constructor(dummy: Boolean) : super(dummy)
}
