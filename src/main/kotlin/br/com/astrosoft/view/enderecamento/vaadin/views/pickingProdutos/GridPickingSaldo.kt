package br.com.astrosoft.view.enderecamento.vaadin.views.pickingProdutos

import br.com.astrosoft.model.enderecamento.domain.ETipoNivel.PICKING
import br.com.astrosoft.model.enderecamento.domain.Saldo
import br.com.astrosoft.model.framework.utils.toDate
import br.com.astrosoft.view.framework.vaadin.dsls.column
import br.com.astrosoft.view.framework.vaadin.dsls.columnButton
import br.com.astrosoft.view.framework.vaadin.dsls.columnDate
import br.com.astrosoft.view.framework.vaadin.dsls.columnQuant
import br.com.astrosoft.view.framework.vaadin.dsls.config
import br.com.astrosoft.view.framework.vaadin.fields.GridField
import br.com.astrosoft.viewmodel.enderecamento.presenters.pickingProdutos.ListSaldoModel
import com.vaadin.icons.VaadinIcons
import com.vaadin.ui.Grid

class GridPickingSaldo(private val modelSaldo: ListSaldoModel) :
        GridField<Saldo>(modelSaldo, Saldo::class) {
  
  private val popupView = PickingDialog(modelSaldo).apply {
    addClickListenerOk { bean -> btnPickingOK(bean) }
  }
  
  private fun btnPickingOK(bean : PickingBean) {
    modelSaldo.run {
      endereco = bean.endereco
      quantidade = bean.quantidade
      itemSelected = bean.saldo
      savePicking()
    }
  }
  
  override fun configGrid(grid: Grid<Saldo>) {
    grid.config {
      column({ it.endereco?.localizacao }) {
        caption = "Endereço"
        expandRatio = 3
      }
      columnDate({ it.ultEntrada.toDate() }) {
        caption = "Ult Entrada"
        expandRatio = 2
      }
      columnQuant({ it.saldoNConfirmado }) {
        caption = "Não Conf."
        expandRatio = 2
      }
      columnQuant({ it.saldoConfirmado }) {
        caption = "Confir."
        expandRatio = 2
      }
      columnButton(VaadinIcons.ARROW_DOWN, { saldo -> realizaPicking(saldo) }) {
        caption = "Picking"
        expandRatio = 1
      }
      clearSortOrder()
    }
  }
  
  private fun realizaPicking(saldo: Saldo) {
    if (saldo.endereco?.tipoNivel != PICKING) {
      val bean = PickingBean(endereco = "",
                             saldo = saldo,
                             quantidade = saldo.saldoConfirmado + saldo.saldoNConfirmado)
      popupView.show(bean)
    }
  }
}
