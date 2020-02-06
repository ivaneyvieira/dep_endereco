package br.com.astrosoft.view.enderecamento.vaadin.views.separacaoCarga

import br.com.astrosoft.model.enderecamento.domain.Endereco
import br.com.astrosoft.model.enderecamento.domain.User
import com.github.mvysny.karibudsl.v8.alignment
import com.github.mvysny.karibudsl.v8.button
import com.github.mvysny.karibudsl.v8.comboBox
import com.github.mvysny.karibudsl.v8.textField
import com.vaadin.shared.ui.ValueChangeMode.LAZY
import com.vaadin.ui.Alignment
import com.vaadin.ui.ComboBox
import com.vaadin.ui.HorizontalLayout
import com.vaadin.ui.TextField
import com.vaadin.ui.themes.ValoTheme

class HeaderView(view: SeparacaoCargaView): HorizontalLayout() {
  var cmbEmpilhador: ComboBox<User>
  var cmbSeparador: ComboBox<User>
  val cmbExpedicao: ComboBox<Endereco>
  val text: TextField
  
  init {
    setWidth("100%")
    text = textField("Número da Carga") {
      this.valueChangeMode = LAZY
      addValueChangeListener {
        val value = it.value
        view.model.headerModel.numeroCarga = value.toIntOrNull() ?: 0
        view.gridView.update()
      }
    }
    cmbExpedicao = comboBox("Doca de Expedição") {
      this.isTextInputAllowed = false
      this.isEmptySelectionAllowed = false
      this.setItems(view.model.enderecosExpedicao())
    }
    cmbSeparador = comboBox<User>("Separador") {
      this.isTextInputAllowed = false
      this.isEmptySelectionAllowed = false
      this.setItems(view.model.sepradores())
    }
    cmbEmpilhador = comboBox<User>("Empilhador") {
      this.isTextInputAllowed = false
      this.isEmptySelectionAllowed = false
      this.setItems(view.model.sepradores())
    }
    button("Processamento") {
      addStyleName(ValoTheme.BUTTON_PRIMARY)
      alignment = Alignment.BOTTOM_RIGHT
      addClickListener {
        view.model.processamento()
      }
    }
  }
}