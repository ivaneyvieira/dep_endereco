package br.com.astrosoft.model.enderecamento.domain.finder

import br.com.astrosoft.model.enderecamento.domain.Predio
import br.com.astrosoft.model.enderecamento.domain.query.QPredio
import io.ebean.Finder

open class PredioFinder : Finder<Long, Predio>(Predio::class.java) {

  val alias = QPredio._alias

  /**
   * Start a new typed query.
   */
  fun where(): QPredio {
     return QPredio(db())
  }

  /**
   * Start a new document store query.
   */
  fun text(): QPredio {
     return QPredio(db()).text()
  }
}
