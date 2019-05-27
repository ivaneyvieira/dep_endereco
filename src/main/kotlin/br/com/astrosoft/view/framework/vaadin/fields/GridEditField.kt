package br.com.astrosoft.view.framework.vaadin.fields

import br.com.astrosoft.model.framework.exceptions.AppException
import br.com.astrosoft.viewmodel.framework.viewmodel.IListModel
import com.vaadin.ui.components.grid.EditorCancelListener
import com.vaadin.ui.components.grid.EditorSaveEvent
import com.vaadin.ui.components.grid.EditorSaveListener
import java.util.*
import kotlin.reflect.KClass

abstract class GridEditField<BEAN : Any>(model: IListModel<BEAN>, classBean: KClass<BEAN>) :
        GridField<BEAN>(model, classBean) {
  init {
    enableEditor()
  }
  
  private fun enableEditor() {
    enableEditor(EditorSaveListener { this.actionSaveBean(it) }, EditorCancelListener { this.actionCancelBean() })
  }
  
  protected abstract fun isNewBean(bean: BEAN): Boolean
  private fun actionSaveBean(event: EditorSaveEvent<BEAN>) {
    val bean = event.bean
    try {
      saveBean(bean)
    } catch (e: AppException) {
      cancelEdit()
    }
    
    updateView()
  }
  
  protected abstract val editBeans: List<BEAN>
  private fun addBlankRow() {
    val lista = newBeans
    if (lista.isEmpty()) {
      val newBean = createNewBean()
      newBean.let { bean ->
        val itens = value
        val listaArray = ArrayList<BEAN>()
        listaArray.addAll(itens.orEmpty())
        if (bean != null) listaArray.add(bean)
        setValue(listaArray)
      }
    }
  }
  
  protected abstract fun createNewBean(): BEAN?
  protected abstract fun saveBean(bean: BEAN)
  private fun cancelEdit() {
    val listaNovos = newBeans
    setValue(value?.minus(listaNovos) ?: Collections.emptyList())
  }
  
  private val newBeans: List<BEAN>
    get() = value?.filter { this.isNewBean(it) }.orEmpty()
  
  private fun actionCancelBean() {
    cancelEdit()
    updateView()
  }
  
  override fun updateView() {
    super.updateView()
    addBlankRow()
  }
}
