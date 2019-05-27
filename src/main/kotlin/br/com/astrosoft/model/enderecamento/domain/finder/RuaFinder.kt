package br.com.astrosoft.model.enderecamento.domain.finder

import br.com.astrosoft.model.enderecamento.domain.Rua
import br.com.astrosoft.model.enderecamento.domain.query.QRua
import io.ebean.Finder

open class RuaFinder : Finder<Long, Rua>(Rua::class.java) {

  val alias = QRua._alias

  /**
   * Start a new typed query.
   */
  fun where(): QRua {
     return QRua(db())
  }

  /**
   * Start a new document store query.
   */
  fun text(): QRua {
     return QRua(db()).text()
  }
}
