package br.com.astrosoft.model.enderecamento.domain.query.assoc

import br.com.astrosoft.model.enderecamento.domain.ETipoEndereco
import br.com.astrosoft.model.enderecamento.domain.ETipoNivel
import br.com.astrosoft.model.enderecamento.domain.Endereco
import br.com.astrosoft.model.enderecamento.domain.query.QEndereco
import io.ebean.typequery.PEnum
import io.ebean.typequery.PInteger
import io.ebean.typequery.PLocalDateTime
import io.ebean.typequery.PLong
import io.ebean.typequery.PString
import io.ebean.typequery.TQAssocBean
import io.ebean.typequery.TQProperty
import io.ebean.typequery.TypeQueryBean

/**
 * Association query bean for AssocEndereco.
 * 
 * THIS IS A GENERATED OBJECT, DO NOT MODIFY THIS CLASS.
 */
@TypeQueryBean
class QAssocEndereco<R>(name: String, root: R) : TQAssocBean<Endereco,R>(name, root) {

  lateinit var id: PLong<R>
  lateinit var createdAt: PLocalDateTime<R>
  lateinit var updatedAt: PLocalDateTime<R>
  lateinit var version: PInteger<R>
  lateinit var tipoEndereco: PEnum<R,ETipoEndereco>
  lateinit var observacao: PString<R>
  lateinit var localizacao: PString<R>
  lateinit var tipoNivel: PEnum<R,ETipoNivel>
  lateinit var apto: QAssocApto<R>
  lateinit var saldos: QAssocSaldo<R>
  lateinit var descricao: PString<R>

  /**
   * Eagerly fetch this association loading the specified properties.
   */
  fun fetch(vararg properties: TQProperty<QEndereco>) : R {
    return fetchProperties(*properties)
  }

  /**
   * Eagerly fetch this association using a 'query join' loading the specified properties.
   */
  fun fetchQuery(vararg properties: TQProperty<QEndereco>) : R {
    return fetchQueryProperties(*properties)
  }

  /**
   * Use lazy loading for this association loading the specified properties.
   */
  fun fetchLazy(vararg properties: TQProperty<QEndereco>) : R {
    return fetchLazyProperties(*properties)
  }

}
