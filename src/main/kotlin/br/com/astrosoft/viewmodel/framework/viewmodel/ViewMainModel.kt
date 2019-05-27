package br.com.astrosoft.viewmodel.framework.viewmodel

import br.com.astrosoft.model.enderecamento.domain.User

abstract class ViewMainModel : ViewModel() {
  abstract fun findUser(username: String?): User?
}