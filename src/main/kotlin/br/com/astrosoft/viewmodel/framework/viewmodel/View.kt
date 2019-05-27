package br.com.astrosoft.viewmodel.framework.viewmodel

interface View : IBinder {
  var isShow: Boolean
  
  fun showError(message: String)
  fun showWarnings(message: String)
  fun showInfo(message: String)
  fun processaParametros(parameters: Map<String, String?>)
  
  fun enterView(parameters: Map<String, String?>) {
    isShow = true
    processaParametros(parameters)
  }
  
  override fun errorNotify(errorMenssage: String) {
    showError(errorMenssage)
  }
}