package br.com.astrosoft.view.enderecamento.vaadin.views.separacaoCarga

import br.com.astrosoft.model.enderecamento.domain.Endereco
import br.com.astrosoft.model.enderecamento.domain.User
import br.com.astrosoft.model.enderecamento.dtos.Carga
import br.com.astrosoft.view.framework.vaadin.menu.ViewMenuItem
import br.com.astrosoft.view.framework.vaadin.views.FormView
import br.com.astrosoft.viewmodel.enderecamento.separacaoCarga.ISeparacaoCargaViewModel
import br.com.astrosoft.viewmodel.enderecamento.separacaoCarga.SeparacaoCargaViewModel
import com.github.mvysny.karibudsl.v8.AutoView
import com.github.mvysny.karibudsl.v8.getAll
import com.vaadin.icons.VaadinIcons
import com.vaadin.ui.Component
import com.vaadin.ui.VerticalLayout

@AutoView("sepCarga")
@ViewMenuItem(title = "Separacao de Carga", icon = VaadinIcons.TRUCK,
              grupo = "Movimentação", order = 21, tags = ["REC"])
class SeparacaoCargaView: FormView<SeparacaoCargaViewModel>(SeparacaoCargaViewModel()), ISeparacaoCargaViewModel {
  val headerView = HeaderView(this)
  val gridView = GridView(this)
  
  init {
    title = "Separação de mercadoria"
    model.setView(this)
  }
  
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
    gridView.update()
  }
  
  fun enderecos(): List<Endereco> {
    return model.enderecos()
  }
  
  override var cargaNo: Int?
    get() = headerView.text.value?.toIntOrNull()
    set(value) {
      headerView.text.value = value?.toString()
    }
  override var enderecoDoca: Endereco?
    get() = headerView.cmbExpedicao.value
    set(value) {
      headerView.cmbExpedicao.value = value
    }
  override var usuarioSeparador: User?
    get() = headerView.cmbSeparador.value
    set(value) {
      headerView.cmbSeparador.value = value
    }
  override var usuarioEmpilhador: User?
    get() = headerView.cmbEmpilhador.value
    set(value) {
      headerView.cmbEmpilhador.value = value
    }
  override val listagemCarga: List<Carga>
    get() = gridView.provider.getAll()
}