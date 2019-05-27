package br.com.astrosoft.model.enderecamento.domain.finder

import br.com.astrosoft.model.enderecamento.domain.Transferencia
import br.com.astrosoft.model.enderecamento.domain.query.QTransferencia
import io.ebean.Finder

open class TransferenciaFinder : Finder<Long, Transferencia>(Transferencia::class.java) {

  val alias = QTransferencia._alias

  /**
   * Start a new typed query.
   */
  fun where(): QTransferencia {
     return QTransferencia(db())
  }

  /**
   * Start a new document store query.
   */
  fun text(): QTransferencia {
     return QTransferencia(db()).text()
  }
}
