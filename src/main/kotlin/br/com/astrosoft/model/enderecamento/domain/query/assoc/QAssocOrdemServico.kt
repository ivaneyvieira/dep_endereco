package br.com.astrosoft.model.enderecamento.domain.query.assoc

import br.com.astrosoft.model.enderecamento.domain.OrdemServico
import io.ebean.typequery.PInteger
import io.ebean.typequery.PLocalDateTime
import io.ebean.typequery.PLong
import io.ebean.typequery.TQAssocBean
import io.ebean.typequery.TypeQueryBean

/**
 * Association query bean for AssocOrdemServico.
 * 
 * THIS IS A GENERATED OBJECT, DO NOT MODIFY THIS CLASS.
 */
@TypeQueryBean
class QAssocOrdemServico<R>(name: String, root: R) : TQAssocBean<OrdemServico,R>(name, root) {

  lateinit var id: PLong<R>
  lateinit var createdAt: PLocalDateTime<R>
  lateinit var updatedAt: PLocalDateTime<R>
  lateinit var version: PInteger<R>
  lateinit var transferencia: QAssocTransferencia<R>
  lateinit var numero: PLong<R>
  lateinit var dataHora: PLocalDateTime<R>
  lateinit var dataHoraConf: PLocalDateTime<R>
  lateinit var user: QAssocUser<R>

  // type safe fetch(properties) using varargs not supported yet ...
}
