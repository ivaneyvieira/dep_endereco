package br.com.astrosoft.model.enderecamento.domain.query.assoc

import br.com.astrosoft.model.enderecamento.domain.Produto
import io.ebean.typequery.PBigDecimal
import io.ebean.typequery.PInteger
import io.ebean.typequery.PLocalDateTime
import io.ebean.typequery.PLong
import io.ebean.typequery.PString
import io.ebean.typequery.TQAssocBean
import io.ebean.typequery.TypeQueryBean

/**
 * Association query bean for AssocProduto.
 * 
 * THIS IS A GENERATED OBJECT, DO NOT MODIFY THIS CLASS.
 */
@TypeQueryBean
class QAssocProduto<R>(name: String, root: R) : TQAssocBean<Produto,R>(name, root) {

  lateinit var id: PLong<R>
  lateinit var createdAt: PLocalDateTime<R>
  lateinit var updatedAt: PLocalDateTime<R>
  lateinit var version: PInteger<R>
  lateinit var codbar: PString<R>
  lateinit var grade: PString<R>
  lateinit var nome: PString<R>
  lateinit var prdno: PString<R>
  lateinit var clno: PInteger<R>
  lateinit var vendno: PInteger<R>
  lateinit var custo: PBigDecimal<R>
  lateinit var preco: PBigDecimal<R>
  lateinit var quantVolumes: PInteger<R>
  lateinit var estoqueMinimo: PBigDecimal<R>
  lateinit var saldos: QAssocSaldo<R>
  lateinit var movProdutos: QAssocMovProduto<R>

  // type safe fetch(properties) using varargs not supported yet ...
}
