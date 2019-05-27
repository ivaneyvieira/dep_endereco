package br.com.astrosoft.model.enderecamento.domain.finder

import br.com.astrosoft.model.enderecamento.domain.User
import br.com.astrosoft.model.enderecamento.domain.query.QUser
import io.ebean.Finder

open class UserFinder : Finder<Long, User>(User::class.java) {

  val alias = QUser._alias

  /**
   * Start a new typed query.
   */
  fun where(): QUser {
     return QUser(db())
  }

  /**
   * Start a new document store query.
   */
  fun text(): QUser {
     return QUser(db()).text()
  }
}
