package br.com.astrosoft.view.enderecamento.vaadin.views.mapaDeposito

import br.com.astrosoft.model.enderecamento.domain.Apto
import br.com.astrosoft.model.enderecamento.domain.EOcupacao.CINZA
import br.com.astrosoft.model.enderecamento.domain.EOcupacao.ERRO
import br.com.astrosoft.model.enderecamento.domain.EOcupacao.NAO_OCUPADO
import br.com.astrosoft.model.enderecamento.domain.EOcupacao.OCUPADO
import br.com.astrosoft.model.enderecamento.domain.RepositorioApto
import br.com.astrosoft.view.enderecamento.vaadin.Styles
import br.com.astrosoft.viewmodel.enderecamento.presenters.mapaDeposito.LayoutLado
import br.com.astrosoft.viewmodel.enderecamento.presenters.mapaDeposito.MapaNiveisAptosModel
import com.vaadin.server.Resource
import com.vaadin.server.ThemeResource
import com.vaadin.ui.Image
import com.vaadin.ui.Panel
import com.vaadin.ui.themes.ValoTheme

class PanelApto(val apto: RepositorioApto, private val lado: LayoutLado, private val model: MapaNiveisAptosModel) :
        Panel() {
  private val dialogEndereco: DialogEndereco = DialogEndereco(model.mapaEnderecoModel)
  
  private val caixa: Resource
  private val imageOcupado: Image
  private val imageLivre: Image

  init {
    caixa = ThemeResource("img/caixa.png")
    imageOcupado = Image("Caxia", caixa)
    imageOcupado.setSizeFull()
    imageLivre = Image()
    imageLivre.setSizeFull()
    addStyleName(Styles.hand_select)
    addStyleName(ValoTheme.PANEL_BORDERLESS)
    when (apto.enderecoOcupado()){
      NAO_OCUPADO -> aptoDesocupado()
      OCUPADO     -> aptoOcupado("")
      CINZA       -> aptoOcupado(Styles.cinza)
      ERRO        -> aptoOcupado(Styles.sepia)
    }

    setSizeFull()
    addClickListener {
      val endereco = model.mapaEnderecoModel.updateEnderco(apto.endereco_id)
      endereco?.let { showDiagloApartamento() }
    }
  }
  
  private fun aptoOcupado(style : String) {
    imageOcupado.removeStyleName(Styles.cinza)
    imageOcupado.removeStyleName(Styles.sepia)
    if (style != "")
      imageOcupado.addStyleName(style)
    content = imageOcupado
  }
  
  private fun aptoDesocupado() {
    content = imageLivre
  }
  
  private fun showDiagloApartamento() {
    model.atualizaEndereco()
    dialogEndereco.let { dialog ->
      model.mapaEnderecoModel.listEnderecoProduto.processaSaldo()
      dialog.show()
    }
  }

  fun unMark() {
    imageLivre.removeStyleName(Styles.img_select)
    imageOcupado.removeStyleName(Styles.img_select)
  }
  
  fun mark() {
    imageLivre.addStyleName(Styles.img_select)
    imageOcupado.addStyleName(Styles.img_select)
  }
}
