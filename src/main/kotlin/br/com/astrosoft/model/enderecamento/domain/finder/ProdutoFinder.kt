package br.com.astrosoft.model.enderecamento.domain.finder

import br.com.astrosoft.model.enderecamento.domain.Produto
import br.com.astrosoft.model.enderecamento.domain.query.QProduto
import io.ebean.Finder

open class ProdutoFinder : Finder<Long, Produto>(Produto::class.java) {
  /**
   * Start a new typed query.
   */
  fun where(): QProduto {
    return QProduto(db())
  }

  /**
   * Start a new document store query.
   */
  fun text(): QProduto {
    return QProduto(db()).text()
  }
}