package br.com.astrosoft.view.enderecamento.vaadin.views.mapaDeposito

import br.com.astrosoft.model.enderecamento.domain.RepositorioRua
import br.com.astrosoft.model.enderecamento.domain.Rua
import br.com.astrosoft.viewmodel.enderecamento.presenters.mapaDeposito.LayoutLado
import br.com.astrosoft.viewmodel.enderecamento.presenters.mapaDeposito.LayoutRuaPredio
import br.com.astrosoft.viewmodel.enderecamento.presenters.mapaDeposito.MapaRuasPrediosModel
import com.vaadin.ui.CssLayout
import com.vaadin.ui.GridLayout
import com.vaadin.ui.Panel
import org.apache.commons.lang3.mutable.MutableInt
import java.util.*

class MapaRuasPredios(private val model: MapaRuasPrediosModel): CssLayout() {
  private val mapPredio = HashMap<Long, PanelPredio>()
  private val layoutRuaPredio: LayoutRuaPredio? = this.model.layoutRuaPredio
  val panel = Panel()

  init {
    setSizeFull()
    panel.setSizeFull()
    addComponent(panel)
  }

  private fun buidGridLayout(): GridLayout {
    val largura = layoutRuaPredio?.largura ?: 1
    val altura = layoutRuaPredio?.altura ?: 1
    val layout = GridLayout(largura, altura)
    layout.setSizeFull()
    configLayout(layout, layoutRuaPredio)
    return layout
  }

  private fun configLayout(layout: GridLayout, layoutRuaPredios: LayoutRuaPredio?) {
    val coluna = MutableInt(0)
    val sorted = layoutRuaPredios?.ruas.orEmpty()
    for(rua in sorted) {
      configLayout(layout, layoutRuaPredios, rua, coluna)
    }
  }

<<<<<<< HEAD
  private fun configLayout(layout: GridLayout, layoutRuaPredios: LayoutRuaPredio?, rua: Rua, coluna: MutableInt) {
=======
  private fun configLayout(layout: GridLayout, layoutRuaPredios: LayoutRuaPredio?, rua: RepositorioRua,
                           coluna: MutableInt) {
>>>>>>> 27ad73c0d3590160f701d5aa4e35586f96bc2592
    val ladoImpar = layoutRuaPredios?.getLadoImpar(rua)
    val ladoPar = layoutRuaPredios?.getLadoPar(rua)
    configLayoutPredios(layout, ladoImpar, coluna)
    configLayoutRua(layout, rua, coluna)
    configLayoutPredios(layout, ladoPar, coluna)
  }

  private fun configLayoutPredios(layout: GridLayout, lado: LayoutLado?, coluna: MutableInt) {
    val predios = lado?.predios.orEmpty()
    if(predios.isNotEmpty()) {
      val row = MutableInt(layout.rows - 1)
      predios.forEach {predio ->
        val linha = row.value
        val col = coluna.value
        val layoutPredio = PanelPredio(predio) {p -> model.setLado(p)}

        layout.addComponent(layoutPredio, col, linha)

<<<<<<< HEAD
        this.mapPredio[predio.id] = layoutPredio
=======
        this.mapPredio[predio.predio_id] = layoutPredio
>>>>>>> 27ad73c0d3590160f701d5aa4e35586f96bc2592
        row.add(-1)
      }
      coluna.add(1)
    }
  }

<<<<<<< HEAD
  private fun configLayoutRua(layout: GridLayout, rua: Rua, coluna: MutableInt) {
=======
  private fun configLayoutRua(layout: GridLayout, rua: RepositorioRua, coluna: MutableInt) {
>>>>>>> 27ad73c0d3590160f701d5aa4e35586f96bc2592
    val layoutLado = PanelRua(rua)
    val fim = layout.rows - 1
    val col = coluna.value
    layout.addComponent(layoutLado, col, 1, col, fim)
    coluna.add(1)
  }

  fun show() {
    this.model.showMapa()
  }

  fun selecionaEnderecos() {
    val enderecos = model.enderecosSelecionado.orEmpty()
    limpaEnderecos()
    enderecos.forEach {endereco ->
      val predio = endereco.predio
      predio?.let {p ->
        val pnlPredio = this.mapPredio[p.id]
        pnlPredio?.markPredio()
      }
    }
  }

  private fun limpaEnderecos() {
    for(panelPredio in this.mapPredio.values) {
      panelPredio.unmarkPredio()
    }
  }

  fun updateView() {
    val grid = buidGridLayout()
    for(i in 0 until grid.columns)
      grid.setColumnExpandRatio(i, 1f)
    panel.content = grid
  }

  fun updateModel() {
<<<<<<< HEAD
    //Vazio
=======
>>>>>>> 27ad73c0d3590160f701d5aa4e35586f96bc2592
  }
}
