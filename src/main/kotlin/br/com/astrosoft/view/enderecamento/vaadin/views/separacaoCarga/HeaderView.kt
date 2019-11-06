package br.com.astrosoft.view.enderecamento.vaadin.views.separacaoCarga

import com.github.mvysny.karibudsl.v8.alignment
import com.github.mvysny.karibudsl.v8.button
import com.github.mvysny.karibudsl.v8.textField
import com.vaadin.icons.VaadinIcons
import com.vaadin.ui.Alignment
import com.vaadin.ui.HorizontalLayout
import com.vaadin.ui.TextField
import java.awt.SystemColor.text

class HeaderView(view: SeparacaoCargaView): HorizontalLayout() {
  val text: TextField = textField("Número da Carga") {
  }

  init {
    button {
      icon = VaadinIcons.CHECK_CIRCLE_O
      alignment = Alignment.BOTTOM_LEFT
      addClickListener {
        view.model.headerModel.numeroCarga = text.value.toIntOrNull() ?: 0
        view.gridView.update()
      }
    }
  }
}