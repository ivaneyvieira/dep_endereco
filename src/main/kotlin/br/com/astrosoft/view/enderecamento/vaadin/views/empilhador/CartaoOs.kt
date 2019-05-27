package br.com.astrosoft.view.enderecamento.vaadin.views.empilhador

import br.com.astrosoft.model.enderecamento.domain.OrdemServico
import br.com.astrosoft.model.framework.utils.format
import br.com.astrosoft.model.framework.utils.formatQuant
import com.vaadin.ui.Alignment
import com.vaadin.ui.CssLayout
import com.vaadin.ui.HorizontalLayout
import com.vaadin.ui.Label
import com.vaadin.ui.VerticalLayout
import com.vaadin.ui.themes.ValoTheme
import org.vaadin.teemu.switchui.Switch

class CartaoOs(val os: OrdemServico) : CssLayout() {
  var confirmaOS: (Switch, OrdemServico, Boolean) -> Unit = { _: Switch, _: OrdemServico, _: Boolean -> }
  private val ordemServico = textString("No. OS", os.numero.format("000000"))
  val descricao = textString("Descrição", os.produto?.nome ?: "")
  private val codigo = textString("Código", os.produto?.codigoGrade ?: "")
  val quant = textString("Quantidade", os.transferencia?.quantMov?.formatQuant() ?: "")
  private val palete = textString("Palete", os.transferencia?.paletE?.toString() ?: "")
  private val obs = textString("Observação", os.transferencia?.observacao ?: "-")
  private val dataHora = textString("Data/Hora", os.dataHora.format() ?: "-")
  private val endOrigem = textString("Origem", os.transferencia?.enderecoS?.descricao ?: "-")
  private val endDestino = textString("Destino", os.transferencia?.enderecoE?.descricao ?: "-")
  
  init {
    val button = Switch("Concluído")
    button.addValueChangeListener {
      if (it.isUserOriginated) {
        val value = it.value
        confirmaOS(it.component as Switch, os, value)
      }
    }
    if (obs.value == "")
      obs.value = "-"
    quant.styleName = "right"
    button.value = os.transferencia?.confirmacao ?: false
    val layout = VerticalLayout().apply {
      addComponent(HorizontalLayout().apply {
        addComponentsAndExpand(dataHora, endOrigem, endDestino)
      })
      addComponent(HorizontalLayout().apply {
        addComponentsAndExpand(ordemServico, codigo, palete, quant)
      })
      addComponent(HorizontalLayout().apply {
        addComponentsAndExpand(descricao)
        addComponent(button)
        setComponentAlignment(button, Alignment.BOTTOM_RIGHT)
      })
      addComponent(HorizontalLayout().apply {
        addComponentsAndExpand(obs)
      })
    }
    
    layout.isSpacing = true
    layout.setMargin(true)
    layout.setWidth("100%")
    
    addStyleName(ValoTheme.LAYOUT_CARD)
    addStyleName("sombra")
    
    setWidth("450px")
    addComponent(layout)
  }
  
  private fun textString(
          caption: String,
          value: String
                        ): Label {
    val label = Label(value)
    label.caption = caption
    return label
  }
}