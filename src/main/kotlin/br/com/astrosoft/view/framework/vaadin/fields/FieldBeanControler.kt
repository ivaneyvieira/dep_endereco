package br.com.astrosoft.view.framework.vaadin.fields

abstract class FieldBeanControler<out T> {
  abstract fun getBeanValue(value: String): T
}
