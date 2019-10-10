package br.com.astrosoft.view.enderecamento.vaadin.views.separacaoCarga

import br.com.astrosoft.view.framework.vaadin.views.FormView
import br.com.astrosoft.viewmodel.enderecamento.presenters.ordemServico.OrdemServicoViewModel
import br.com.astrosoft.viewmodel.enderecamento.separacaoCarga.SeparacaoCargaViewModel
import com.vaadin.ui.Component
import com.vaadin.ui.VerticalLayout

class SeparacaoCargaView: FormView<SeparacaoCargaViewModel>(SeparacaoCargaViewModel()) {
  val headerView = HeaderView(this)
  val gridView = GridView(this)

  override fun buildContentPanels(): Component {
    val layout = VerticalLayout()
    layout.addComponent(headerView)
    layout.addComponentsAndExpand(gridView)
    return layout
  }

  override fun updateModel() {
    //Vazio
  }

  override fun updateView() {
    //Vazio
  }
}