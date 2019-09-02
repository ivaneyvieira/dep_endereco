package br.com.astrosoft.model.enderecamento.domain.query.assoc

import br.com.astrosoft.model.enderecamento.domain.MovProduto
import br.com.astrosoft.model.enderecamento.domain.query.QMovProduto
import io.ebean.typequery.PBigDecimal
import io.ebean.typequery.PInteger
import io.ebean.typequery.PLocalDateTime
import io.ebean.typequery.PLong
import io.ebean.typequery.TQAssocBean
import io.ebean.typequery.TQProperty
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

  /**
   * Eagerly fetch this association loading the specified properties.
   */
  fun fetch(vararg properties: TQProperty<QMovProduto>) : R {
    return fetchProperties(*properties)
  }

  /**
   * Eagerly fetch this association using a 'query join' loading the specified properties.
   */
  fun fetchQuery(vararg properties: TQProperty<QMovProduto>) : R {
    return fetchQueryProperties(*properties)
  }

  /**
   * Use lazy loading for this association loading the specified properties.
   */
  fun fetchLazy(vararg properties: TQProperty<QMovProduto>) : R {
    return fetchLazyProperties(*properties)
  }

}
