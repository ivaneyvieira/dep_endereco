package br.com.astrosoft.model.enderecamento.domain.finder

import br.com.astrosoft.model.enderecamento.domain.LayoutNivel
import br.com.astrosoft.model.enderecamento.domain.query.QLayoutNivel
import io.ebean.Finder

open class LayoutNivelFinder : Finder<Long, LayoutNivel>(LayoutNivel::class.java) {

  val alias = QLayoutNivel._alias

  /**
   * Start a new typed query.
   */
  fun where(): QLayoutNivel {
     return QLayoutNivel(db())
  }

  /**
   * Start a new document store query.
   */
  fun text(): QLayoutNivel {
     return QLayoutNivel(db()).text()
  }
}
