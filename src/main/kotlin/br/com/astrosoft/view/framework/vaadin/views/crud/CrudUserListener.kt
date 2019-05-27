package br.com.astrosoft.view.framework.vaadin.views.crud

import br.com.astrosoft.viewmodel.framework.viewmodel.CrudControler
import org.vaadin.crudui.crud.CrudListener

class CrudUserListener<BEAN : Any>(private val controler: CrudControler<BEAN>) : CrudListener<BEAN> {
  
  override fun findAll(): Collection<BEAN> {
    return controler.findAll()
  }
  
  override fun add(user: BEAN): BEAN? {
    return controler.add(user)
  }
  
  override fun update(user: BEAN): BEAN? {
    return controler.update(user)
  }
  
  override fun delete(user: BEAN) {
    controler.delete(user)
  }
}
