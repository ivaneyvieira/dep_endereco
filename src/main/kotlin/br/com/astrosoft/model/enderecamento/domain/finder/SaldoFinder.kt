package br.com.astrosoft.model.enderecamento.domain.finder

import br.com.astrosoft.model.enderecamento.domain.Saldo
import br.com.astrosoft.model.enderecamento.domain.query.QSaldo
import io.ebean.Finder

open class SaldoFinder : Finder<Long, Saldo>(Saldo::class.java) {

  val alias = QSaldo._alias

  /**
   * Start a new typed query.
   */
  fun where(): QSaldo {
     return QSaldo(db())
  }

  /**
   * Start a new document store query.
   */
  fun text(): QSaldo {
     return QSaldo(db()).text()
  }
}
