package br.com.astrosoft.view.framework.vaadin.login

import br.com.astrosoft.model.enderecamento.domain.User
import com.github.mvysny.karibudsl.v8.alignment
import com.github.mvysny.karibudsl.v8.button
import com.github.mvysny.karibudsl.v8.expandRatio
import com.github.mvysny.karibudsl.v8.fillParent
import com.github.mvysny.karibudsl.v8.horizontalLayout
import com.github.mvysny.karibudsl.v8.label
import com.github.mvysny.karibudsl.v8.onLeftClick
import com.github.mvysny.karibudsl.v8.panel
import com.github.mvysny.karibudsl.v8.passwordField
import com.github.mvysny.karibudsl.v8.px
import com.github.mvysny.karibudsl.v8.setPrimary
import com.github.mvysny.karibudsl.v8.textField
import com.github.mvysny.karibudsl.v8.verticalLayout
import com.github.mvysny.karibudsl.v8.w
import com.vaadin.icons.VaadinIcons
import com.vaadin.server.Page
import com.vaadin.server.VaadinSession
import com.vaadin.ui.Alignment
import com.vaadin.ui.Notification
import com.vaadin.ui.TextField
import com.vaadin.ui.VerticalLayout
import com.vaadin.ui.themes.ValoTheme

object LoginService {
  fun login(user: User) {
    Session[User::class] = user
    Page.getCurrent().reload()
  }
  
  val currentUser: User?
    get() = Session[User::class]
  
  fun logout() {
    VaadinSession.getCurrent().close()
    Page.getCurrent()?.reload()
  }
}

class LoginForm(private val appTitle: String) : VerticalLayout() {
  private lateinit var username: TextField
  private lateinit var password: TextField
  
  init {
    setSizeFull()
    panel {
      w = 500.px
      alignment = Alignment.MIDDLE_CENTER
      verticalLayout {
        w = fillParent
        horizontalLayout {
          w = fillParent
          label("Login") {
            alignment = Alignment.BOTTOM_LEFT
            addStyleNames(ValoTheme.LABEL_H4, ValoTheme.LABEL_COLORED)
          }
          label(appTitle) {
            alignment = Alignment.BOTTOM_RIGHT
            styleName = ValoTheme.LABEL_H3
            expandRatio = 1f
          }
        }
        horizontalLayout {
          w = fillParent
          username = textField("Usuário") {
            expandRatio = 1f
            w = fillParent
            icon = VaadinIcons.USER
            styleName = ValoTheme.TEXTFIELD_INLINE_ICON
          }
          password = passwordField("Senha") {
            expandRatio = 1f
            w = fillParent
            icon = VaadinIcons.LOCK
            styleName = ValoTheme.TEXTFIELD_INLINE_ICON
          }
          button("Login") {
            alignment = Alignment.BOTTOM_RIGHT
            setPrimary()
            onLeftClick { login() }
          }
        }
      }
    }
  }
  
  private fun login() {
    val name = username.value
    val pass = password.value
    val user = User.findUser(name)
    if (user == null || user.passw != pass) {
      Notification.show("Usuário ou senha inválidos. Por favor, tente novamente.")
      LoginService.logout()
    } else
      LoginService.login(user)
  }
}