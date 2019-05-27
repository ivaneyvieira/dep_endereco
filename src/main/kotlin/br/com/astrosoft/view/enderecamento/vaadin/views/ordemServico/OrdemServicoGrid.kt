package br.com.astrosoft.view.enderecamento.vaadin.views.ordemServico

import br.com.astrosoft.model.enderecamento.domain.OrdemServico
import br.com.astrosoft.model.enderecamento.domain.User
import br.com.astrosoft.model.framework.utils.format
import br.com.astrosoft.view.framework.vaadin.Alignment
import br.com.astrosoft.view.framework.vaadin.dsls.column
import br.com.astrosoft.view.framework.vaadin.dsls.columnLocalDate
import br.com.astrosoft.view.framework.vaadin.dsls.columnQuant
import br.com.astrosoft.view.framework.vaadin.dsls.config
import br.com.astrosoft.view.framework.vaadin.fields.GridField
import br.com.astrosoft.viewmodel.enderecamento.presenters.ordemServico.GridModel
import com.vaadin.ui.ComboBox
import com.vaadin.ui.Grid
import com.vaadin.ui.ItemCaptionGenerator
import com.vaadin.ui.components.grid.EditorCancelListener
import com.vaadin.ui.components.grid.EditorSaveListener
import com.vaadin.ui.renderers.TextRenderer
import java.math.BigDecimal

class OrdemServicoGrid(val model: GridModel) : GridField<OrdemServico>(model, OrdemServico::class) {
  private val empilhadores: List<User> = model.findEmpilhadores()
  
  override fun configGrid(grid: Grid<OrdemServico>) {
    val combo = createCombo()
    grid.config {
      setSelectionMode(Grid.SelectionMode.MULTI)
      column({ it.numero.format("000000") }) {
        caption = "OS"
        expandRatio = 1
      }
      columnLocalDate({ it.dataHora }) {
        caption = "Data Hora"
        expandRatio = 2
      }
      column({ it.transferencia?.tipoMov?.descricao ?: "" }) {
        caption = "Tipo"
        expandRatio = 1
      }
      column({ it.transferencia?.movProduto?.produto?.codigoGrade }) {
        caption = "Código"
        expandRatio = 2
      }
      column({ it.transferencia?.enderecoS?.descricao }) {
        caption = "Origem"
        expandRatio = 2
      }
      column({ it.transferencia?.enderecoE?.descricao }) {
        caption = "Destino"
        expandRatio = 2
      }
      columnQuant({ it.transferencia?.quantMov ?: BigDecimal.ZERO }) {
        caption = "Quant"
        expandRatio = 1
      }
      column({ it.user }) {
        caption = "Empilhador"
        setEditorComponent(combo) { transf, value ->
          transf.user = value
        }
        expandRatio = 2
        setRenderer({ user -> user?.userName }, TextRenderer())
      }
      column({ it.status }) {
        caption = "Status"
        expandRatio = 1
        setStyleGenerator { Alignment.center }
      }
      enableEditor(EditorSaveListener { actionSaveBean(it.bean) },
                   EditorCancelListener { actionCancelBean() })
    }
  }
  
  private fun actionSaveBean(ordemServico: OrdemServico) {
    val ordensPendentes = grid.selectedItems.filter { !it.confimacao }
    val ordensParaSalvar = when {
      ordensPendentes.isEmpty() -> listOf(ordemServico)
      ordemServico.confimacao   -> listOf(ordemServico)
      ordemServico.id == 0L     -> listOf(ordemServico)
      else                      -> ordensPendentes
    }
    ordensParaSalvar.forEach {
      it.user = ordemServico.user
    }
    
    model.saveOrdemServico(ordensParaSalvar)
  }
  
  private fun actionCancelBean() {
  
  }
  
  private fun createCombo(): ComboBox<User?> {
    val combo = ComboBox<User?>()
    combo.setItems(empilhadores)
    combo.itemCaptionGenerator = ItemCaptionGenerator { user -> user?.userName }
    return combo
  }
}