package br.com.astrosoft.view.enderecamento.vaadin.views.enderecamentoProduto

import br.com.astrosoft.model.enderecamento.domain.EStatusEntrada.CONFERIDA
import br.com.astrosoft.model.enderecamento.domain.EStatusEntrada.ENDERECADA
import br.com.astrosoft.model.enderecamento.domain.EStatusEntrada.INCONSISTENTE
import br.com.astrosoft.model.enderecamento.domain.EStatusEntrada.NAO_RECEBIDA
import br.com.astrosoft.model.enderecamento.domain.EStatusEntrada.RECEBIDA
import br.com.astrosoft.model.enderecamento.domain.MovProduto
import br.com.astrosoft.view.enderecamento.vaadin.Styles
import br.com.astrosoft.view.framework.vaadin.dsls.column
import br.com.astrosoft.view.framework.vaadin.dsls.columnButton
import br.com.astrosoft.view.framework.vaadin.dsls.columnQuant
import br.com.astrosoft.view.framework.vaadin.dsls.config
import br.com.astrosoft.view.framework.vaadin.fields.GridField
import br.com.astrosoft.viewmodel.enderecamento.presenters.enderecamentoProduto.ProdutosRecebidosModel
import com.vaadin.icons.VaadinIcons
import com.vaadin.ui.Grid

class ProdutosRecebidosGrid(private val modelProduto: ProdutosRecebidosModel) : GridField<MovProduto>(modelProduto,
                                                                                                      MovProduto::class) {
  
  private val transferenciaEntradaDialog = TransferenciaEntradaDialog(modelProduto.transferenciaEntradaModel)
  override fun configGrid(grid: Grid<MovProduto>) {
    grid.config {
      column({ it.movimentacao?.chave }) {
        caption = "Chave"
        expandRatio = 2
      }
      column({ it.movimentacao?.documento }) {
        caption = "Documento"
        expandRatio = 2
      }
      column({ it.produto?.codigoGrade }) {
        caption = "Código"
        expandRatio = 2
      }
      column({ it.produto?.nome }) {
        caption = "Descrição"
        expandRatio = 4
      }
      
      columnQuant({ it.quantMov }) {
        caption = "Recebido"
        expandRatio = 1
      }
      columnQuant({ it.quantNaoEnderecada }) {
        caption = "Sem Endereço"
        expandRatio = 1
      }
      columnButton(VaadinIcons.STOCK, { enderecamento(it) }) {
        caption = "Endereçamento"
        expandRatio = 1
      }
      setStyleGenerator { mp ->
        when (mp.statusEntrada) {
          NAO_RECEBIDA  -> Styles.grid_yellow
          RECEBIDA      -> Styles.grid_orange
          ENDERECADA    -> Styles.grid_purple
          CONFERIDA     -> Styles.grid_green
          INCONSISTENTE -> Styles.grid_red
        }
      }
    }
  }
  
  private fun enderecamento(item: MovProduto) {
    modelProduto.atualizaDialog(item)
    this.transferenciaEntradaDialog.show()
  }
  
  override fun updateView() {
    super.updateView()
    transferenciaEntradaDialog.updateView()
  }
  
  override fun updateModel() {
    super.updateModel()
    transferenciaEntradaDialog.updateModel()
  }
}
