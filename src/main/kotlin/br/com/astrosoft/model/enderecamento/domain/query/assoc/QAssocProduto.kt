package br.com.astrosoft.model.enderecamento.domain.query.assoc

import br.com.astrosoft.model.enderecamento.domain.Produto
import br.com.astrosoft.model.enderecamento.domain.query.QProduto
import io.ebean.typequery.PBigDecimal
import io.ebean.typequery.PInteger
import io.ebean.typequery.PLocalDateTime
import io.ebean.typequery.PLong
import io.ebean.typequery.PString
import io.ebean.typequery.TQAssocBean
import io.ebean.typequery.TQProperty
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

  /**
   * Eagerly fetch this association loading the specified properties.
   */
  fun fetch(vararg properties: TQProperty<QProduto>) : R {
    return fetchProperties(*properties)
  }

  /**
   * Eagerly fetch this association using a 'query join' loading the specified properties.
   */
  fun fetchQuery(vararg properties: TQProperty<QProduto>) : R {
    return fetchQueryProperties(*properties)
  }

  /**
   * Use lazy loading for this association loading the specified properties.
   */
  fun fetchLazy(vararg properties: TQProperty<QProduto>) : R {
    return fetchLazyProperties(*properties)
  }

}
