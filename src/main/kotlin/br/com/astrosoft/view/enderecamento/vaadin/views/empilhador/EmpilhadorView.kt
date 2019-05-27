package br.com.astrosoft.view.enderecamento.vaadin.views.empilhador

import br.com.astrosoft.model.enderecamento.domain.OrdemServico
import br.com.astrosoft.view.framework.vaadin.login.LoginService
import br.com.astrosoft.view.framework.vaadin.menu.ViewMenuItem
import br.com.astrosoft.view.framework.vaadin.views.FormView
import br.com.astrosoft.viewmodel.enderecamento.presenters.empilhador.EmpilhadorModel
import com.github.mvysny.karibudsl.v8.AutoView
import com.github.mvysny.karibudsl.v8.alignment
import com.github.mvysny.karibudsl.v8.checkBox
import com.github.mvysny.karibudsl.v8.expandRatio
import com.github.mvysny.karibudsl.v8.horizontalLayout
import com.github.mvysny.karibudsl.v8.label
import com.github.mvysny.karibudsl.v8.panel
import com.github.mvysny.karibudsl.v8.verticalLayout
import com.vaadin.icons.VaadinIcons
import com.vaadin.ui.Alignment
import com.vaadin.ui.CheckBox
import com.vaadin.ui.HorizontalLayout
import com.vaadin.ui.Label
import com.vaadin.ui.VerticalLayout
import com.vaadin.ui.themes.ValoTheme
import de.steinwedel.messagebox.ButtonOption
import de.steinwedel.messagebox.MessageBox
import org.vaadin.teemu.switchui.Switch

@AutoView("empilhador")
@ViewMenuItem(title = "Empilhador",
              icon = VaadinIcons.ARROW_CIRCLE_DOWN,
              grupo = "Movimentação",
              order = 26, tags = ["EMP", "REC", "EXP"])
class EmpilhadorView : FormView<EmpilhadorModel>(EmpilhadorModel()) {
  override fun buildContentPanels() = VerticalLayout().apply {
    verticalLayout {
      setMargin(false)
      setSizeFull()
      horizontalLayout {
        setWidth("100%")
        setMargin(true)
        addStyleName(ValoTheme.LAYOUT_WELL)
        lblUsuario = label("Usuário") {
          styleName = ValoTheme.LABEL_LARGE
        }
        chkPendente = checkBox(caption = "Somente Pendentes", checked = true) {
          styleName = ValoTheme.LABEL_LARGE
          alignment = Alignment.BOTTOM_RIGHT
          addValueChangeListener { event ->
            if (event.isUserOriginated) {
              updateModel()
            }
          }
        }
      }
      panel {
        expandRatio = 1f
        setSizeFull()
        content = cartoesContent
      }
    }
  }
  
  private var lblUsuario: Label? = null
  private var chkPendente: CheckBox? = null
  
  private var cartoesContent = HorizontalLayout().apply {
    addStyleName(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING)
    setMargin(true)
  }
  
  init {
    title = "Empilhador"
    updateModel()
  }
  
  override fun updateModel() {
    model.empilhadorHeaderModel.user = LoginService.currentUser
    
    model.empilhadorHeaderModel.osPendente = chkPendente?.value ?: true
    
    model.updateGrid()
  }
  
  override fun updateView() {
    lblUsuario?.value = model.empilhadorHeaderModel.user?.firstName + " " + model.empilhadorHeaderModel.user?.lastName
    updateCartoes()
  }
  
  private fun confirmaOS(button: Switch, os: OrdemServico, confirma: Boolean) {
    MessageBox.createQuestion()
            .withCaption("Ordem de serviço")
            .withMessage("Voce quer finalizar esta Ordem de Serviço?")
            .withYesButton(Runnable { model.confirmaOS(os, confirma) }, ButtonOption.caption("Sim"))
            .withNoButton(Runnable { button.value = !confirma }, ButtonOption.caption("Não"))
            .open()
  }
  
  private fun updateCartoes() {
    model.updateGrid()
    val ordensServico = model.empilhadorGridModel.ordensServico
    cartoesContent.removeAllComponents()
    ordensServico.forEach { os ->
      val cartao = CartaoOs(os)
      cartao.confirmaOS = this::confirmaOS
      cartoesContent.addComponent(cartao)
    }
  }
}