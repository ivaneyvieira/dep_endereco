package br.com.astrosoft.view.framework.vaadin

import org.slf4j.LoggerFactory
import org.slf4j.bridge.SLF4JBridgeHandler
import javax.servlet.ServletContextEvent
import javax.servlet.ServletContextListener
import javax.servlet.annotation.WebListener
import javax.ws.rs.ApplicationPath
import javax.ws.rs.core.Application

@WebListener
class Bootstrap : ServletContextListener {
  override fun contextInitialized(sce: ServletContextEvent?) {
    log.info("Starting up")
    //Init db
    log.info("Initialization complete")
  }
  
  override fun contextDestroyed(sce: ServletContextEvent?) {
    log.info("Shutting down")
    //Destroy
    log.info("Shutdown complete")
  }
  
  companion object {
    private val log = LoggerFactory.getLogger(Bootstrap::class.java)
    
    init {
      // let java.utils.logging log to slf4j
      SLF4JBridgeHandler.removeHandlersForRootLogger()
      SLF4JBridgeHandler.install()
    }
  }
}

@ApplicationPath("/rest")
class ApplicationConfig : Application()
