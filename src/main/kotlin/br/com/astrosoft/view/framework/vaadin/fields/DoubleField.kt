package br.com.astrosoft.view.framework.vaadin.fields

import org.vaadin.viritin.fields.AbstractNumberField

class DoubleField : AbstractNumberField<DoubleField, Double>() {
  private var valor: Double? = null
  
  override fun userInputToValue(str: String) {
    valor = java.lang.Double.parseDouble(str)
  }
  
  override fun getValue(): Double? {
    return valor
  }
}
