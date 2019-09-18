package br.com.astrosoft.view.enderecamento.vaadin.views.mapaDeposito

import br.com.astrosoft.model.enderecamento.domain.Predio
import br.com.astrosoft.viewmodel.enderecamento.presenters.mapaDeposito.LayoutLado
import br.com.astrosoft.viewmodel.enderecamento.presenters.mapaDeposito.MapaNiveisAptosModel
import com.vaadin.icons.VaadinIcons
import com.vaadin.ui.Alignment
import com.vaadin.ui.Button
import com.vaadin.ui.Component
import com.vaadin.ui.CssLayout
import com.vaadin.ui.GridLayout
import com.vaadin.ui.HorizontalLayout
import com.vaadin.ui.Label
import com.vaadin.ui.VerticalLayout
import com.vaadin.ui.themes.ValoTheme
import java.util.*

class MapaNivelApto(private val model: MapaNiveisAptosModel) : CssLayout() {
  private val panelNivels = ArrayList<PanelNivel>()
  private val gridLayout: GridLayout
  private val lblTitle: Label
  
  init {
    val layoutVertical = VerticalLayout()
    this.lblTitle = buildTitle()
    val toolBar = buildToolBar()
    this.gridLayout = GridLayout()
    this.gridLayout.setSizeFull()
    setSizeFull()
    layoutVertical.addComponents(toolBar, this.gridLayout)
    layoutVertical.setSizeFull()
    layoutVertical.setExpandRatio(this.gridLayout, 1f)
    addComponent(layoutVertical)
  }
  
  private fun buildTitle(): Label {
    val label = Label()
    label.addStyleName(ValoTheme.LABEL_H3)
    return label
  }
  
  private fun buildToolBar(): Component {
    val layout = HorizontalLayout()
    layout.setWidth("100%")
    
    val btnVoltar = buildBtnVoltar()
    layout.addComponent(btnVoltar)
    layout.addComponent(this.lblTitle)
    layout.setComponentAlignment(this.lblTitle, Alignment.MIDDLE_LEFT)
    layout.isSpacing = true
    layout.setMargin(false)
    layout.setExpandRatio(this.lblTitle, 1f)
    return layout
  }
  
  private fun buildBtnVoltar(): Button {
    val button = Button(VaadinIcons.ARROW_LEFT)
    button.addStyleName(ValoTheme.BUTTON_BORDERLESS)
    button.addClickListener { model.model.mapaRuasPredios.showMapa() }
    return button
  }
  
  fun buildLayout() {
    val layoutLado = model.ladoSelecionado
    layoutLado?.let {
      it.carregaNiveis()
      val predios = it.predios
      val linhas = it.altura ?: 0+1
      val colunas = predios.size + 1
      limpaCelulas(colunas, linhas)
      predios.forEach { predio -> buildLayout(it, predio) }
      setTitulo(layoutLado)
    }
  }
  
  private fun buildLayout(layoutLado: LayoutLado, predio: Predio) {
    val niveis = layoutLado.niveis(predio)
    val col = layoutLado.calculaCol(predio)
    niveis.forEach { nivel ->
      val alturaNiveis = layoutLado.altura ?: 0
      val row = layoutLado.calculaRow(nivel, alturaNiveis)
      val posicao = when (row) {
        alturaNiveis - 2 -> EPosicaoNivel.BASE
        0                -> EPosicaoNivel.TOPO
        else             -> EPosicaoNivel.MEIO
      }
      val panelNivel = PanelNivel(nivel, layoutLado, posicao, model)
      this.panelNivels.add(panelNivel)
      addLayout(panelNivel, col, row)
      val lblNivel = Label(nivel.numero)
      addLayout(lblNivel, 0, row)
      val altura = nivel.altura.toFloat()
      val ratio = gridLayout.getRowExpandRatio(row)
      if (altura > ratio) gridLayout.setRowExpandRatio(row, altura)
    }
    val lblPredio = Label(predio.numero)
    addLayout(lblPredio, col, this.gridLayout.rows - 1)
  }
  
  private fun addLayout(component: Component, column: Int, row: Int): Boolean {
    return addLayout(component, column, row, column, row)
  }
  
  private fun addLayout(component: Component, column1: Int, row1: Int, column2: Int, row2: Int): Boolean {
    return try {
      this.gridLayout.addComponent(component, column1, row1, column2, row2)
      true
    } catch (e: GridLayout.OverlapsException) {
      false
    } catch (e: GridLayout.OutOfBoundsException) {
      false
    }
  }
  
  private fun limpaCelulas(columns: Int, rows: Int) {
    this.gridLayout.removeAllComponents()
    this.gridLayout.columns = columns
    this.gridLayout.rows = rows
    this.gridLayout.defaultComponentAlignment = Alignment.MIDDLE_CENTER
    for (i in 1 until columns) this.gridLayout.setColumnExpandRatio(i, 1f)
    this.gridLayout.setColumnExpandRatio(0, 0f)
    for (i in 0 until rows - 1) this.gridLayout.setRowExpandRatio(i, 100f)
    this.gridLayout.setRowExpandRatio(rows - 1, 0f)
  }
  
  private fun setTitulo(lado: LayoutLado?) {
    if (lado == null) return
    val nomeLado = lado.lado.toString()
    val rua = lado.rua
    val nomeRua = rua.numero
    this.lblTitle.value = "Rua $nomeRua Lado $nomeLado"
  }
  
  fun selecionaEnderecos() {
    val enderecos = model.enderecosSelecionado.orEmpty()
    this.panelNivels.forEach { nivel -> nivel.selecionaEnderecos(enderecos) }
  }
  
  fun updateView() {
    buildLayout()
  }
  
  fun updateModel() {
  
  }
}
