package br.com.astrosoft.view.enderecamento.vaadin.views.recebimentoNota

import br.com.astrosoft.model.enderecamento.domain.EStatusEntrada.CONFERIDA
import br.com.astrosoft.model.enderecamento.domain.EStatusEntrada.ENDERECADA
import br.com.astrosoft.model.enderecamento.domain.EStatusEntrada.INCONSISTENTE
import br.com.astrosoft.model.enderecamento.domain.EStatusEntrada.NAO_RECEBIDA
import br.com.astrosoft.model.enderecamento.domain.EStatusEntrada.RECEBIDA
import br.com.astrosoft.model.framework.legado.beans.ProdutoNotaEntrada
import br.com.astrosoft.view.enderecamento.vaadin.Styles
import br.com.astrosoft.view.enderecamento.vaadin.fields.QuantField
import br.com.astrosoft.view.framework.vaadin.dsls.column
import br.com.astrosoft.view.framework.vaadin.dsls.columnQuant
import br.com.astrosoft.view.framework.vaadin.dsls.config
import br.com.astrosoft.view.framework.vaadin.fields.GridField
import br.com.astrosoft.viewmodel.enderecamento.presenters.recebimentoNota.ListProdutoNota
import com.vaadin.ui.Grid
import java.math.BigDecimal

class GridProdutoNotaEntrada(private val modelProduto: ListProdutoNota) :
        GridField<ProdutoNotaEntrada>(modelProduto, ProdutoNotaEntrada::class) {
  override fun configGrid(grid: Grid<ProdutoNotaEntrada>) {
    val quant = QuantField("Quantidade")
    val palet = QuantField("Palete")
    grid.config {
      grid.addColumn { it.produtoGrade }
        .setCaption("Código").expandRatio = 2
      grid.addColumn { it.nomeProduto }
        .setCaption("Nome").expandRatio = 5
      columnQuant({ BigDecimal.valueOf(it.quant ?: 0.000) }) {
        caption = "Quant"
        expandRatio = 1
      }
      columnQuant({ it.quantRecebido }) {
        setEditorComponent(quant) { obj, decimal -> obj.quantRecebido = decimal }
        caption = "Recebido"
        expandRatio = 1
      }
      columnQuant({ it.quantPalete ?: BigDecimal.ZERO }) {
        setEditorComponent(palet) { obj, decimal -> obj.quantPalete = decimal }
        caption = "Palete"
        expandRatio = 1
      }
      column({ it.status }) {
        caption = "Status"
        expandRatio = 2
      }
      setStyleGenerator { mp ->
        when (mp.status) {
          NAO_RECEBIDA  -> Styles.grid_yellow
          RECEBIDA      -> Styles.grid_orange
          ENDERECADA    -> Styles.grid_purple
          CONFERIDA     -> Styles.grid_green
          INCONSISTENTE -> Styles.grid_red
        }
      }
      editor.isEnabled = true
      editor.cancelCaption = "Cancelar"
      editor.saveCaption = "Salvar"
      editor.addSaveListener {e -> recebimento(e.bean) }
    }
  }
  
  private fun recebimento(produto: ProdutoNotaEntrada) {
    modelProduto.itemSelected = produto
    this.modelProduto.recebeProduto()
    
    setValue(this.value.orEmpty())
    refresh()
  }
}
  

