package br.com.astrosoft.model.enderecamento.domain.query.assoc

import br.com.astrosoft.model.enderecamento.domain.EMovTipo
import br.com.astrosoft.model.enderecamento.domain.Movimentacao
import br.com.astrosoft.model.enderecamento.domain.query.QMovimentacao
import io.ebean.typequery.PEnum
import io.ebean.typequery.PInteger
import io.ebean.typequery.PLocalDate
import io.ebean.typequery.PLocalDateTime
import io.ebean.typequery.PLong
import io.ebean.typequery.PString
import io.ebean.typequery.TQAssocBean
import io.ebean.typequery.TQProperty
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

  /**
   * Eagerly fetch this association loading the specified properties.
   */
  fun fetch(vararg properties: TQProperty<QMovimentacao>) : R {
    return fetchProperties(*properties)
  }

  /**
   * Eagerly fetch this association using a 'query join' loading the specified properties.
   */
  fun fetchQuery(vararg properties: TQProperty<QMovimentacao>) : R {
    return fetchQueryProperties(*properties)
  }

  /**
   * Use lazy loading for this association loading the specified properties.
   */
  fun fetchLazy(vararg properties: TQProperty<QMovimentacao>) : R {
    return fetchLazyProperties(*properties)
  }

}
