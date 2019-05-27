package br.com.astrosoft.view.enderecamento.vaadin.views.mapaDeposito

import br.com.astrosoft.model.enderecamento.domain.Produto
import br.com.astrosoft.view.enderecamento.vaadin.fields.FieldProduto
import br.com.astrosoft.view.framework.vaadin.menu.ViewMenuItem
import br.com.astrosoft.view.framework.vaadin.views.FormView
import br.com.astrosoft.viewmodel.enderecamento.presenters.mapaDeposito.MapaDepositoViewModel
import com.github.mvysny.karibudsl.v8.AutoView
import com.vaadin.icons.VaadinIcons
import com.vaadin.ui.Component
import com.vaadin.ui.VerticalLayout

@AutoView("mapaDeposito")
@ViewMenuItem(title = "Mapa de depósito", icon = VaadinIcons.PACKAGE, grupo = "Consulta",
              order = 31, tags = ["CON", "REC", "EXP"])
class MapaDepositoView : FormView<MapaDepositoViewModel>(MapaDepositoViewModel()) {
  
  val mapaDeposito: MapaDeposito = MapaDeposito(this.model)
  
  init {
    title = "Mapa de depósito"
  }
  
  override fun buildContentPanels(): Component {
    val pnlProduto = FieldProduto().apply {
      saldoPulmaoTotal = model::saldoPulmaoTotal
      findBeanValue = model::findBeanValue
    }
    pnlProduto.changeValue = { lista -> this.selecionaEnderecosDosProdutos(lista) }
    val layout = VerticalLayout(pnlProduto)
    layout.addComponentsAndExpand(this.mapaDeposito)
    return layout
  }
  
  private fun selecionaEnderecosDosProdutos(lista: List<Produto>) {
    model.produtosSelecionado = lista
    this.model.selecionaEnderecosDosProdutos()
  }
  
  override fun updateView() {
    this.mapaDeposito.updateView()
  }
  
  override fun updateModel() {
    this.mapaDeposito.updateModel()
  }
}

