package br.com.astrosoft.view.enderecamento.vaadin.views.separacaoCarga

import br.com.astrosoft.model.enderecamento.dtos.Carga
import br.com.astrosoft.model.framework.utils.DATE_FORMAT
import br.com.astrosoft.model.framework.utils.QUANT_FORMAT
import br.com.astrosoft.view.framework.vaadin.Alignment
import com.github.mvysny.karibudsl.v8.addColumnFor
import com.github.mvysny.karibudsl.v8.grid
import com.vaadin.data.provider.ListDataProvider
import com.vaadin.ui.HorizontalLayout
import com.vaadin.ui.renderers.DateRenderer
import com.vaadin.ui.renderers.NumberRenderer
import com.vaadin.ui.renderers.TextRenderer

class GridView(val view: SeparacaoCargaView): HorizontalLayout() {
  val provider = ListDataProvider<Carga>(mutableListOf())
  val grid = grid(dataProvider = provider) {
    setSizeFull()
    addColumnFor(Carga::data) {
      caption = "Data"
      expandRatio = 1
      setRenderer(DateRenderer(DATE_FORMAT))
      setStyleGenerator {Alignment.left}
    }
    addColumnFor(Carga::cliente) {
      expandRatio = 3
      caption = "Cliente"
    }
    addColumnFor(Carga::prdno) {
      caption = "Código"
      expandRatio = 1
    }
    addColumnFor(Carga::grade) {
      caption = "Grade"
      expandRatio = 1
    }
    addColumnFor(Carga::quant) {
      caption = "Quant"
      expandRatio = 1
      setRenderer(NumberRenderer(QUANT_FORMAT))
      setStyleGenerator {Alignment.right}
    }
    addColumnFor(Carga::destino) {
      caption = "Destino"
      expandRatio = 1
    }
    addColumnFor(Carga::storenoStk) {
      caption = "Loja Estoque"
      expandRatio = 1
    }
    addColumnFor(Carga::enderecoOrigem) {
      caption = "Origem"
      setRenderer({endereco ->
                    endereco?.localizacao ?: ""
                  }, TextRenderer())
      expandRatio = 1
    }
  }
  
  init {
    setSizeFull()
  }
  
  fun update() {
    provider.items.clear()
    val list = view.model.gridModel.listUpdate()
    provider.items.addAll(list)
    provider.refreshAll()
  }
}