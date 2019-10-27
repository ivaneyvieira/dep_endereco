package br.com.astrosoft.view.enderecamento.vaadin.views.separacaoCarga

import br.com.astrosoft.model.enderecamento.dtos.Carga
import com.github.mvysny.karibudsl.v8.addColumnFor
import com.github.mvysny.karibudsl.v8.column
import com.github.mvysny.karibudsl.v8.expandRatio
import com.github.mvysny.karibudsl.v8.grid
import com.github.mvysny.karibudsl.v8.isSizeFull
import com.vaadin.data.provider.ListDataProvider
import com.vaadin.ui.HorizontalLayout

class GridView(val view: SeparacaoCargaView): HorizontalLayout() {
  val provider = ListDataProvider<Carga>(mutableListOf())
  val grid = grid(dataProvider = provider) {
    expandRatio = 1f
    setSizeFull()
    addColumnFor(Carga::data){
      caption = "Data"
    }
    addColumnFor(Carga::cliente){
      caption = "Cliente"
    }
    addColumnFor(Carga::prdno){
      caption = "Código"
    }
    addColumnFor(Carga::grade){
      caption = "Grade"
    }
    addColumnFor(Carga::quant){
      caption = "Quant"
    }
  }

  fun update() {
    provider.items.clear()
    provider.items.addAll(view.model.gridModel.listUpdate())
  }
}