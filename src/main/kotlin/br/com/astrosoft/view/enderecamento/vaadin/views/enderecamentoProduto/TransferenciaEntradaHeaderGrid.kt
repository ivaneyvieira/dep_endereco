package br.com.astrosoft.view.enderecamento.vaadin.views.enderecamentoProduto

import br.com.astrosoft.model.enderecamento.domain.Saldo
import br.com.astrosoft.view.framework.vaadin.dsls.column
import br.com.astrosoft.view.framework.vaadin.dsls.columnQuant
import br.com.astrosoft.view.framework.vaadin.dsls.config
import br.com.astrosoft.view.framework.vaadin.fields.GridField
import br.com.astrosoft.viewmodel.framework.viewmodel.IListModel
import com.vaadin.ui.Grid

class TransferenciaEntradaHeaderGrid(model: IListModel<Saldo>) : GridField<Saldo>(model, Saldo::class) {
  override fun configGrid(grid: Grid<Saldo>) {
    grid.config {
      column({ it.endereco?.descricao }) { caption = "Endereco" }
      columnQuant({ it.saldoConfirmado }) { caption = "Quant" }
    }
  }
}
