package br.com.astrosoft.model.enderecamento.domain.query.assoc

import br.com.astrosoft.model.enderecamento.domain.ELado
import br.com.astrosoft.model.enderecamento.domain.EPalet
import br.com.astrosoft.model.enderecamento.domain.ETipoAltura
import br.com.astrosoft.model.enderecamento.domain.OcupacaoEndereco
import io.ebean.typequery.PEnum
import io.ebean.typequery.PLong
import io.ebean.typequery.PString
import io.ebean.typequery.TQAssocBean
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

  // type safe fetch(properties) using varargs not supported yet ...
}
