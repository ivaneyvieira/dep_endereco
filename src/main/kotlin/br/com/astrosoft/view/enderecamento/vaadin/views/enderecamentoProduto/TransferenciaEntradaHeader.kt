package br.com.astrosoft.view.enderecamento.vaadin.views.enderecamentoProduto

import br.com.astrosoft.model.enderecamento.domain.ELado
import br.com.astrosoft.model.enderecamento.domain.EPalet
import br.com.astrosoft.model.enderecamento.domain.ETipoAltura
import br.com.astrosoft.model.enderecamento.domain.Rua
import br.com.astrosoft.viewmodel.enderecamento.presenters.enderecamentoProduto.TransferenciaEntradaHeaderModel
import com.explicatis.ext_token_field.ExtTokenField
import com.explicatis.ext_token_field.SimpleTokenizable
import com.vaadin.data.HasValue.ValueChangeListener
import com.vaadin.icons.VaadinIcons
import com.vaadin.server.ThemeResource
import com.vaadin.ui.Alignment
import com.vaadin.ui.Button
import com.vaadin.ui.ComboBox
import com.vaadin.ui.Component
import com.vaadin.ui.HorizontalLayout
import com.vaadin.ui.Panel
import com.vaadin.ui.TextField
import com.vaadin.ui.VerticalLayout
import org.vaadin.ui.NumberField
import java.math.BigDecimal

class TransferenciaEntradaHeader(val model: TransferenciaEntradaHeaderModel) : Panel() {
  private val comboPalete: ComboBox<EPalet>
  private val comboLado: ComboBox<ELado>
  private val comboAltura: ComboBox<ETipoAltura>
  private val comboRuas: ComboBox<SimpleTokenizable>
  private val headerGrid: TransferenciaEntradaHeaderGrid
  private val tokenRuas: ExtTokenField
  private val btnProcessa: Button
  private val codigo: TextField
  private val descricao: TextField
  private val quantPalete: NumberField
  
  init {
    this.comboPalete = buildComboPalete()
    this.comboAltura = buildComboAltura()
    this.comboLado = buildChecklado()
    
    this.tokenRuas = ExtTokenField()
    this.comboRuas = buildTokenRuas()
    this.tokenRuas.caption = "Ruas"
    this.tokenRuas.inputField = this.comboRuas
    
    this.headerGrid = TransferenciaEntradaHeaderGrid(model)
    this.btnProcessa = buildBtnProcessa()
    this.codigo = buildTextLabelText("Código")
    this.descricao = buildTextLabelText("Descrição")
    this.quantPalete = buildTextEditQuant("Quant. por Palete")
    content = buildContent()
  }
  
  private fun buildBtnProcessa(): Button {
    val button = Button("Processa")
    button.icon = VaadinIcons.COGS
    button.addClickListener { this.processaEnderecamento() }
    return button
  }
  
  private fun processaEnderecamento() {
    model.processaEnderecamento()
  }
  
  fun mapToken(rua: Rua) = SimpleTokenizable(rua.id, rua.toString())
  
  private fun buildTokenRuas(): ComboBox<SimpleTokenizable> {
    val ruas = model.ruasPulmao
    val tokenizables = ruas?.map { r ->
      mapToken(r)
    }.orEmpty()
    val comboBox = ComboBox("Ruas", tokenizables)
    comboBox.setItemCaptionGenerator { it.stringValue }
    comboBox.addValueChangeListener(getComboBoxValueChange(this.tokenRuas))
    this.tokenRuas.setEnableDefaultDeleteTokenAction(true)
    return comboBox
  }
  
  private fun getComboBoxValueChange(extTokenField: ExtTokenField) = ValueChangeListener<SimpleTokenizable> {
    val value = it.value
    if (value != null) {
      extTokenField.addTokenizable(value)
      it.source.value = null
    }
  }
  
  private fun buildChecklado(): ComboBox<ELado> {
    return ComboBox<ELado>("Lado").apply {
      isTextInputAllowed = false
      setItems(ELado.values().asList())
      this.emptySelectionCaption = "Ambos"
      this.setItemCaptionGenerator { lado -> lado.toString() }
    }
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
    combo.setWidth("250px")
    combo.isEmptySelectionAllowed = false
    combo.isTextInputAllowed = false
    combo.setItems(*EPalet.values())
    combo.setItemIconGenerator { p -> ThemeResource("img/paletes" + p.sigla + ".png") }
    combo.setItemCaptionGenerator { p -> "     " + p.descricao }
    combo.value = EPalet.values()[0]
    return combo
  }
  
  private fun buildContent(): Component {
    val filtro = HorizontalLayout()
    filtro.addComponentsAndExpand(this.comboPalete, this.comboAltura, comboLado)
    val header = buildHeaderMaster()
    val filtro2 = HorizontalLayout().apply {
      addComponentsAndExpand(tokenRuas)
      addComponent(btnProcessa)
      setComponentAlignment(btnProcessa, Alignment.BOTTOM_CENTER)
    }
    
    val headerCampo = VerticalLayout(header, filtro, filtro2)
    headerCampo.setHeightUndefined()
    headerCampo.setWidth("100%")
    headerCampo.isSpacing = true
    this.headerGrid.setSizeFull()
    val layout = HorizontalLayout(headerCampo, this.headerGrid)
    layout.isSpacing = false
    layout.setExpandRatio(headerCampo, 3f)
    layout.setExpandRatio(this.headerGrid, 1f)
    layout.setWidth("100%")
    layout.setHeightUndefined()
    return layout
  }
  
  private fun buildHeaderMaster(): HorizontalLayout {
    this.descricao.setSizeFull()
    val layout = HorizontalLayout()
    layout.addComponent(this.codigo)
    layout.addComponent(this.descricao)
    layout.addComponent(this.quantPalete)
    layout.setExpandRatio(this.descricao, 1f)
    layout.setSizeFull()
    return layout
  }
  
  private fun buildTextLabelText(caption: String): TextField {
    val textField = TextField(caption)
    textField.isReadOnly = true
    return textField
  }
  
  private fun buildTextEditQuant(caption: String): NumberField {
    val numberField = NumberField(caption)
    
    numberField.decimalPrecision = 0
    numberField.decimalSeparator = ','
    numberField.groupingSeparator = '.'
    numberField.isDecimalSeparatorAlwaysShown = false
    numberField.minimumFractionDigits = 0
    
    return numberField
  }
  
  fun updateView() {
    quantPalete.value = model.quantPalete.intValueExact().toString()
    codigo.value = model.produto?.codigoGrade ?: ""
    descricao.value = model.produto?.nome ?: ""
    headerGrid.updateView()
  }
  
  fun updateModel() {
    model.quantPalete = BigDecimal.valueOf(quantPalete.doubleValueDoNotThrow)
    model.lado = comboLado.value
    model.comboAltura = comboAltura.value
    model.comboPalete = comboPalete.value
    model.tokenRuas.clear()
    model.tokenRuas.addAll(tokenRuas.value.map {
      val id = it.identifier
      this@TransferenciaEntradaHeader.model.ruasPulmao.orEmpty().first {rua -> rua.id == id }
    })
    headerGrid.updateModel()
  }
}
