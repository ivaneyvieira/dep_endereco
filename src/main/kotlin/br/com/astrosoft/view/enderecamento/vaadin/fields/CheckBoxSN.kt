package br.com.astrosoft.view.enderecamento.vaadin.fields

import br.com.astrosoft.model.enderecamento.domain.ESimNao
import br.com.astrosoft.model.enderecamento.domain.ESimNao.NAO
import br.com.astrosoft.model.enderecamento.domain.ESimNao.SIM
import com.vaadin.data.HasValue
import com.vaadin.ui.CheckBox
import com.vaadin.ui.Component
import com.vaadin.ui.CustomField

class CheckBoxSN(caption: String) : CustomField<ESimNao>() {
  private val checkBox: CheckBox = CheckBox(caption)
  private var value: ESimNao? = null
  
  private fun changeValue(event: HasValue.ValueChangeEvent<Boolean>) {
    if (event.value == true) setValue(SIM)
    else setValue(NAO)
  }
  
  override fun initContent(): Component {
    this.checkBox.addValueChangeListener { this.changeValue(it) }
    return this.checkBox
  }
  
  override fun doSetValue(value: ESimNao) {
    this.value = value
    this.checkBox.value = value == SIM
  }
  
  override fun getValue(): ESimNao? {
    return this.value
  }
}
