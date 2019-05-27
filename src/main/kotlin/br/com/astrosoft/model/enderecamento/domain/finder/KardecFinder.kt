package br.com.astrosoft.model.enderecamento.domain.finder

import br.com.astrosoft.model.enderecamento.domain.Kardec
import br.com.astrosoft.model.enderecamento.domain.query.QKardec
import io.ebean.Finder

open class KardecFinder : Finder<Long, Kardec>(Kardec::class.java) {

  val alias = QKardec._alias

  /**
   * Start a new typed query.
   */
  fun where(): QKardec {
     return QKardec(db())
  }

  /**
   * Start a new document store query.
   */
  fun text(): QKardec {
     return QKardec(db()).text()
  }
}
