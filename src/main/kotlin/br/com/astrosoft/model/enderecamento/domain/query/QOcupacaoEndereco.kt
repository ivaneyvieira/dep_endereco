package br.com.astrosoft.model.enderecamento.domain.query

import br.com.astrosoft.model.enderecamento.domain.ELado
import br.com.astrosoft.model.enderecamento.domain.EPalet
import br.com.astrosoft.model.enderecamento.domain.ETipoAltura
import br.com.astrosoft.model.enderecamento.domain.OcupacaoEndereco
import br.com.astrosoft.model.enderecamento.domain.query.assoc.QAssocEndereco
import br.com.astrosoft.model.enderecamento.domain.query.assoc.QAssocNivel
import br.com.astrosoft.model.enderecamento.domain.query.assoc.QAssocPredio
import br.com.astrosoft.model.enderecamento.domain.query.assoc.QAssocRua
import io.ebean.EbeanServer
import io.ebean.typequery.PEnum
import io.ebean.typequery.PLong
import io.ebean.typequery.PString
import io.ebean.typequery.TQRootBean
import io.ebean.typequery.TypeQueryBean

/**
 * Query bean for OcupacaoEndereco.
 * 
 * THIS IS A GENERATED OBJECT, DO NOT MODIFY THIS CLASS.
 */
@TypeQueryBean
class QOcupacaoEndereco : TQRootBean<OcupacaoEndereco, QOcupacaoEndereco> {

  companion object {
    /**
     * shared 'Alias' instance used to provide
     * properties to select and fetch clauses
     */
    val _alias = QOcupacaoEndereco(true)
  }

  lateinit var id: PLong<QOcupacaoEndereco>
  lateinit var nivel: QAssocNivel<QOcupacaoEndereco>
  lateinit var predio: QAssocPredio<QOcupacaoEndereco>
  lateinit var rua: QAssocRua<QOcupacaoEndereco>
  lateinit var endereco: QAssocEndereco<QOcupacaoEndereco>
  lateinit var numero: PString<QOcupacaoEndereco>
  lateinit var tipoPalet: PEnum<QOcupacaoEndereco,EPalet>
  lateinit var tipoAltura: PEnum<QOcupacaoEndereco,ETipoAltura>
  lateinit var ocupado: PString<QOcupacaoEndereco>
  lateinit var lado: PEnum<QOcupacaoEndereco,ELado>


  /**
   * Construct with a given EbeanServer.
   */
  constructor(server: EbeanServer) : super(OcupacaoEndereco::class.java, server)

  /**
   * Construct using the default EbeanServer.
   */
  constructor() : super(OcupacaoEndereco::class.java)

  /**
   * Construct for Alias.
   */
  private constructor(dummy: Boolean) : super(dummy)
}
