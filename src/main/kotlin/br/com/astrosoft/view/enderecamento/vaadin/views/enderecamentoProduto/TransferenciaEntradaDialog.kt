package br.com.astrosoft.view.enderecamento.vaadin.views.enderecamentoProduto

import br.com.astrosoft.view.framework.vaadin.fields.DialogModal
import br.com.astrosoft.viewmodel.enderecamento.presenters.enderecamentoProduto.TransferenciaEntradaModel
import com.vaadin.ui.Button.ClickEvent
import com.vaadin.ui.VerticalLayout

class TransferenciaEntradaDialog(val model: TransferenciaEntradaModel) : DialogModal("Endereçamento") {
  
  private val transferenciaEntradaGrid: TransferenciaEntradaGrid = TransferenciaEntradaGrid(model.grid)
  private val transferenciaEntradaHeader: TransferenciaEntradaHeader = TransferenciaEntradaHeader(model.header, transferenciaEntradaGrid)
  
  override fun buildForm(): VerticalLayout {
    val layout = VerticalLayout(this.transferenciaEntradaHeader)
    layout.addComponentsAndExpand(this.transferenciaEntradaGrid)
    return layout
  }
  
  override fun updateView() {
    this.transferenciaEntradaHeader.updateView()
    this.transferenciaEntradaGrid.updateView()
  }
  
  override fun updateModel() {
    this.transferenciaEntradaHeader.updateModel()
    this.transferenciaEntradaGrid.updateModel()
  }
  
  override fun salvar(event: ClickEvent) {
    transferenciaEntradaHeader.updateModel()
    model.salvaHeader()
    super.salvar(event)
  }
}
