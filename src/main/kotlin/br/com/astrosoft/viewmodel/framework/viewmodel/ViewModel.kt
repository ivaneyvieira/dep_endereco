package br.com.astrosoft.viewmodel.framework.viewmodel

import br.com.astrosoft.model.framework.entityManager.Transaction
import br.com.astrosoft.model.framework.exceptions.AppException
import javax.persistence.PersistenceException

abstract class ViewModel {
  var binder: IBinder? = null
  val logger: Logger = Logger()
  private var isProceesing = false
  private var isOpen = false

  open fun processaParametros(parameters: Map<String, String?>) {}

  fun updateModel() {
    binder?.updateModel()
  }

  fun updateView() {
    binder?.updateView()
  }

  fun openModel() {
    reloadModel()
    updateView()
    isOpen = true
  }

  abstract fun reloadModel()

  fun <T> exec(valDefault: T, runnabler: () -> T?): T {
    return if(isProceesing || !isOpen) execPrivate(valDefault, runnabler)
    else {
      isProceesing = true
      if(valDefault is Unit)
        updateModel()
      val ret = execPrivate(valDefault, runnabler)
      if(valDefault is Unit)
        updateView()
      isProceesing = false
      ret
    }
  }

  fun exec(runnabler: () -> Unit) {
    exec(Unit, runnabler)
  }

  private fun <T> execPrivate(valDefault: T, runnabler: () -> T?): T {
    return try {
      val ret = runnabler()
      Transaction.commit()
      ret ?: valDefault
    } catch(e: AppException) {
      trataException(e)
      Transaction.rollback()
      valDefault
    } catch(e: PersistenceException) {
      trataException(AppException(e, e.message ?: ""))
      Transaction.rollback()
      valDefault
    } catch(e: Throwable) {
      logger.error("Outros erros", e)
      Transaction.rollback()
      valDefault
    }
  }

  fun <E> execList(runnabler: () -> List<E>?): List<E> {
    return exec(emptyList(), runnabler)
  }

  fun <E> execNull(runnabler: () -> E?): E? {
    return exec(null, runnabler)
  }

  private fun trataException(e: AppException) {
    binder?.errorNotify(e.menssagem)
    logger.error("Erro do tipo AppExcaption", e)
  }
}

