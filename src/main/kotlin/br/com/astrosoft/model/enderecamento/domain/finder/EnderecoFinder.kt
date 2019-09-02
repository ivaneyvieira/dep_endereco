package br.com.astrosoft.model.enderecamento.domain.finder

import br.com.astrosoft.model.enderecamento.domain.Endereco
import br.com.astrosoft.model.enderecamento.domain.query.QEndereco
import io.ebean.Finder

open class EnderecoFinder : Finder<Long, Endereco>(Endereco::class.java) {
  /**
   * Start a new typed query.
   */
  fun where(): QEndereco {
    return QEndereco(db())
  }

  /**
   * Start a new document store query.
   */
  fun text(): QEndereco {
    return QEndereco(db()).text()
  }
}