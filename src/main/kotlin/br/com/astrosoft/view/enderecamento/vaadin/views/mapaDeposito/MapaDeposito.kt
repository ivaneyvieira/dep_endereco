package br.com.astrosoft.view.enderecamento.vaadin.views.mapaDeposito

import br.com.astrosoft.viewmodel.enderecamento.presenters.mapaDeposito.MapaDepositoViewModel
import br.com.astrosoft.viewmodel.enderecamento.presenters.mapaDeposito.MapaRuasPrediosModel
import com.vaadin.ui.Panel
import com.vaadin.ui.themes.ValoTheme

class MapaDeposito(val model: MapaDepositoViewModel) : Panel() {
  private val mapaRuasPredios = MapaRuasPredios(model.mapaRuasPredios)
  private val mapaNivelApto = MapaNivelApto(model.mapaNiveisAptos)
  
  init {
    content = this.mapaRuasPredios
    setSizeFull()
    addStyleName(ValoTheme.PANEL_BORDERLESS)
  }
  
  fun goHome() {
    content = this.mapaRuasPredios
  }
  
  private fun selecionaEnderecos() {
    this.mapaNivelApto.selecionaEnderecos()
    this.mapaRuasPredios.selecionaEnderecos()
  }
  
  private fun openMapaNivelApto() {
    mapaNivelApto.buildLayout()
    content = mapaNivelApto
  }
  
  private fun openMapaRuasPredios() {
    content = mapaRuasPredios
  }
  
  fun updateView() {
    if (model.mapaSelecionado == null)
      model.mapaSelecionado = model.mapaRuasPredios
    if (model.mapaSelecionado is MapaRuasPrediosModel) {
      this.mapaRuasPredios.updateView()
      openMapaRuasPredios()
    } else {
      this.mapaNivelApto.updateView()
      openMapaNivelApto()
    }
    selecionaEnderecos()
  }
  
  fun updateModel() {
    this.mapaNivelApto.updateModel()
    this.mapaRuasPredios.updateModel()
  }
}
