package br.com.astrosoft.view.enderecamento.vaadin.fields

import com.vaadin.data.HasValue
import com.vaadin.ui.Component
import com.vaadin.ui.CustomField
import com.vaadin.ui.TextField
import com.vaadin.ui.themes.ValoTheme

import java.math.BigDecimal

class QuantField(caption: String) : CustomField<BigDecimal>() {
  private val edtQuant: TextField = TextField()
  private var valor: BigDecimal? = null
  
  init {
    setCaption(caption)
  }
  
  override fun initContent(): Component {
    this.edtQuant.addValueChangeListener { this.changeValue(it) }
    this.edtQuant.addStyleName(ValoTheme.TEXTFIELD_ALIGN_RIGHT)
    setFocusDelegate(this.edtQuant)
    return this.edtQuant
  }
  
  private fun changeValue(event: HasValue.ValueChangeEvent<String>) {
    val strValue = event.value
    if (strValue == null || strValue == "") value = BigDecimal.ZERO
    else if (strValue.matches("^[0-9]+$".toRegex())) {
      value = BigDecimal.valueOf(Integer.parseInt(strValue).toLong())
    }
  }
  
  override fun doSetValue(decimal: BigDecimal?) {
    this.valor = decimal
    this.edtQuant.value = decimal?.toInt()?.toString() ?: ""
  }
  
  override fun getValue(): BigDecimal? {
    return this.valor
  }
}
