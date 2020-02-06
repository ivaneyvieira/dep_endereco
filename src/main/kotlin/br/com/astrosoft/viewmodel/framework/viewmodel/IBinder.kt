package br.com.astrosoft.viewmodel.framework.viewmodel

interface IBinder {
  fun updateModel()
  fun updateView()
  fun errorNotify(errorMenssage: String)
  fun infoNotify(infoMessage: String)
}