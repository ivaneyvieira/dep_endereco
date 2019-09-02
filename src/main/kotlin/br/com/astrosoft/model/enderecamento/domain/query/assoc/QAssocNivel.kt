package br.com.astrosoft.model.enderecamento.domain.query.assoc

import br.com.astrosoft.model.enderecamento.domain.ETipoNivel
import br.com.astrosoft.model.enderecamento.domain.Nivel
import br.com.astrosoft.model.enderecamento.domain.query.QNivel
import io.ebean.typequery.PBigDecimal
import io.ebean.typequery.PEnum
import io.ebean.typequery.PInteger
import io.ebean.typequery.PLocalDateTime
import io.ebean.typequery.PLong
import io.ebean.typequery.PString
import io.ebean.typequery.TQAssocBean
import io.ebean.typequery.TQProperty
import io.ebean.typequery.TypeQueryBean

/**
 * Association query bean for AssocNivel.
 * 
 * THIS IS A GENERATED OBJECT, DO NOT MODIFY THIS CLASS.
 */
@TypeQueryBean
class QAssocNivel<R>(name: String, root: R) : TQAssocBean<Nivel,R>(name, root) {

  lateinit var id: PLong<R>
  lateinit var createdAt: PLocalDateTime<R>
  lateinit var updatedAt: PLocalDateTime<R>
  lateinit var version: PInteger<R>
  lateinit var numero: PString<R>
  lateinit var altura: PBigDecimal<R>
  lateinit var tipoNivel: PEnum<R,ETipoNivel>
  lateinit var predio: QAssocPredio<R>
  lateinit var aptos: QAssocApto<R>
  lateinit var layout: QAssocLayoutNivel<R>

  /**
   * Eagerly fetch this association loading the specified properties.
   */
  fun fetch(vararg properties: TQProperty<QNivel>) : R {
    return fetchProperties(*properties)
  }

  /**
   * Eagerly fetch this association using a 'query join' loading the specified properties.
   */
  fun fetchQuery(vararg properties: TQProperty<QNivel>) : R {
    return fetchQueryProperties(*properties)
  }

  /**
   * Use lazy loading for this association loading the specified properties.
   */
  fun fetchLazy(vararg properties: TQProperty<QNivel>) : R {
    return fetchLazyProperties(*properties)
  }

}
