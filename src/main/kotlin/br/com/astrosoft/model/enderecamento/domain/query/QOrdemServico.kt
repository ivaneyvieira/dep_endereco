package br.com.astrosoft.model.enderecamento.domain.query

import br.com.astrosoft.model.enderecamento.domain.OrdemServico
import br.com.astrosoft.model.enderecamento.domain.query.assoc.QAssocTransferencia
import br.com.astrosoft.model.enderecamento.domain.query.assoc.QAssocUser
import io.ebean.Database
import io.ebean.typequery.PInteger
import io.ebean.typequery.PLocalDateTime
import io.ebean.typequery.PLong
import io.ebean.typequery.TQRootBean
import io.ebean.typequery.TypeQueryBean

/**
 * Query bean for OrdemServico.
 * 
 * THIS IS A GENERATED OBJECT, DO NOT MODIFY THIS CLASS.
 */
@TypeQueryBean
class QOrdemServico : TQRootBean<OrdemServico, QOrdemServico> {

  companion object {
    /**
     * shared 'Alias' instance used to provide
     * properties to select and fetch clauses
     */
    val _alias = QOrdemServico(true)
  }

  lateinit var id: PLong<QOrdemServico>
  lateinit var createdAt: PLocalDateTime<QOrdemServico>
  lateinit var updatedAt: PLocalDateTime<QOrdemServico>
  lateinit var version: PInteger<QOrdemServico>
  lateinit var transferencia: QAssocTransferencia<QOrdemServico>
  lateinit var numero: PLong<QOrdemServico>
  lateinit var dataHora: PLocalDateTime<QOrdemServico>
  lateinit var dataHoraConf: PLocalDateTime<QOrdemServico>
  lateinit var user: QAssocUser<QOrdemServico>


  /**
   * Construct with a given Database.
   */
  constructor(database: Database) : super(OrdemServico::class.java, database)

  /**
   * Construct using the default Database.
   */
  constructor() : super(OrdemServico::class.java)

  /**
   * Construct for Alias.
   */
  private constructor(dummy: Boolean) : super(dummy)
}
