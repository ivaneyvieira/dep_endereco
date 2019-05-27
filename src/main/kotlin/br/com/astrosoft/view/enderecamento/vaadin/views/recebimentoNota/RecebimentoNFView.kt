package br.com.astrosoft.view.enderecamento.vaadin.views.recebimentoNota

import br.com.astrosoft.view.framework.vaadin.menu.ViewMenuItem
import br.com.astrosoft.view.framework.vaadin.views.FormView
import br.com.astrosoft.viewmodel.enderecamento.presenters.recebimentoNota.RecebimentoNFViewModel
import com.github.mvysny.karibudsl.v8.AutoView
import com.vaadin.icons.VaadinIcons
import com.vaadin.ui.Component
import com.vaadin.ui.VerticalLayout

@AutoView("movNota")
@ViewMenuItem(title = "Recebimento Nota", icon = VaadinIcons.TRUCK,
              grupo = "Movimentação", order = 20, tags = ["REC"])
class RecebimentoNFView : FormView<RecebimentoNFViewModel>(RecebimentoNFViewModel()) {
  
  private val fieldNotaEntrada = FieldNotaEntrada().apply {
    findBeanValue = model::findNotaEntrada
  }
  
  private val gridProdutoNotaEntrada = GridProdutoNotaEntrada(this.model.listProdutoNota)
  
  fun setInvno(invno: Int) {
    val ne = model.getNotaEntrada(invno)
    ne?.let {
      fieldNotaEntrada.value = it
      this.model.notaEntrada = it
    }
  }
  
  init {
    title = "Recebimento de Notas de Entrada"
    buildEditButton("Recebe tudo", VaadinIcons.COGS) {
      this.finalizarRecebimento()
    }
  }
  
  override fun buildContentPanels(): Component {
    this.fieldNotaEntrada.changeValue = { v ->
      model.notaEntrada = v
      model.processaListProdutos()
    }
    val layout = VerticalLayout(this.fieldNotaEntrada)
    layout.addComponentsAndExpand(this.gridProdutoNotaEntrada)
    return layout
  }
  
  private fun finalizarRecebimento() {
    this.model.notaEntrada = this.fieldNotaEntrada.value
    this.model.listProdutoNota.recebeTudo()
  }
  
  override fun updateView() {
    fieldNotaEntrada.value = model.notaEntrada
    gridProdutoNotaEntrada.updateView()
  }
  
  override fun updateModel() {}
}

