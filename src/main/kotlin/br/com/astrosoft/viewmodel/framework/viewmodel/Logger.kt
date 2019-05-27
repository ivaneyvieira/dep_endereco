package br.com.astrosoft.viewmodel.framework.viewmodel

class Logger {
  fun error(msg: String, e: Throwable) {
    println(msg)
    println("==========================================")
    e.printStackTrace()
  }
}