package br.com.astrosoft.view.enderecamento.vaadin

import br.com.astrosoft.model.framework.utils.readFile
import br.com.astrosoft.view.framework.vaadin.ViewVaadinUI
import br.com.astrosoft.view.framework.vaadin.menu.BeanViewManager
import br.com.astrosoft.viewmodel.enderecamento.presenters.EnderecamentoViewMainModel
import com.vaadin.annotations.JavaScript
import com.vaadin.annotations.Push
import com.vaadin.annotations.Theme
import com.vaadin.annotations.Title
import com.vaadin.annotations.Viewport
import com.vaadin.navigator.PushStateNavigation
import com.vaadin.shared.ui.ui.Transport

val versaoSistema = "/versao.txt".readFile()

@Theme("enderecamentotheme")
@Title("Endereçamento")
@Viewport("width=device-width, initial-scale=1.0")
@Push(transport = Transport.WEBSOCKET_XHR)
@JavaScript("https://code.jquery.com/jquery-2.1.4.min.js",
            "https://code.responsivevoice.org/responsivevoice.js")
@PushStateNavigation
class EnderecamentoUI : ViewVaadinUI(
  "Enderecamento",
  "Versão $versaoSistema",
  BeanViewManager("br.com.astrosoft"),
  EnderecamentoViewMainModel()
                                    )

