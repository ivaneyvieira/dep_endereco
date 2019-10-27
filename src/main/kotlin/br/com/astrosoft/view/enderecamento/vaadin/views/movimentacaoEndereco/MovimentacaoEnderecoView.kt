package br.com.astrosoft.view.enderecamento.vaadin.views.movimentacaoEndereco

import br.com.astrosoft.model.enderecamento.domain.Endereco
import br.com.astrosoft.view.enderecamento.vaadin.fields.MaskEnderecoField
import br.com.astrosoft.view.framework.vaadin.menu.ViewMenuItem
import br.com.astrosoft.view.framework.vaadin.views.FormView
import br.com.astrosoft.viewmodel.enderecamento.presenters.movimentacaoEndereco.MovimentacaoEnderecoViewModel
import br.com.astrosoft.viewmodel.enderecamento.presenters.movimentacaoEndereco.ProdutosGrid
import com.github.mvysny.karibudsl.v8.AutoView
import com.github.mvysny.karibudsl.v8.alignment
import com.github.mvysny.karibudsl.v8.button
import com.github.mvysny.karibudsl.v8.expandRatio
import com.github.mvysny.karibudsl.v8.grid
import com.github.mvysny.karibudsl.v8.horizontalLayout
import com.vaadin.data.provider.ListDataProvider
import com.vaadin.icons.VaadinIcons
import com.vaadin.ui.Alignment
import com.vaadin.ui.Component
import com.vaadin.ui.Grid
import com.vaadin.ui.VerticalLayout

@AutoView("movimentacaoEndereco")
@ViewMenuItem(title = "Transferencia de Produtos",
              icon = VaadinIcons.EXCHANGE,
              grupo = "Movimentação",
              order = 22, tags = ["REC", "EXP"])
class MovimentacaoEnderecoView : FormView<MovimentacaoEnderecoViewModel>(
  MovimentacaoEnderecoViewModel()) {
  private var grid: Grid<ProdutosGrid>? = null
  private var origemField: MaskEnderecoField? = null
  private var destinoField: MaskEnderecoField? = null

  init {
    title = "Transferencia entre endereços"
  }

  override fun buildContentPanels(): Component {
    return VerticalLayout().apply {
      setSizeFull()
      horizontalLayout {
        val enderecosPulmao = Endereco.enderecosPulmao
        origemField = MaskEnderecoField("Endereço de origem", enderecosPulmao).apply {
          addValueChangeListener { e->
            if(e.isUserOriginated){
              model.updateModel()
              model.enderecoOrigem = Endereco.findEnderecoPulmao(e.value)
              model.reloadModel()
              model.updateView()
            }
          }
        }
        destinoField = MaskEnderecoField("Endereço de destino", enderecosPulmao).apply {
          addValueChangeListener { e->
            if(e.isUserOriginated){
              model.updateModel()
              model.enderecoDestino = Endereco.findEnderecoPulmao(e.value)
              model.reloadModel()
              model.updateView()
            }
          }
        }
        addComponentsAndExpand(origemField, destinoField)
        button("Transferencia") {
          addListener {
            model.updateEnderecos()
            model.processaTranferencia()
          }
          alignment = Alignment.BOTTOM_RIGHT
        }
      }
      grid = grid(itemClass = ProdutosGrid::class, dataProvider = ListDataProvider(emptyList<ProdutosGrid>())) {
        this.expandRatio = 1f
        setSizeFull()
        removeAllColumns()
        addColumn(ProdutosGrid::localizacao).apply {
          caption = "Localização"
        }

        addColumn(ProdutosGrid::codigo).apply {
          caption = "Código"
        }

        addColumn(ProdutosGrid::nome).apply {
          caption = "Nome"
        }

        addColumn(ProdutosGrid::quantidade).apply {
          caption = "Quantidade"
        }
      }
    }
  }

  override fun updateModel() {
    model.apply {
      this.enderecoOrigem = Endereco.findEnderecoPulmao(origemField?.value)
      this.enderecoDestino = Endereco.findEnderecoPulmao(destinoField?.value)
    }
  }

  override fun updateView() {
    origemField?.value = model.enderecoOrigem?.localizacao
    destinoField?.value = model.enderecoDestino?.localizacao
    grid?.dataProvider = ListDataProvider(model.produtos)
  }
}