package br.com.astrosoft.model.enderecamento.domain.query.assoc

import br.com.astrosoft.model.enderecamento.domain.Saldo
import br.com.astrosoft.model.enderecamento.domain.query.QSaldo
import io.ebean.typequery.PBigDecimal
import io.ebean.typequery.PInteger
import io.ebean.typequery.PLocalDate
import io.ebean.typequery.PLocalDateTime
import io.ebean.typequery.PLong
import io.ebean.typequery.TQAssocBean
import io.ebean.typequery.TQProperty
import io.ebean.typequery.TypeQueryBean

/**
 * Association query bean for AssocSaldo.
 * 
 * THIS IS A GENERATED OBJECT, DO NOT MODIFY THIS CLASS.
 */
@TypeQueryBean
class QAssocSaldo<R>(name: String, root: R) : TQAssocBean<Saldo,R>(name, root) {

  lateinit var id: PLong<R>
  lateinit var createdAt: PLocalDateTime<R>
  lateinit var updatedAt: PLocalDateTime<R>
  lateinit var version: PInteger<R>
  lateinit var capacidade: PBigDecimal<R>
  lateinit var saldoConfirmado: PBigDecimal<R>
  lateinit var saldoNConfirmado: PBigDecimal<R>
  lateinit var ultSaida: PLocalDate<R>
  lateinit var ultEntrada: PLocalDate<R>
  lateinit var endereco: QAssocEndereco<R>
  lateinit var produto: QAssocProduto<R>

  /**
   * Eagerly fetch this association loading the specified properties.
   */
  fun fetch(vararg properties: TQProperty<QSaldo>) : R {
    return fetchProperties(*properties)
  }

  /**
   * Eagerly fetch this association using a 'query join' loading the specified properties.
   */
  fun fetchQuery(vararg properties: TQProperty<QSaldo>) : R {
    return fetchQueryProperties(*properties)
  }

  /**
   * Use lazy loading for this association loading the specified properties.
   */
  fun fetchLazy(vararg properties: TQProperty<QSaldo>) : R {
    return fetchLazyProperties(*properties)
  }

}
