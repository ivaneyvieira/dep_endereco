package br.com.astrosoft.model.enderecamento.domain.query.assoc

import br.com.astrosoft.model.enderecamento.domain.ELado
import br.com.astrosoft.model.enderecamento.domain.EPalet
import br.com.astrosoft.model.enderecamento.domain.ETipoAltura
import br.com.astrosoft.model.enderecamento.domain.OcupacaoEndereco
import br.com.astrosoft.model.enderecamento.domain.query.QOcupacaoEndereco
import io.ebean.typequery.PEnum
import io.ebean.typequery.PLong
import io.ebean.typequery.PString
import io.ebean.typequery.TQAssocBean
import io.ebean.typequery.TQProperty
import io.ebean.typequery.TypeQueryBean

/**
 * Association query bean for AssocOcupacaoEndereco.
 * 
 * THIS IS A GENERATED OBJECT, DO NOT MODIFY THIS CLASS.
 */
@TypeQueryBean
class QAssocOcupacaoEndereco<R>(name: String, root: R) : TQAssocBean<OcupacaoEndereco,R>(name, root) {

  lateinit var id: PLong<R>
  lateinit var nivel: QAssocNivel<R>
  lateinit var predio: QAssocPredio<R>
  lateinit var rua: QAssocRua<R>
  lateinit var endereco: QAssocEndereco<R>
  lateinit var numero: PString<R>
  lateinit var tipoPalet: PEnum<R,EPalet>
  lateinit var tipoAltura: PEnum<R,ETipoAltura>
  lateinit var ocupado: PString<R>
  lateinit var lado: PEnum<R,ELado>

  /**
   * Eagerly fetch this association loading the specified properties.
   */
  fun fetch(vararg properties: TQProperty<QOcupacaoEndereco>) : R {
    return fetchProperties(*properties)
  }

  /**
   * Eagerly fetch this association using a 'query join' loading the specified properties.
   */
  fun fetchQuery(vararg properties: TQProperty<QOcupacaoEndereco>) : R {
    return fetchQueryProperties(*properties)
  }

  /**
   * Use lazy loading for this association loading the specified properties.
   */
  fun fetchLazy(vararg properties: TQProperty<QOcupacaoEndereco>) : R {
    return fetchLazyProperties(*properties)
  }

}
