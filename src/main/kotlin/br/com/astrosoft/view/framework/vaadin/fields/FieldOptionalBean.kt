package br.com.astrosoft.view.framework.vaadin.fields

abstract class FieldOptionalBean<T> : FieldBean<T>() {
  init {
    this.beanValue = null
  }
  
  override fun doSetValue(optionalValue: T?) {
    if (optionalValue != null) doSetValueBean(optionalValue)
    else doSetValueEmpty()
    this.beanValue = optionalValue
  }
  
  protected abstract fun doSetValueEmpty()
  protected abstract fun doSetValueBean(t: T)
}
