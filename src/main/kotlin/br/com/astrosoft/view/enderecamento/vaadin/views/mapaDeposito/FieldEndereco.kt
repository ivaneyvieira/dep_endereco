package br.com.astrosoft.view.enderecamento.vaadin.views.mapaDeposito

import br.com.astrosoft.model.enderecamento.domain.Apto
import br.com.astrosoft.model.enderecamento.domain.EPalet
import br.com.astrosoft.model.enderecamento.domain.ETipoAltura
import br.com.astrosoft.model.enderecamento.domain.Endereco
import br.com.astrosoft.view.framework.vaadin.fields.FormBinder
import br.com.astrosoft.viewmodel.enderecamento.presenters.mapaDeposito.MapaEnderecoModel
import com.vaadin.server.ThemeResource
import com.vaadin.ui.ComboBox
import com.vaadin.ui.RadioButtonGroup
import com.vaadin.ui.TextField
import com.vaadin.ui.themes.ValoTheme

class FieldEndereco(val model: MapaEnderecoModel) : FormBinder<Endereco>(Endereco::class) {
  private lateinit var lblNumero: TextField
  private lateinit var edtObservacao: TextField
  private lateinit var comboPalete: ComboBox<EPalet>
  private val aptoOption = RadioButtonGroup<Apto>().apply {
    caption = "Mudar o Apatamento"
    addStyleName(ValoTheme.OPTIONGROUP_HORIZONTAL)
    setItemCaptionGenerator { apto ->
      apto.numero
    }
    addValueChangeListener {
      if (it.isUserOriginated) {
        val apto = it.value
        val endereco = this@FieldEndereco.value
        endereco?.let { model.changeApto(endereco, apto) }
      }
    }
  }
  
  override fun configFields() {
    this.lblNumero = TextField("Endereço")
    this.edtObservacao = TextField("Observação")
    
    this.comboPalete = buildComboPalete()
    this.lblNumero.isReadOnly = true
    forField<String?>(this.lblNumero).bind("localizacao")
    forField<String?>(this.edtObservacao).bind("observacao")
    forField<EPalet?>(this.comboPalete).bind("tipoPalet")
    
    addFieldLayout(this.lblNumero, 2)
    addFieldLayout(this.comboPalete, 2)
    addFieldLayout(aptoOption, 3)
    addFieldLayout(this.edtObservacao, 5)
  }
  
  override fun setValue(value: Endereco?) {
    super.setValue(value)
    val aptos = model.aptosNivel(value)
    aptoOption.setItems(aptos)
    aptoOption.value = value?.apto
  }
  
  private fun buildComboAltura(): ComboBox<ETipoAltura> {
    val combo = ComboBox<ETipoAltura>("Altura do Palete")
    combo.setItems(*ETipoAltura.values())
    combo.isEmptySelectionAllowed = false
    combo.isTextInputAllowed = false
    combo.setItemCaptionGenerator { p -> p.toString() }
    combo.value = ETipoAltura.values()[0]
    return combo
  }
  
  private fun buildComboPalete(): ComboBox<EPalet> {
    val combo = ComboBox<EPalet>("Largura do Palete")
    combo.setWidth("350px")
    combo.isEmptySelectionAllowed = false
    combo.isTextInputAllowed = false
    combo.setItems(*EPalet.values())
    combo.setItemIconGenerator { p -> ThemeResource("img/paletes" + p.sigla + ".png") }
    combo.setItemCaptionGenerator { p -> "     " + p.descricao }
    combo.value = EPalet.values()[0]
    return combo
  }
}
