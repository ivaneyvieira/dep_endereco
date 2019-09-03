package br.com.astrosoft.view.enderecamento.vaadin

import br.com.astrosoft.model.enderecamento.domain.RegistroEndereco
import br.com.astrosoft.view.framework.vaadin.ViewVaadinServlet
import com.vaadin.annotations.VaadinServletConfiguration
import javax.servlet.ServletContextEvent
import javax.servlet.ServletContextListener
import javax.servlet.annotation.WebListener
import javax.servlet.annotation.WebServlet
import org.slf4j.LoggerFactory

private val log = LoggerFactory.getLogger(MyUIServlet::class.java)

@WebServlet(urlPatterns = ["/*"], name = "MyUIServlet")
@VaadinServletConfiguration(ui = EnderecamentoUI::class, productionMode = true)
class MyUIServlet : ViewVaadinServlet(){
  init {
   // System.setProperty("ebean.props.file", "${System.getProperty("user.home")}/ebean.properties")
  }
}

@WebListener
class Bootstrap : ServletContextListener {
  override fun contextDestroyed(sce: ServletContextEvent?) {
    log.info("Shutting down")
    log.info("Destroying VaadinOnKotlin")
    log.info("Shutdown complete")
  }

  override fun contextInitialized(sce: ServletContextEvent?) {
    log.info("Starting up")
    val home = System.getenv("HOME")
    val fileName = System.getenv("EBEAN_PROPS") ?: "$home/ebean.dep.properties"
    System.setProperty("ebean.props.file", fileName)
    RegistroEndereco.updateRegistros()
  }
}