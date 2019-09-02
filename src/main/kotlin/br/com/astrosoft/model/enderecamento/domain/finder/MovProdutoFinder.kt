package br.com.astrosoft.model.enderecamento.domain.finder

import br.com.astrosoft.model.enderecamento.domain.MovProduto
import br.com.astrosoft.model.enderecamento.domain.query.QMovProduto
import io.ebean.Finder

open class MovProdutoFinder : Finder<Long, MovProduto>(MovProduto::class.java) {
  /**
   * Start a new typed query.
   */
  fun where(): QMovProduto {
    return QMovProduto(db())
  }

  /**
   * Start a new document store query.
   */
  fun text(): QMovProduto {
    return QMovProduto(db()).text()
  }
}