package br.com.astrosoft.model.enderecamento.domain.query.assoc

import br.com.astrosoft.model.enderecamento.domain.EEntradaSaida
import br.com.astrosoft.model.enderecamento.domain.EOperacaoKardec
import br.com.astrosoft.model.enderecamento.domain.ETipoKardec
import br.com.astrosoft.model.enderecamento.domain.Kardec
import io.ebean.typequery.PBigDecimal
import io.ebean.typequery.PEnum
import io.ebean.typequery.PInteger
import io.ebean.typequery.PLocalDate
import io.ebean.typequery.PLocalDateTime
import io.ebean.typequery.PLong
import io.ebean.typequery.TQAssocBean
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

  // type safe fetch(properties) using varargs not supported yet ...
}
