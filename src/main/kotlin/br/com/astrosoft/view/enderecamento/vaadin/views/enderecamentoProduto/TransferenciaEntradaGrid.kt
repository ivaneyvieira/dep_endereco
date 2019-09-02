package br.com.astrosoft.view.enderecamento.vaadin.views.enderecamentoProduto

import br.com.astrosoft.model.enderecamento.domain.EPalet
import br.com.astrosoft.model.enderecamento.domain.MovProduto
import br.com.astrosoft.model.enderecamento.domain.Transferencia
import br.com.astrosoft.view.enderecamento.vaadin.Styles
import br.com.astrosoft.view.enderecamento.vaadin.fields.QuantField
import br.com.astrosoft.view.framework.vaadin.Alignment
import br.com.astrosoft.view.framework.vaadin.dsls.column
import br.com.astrosoft.view.framework.vaadin.dsls.columnButton
import br.com.astrosoft.view.framework.vaadin.dsls.columnLocalDate
import br.com.astrosoft.view.framework.vaadin.dsls.columnQuant
import br.com.astrosoft.view.framework.vaadin.dsls.config
import br.com.astrosoft.view.framework.vaadin.dsls.nav
import br.com.astrosoft.view.framework.vaadin.fields.GridEditField
import br.com.astrosoft.viewmodel.enderecamento.presenters.enderecamentoProduto.TransferenciaEntradaGridModel
import com.vaadin.icons.VaadinIcons
import com.vaadin.server.ThemeResource
import com.vaadin.ui.ComboBox
import com.vaadin.ui.Grid
import com.vaadin.ui.Grid.Column
import com.vaadin.ui.TextField
import java.math.BigDecimal
import java.time.LocalDateTime

class TransferenciaEntradaGrid(private val model: TransferenciaEntradaGridModel) :
        GridEditField<Transferencia>(model, Transferencia::class) {
  
  private var colQuantMov: Column<Transferencia, BigDecimal>? = null
  
  override fun configGrid(grid: Grid<Transferencia>) {
    val observacao = TextField("Observação")
    val quant = QuantField("Quantidade")
    val endereco = EnderecoField(model, "Endereço")
    val comboPalete = buildComboPalete()
    
    grid.config {
      columnLocalDate({ it.dataHoraMov }) {
        caption = "Data e Hora"
        expandRatio = 2
      }
      column({ it.enderecoE }) {
        setEditorComponent(endereco) { ent, end -> ent.enderecoE = end }
        caption = "Endereço"
        expandRatio = 2
      }
      column({ it.paletE }) {
        setEditorComponent(comboPalete) { ent, pal -> ent?.enderecoE?.apto?.tipoPalet = pal ?: EPalet.P }
        caption = "Palete"
        expandRatio = 1
        setStyleGenerator { Alignment.center }
      }
      column({ it.tipoAltura }) {
        caption = "Altura"
        expandRatio = 1
        setStyleGenerator { Alignment.center }
      }
      colQuantMov = columnQuant({ it.quantMov }) {
        setEditorComponent(quant) { obj, decimal -> obj.quantMov = decimal }
        setCaption("Quantidade")
      }
      column({ it.observacao }) {
        setEditorComponent(observacao) { obj, s -> obj.observacao = s }
        caption = "Observação"
        expandRatio = 5
      }
      columnButton(VaadinIcons.TRASH, { actionDeleteBean(it) }) {}
      setStyleGenerator {
        if (it.id == 0L) Styles.grid_yellow else null
      }
    }
    
    refreshFooter()
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
  
  override fun refresh() {
    super.refresh()
    refreshFooter()
  }
  
  private fun refreshFooter() {
    if (this.grid.footerRowCount == 0) this.grid.appendFooterRow()
    val countCol = this.grid.columns.filter { c -> c == this.colQuantMov }.count()
    if (countCol > 0) {
      val total = value?.map { t -> t.quantMov.toInt() }?.sum() ?: 0
      val footerRow = this.grid.getFooterRow(0)
      val quantMov = footerRow.getCell(this.colQuantMov)
      quantMov.styleName = Alignment.right
      quantMov.text = total.toString()
    }
  }
  
  private fun actionDeleteBean(transferencia: Transferencia) {
    deleteBean(transferencia)
  }
  
  private fun deleteBean(transferencia: Transferencia) {
    model.itemSelected = transferencia
    model.deleteTransferencia()
  }
  
  override fun isNewBean(bean: Transferencia): Boolean {
    return bean.id == 0L
  }
  
  override val editBeans: List<Transferencia>
    get() = model.list.orEmpty()
  
  override fun createNewBean(): Transferencia {
    model.recebimento.let { rec ->
      val transferencia = Transferencia()
      transferencia.id = 0
      transferencia.dataHoraMov = LocalDateTime.now()
      transferencia.movProduto = model.model.produtosRecebidosModel.itemSelected ?: MovProduto()
      transferencia.enderecoS = rec
      transferencia.quantMov = BigDecimal.ZERO
      transferencia.enderecoE = null
      transferencia.confirmacao = false
      transferencia.observacao = ""
      return transferencia
    }
  }
  
  override fun saveBean(bean: Transferencia) {
    model.itemSelected = bean
    model.salvaTransferencia()
  }
}
