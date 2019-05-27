package br.com.astrosoft.model.enderecamento.domain.query

import br.com.astrosoft.model.enderecamento.domain.EMovTipo
import br.com.astrosoft.model.enderecamento.domain.Movimentacao
import br.com.astrosoft.model.enderecamento.domain.query.assoc.QAssocMovProduto
import io.ebean.EbeanServer
import io.ebean.typequery.PEnum
import io.ebean.typequery.PInteger
import io.ebean.typequery.PLocalDate
import io.ebean.typequery.PLocalDateTime
import io.ebean.typequery.PLong
import io.ebean.typequery.PString
import io.ebean.typequery.TQRootBean
import io.ebean.typequery.TypeQueryBean

/**
 * Query bean for Movimentacao.
 * 
 * THIS IS A GENERATED OBJECT, DO NOT MODIFY THIS CLASS.
 */
@TypeQueryBean
class QMovimentacao : TQRootBean<Movimentacao, QMovimentacao> {

  companion object {
    /**
     * shared 'Alias' instance used to provide
     * properties to select and fetch clauses
     */
    val _alias = QMovimentacao(true)
  }

  lateinit var id: PLong<QMovimentacao>
  lateinit var createdAt: PLocalDateTime<QMovimentacao>
  lateinit var updatedAt: PLocalDateTime<QMovimentacao>
  lateinit var version: PInteger<QMovimentacao>
  lateinit var chave: PString<QMovimentacao>
  lateinit var documento: PString<QMovimentacao>
  lateinit var data: PLocalDate<QMovimentacao>
  lateinit var observacao: PString<QMovimentacao>
  lateinit var tipoMov: PEnum<QMovimentacao,EMovTipo>
  lateinit var movProdutos: QAssocMovProduto<QMovimentacao>


  /**
   * Construct with a given EbeanServer.
   */
  constructor(server: EbeanServer) : super(Movimentacao::class.java, server)

  /**
   * Construct using the default EbeanServer.
   */
  constructor() : super(Movimentacao::class.java)

  /**
   * Construct for Alias.
   */
  private constructor(dummy: Boolean) : super(dummy)
}
