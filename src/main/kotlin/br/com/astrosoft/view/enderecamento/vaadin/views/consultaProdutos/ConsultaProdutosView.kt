package br.com.astrosoft.view.enderecamento.vaadin.views.consultaProdutos

import br.com.astrosoft.model.enderecamento.domain.Produto
import br.com.astrosoft.view.enderecamento.vaadin.fields.FieldProduto
import br.com.astrosoft.view.enderecamento.vaadin.views.consultaPulmao.GridConsultaPulmao
import br.com.astrosoft.view.framework.vaadin.fields.ControlerFieldBean
import br.com.astrosoft.view.framework.vaadin.menu.ViewMenuItem
import br.com.astrosoft.view.framework.vaadin.views.FormView
import br.com.astrosoft.viewmodel.enderecamento.presenters.consultaPulmao.ConsultaPulmaoViewModel
import com.github.mvysny.karibudsl.v8.AutoView
import com.vaadin.icons.VaadinIcons
import com.vaadin.ui.Component
import com.vaadin.ui.VerticalLayout

@AutoView("consultaProduto")
@ViewMenuItem(
        title = "Consulta Produtos",
        icon = VaadinIcons.CUBE,
        grupo = "Consulta",
        order = 32,
        tags = ["CON", "REC", "EXP"])
class ConsultaProdutosView : FormView<ConsultaPulmaoViewModel>(ConsultaPulmaoViewModel()) {
  private val fieldProduto: FieldProduto
  private val gridConsultaPulmao: GridConsultaPulmao
  
  init {
    title = "Consulta de localização de produto"
    fieldProduto = FieldProduto().apply {
      saldoPulmaoTotal = model::saldoPulmaoTotal
      findBeanValue = model::findBeanValue
    }
    gridConsultaPulmao = GridConsultaPulmao(model.gridProdutos)
    
    buildEditButton("Relatorio", VaadinIcons.BOOK) {
      this.abrirRelatorio()
    }
  }
  
  override fun buildContentPanels(): Component {
    fieldProduto.changeValue = {
      model.produtos = it
      model.gridProdutos.processaGrid()
    }
    val layout = VerticalLayout(fieldProduto)
    layout.addComponentsAndExpand(gridConsultaPulmao)
    return layout
  }
  
  private fun abrirRelatorio() {
    //val report = RelatorioProdutos()
    //    val dialog = ReportDialog("Produtos", report)
    //  dialog.show()
  }
  
  override fun updateView() {
    fieldProduto.value = model.produtos
    gridConsultaPulmao.updateView()
  }
  
  override fun updateModel() {}
}

private class ControleConsultaProduto(val model: ConsultaPulmaoViewModel) : ControlerFieldBean<List<Produto>> {
  override fun findBeanValue(text: String): List<Produto> {
    return model.findBeanValue(text)
  }
  
  override fun saldoPulmaoTotal(produto: Produto): Double {
    return model.saldoPulmaoTotal(produto)
  }
}
