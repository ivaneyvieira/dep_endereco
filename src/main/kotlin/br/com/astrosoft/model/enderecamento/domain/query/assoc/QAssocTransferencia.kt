package br.com.astrosoft.model.enderecamento.domain.query.assoc

import br.com.astrosoft.model.enderecamento.domain.Transferencia
import io.ebean.typequery.PBigDecimal
import io.ebean.typequery.PBoolean
import io.ebean.typequery.PInteger
import io.ebean.typequery.PLocalDateTime
import io.ebean.typequery.PLong
import io.ebean.typequery.PString
import io.ebean.typequery.TQAssocBean
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

  // type safe fetch(properties) using varargs not supported yet ...
}
