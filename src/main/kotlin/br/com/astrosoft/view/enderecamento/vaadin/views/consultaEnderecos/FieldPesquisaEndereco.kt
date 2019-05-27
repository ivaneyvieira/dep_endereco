package br.com.astrosoft.view.enderecamento.vaadin.views.consultaEnderecos

import br.com.astrosoft.model.enderecamento.domain.ELado
import br.com.astrosoft.model.enderecamento.domain.ETipoNivel
import br.com.astrosoft.viewmodel.enderecamento.presenters.consultaEnderecos.ConsultaEnderecoViewModel
import com.github.mvysny.karibudsl.v8.bind
import com.vaadin.data.Binder
import com.vaadin.ui.ComboBox
import com.vaadin.ui.HorizontalLayout
import com.vaadin.ui.ItemCaptionGenerator

class FieldPesquisaEndereco(private val model: ConsultaEnderecoViewModel) : HorizontalLayout() {
  private val binder = Binder<ConsultaEnderecoViewModel>(ConsultaEnderecoViewModel::class.java)
  private val comboTipo: ComboBox<ETipoNivel>
  private val comboRua: ComboBox<String>
  private val comboLado: ComboBox<ELado>
  private val comboPredio: ComboBox<String>
  private val comboNivel: ComboBox<String>
  private val comboApto: ComboBox<String>
  private var processaSaldos: () -> Unit = {}
  
  init {
    comboTipo = buildComboTipo()
    comboRua = buildComboRua()
    comboLado = buildComboLado()
    comboPredio = buildComboPredio()
    comboNivel = buildComboNivel()
    comboApto = buildComboApto()
    configFields()
  }
  
  private fun buildComboApto(): ComboBox<String> {
    val field = buildCombo("Apartamento", model.aptos()) {a -> "Apto $a" }
    field.bind(binder).bind(ConsultaEnderecoViewModel::apto)
    return field
  }
  
  private fun buildComboNivel(): ComboBox<String> {
    val field = buildCombo("Nivel", model.niveis()) {n -> "Nível $n" }
    field.bind(binder).bind(ConsultaEnderecoViewModel::nivel)
    return field
  }
  
  private fun buildComboPredio(): ComboBox<String> {
    val field = buildCombo("Predio", model.predios()) {n -> "Predio $n" }
    field.bind(binder).bind(ConsultaEnderecoViewModel::predio)
    return field
  }
  
  private fun buildComboRua(): ComboBox<String> {
    val field = buildCombo("Rua", model.ruas()) {n -> "Rua $n" }
    field.bind(binder).bind(ConsultaEnderecoViewModel::rua)
    return field
  }
  
  private fun buildComboLado(): ComboBox<ELado> {
    val field = buildCombo("Lado", model.ladosRuas()) {l -> l.toString() }
    field.bind(binder).bind(ConsultaEnderecoViewModel::lado)
    return field
  }
  
  private fun buildComboTipo(): ComboBox<ETipoNivel> {
    val field :ComboBox<ETipoNivel> = buildCombo("Tipo", model.tipoNiveis()) {t -> t.toString() }
    field.bind(binder).bind(ConsultaEnderecoViewModel::tipoNivel)
    return field
  }
  
  private fun <T> buildCombo(caption: String, lista: List<T>, captionGen: (T) -> String): ComboBox<T> {
    val combo = ComboBox(caption, lista)
    combo.itemCaptionGenerator = ItemCaptionGenerator { captionGen(it) }
    combo.isEmptySelectionAllowed = true
    combo.emptySelectionCaption = "Todos"
    combo.addValueChangeListener {
      processaSaldos()
    }
    combo.isTextInputAllowed = false
    return combo
  }
  
  private fun configFields() {
    addComponentsAndExpand(comboTipo, comboRua, comboLado, comboPredio, comboNivel, comboApto)
  }
  
  fun setProcessaSaldos(processaSaldos: () -> Unit) {
    this.processaSaldos = processaSaldos
  }
  
  fun updateView() {
    binder.readBean(model)
  }
  
  fun updateModel() {
    binder.writeBean(model)
  }
}
