package br.com.astrosoft.view.enderecamento.vaadin.views.separacaoCarga

import br.com.astrosoft.model.enderecamento.domain.ETipoCarga
import br.com.astrosoft.model.enderecamento.domain.ETipoCarga.EXPEDICAO
import br.com.astrosoft.model.enderecamento.domain.Endereco
import br.com.astrosoft.model.enderecamento.dtos.Carga
import br.com.astrosoft.model.framework.utils.DATE_FORMAT
import br.com.astrosoft.model.framework.utils.QUANT_FORMAT
import br.com.astrosoft.view.enderecamento.vaadin.Styles
import br.com.astrosoft.view.framework.vaadin.Alignment
import com.github.mvysny.karibudsl.v8.addColumnFor
import com.github.mvysny.karibudsl.v8.grid
import com.vaadin.data.provider.ListDataProvider
import com.vaadin.ui.ComboBox
import com.vaadin.ui.HorizontalLayout
import com.vaadin.ui.renderers.DateRenderer
import com.vaadin.ui.renderers.NumberRenderer
import com.vaadin.ui.renderers.TextRenderer

class GridView(val view: SeparacaoCargaView): HorizontalLayout() {
  val provider = ListDataProvider<Carga>(mutableListOf())
  val grid = grid(dataProvider = provider) {
    setSizeFull()
    val cmbDestino = ComboBox<ETipoCarga>().apply {
      setItems(ETipoCarga.values().toList())
      this.isEmptySelectionAllowed = false
      this.isTextInputAllowed = false
    }
    val cmbEndereco = ComboBox<Endereco>().apply {
      setItems(view.enderecos())
      this.isEmptySelectionAllowed = false
      this.isTextInputAllowed = true
      this.setItemCaptionGenerator {
        it.localizacao
      }
    }
    editor.isEnabled = true
  
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
      setEditorComponent(cmbDestino, Carga::destino.setter)
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
      setEditorComponent(cmbEndereco, Carga::enderecoOrigem.setter)
    }
    editor.addOpenListener {
      val carga = it.bean
      cmbEndereco.isEnabled = carga.destino == EXPEDICAO
    }
  
    setStyleGenerator {
      if(it.processado) Styles.grid_green
      else null
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