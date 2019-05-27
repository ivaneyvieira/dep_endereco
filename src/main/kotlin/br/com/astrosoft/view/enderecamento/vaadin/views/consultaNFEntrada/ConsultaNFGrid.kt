package br.com.astrosoft.view.enderecamento.vaadin.views.consultaNFEntrada

import br.com.astrosoft.model.framework.legado.beans.NotaEntrada
import br.com.astrosoft.view.enderecamento.vaadin.views.recebimentoNota.RecebimentoNFView
import br.com.astrosoft.view.framework.vaadin.dsls.column
import br.com.astrosoft.view.framework.vaadin.dsls.columnButton
import br.com.astrosoft.view.framework.vaadin.dsls.columnDate
import br.com.astrosoft.view.framework.vaadin.dsls.columnInteger
import br.com.astrosoft.view.framework.vaadin.dsls.columnMoney
import br.com.astrosoft.view.framework.vaadin.dsls.config
import br.com.astrosoft.view.framework.vaadin.dsls.navigatorTo
import br.com.astrosoft.view.framework.vaadin.fields.GridField
import br.com.astrosoft.viewmodel.framework.viewmodel.IListModel
import com.vaadin.icons.VaadinIcons
import com.vaadin.ui.Grid
import java.math.BigDecimal

class ConsultaNFGrid(model: IListModel<NotaEntrada>) : GridField<NotaEntrada>(model, NotaEntrada::class) {
  override fun configGrid(grid: Grid<NotaEntrada>) {
    grid.config {
      columnInteger({ it.invno ?: 0 }) {
        caption = "Número Interno"
      }
      column({ it.documento }) {
        caption = "Nota Fiscal"
      }
      columnDate({ it.data }) {
        caption = "Data"
      }
      column({ it.fornecedor }) {
        caption = "Fornecedor"
      }
      column({ it.cnpj }) {
        caption = "CNPJ"
      }
      columnMoney({ it.valor ?: BigDecimal.ZERO }) {
        caption = "Valor"
      }
      columnButton(VaadinIcons.TRUCK, { recebimento(it) }) {
        caption = "Recebimento"
      }
    }
  }
  
  private fun recebimento(nota: NotaEntrada) {
    nota.invno?.toString()?.let {
      RecebimentoNFView::class.navigatorTo(hashMapOf("invno" to it))
    }
  }
}