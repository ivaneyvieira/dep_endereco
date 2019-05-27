package br.com.astrosoft.view.framework.vaadin

import com.vaadin.server.BootstrapFragmentResponse
import com.vaadin.server.BootstrapListener
import com.vaadin.server.BootstrapPageResponse
import com.vaadin.server.ServiceException
import com.vaadin.server.SessionInitEvent
import com.vaadin.server.SessionInitListener

class VaadinSessionInitListener : SessionInitListener {
  @Throws(ServiceException::class)
  override fun sessionInit(event: SessionInitEvent) {
    event.session.addBootstrapListener(object : BootstrapListener {
      override fun modifyBootstrapFragment(response: BootstrapFragmentResponse) {}
      
      override fun modifyBootstrapPage(response: BootstrapPageResponse) {
        val head = response.document.head()
        head.appendElement("meta").attr("name", "viewport").attr("content",
                                                                 "width=device-width, initial-scale=1, maximum-scale=1.0, user-scalable=no")
        head.appendElement("meta").attr("name", "apple-mobile-web-app-capable").attr("content", "yes")
        head.appendElement("meta").attr("name", "apple-mobile-web-app-status-bar-style").attr("content",
                                                                                              "black-translucent")
        val contextPath = response.request.contextPath
        head.appendElement("link").attr("rel", "apple-touch-icon").attr("href",
                                                                        "$contextPath/VAADIN/themes/dashboard/img/app-icon.png")
      }
    })
  }
}
