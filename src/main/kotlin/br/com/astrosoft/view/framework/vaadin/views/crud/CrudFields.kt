package br.com.astrosoft.view.framework.vaadin.views.crud

import java.util.*

class CrudFields @JvmOverloads constructor(
        val name: String,
        val caption: String,
        vararg optionsList: ECrudFieldOptions = ECrudFieldOptions.values()
                                          ) {
  private val options = HashSet<ECrudFieldOptions>()
  
  init {
    addOptions(*optionsList)
  }
  
  private fun addOptions(vararg optionsList: ECrudFieldOptions) {
    options.addAll(optionsList)
  }
  
  val isRead: Boolean
    get() = options.contains(ECrudFieldOptions.READ)
  
  val isUpdate: Boolean
    get() = options.contains(ECrudFieldOptions.UPDATE)
  
  val isInsert: Boolean
    get() = options.contains(ECrudFieldOptions.INSERT)
  
  val isDelete: Boolean
    get() = options.contains(ECrudFieldOptions.DELETE)
}
