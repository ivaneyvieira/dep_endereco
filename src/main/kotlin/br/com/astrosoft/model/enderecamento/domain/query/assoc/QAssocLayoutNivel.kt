package br.com.astrosoft.model.enderecamento.domain.query.assoc

import br.com.astrosoft.model.enderecamento.domain.LayoutNivel
import io.ebean.typequery.PLong
import io.ebean.typequery.PString
import io.ebean.typequery.TQAssocBean
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

  // type safe fetch(properties) using varargs not supported yet ...
}
