package br.com.astrosoft.viewmodel.enderecamento.presenters.crudUser

import br.com.astrosoft.model.enderecamento.domain.Role
import br.com.astrosoft.model.enderecamento.domain.User
import br.com.astrosoft.model.framework.services.findAll
import br.com.astrosoft.viewmodel.framework.viewmodel.CrudControler
import br.com.astrosoft.viewmodel.framework.viewmodel.ViewModel

class CrudUserViewModel : ViewModel(), CrudControler<User> {
  override fun reloadModel() {
  
  }
  
  override var bean: User? = null
  
  override fun findAll(): Collection<User> {
    return User.findAll()
  }
  
  override fun add() = exec {
    this.bean?.let {
      it.insert()
      this.bean = it
    }
  }
  
  override fun update() = exec {
    this.bean?.let {
      it.update()
      this.bean = it
    }
  }
  
  override fun delete() = exec {
    this.bean?.let {
      it.delete()
      it
    }
  }
  
  fun grupos(): List<Role> {
    return Role.findAll()
  }
}
