package br.com.astrosoft.view.enderecamento.vaadin.views.consultaPulmao

import br.com.astrosoft.model.enderecamento.domain.Produto
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
import java.math.BigDecimal

class GridConsultaPulmao(model: IListModel<Produto>): GridField<Produto>(model, Produto::class) {
  override fun configGrid(grid: Grid<Produto>) {
    grid.config {
      column({it.codigoGrade}) {
        caption = "Código"
      }
      column({it.nome}) {
        caption = "Descrição"
      }
      columnQuant({it?.saldoPulmaoTotal.toBigDecimal()}) {
        caption = "Confirmado"
      }
      columnQuant({it.saldoSaci ?: BigDecimal.ZERO}) {
        caption = "Saldo Saci"
      }
    }
  }

  private fun navPickinView(saldo: Saldo) {
    PickingView::class.navigatorTo(hashMapOf("id" to saldo.id))
  }
}



