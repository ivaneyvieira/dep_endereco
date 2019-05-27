package br.com.astrosoft.view.enderecamento.vaadin.views.mapaDeposito

import br.com.astrosoft.model.enderecamento.domain.Saldo
import br.com.astrosoft.view.enderecamento.vaadin.views.pickingProdutos.PickingView
import br.com.astrosoft.view.framework.vaadin.dsls.column
import br.com.astrosoft.view.framework.vaadin.dsls.columnButton
import br.com.astrosoft.view.framework.vaadin.dsls.columnQuant
import br.com.astrosoft.view.framework.vaadin.dsls.config
import br.com.astrosoft.view.framework.vaadin.dsls.navigatorTo
import br.com.astrosoft.view.framework.vaadin.fields.GridField
import br.com.astrosoft.viewmodel.framework.viewmodel.IListModel
import com.vaadin.icons.VaadinIcons
import com.vaadin.ui.Grid

class GridEndProduto(model: IListModel<Saldo>) : GridField<Saldo>(model, Saldo::class) {
  override fun configGrid(grid: Grid<Saldo>) {
    grid.config {
      column({ it.produto?.codigoGrade }) {
        caption = "Código"
        expandRatio = 2
      }
      column({ it.produto?.nome }) {
        caption = "Nome"
        expandRatio = 8
      }
      columnQuant({ it.saldoNConfirmado }) {
        caption = "Não confirmado"
        expandRatio = 2
      }
      columnQuant({ it.saldoConfirmado }) {
        caption = "Confirmado"
        expandRatio = 2
      }
      columnButton(VaadinIcons.ARROW_CIRCLE_DOWN,
                   { PickingView::class.navigatorTo(hashMapOf("id" to it.id.toString())) }) {
        caption = "Picking"
      }
    }
  }
}

