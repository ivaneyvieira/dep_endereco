package br.com.astrosoft.model.enderecamento.domain.finder

import br.com.astrosoft.model.enderecamento.domain.Nivel
import br.com.astrosoft.model.enderecamento.domain.query.QNivel
import io.ebean.Finder

open class NivelFinder : Finder<Long, Nivel>(Nivel::class.java) {

  val alias = QNivel._alias

  /**
   * Start a new typed query.
   */
  fun where(): QNivel {
     return QNivel(db())
  }

  /**
   * Start a new document store query.
   */
  fun text(): QNivel {
     return QNivel(db()).text()
  }
}
