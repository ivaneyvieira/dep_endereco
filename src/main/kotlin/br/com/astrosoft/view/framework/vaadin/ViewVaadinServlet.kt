package br.com.astrosoft.view.framework.vaadin

import com.vaadin.server.CustomizedSystemMessages
import com.vaadin.server.VaadinServlet

import javax.servlet.ServletException

open class ViewVaadinServlet : VaadinServlet() {
  @Throws(ServletException::class)
  override fun servletInitialized() {
    super.servletInitialized()
    service.addSessionInitListener(VaadinSessionInitListener())
    
    service.setSystemMessagesProvider {
      val messages = CustomizedSystemMessages()
      messages.communicationErrorCaption = "Erro de comunicação"
      messages.communicationErrorMessage = "Perdeu a conexão com o servidor"
      messages.isCommunicationErrorNotificationEnabled = true
      //messages.setCommunicationErrorURL("http://vaadin.com/");
      messages
    }
  }
}