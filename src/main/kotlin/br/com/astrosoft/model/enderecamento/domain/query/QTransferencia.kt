package br.com.astrosoft.model.enderecamento.domain.query

import br.com.astrosoft.model.enderecamento.domain.Transferencia
import br.com.astrosoft.model.enderecamento.domain.query.assoc.QAssocEndereco
import br.com.astrosoft.model.enderecamento.domain.query.assoc.QAssocMovProduto
import br.com.astrosoft.model.enderecamento.domain.query.assoc.QAssocOrdemServico
import io.ebean.EbeanServer
import io.ebean.typequery.PBigDecimal
import io.ebean.typequery.PBoolean
import io.ebean.typequery.PInteger
import io.ebean.typequery.PLocalDateTime
import io.ebean.typequery.PLong
import io.ebean.typequery.PString
import io.ebean.typequery.TQRootBean
import io.ebean.typequery.TypeQueryBean

/**
 * Query bean for Transferencia.
 * 
 * THIS IS A GENERATED OBJECT, DO NOT MODIFY THIS CLASS.
 */
@TypeQueryBean
class QTransferencia : TQRootBean<Transferencia, QTransferencia> {

  companion object {
    /**
     * shared 'Alias' instance used to provide
     * properties to select and fetch clauses
     */
    val _alias = QTransferencia(true)
  }

  lateinit var id: PLong<QTransferencia>
  lateinit var createdAt: PLocalDateTime<QTransferencia>
  lateinit var updatedAt: PLocalDateTime<QTransferencia>
  lateinit var version: PInteger<QTransferencia>
  lateinit var dataHoraMov: PLocalDateTime<QTransferencia>
  lateinit var observacao: PString<QTransferencia>
  lateinit var quantMov: PBigDecimal<QTransferencia>
  lateinit var confirmacao: PBoolean<QTransferencia>
  lateinit var enderecoE: QAssocEndereco<QTransferencia>
  lateinit var movProduto: QAssocMovProduto<QTransferencia>
  lateinit var enderecoS: QAssocEndereco<QTransferencia>
  lateinit var ordemServico: QAssocOrdemServico<QTransferencia>


  /**
   * Construct with a given EbeanServer.
   */
  constructor(server: EbeanServer) : super(Transferencia::class.java, server)

  /**
   * Construct using the default EbeanServer.
   */
  constructor() : super(Transferencia::class.java)

  /**
   * Construct for Alias.
   */
  private constructor(dummy: Boolean) : super(dummy)
}
