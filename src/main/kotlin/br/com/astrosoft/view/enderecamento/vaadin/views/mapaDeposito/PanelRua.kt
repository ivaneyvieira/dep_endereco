package br.com.astrosoft.view.enderecamento.vaadin.views.mapaDeposito

import br.com.astrosoft.model.enderecamento.domain.Rua
import br.com.astrosoft.view.enderecamento.vaadin.Styles
import com.vaadin.ui.Alignment
import com.vaadin.ui.CssLayout
import com.vaadin.ui.GridLayout
import com.vaadin.ui.Label
import com.vaadin.ui.Panel
import com.vaadin.ui.themes.ValoTheme

class PanelRua(rua: Rua) : CssLayout() {
  init {
    setSizeFull()
    val panel = Panel()
    panel.setSizeFull()
    val cell = GridLayout()
    val label = Label("Rua " + rua.numero)
    label.addStyleName(Styles.box_rotate)
    cell.defaultComponentAlignment = Alignment.MIDDLE_CENTER
    cell.addComponent(label)
    cell.setSizeFull()
    panel.content = cell
    panel.addStyleName(ValoTheme.PANEL_BORDERLESS)
    addComponent(panel)
  }
}
