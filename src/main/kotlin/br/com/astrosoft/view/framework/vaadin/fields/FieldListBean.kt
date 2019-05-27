package br.com.astrosoft.view.framework.vaadin.fields

import com.vaadin.ui.TextField
import org.vaadin.addons.autocomplete.AutocompleteExtension
import org.vaadin.addons.autocomplete.event.SuggestionSelectEvent
import java.util.*

abstract class FieldListBean<T> : FieldBean<List<T>>() {
  override fun doSetValue(listValue: List<T>?) {
    if (listValue.orEmpty().isNotEmpty())
      doSetValueBean(listValue.orEmpty())
    else
      doSetValueEmpty()
    this.beanValue = listValue.orEmpty()
  }
  
  protected abstract fun doSetValueEmpty()

  protected abstract fun doSetValueBean(list: List<T>)
  
  protected fun buildAutoComplete(caption: String): TextField {
    val edt = TextField(caption)
    val completePrd = AutocompleteExtension<T>(edt).apply {
      suggestionListSize = 100
    }
    completePrd.setSuggestionGenerator({ query, cap ->
                                         this.suggestPrd(query, cap)
                                       },
                                       { bean ->
                                         this.convertValuePrd(bean)
                                       },
                                       { bean, _ ->
                                         this.convertCaptionPrd(bean)
                                       })
    completePrd.addSuggestionSelectListener { this.selectSuggestion(it) }

    return edt
  }
  
  private fun convertCaptionPrd(bean: T): String {
    return convertValuePrd(bean)
  }
  
  private fun convertValuePrd(bean: T?): String {
    return bean?.toString() ?: ""
  }
  
  private fun suggestPrd(query: String, cap: Int): List<T> {
    return findBeanValue(query)
  }
  
  private fun selectSuggestion(event: SuggestionSelectEvent<T>) {
    val lista = ArrayList<T>()
    if (event.selectedItem.isPresent) {
      lista.add(event.selectedItem.get())
      value = lista
    } else
      value = null
  }
}

