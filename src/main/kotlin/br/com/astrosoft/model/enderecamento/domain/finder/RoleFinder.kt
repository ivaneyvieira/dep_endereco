package br.com.astrosoft.model.enderecamento.domain.finder

import br.com.astrosoft.model.enderecamento.domain.Role
import br.com.astrosoft.model.enderecamento.domain.query.QRole
import io.ebean.Finder

open class RoleFinder : Finder<Long, Role>(Role::class.java) {

  val alias = QRole._alias

  /**
   * Start a new typed query.
   */
  fun where(): QRole {
     return QRole(db())
  }

  /**
   * Start a new document store query.
   */
  fun text(): QRole {
     return QRole(db()).text()
  }
}
