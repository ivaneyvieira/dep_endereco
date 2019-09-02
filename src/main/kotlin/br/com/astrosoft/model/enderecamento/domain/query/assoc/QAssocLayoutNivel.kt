package br.com.astrosoft.model.enderecamento.domain.query.assoc

import br.com.astrosoft.model.enderecamento.domain.LayoutNivel
import br.com.astrosoft.model.enderecamento.domain.query.QLayoutNivel
import io.ebean.typequery.PInteger
import io.ebean.typequery.PLong
import io.ebean.typequery.PString
import io.ebean.typequery.TQAssocBean
import io.ebean.typequery.TQProperty
import io.ebean.typequery.TypeQueryBean

/**
 * Association query bean for AssocLayoutNivel.
 * 
 * THIS IS A GENERATED OBJECT, DO NOT MODIFY THIS CLASS.
 */
@TypeQueryBean
class QAssocLayoutNivel<R>(name: String, root: R) : TQAssocBean<LayoutNivel,R>(name, root) {

  lateinit var id: PLong<R>
  lateinit var layout: PString<R>
  lateinit var total: PInteger<R>
  lateinit var nivel: QAssocNivel<R>

  /**
   * Eagerly fetch this association loading the specified properties.
   */
  fun fetch(vararg properties: TQProperty<QLayoutNivel>) : R {
    return fetchProperties(*properties)
  }

  /**
   * Eagerly fetch this association using a 'query join' loading the specified properties.
   */
  fun fetchQuery(vararg properties: TQProperty<QLayoutNivel>) : R {
    return fetchQueryProperties(*properties)
  }

  /**
   * Use lazy loading for this association loading the specified properties.
   */
  fun fetchLazy(vararg properties: TQProperty<QLayoutNivel>) : R {
    return fetchLazyProperties(*properties)
  }

}
