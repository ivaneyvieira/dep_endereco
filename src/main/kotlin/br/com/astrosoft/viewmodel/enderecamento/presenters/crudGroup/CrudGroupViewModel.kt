package br.com.astrosoft.viewmodel.enderecamento.presenters.crudGroup

import br.com.astrosoft.model.enderecamento.domain.Role
import br.com.astrosoft.model.framework.services.findAll
import br.com.astrosoft.viewmodel.framework.viewmodel.CrudControler
import br.com.astrosoft.viewmodel.framework.viewmodel.ViewModel

class CrudGroupViewModel : ViewModel(), CrudControler<Role> {
  override fun reloadModel() {
  
  }
  
  override var bean: Role? = null
  
  override fun findAll(): Collection<Role> {
    return Role.findAll()
  }
  
  override fun add() = exec {
    this.bean = this.bean?.let {
      it.insert()
      it
    }
  }
  
  override fun update() = exec {
    this.bean = this.bean?.let {
      it.update()
      it
    }
  }
  
  override fun delete() = exec {
    this.bean?.delete()
  }
}
