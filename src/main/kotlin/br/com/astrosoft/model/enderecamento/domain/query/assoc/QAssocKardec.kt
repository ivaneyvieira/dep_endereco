package br.com.astrosoft.model.enderecamento.domain.query.assoc

import br.com.astrosoft.model.enderecamento.domain.EEntradaSaida
import br.com.astrosoft.model.enderecamento.domain.EOperacaoKardec
import br.com.astrosoft.model.enderecamento.domain.ETipoKardec
import br.com.astrosoft.model.enderecamento.domain.Kardec
import br.com.astrosoft.model.enderecamento.domain.query.QKardec
import io.ebean.typequery.PBigDecimal
import io.ebean.typequery.PEnum
import io.ebean.typequery.PInteger
import io.ebean.typequery.PLocalDate
import io.ebean.typequery.PLocalDateTime
import io.ebean.typequery.PLong
import io.ebean.typequery.TQAssocBean
import io.ebean.typequery.TQProperty
import io.ebean.typequery.TypeQueryBean

/**
 * Association query bean for AssocKardec.
 * 
 * THIS IS A GENERATED OBJECT, DO NOT MODIFY THIS CLASS.
 */
@TypeQueryBean
class QAssocKardec<R>(name: String, root: R) : TQAssocBean<Kardec,R>(name, root) {

  lateinit var id: PLong<R>
  lateinit var createdAt: PLocalDateTime<R>
  lateinit var updatedAt: PLocalDateTime<R>
  lateinit var version: PInteger<R>
  lateinit var procduto: QAssocProduto<R>
  lateinit var date: PLocalDate<R>
  lateinit var tipo: PEnum<R,ETipoKardec>
  lateinit var operacao: PEnum<R,EOperacaoKardec>
  lateinit var es: PEnum<R,EEntradaSaida>
  lateinit var quant: PBigDecimal<R>

  /**
   * Eagerly fetch this association loading the specified properties.
   */
  fun fetch(vararg properties: TQProperty<QKardec>) : R {
    return fetchProperties(*properties)
  }

  /**
   * Eagerly fetch this association using a 'query join' loading the specified properties.
   */
  fun fetchQuery(vararg properties: TQProperty<QKardec>) : R {
    return fetchQueryProperties(*properties)
  }

  /**
   * Use lazy loading for this association loading the specified properties.
   */
  fun fetchLazy(vararg properties: TQProperty<QKardec>) : R {
    return fetchLazyProperties(*properties)
  }

}
