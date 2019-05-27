package br.com.astrosoft.model.enderecamento.domain.query

import br.com.astrosoft.model.enderecamento.domain.EEntradaSaida
import br.com.astrosoft.model.enderecamento.domain.EOperacaoKardec
import br.com.astrosoft.model.enderecamento.domain.ETipoKardec
import br.com.astrosoft.model.enderecamento.domain.Kardec
import br.com.astrosoft.model.enderecamento.domain.query.assoc.QAssocProduto
import io.ebean.EbeanServer
import io.ebean.typequery.PBigDecimal
import io.ebean.typequery.PEnum
import io.ebean.typequery.PInteger
import io.ebean.typequery.PLocalDate
import io.ebean.typequery.PLocalDateTime
import io.ebean.typequery.PLong
import io.ebean.typequery.TQRootBean
import io.ebean.typequery.TypeQueryBean

/**
 * Query bean for Kardec.
 * 
 * THIS IS A GENERATED OBJECT, DO NOT MODIFY THIS CLASS.
 */
@TypeQueryBean
class QKardec : TQRootBean<Kardec, QKardec> {

  companion object {
    /**
     * shared 'Alias' instance used to provide
     * properties to select and fetch clauses
     */
    val _alias = QKardec(true)
  }

  lateinit var id: PLong<QKardec>
  lateinit var createdAt: PLocalDateTime<QKardec>
  lateinit var updatedAt: PLocalDateTime<QKardec>
  lateinit var version: PInteger<QKardec>
  lateinit var procduto: QAssocProduto<QKardec>
  lateinit var date: PLocalDate<QKardec>
  lateinit var tipo: PEnum<QKardec,ETipoKardec>
  lateinit var operacao: PEnum<QKardec,EOperacaoKardec>
  lateinit var es: PEnum<QKardec,EEntradaSaida>
  lateinit var quant: PBigDecimal<QKardec>


  /**
   * Construct with a given EbeanServer.
   */
  constructor(server: EbeanServer) : super(Kardec::class.java, server)

  /**
   * Construct using the default EbeanServer.
   */
  constructor() : super(Kardec::class.java)

  /**
   * Construct for Alias.
   */
  private constructor(dummy: Boolean) : super(dummy)
}
