package br.com.astrosoft.view.enderecamento.vaadin.views.pickingProdutos

import br.com.astrosoft.view.enderecamento.vaadin.fields.MaskEnderecoField
import br.com.astrosoft.view.enderecamento.vaadin.fields.QuantField
import br.com.astrosoft.view.framework.vaadin.fields.DialogPopup
import br.com.astrosoft.viewmodel.enderecamento.presenters.pickingProdutos.ListSaldoModel
import com.vaadin.ui.Panel
import com.vaadin.ui.VerticalLayout

class PickingDialog(model: ListSaldoModel) : DialogPopup<PickingBean>("Picking", PickingBean::class) {
  private val endereco: MaskEnderecoField = MaskEnderecoField("Endereco Piking", model.enderecosPicking())
  private val quantidade: QuantField = QuantField("Quantidade")
  
  override fun configForm(form: Panel) {
    form.content = VerticalLayout(endereco, quantidade)
  }
}
