package br.com.astrosoft.model.enderecamento.domain.finder

import br.com.astrosoft.model.enderecamento.domain.Apto
import br.com.astrosoft.model.enderecamento.domain.query.QApto
import io.ebean.Finder

open class AptoFinder : Finder<Long, Apto>(Apto::class.java) {

  val alias = QApto._alias

  /**
   * Start a new typed query.
   */
  fun where(): QApto {
     return QApto(db())
  }

  /**
   * Start a new document store query.
   */
  fun text(): QApto {
     return QApto(db()).text()
  }
}
