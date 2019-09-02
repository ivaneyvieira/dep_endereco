package br.com.astrosoft.model.enderecamento.domain.finder

import br.com.astrosoft.model.enderecamento.domain.OcupacaoEndereco
import br.com.astrosoft.model.enderecamento.domain.query.QOcupacaoEndereco
import io.ebean.Finder

open class OcupacaoEnderecoFinder : Finder<Long, OcupacaoEndereco>(OcupacaoEndereco::class.java) {
  /**
   * Start a new typed query.
   */
  fun where(): QOcupacaoEndereco {
    return QOcupacaoEndereco(db())
  }

  /**
   * Start a new document store query.
   */
  fun text(): QOcupacaoEndereco {
    return QOcupacaoEndereco(db()).text()
  }
}