package br.com.astrosoft.view.enderecamento.vaadin.views.mapaDeposito

import br.com.astrosoft.model.enderecamento.domain.ELado
import br.com.astrosoft.model.enderecamento.domain.EOcupacao.NAO_OCUPADO
import br.com.astrosoft.model.enderecamento.domain.Endereco
import br.com.astrosoft.model.enderecamento.domain.Nivel
import br.com.astrosoft.view.enderecamento.vaadin.Styles
import br.com.astrosoft.viewmodel.enderecamento.presenters.mapaDeposito.LayoutLado
import br.com.astrosoft.viewmodel.enderecamento.presenters.mapaDeposito.MapaNiveisAptosModel
import com.vaadin.ui.Component
import com.vaadin.ui.CssLayout
import com.vaadin.ui.GridLayout
import com.vaadin.ui.Panel
import com.vaadin.ui.themes.ValoTheme
import java.util.*

class PanelNivel(
  private val nivel: Nivel,
  private val lado: LayoutLado,
  posicao: EPosicaoNivel,
  private val nivelPredioModel: MapaNiveisAptosModel
                ) : CssLayout() {
  private val mapPanelApto = HashMap<Long, PanelApto>()

  init {
    setSizeFull()
    val panel = buildPanel(posicao)
    addComponent(panel)
  }

  private fun buildPanel(posicao: EPosicaoNivel): Panel {
    val panel = Panel()
    panel.setSizeFull()
    panel.addStyleName(ValoTheme.PANEL_BORDERLESS)
    when (posicao) {
      EPosicaoNivel.BASE -> panel.addStyleName(Styles.box_nivel_base)
      EPosicaoNivel.TOPO -> panel.addStyleName(Styles.box_nivel_topo)
      else               -> panel.addStyleName(Styles.box_nivel)
    }
    if (nivel.erroLayout())
      panel.addStyleName(Styles.box_erro)
    else
      panel.removeStyleName(Styles.box_erro)

    panel.content = buildAptos()
    return panel
  }

  private fun buildAptos(): Component {
    val eLado = this.nivel.predio?.lado
    val aptos = this.lado.getAptos(nivel).sortedBy { it.numero }.let {
      if (eLado === ELado.PAR) it.asReversed() else it
    }
    val colunas = aptos.size
    val linha = 1
    val grid = GridLayout(colunas, linha)

    grid.setSizeFull()
    val total = nivel.total()
    val resto = 30 - total
    var acumulado = 0
    val tamanhosVazios = when (resto) {
      30   -> arrayListOf(10, 10, 10)
      20   -> arrayListOf(10, 10)
      15   -> arrayListOf(15)
      10   -> arrayListOf(10)
      5    -> arrayListOf(5)
      0    -> arrayListOf(0)
      else -> arrayListOf(0)
    }
    aptos.withIndex().forEach { (index, apto) ->
      val row = linha - 1
      val panelApto = PanelApto(apto, lado, nivelPredioModel)
      this.mapPanelApto[apto.id] = panelApto
      grid.addComponent(panelApto, index, row, index, row)
      val ocupado = apto.ocupacao != NAO_OCUPADO
      val tamanhoApto = if (ocupado)
        apto.tipoPalet.tamanho
      else
        tamanhosVazios.pop()

      acumulado += tamanhoApto

      if (total > 30)
        grid.setColumnExpandRatio(index, 10.toFloat())
      else
        grid.setColumnExpandRatio(index, tamanhoApto.toFloat())
    }
    return grid
  }

  fun selecionaEnderecos(enderecos: List<Endereco>) {
    this.mapPanelApto.values.forEach { it.unMark() }
    enderecos.forEach { endereco ->
      endereco.apto?.let { apto ->
        val panelApto = this.mapPanelApto[apto.id]
        return@let panelApto?.mark()
      }
    }
  }
}

private fun ArrayList<Int>.pop(): Int {
  val f = firstOrNull()
  return if (f == null) 0
  else {
    removeAt(0)
    f
  }
}
