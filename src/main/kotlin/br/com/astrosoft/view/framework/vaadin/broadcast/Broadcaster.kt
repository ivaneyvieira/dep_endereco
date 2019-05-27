package br.com.astrosoft.view.framework.vaadin.broadcast

import java.io.Serializable
import java.util.*
import java.util.concurrent.*

class Broadcaster : Serializable {
  
  interface BroadcastListener {
    fun receiveBroadcast(message: String)
  }
  
  companion object {
    private var executorService = Executors.newSingleThreadExecutor()
    
    private val listeners = LinkedList<BroadcastListener>()
    
    @Synchronized
    fun register(listener: BroadcastListener) {
      listeners.add(listener)
    }
    
    @Synchronized
    fun unregister(listener: BroadcastListener) {
      listeners.remove(listener)
    }
    
    @Synchronized
    fun broadcast(message: String) {
      listeners.forEach { listener ->
        executorService.execute { listener.receiveBroadcast(message) }
      }
    }
  }
}