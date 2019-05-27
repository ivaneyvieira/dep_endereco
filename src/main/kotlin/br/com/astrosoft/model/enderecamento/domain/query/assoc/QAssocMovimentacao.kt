package br.com.astrosoft.model.enderecamento.domain.query.assoc

import br.com.astrosoft.model.enderecamento.domain.EMovTipo
import br.com.astrosoft.model.enderecamento.domain.Movimentacao
import io.ebean.typequery.PEnum
import io.ebean.typequery.PInteger
import io.ebean.typequery.PLocalDate
import io.ebean.typequery.PLocalDateTime
import io.ebean.typequery.PLong
import io.ebean.typequery.PString
import io.ebean.typequery.TQAssocBean
import io.ebean.typequery.TypeQueryBean

/**
 * Association query bean for AssocMovimentacao.
 * 
 * THIS IS A GENERATED OBJECT, DO NOT MODIFY THIS CLASS.
 */
@TypeQueryBean
class QAssocMovimentacao<R>(name: String, root: R) : TQAssocBean<Movimentacao,R>(name, root) {

  lateinit var id: PLong<R>
  lateinit var createdAt: PLocalDateTime<R>
  lateinit var updatedAt: PLocalDateTime<R>
  lateinit var version: PInteger<R>
  lateinit var chave: PString<R>
  lateinit var documento: PString<R>
  lateinit var data: PLocalDate<R>
  lateinit var observacao: PString<R>
  lateinit var tipoMov: PEnum<R,EMovTipo>
  lateinit var movProdutos: QAssocMovProduto<R>

  // type safe fetch(properties) using varargs not supported yet ...
}
