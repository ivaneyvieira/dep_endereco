package br.com.astrosoft.model.enderecamento.domain.query.assoc

import br.com.astrosoft.model.enderecamento.domain.MovProduto
import io.ebean.typequery.PBigDecimal
import io.ebean.typequery.PInteger
import io.ebean.typequery.PLocalDateTime
import io.ebean.typequery.PLong
import io.ebean.typequery.TQAssocBean
import io.ebean.typequery.TypeQueryBean

/**
 * Association query bean for AssocMovProduto.
 * 
 * THIS IS A GENERATED OBJECT, DO NOT MODIFY THIS CLASS.
 */
@TypeQueryBean
class QAssocMovProduto<R>(name: String, root: R) : TQAssocBean<MovProduto,R>(name, root) {

  lateinit var id: PLong<R>
  lateinit var createdAt: PLocalDateTime<R>
  lateinit var updatedAt: PLocalDateTime<R>
  lateinit var version: PInteger<R>
  lateinit var quantCan: PBigDecimal<R>
  lateinit var quantMov: PBigDecimal<R>
  lateinit var quantPalete: PBigDecimal<R>
  lateinit var movimentacao: QAssocMovimentacao<R>
  lateinit var produto: QAssocProduto<R>
  lateinit var transferencias: QAssocTransferencia<R>
  lateinit var sumQuantTransf: PBigDecimal<R>

  // type safe fetch(properties) using varargs not supported yet ...
}
