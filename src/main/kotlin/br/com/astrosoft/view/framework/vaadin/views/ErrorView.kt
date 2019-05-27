package br.com.astrosoft.view.framework.vaadin.views

import br.com.astrosoft.view.framework.vaadin.menu.ViewMenuItem
import com.github.mvysny.karibudsl.v8.AutoView
import com.vaadin.navigator.View
import com.vaadin.navigator.ViewChangeListener
import com.vaadin.ui.Label
import com.vaadin.ui.VerticalLayout

@AutoView("error")
@ViewMenuItem(enabled = false, title = "Erro", grupo = "")
class ErrorView : VerticalLayout(), View {
  init {
    addComponent(Label("Oops. A visualização para a qual você tentou navegar não existe."))
  }
  
  override fun enter(event: ViewChangeListener.ViewChangeEvent?) {}
}