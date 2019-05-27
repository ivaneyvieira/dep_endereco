package br.com.astrosoft.view.enderecamento.vaadin.views

import br.com.astrosoft.view.framework.vaadin.menu.ViewMenuItem
import com.github.mvysny.karibudsl.v8.AutoView
import com.vaadin.icons.VaadinIcons
import com.vaadin.navigator.View
import com.vaadin.navigator.ViewChangeListener
import com.vaadin.ui.VerticalLayout

@AutoView("")
@ViewMenuItem(title = "Tela Principal", icon = VaadinIcons.PIE_BAR_CHART, grupo = "",
              order = 0)
class MainView : VerticalLayout(), View {
  
  init {
    setSizeFull()
  }
  
  override fun enter(event: ViewChangeListener.ViewChangeEvent?) {}
}