package br.com.astrosoft.model.enderecamento.domain.query.assoc

import br.com.astrosoft.model.enderecamento.domain.Transferencia
import br.com.astrosoft.model.enderecamento.domain.query.QTransferencia
import io.ebean.typequery.PBigDecimal
import io.ebean.typequery.PBoolean
import io.ebean.typequery.PInteger
import io.ebean.typequery.PLocalDateTime
import io.ebean.typequery.PLong
import io.ebean.typequery.PString
import io.ebean.typequery.TQAssocBean
import io.ebean.typequery.TQProperty
import io.ebean.typequery.TypeQueryBean

/**
 * Association query bean for AssocTransferencia.
 * 
 * THIS IS A GENERATED OBJECT, DO NOT MODIFY THIS CLASS.
 */
@TypeQueryBean
class QAssocTransferencia<R>(name: String, root: R) : TQAssocBean<Transferencia,R>(name, root) {

  lateinit var id: PLong<R>
  lateinit var createdAt: PLocalDateTime<R>
  lateinit var updatedAt: PLocalDateTime<R>
  lateinit var version: PInteger<R>
  lateinit var dataHoraMov: PLocalDateTime<R>
  lateinit var observacao: PString<R>
  lateinit var quantMov: PBigDecimal<R>
  lateinit var confirmacao: PBoolean<R>
  lateinit var enderecoE: QAssocEndereco<R>
  lateinit var movProduto: QAssocMovProduto<R>
  lateinit var enderecoS: QAssocEndereco<R>
  lateinit var ordemServico: QAssocOrdemServico<R>

  /**
   * Eagerly fetch this association loading the specified properties.
   */
  fun fetch(vararg properties: TQProperty<QTransferencia>) : R {
    return fetchProperties(*properties)
  }

  /**
   * Eagerly fetch this association using a 'query join' loading the specified properties.
   */
  fun fetchQuery(vararg properties: TQProperty<QTransferencia>) : R {
    return fetchQueryProperties(*properties)
  }

  /**
   * Use lazy loading for this association loading the specified properties.
   */
  fun fetchLazy(vararg properties: TQProperty<QTransferencia>) : R {
    return fetchLazyProperties(*properties)
  }

}
