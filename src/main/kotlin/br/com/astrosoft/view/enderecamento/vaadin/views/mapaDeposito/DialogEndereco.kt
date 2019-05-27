package br.com.astrosoft.view.enderecamento.vaadin.views.mapaDeposito

import br.com.astrosoft.view.framework.vaadin.fields.DialogModal
import br.com.astrosoft.viewmodel.enderecamento.presenters.mapaDeposito.MapaEnderecoModel
import com.vaadin.ui.Button.ClickEvent
import com.vaadin.ui.VerticalLayout

class DialogEndereco(val model: MapaEnderecoModel) : DialogModal("Endereço") {
  private val form = FieldEndereco(model)
  private val grid = GridEndProduto(model.listEnderecoProduto)
  
  init {
    this.form.setWidth("100%")
  }
  
  override fun buildForm(): VerticalLayout {
    val layout = VerticalLayout()
    layout.addComponent(this.form)
    layout.addComponent(this.grid)
    layout.setExpandRatio(this.grid, 1f)
    return layout
  }
  
  override fun salvar(event: ClickEvent) {
    val endereco = this.form.value
    endereco?.let {
      model.endereco = it
      this.model.salvaEndereco()
      super.salvar(event)
    }
  }
  
  override fun updateView() {
    form.value = model.endereco
    grid.updateView()
  }
  
  override fun updateModel() {
    model.endereco = form.value
    grid.updateModel()
  }
}
