package br.com.astrosoft.viewmodel.framework.viewmodel

interface CrudControler<BEAN : Any> {
  var bean: BEAN?
  fun findAll(): Collection<BEAN>
  
  fun add(bean: BEAN): BEAN? {
    this.bean = bean
    add()
    return this.bean
  }
  
  fun update(bean: BEAN): BEAN? {
    this.bean = bean
    update()
    return this.bean
  }
  
  fun delete(bean: BEAN) {
    this.bean = bean
    delete()
  }
  
  fun add()
  
  fun update()
  
  fun delete()
}
