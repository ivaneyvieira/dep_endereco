package br.com.astrosoft.model.enderecamento.domain.query

import br.com.astrosoft.model.enderecamento.domain.LayoutNivel
import io.ebean.EbeanServer
import io.ebean.typequery.PLong
import io.ebean.typequery.PString
import io.ebean.typequery.TQRootBean
import io.ebean.typequery.TypeQueryBean

/**
 * Query bean for LayoutNivel.
 * 
 * THIS IS A GENERATED OBJECT, DO NOT MODIFY THIS CLASS.
 */
@TypeQueryBean
class QLayoutNivel : TQRootBean<LayoutNivel, QLayoutNivel> {

  companion object {
    /**
     * shared 'Alias' instance used to provide
     * properties to select and fetch clauses
     */
    val _alias = QLayoutNivel(true)
  }

  lateinit var id: PLong<QLayoutNivel>
  lateinit var layout: PString<QLayoutNivel>


  /**
   * Construct with a given EbeanServer.
   */
  constructor(server: EbeanServer) : super(LayoutNivel::class.java, server)

  /**
   * Construct using the default EbeanServer.
   */
  constructor() : super(LayoutNivel::class.java)

  /**
   * Construct for Alias.
   */
  private constructor(dummy: Boolean) : super(dummy)
}
