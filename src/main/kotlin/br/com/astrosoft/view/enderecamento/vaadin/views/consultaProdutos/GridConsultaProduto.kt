package br.com.astrosoft.view.enderecamento.vaadin.views.consultaProdutos

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

class GridConsultaProduto(model: IListModel<Saldo>) : GridField<Saldo>(model, Saldo::class) {
  override fun configGrid(grid: Grid<Saldo>) {
    grid.config {
      column({ it.endereco?.descricao }) {
        caption = "Endereço"
      }
      column({ it.produto?.codigoGrade }) {
        caption = "Código"
      }
      column({ it.produto?.nome }) {
        caption = "Descrição"
      }
      columnQuant({ it.saldoNConfirmado }) {
        caption = "Não Confirmado"
      }
      columnQuant({ it.saldoConfirmado }) {
        caption = "Confirmado"
      }
      columnQuant({ it.produto?.saldoSaci ?: BigDecimal.ZERO }) {
        caption = "Saldo Saci"
      }
      
      columnButton(VaadinIcons.ARROW_CIRCLE_DOWN, ::navPickinView) {
        caption = "Picking"
      }
    }
  }
  
  private fun navPickinView(saldo: Saldo) {
    PickingView::class.navigatorTo(hashMapOf("id" to saldo.id))
  }
}



