package br.com.astrosoft.view.framework.vaadin.mvp

import br.com.astrosoft.viewmodel.framework.viewmodel.View
import br.com.astrosoft.viewmodel.framework.viewmodel.ViewModel
import com.vaadin.server.Page
import com.vaadin.ui.CustomComponent
import com.vaadin.ui.Notification
import com.vaadin.ui.Notification.Type

abstract class VaadinView<out MODEL : ViewModel>(val model: MODEL) : CustomComponent(), View {
  override var isShow = false
  
  init {
    init()
  }
  
  fun init() {
    setSizeFull()
    setBinder()
  }
  
  private fun setBinder() {
    model.binder = this
  }
  
  override fun processaParametros(parameters: Map<String, String?>) {
    model.processaParametros(parameters)
  }
  
  override fun showError(message: String) {
    Notification("Erro", message, Type.ERROR_MESSAGE).show(Page.getCurrent())
  }
  
  override fun showWarnings(message: String) {
    Notification("Aviso", message, Type.WARNING_MESSAGE).show(Page.getCurrent())
  }
  
  override fun showInfo(message: String) {
    Notification("Menssagem", message, Type.HUMANIZED_MESSAGE).show(Page.getCurrent())
  }
}