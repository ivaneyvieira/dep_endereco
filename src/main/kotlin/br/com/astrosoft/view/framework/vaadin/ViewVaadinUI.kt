package br.com.astrosoft.view.framework.vaadin

import br.com.astrosoft.view.framework.vaadin.images.makeResource
import br.com.astrosoft.view.framework.vaadin.login.LoginForm
import br.com.astrosoft.view.framework.vaadin.login.LoginService
import br.com.astrosoft.view.framework.vaadin.menu.BeanMenuItem
import br.com.astrosoft.view.framework.vaadin.menu.BeanViewManager
import br.com.astrosoft.view.framework.vaadin.views.FormView
import br.com.astrosoft.viewmodel.framework.viewmodel.ViewMainModel
import com.github.mvysny.karibudsl.v8.autoViewProvider
import com.github.mvysny.karibudsl.v8.item
import com.github.mvysny.karibudsl.v8.valoMenu
import com.vaadin.annotations.JavaScript
import com.vaadin.annotations.Push
import com.vaadin.annotations.Viewport
import com.vaadin.icons.VaadinIcons
import com.vaadin.navigator.Navigator
import com.vaadin.navigator.PushStateNavigation
import com.vaadin.navigator.View
import com.vaadin.navigator.ViewDisplay
import com.vaadin.server.Page
import com.vaadin.server.VaadinRequest
import com.vaadin.shared.Position
import com.vaadin.shared.ui.ui.Transport
import com.vaadin.ui.Component
import com.vaadin.ui.Notification
import com.vaadin.ui.ReconnectDialogConfiguration
import com.vaadin.ui.UI
import com.vaadin.ui.themes.ValoTheme
import org.slf4j.LoggerFactory

@Push(transport = Transport.WEBSOCKET_XHR)
@Viewport("width=device-width, initial-scale=1.0")
@JavaScript("https://code.jquery.com/jquery-2.1.4.min.js",
            "https://code.responsivevoice.org/responsivevoice.js")
@PushStateNavigation
open class ViewVaadinUI(
        private val titleApp: String,
        private val subTitleApp: String,
        beanViewManager: BeanViewManager,
        val model: ViewMainModel
                       ) : UI() {
  private val listView: List<BeanMenuItem<View>> = beanViewManager.availableViews
  private val viewDefault: BeanMenuItem<View>? = getViewDefault(this.listView)
  
  private fun getViewDefault(listView: List<BeanMenuItem<View>>): BeanMenuItem<View>? {
    return listView.firstOrNull { bean -> bean.id == "" }
  }
  
  override fun getReconnectDialogConfiguration(): ReconnectDialogConfiguration {
    val configuration = super.getReconnectDialogConfiguration()
    configuration.dialogGracePeriod = 2000
    configuration.isDialogModal = true
    configuration.dialogText = "Restabelecendo conexão com o servidor"
    configuration.dialogTextGaveUp = "Não foi possivel estabelecer uma conexão confiavel com o servidor"
    configuration.reconnectAttempts = 5
    configuration.reconnectInterval = 2000
    
    return configuration
  }
  
  private fun listViewAutorizado(): List<BeanMenuItem<View>> {
    val user = LoginService.currentUser
    val list = user?.let { currentUser ->
      val tagRoles = currentUser.roles.map { it.tag }
      listView.filter { menuItem ->
        if (tagRoles.contains("ADM")) true
        else {
          val tagsMenu = menuItem.tags
          val inter = tagsMenu.intersect(tagRoles)
          inter.isNotEmpty()
        }
      }
    }
    return list
           ?: emptyList()
  }
  
  private val content = valoMenu {
    appTitle = "<h3>$titleApp<strong> $subTitleApp</strong></h3>"
    val user = LoginService.currentUser
    user?.let { currentUser ->
      val listViewAutorizado = listViewAutorizado()
      if (listViewAutorizado.size > 1) {
        userMenu {
          val username = currentUser.userName
          val image = currentUser.fotoPerfil?.makeResource()
          item(username, image) {
            item("Perfil do usuário")
            item("Preferences")
            addSeparator()
            item("Sair", menuSelected = {
              LoginService.logout()
            })
          }
        }
        
        viewDefault?.let { view ->
          menuButton(view.title, view.icon, view = view.viewClass)
        }
        
        val grupos = listViewAutorizado.filter { it.grupo != "" }.groupBy { it.grupo }
        
        grupos.forEach { grupo ->
          section(grupo.key)
          grupo.value.forEach { view ->
            menuButton(view.title, view.icon, view = view.viewClass)
          }
        }
        
      }
    }
  }
  
  override fun init(request: VaadinRequest?) {
    if (LoginService.currentUser == null) {
      setContent(LoginForm(titleApp))
    } else {
      val listViewAutorizado = listViewAutorizado()
      if (listViewAutorizado.size == 1) {
        val oneView = listViewAutorizado.firstOrNull()
        val content = oneView?.viewClass?.newInstance().apply {
          (this as? FormView<*>)?.buildEditButton("Sair", VaadinIcons.CLOSE) {
            LoginService.logout()
          }
        }
        content?.enter(null)
        
        setContent(content as Component)
      } else {
        setContent(content)
        navigator = Navigator(this, content as ViewDisplay)
        navigator.addProvider(autoViewProvider)
        
        setErrorHandler { e ->
          log.error("Vaadin UI uncaught exception ${e.throwable}", e.throwable)
          val description = "Ocorreu um erro, e nos sentimos muito por isso. Já está trabalhando na solução!"
          // when the exception occurs, show a nice notification
          Notification("Oops", description, Notification.Type.ERROR_MESSAGE).apply {
            styleName = "${ValoTheme.NOTIFICATION_CLOSABLE} ${ValoTheme.NOTIFICATION_ERROR}"
            position = Position.TOP_CENTER
            show(Page.getCurrent())
          }
        }
      }
    }
  }
  
  companion object {
    @JvmStatic
    private val log = LoggerFactory.getLogger(ViewVaadinUI::class.java)
  }
}
