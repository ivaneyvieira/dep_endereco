package br.com.astrosoft.view.enderecamento.vaadin.views.crudUser

import br.com.astrosoft.model.framework.utils.SystemUtils
import br.com.astrosoft.view.framework.vaadin.images.makeResource
import com.vaadin.ui.Component
import com.vaadin.ui.CustomField
import com.vaadin.ui.Panel

class FotoField(caption: String) : CustomField<ByteArray>() {
  private val panel = Panel()
  private var value: ByteArray? = null
  
  init {
    panel.setWidth("80px")
    panel.setHeight("100px")
    setCaption(caption)
  }
  
  override fun initContent(): Component {
    return panel
  }
  
  override fun doSetValue(byteImagem: ByteArray) {
    value = SystemUtils.resize(byteImagem, 80, 100)
    val image = byteImagem.makeResource()
    panel.icon = image
  }
  
  override fun getValue(): ByteArray? {
    return value
  }
  
}

