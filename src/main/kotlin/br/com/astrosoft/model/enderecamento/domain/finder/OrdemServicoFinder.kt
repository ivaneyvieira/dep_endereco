package br.com.astrosoft.model.enderecamento.domain.finder

import br.com.astrosoft.model.enderecamento.domain.OrdemServico
import br.com.astrosoft.model.enderecamento.domain.query.QOrdemServico
import io.ebean.Finder

open class OrdemServicoFinder : Finder<Long, OrdemServico>(OrdemServico::class.java) {
  /**
   * Start a new typed query.
   */
  fun where(): QOrdemServico {
    return QOrdemServico(db())
  }

  /**
   * Start a new document store query.
   */
  fun text(): QOrdemServico {
    return QOrdemServico(db()).text()
  }
}