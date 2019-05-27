package br.com.astrosoft.view.enderecamento.vaadin.views.pickingProdutos

import br.com.astrosoft.model.enderecamento.domain.Transferencia
import br.com.astrosoft.view.enderecamento.vaadin.fields.CheckBoxSN
import br.com.astrosoft.view.enderecamento.vaadin.fields.QuantField
import br.com.astrosoft.view.framework.vaadin.Alignment
import br.com.astrosoft.view.framework.vaadin.dsls.column
import br.com.astrosoft.view.framework.vaadin.dsls.columnLocalDate
import br.com.astrosoft.view.framework.vaadin.dsls.columnQuant
import br.com.astrosoft.view.framework.vaadin.dsls.config
import br.com.astrosoft.view.framework.vaadin.fields.GridField
import br.com.astrosoft.viewmodel.enderecamento.presenters.pickingProdutos.ListTransferenciaModel
import com.vaadin.ui.Grid

class GridPickingTransferencia(modelTransferencia: ListTransferenciaModel) :
        GridField<Transferencia>(modelTransferencia, Transferencia::class) {
  override fun configGrid(grid: Grid<Transferencia>) {
    val quant = QuantField("Quantidade")
    val confirma = CheckBoxSN("")
    grid.config {
      columnLocalDate({ it.dataHoraMov }) {
        caption = "Data e Hora"
        expandRatio = 2
      }
      column({ it.enderecoS?.localizacao }) {
        caption = "Pulmão"
        expandRatio = 2
      }
      column({ it.enderecoE?.localizacao }) {
        caption = "Piking"
        expandRatio = 2
      }
      columnQuant({ it.quantMov }) {
        setEditorComponent(quant) {obj, decimal -> obj.quantMov = decimal }
        caption = "Quant."
        expandRatio = 1
      }
      column({ it.eConfirmacao }) {
        setEditorComponent(confirma) {obj, simNao ->
          obj.eConfirmacao = simNao
        }
        caption = "Conf."
        expandRatio = 1
        setStyleGenerator { Alignment.center }
      }
      clearSortOrder()
    }
  }
}
