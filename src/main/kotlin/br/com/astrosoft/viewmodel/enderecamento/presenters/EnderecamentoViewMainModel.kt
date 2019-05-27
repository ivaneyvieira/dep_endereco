package br.com.astrosoft.viewmodel.enderecamento.presenters

import br.com.astrosoft.model.enderecamento.domain.User
import br.com.astrosoft.viewmodel.framework.viewmodel.ViewMainModel

class EnderecamentoViewMainModel : ViewMainModel() {
  override fun findUser(username: String?): User? {
    return username?.let { User.findUser(username) }
  }
  
  override fun reloadModel() {
  }
}