package br.com.astrosoft.view.framework.vaadin.fields

import br.com.astrosoft.viewmodel.framework.viewmodel.IListModel
import com.vaadin.ui.Component
import com.vaadin.ui.CustomField
import com.vaadin.ui.Grid
import com.vaadin.ui.Grid.SelectionMode
import com.vaadin.ui.components.grid.EditorCancelListener
import com.vaadin.ui.components.grid.EditorSaveListener
import com.vaadin.ui.components.grid.MultiSelectionModel
import com.vaadin.ui.components.grid.SingleSelectionModel
import com.vaadin.ui.themes.ValoTheme
import kotlin.reflect.KClass

abstract class GridField<BEAN : Any>(
        private val model: IListModel<BEAN>,
        private val classBean: KClass<BEAN>
                                    ) : CustomField<List<BEAN>>() {
  protected val grid: Grid<BEAN>
  private var list: List<BEAN>? = null
  
  init {
    this.list = emptyList()
    this.grid = buildGrid(this.classBean)
  }
  
  private fun buildGrid(classBean: KClass<BEAN>): Grid<BEAN> {
    val grid = Grid(classBean.java)
    grid.setSelectionMode(SelectionMode.SINGLE)
    grid.removeAllColumns()
    grid.setSizeFull()
    grid.setItems(this.list)
    grid.isResponsive = true
    grid.addStyleName(ValoTheme.TABLE_SMALL)
    
    return grid
  }
  
  protected fun enableEditor(actionSaveBean: EditorSaveListener<BEAN>, actionCancelBean: EditorCancelListener<BEAN>) {
    this.grid.editor?.let { editor ->
      editor.isEnabled = true
      editor.cancelCaption = "Cancelar"
      editor.saveCaption = "Salvar"
      editor.addSaveListener(actionSaveBean)
      editor.addCancelListener(actionCancelBean)
      editor.isBuffered = true
    }
  }
  
  override fun initContent(): Component {
    configGrid(this.grid)
    setSizeFull()
    return this.grid
  }
  
  protected abstract fun configGrid(grid: Grid<BEAN>)
  override fun getValue(): List<BEAN>? {
    return this.list
  }
  
  override fun setValue(value: List<BEAN>) {
    super.setValue(emptyList())
    super.setValue(value)
  }
  
  override fun doSetValue(beans: List<BEAN>?) {
    if (beans == null) this.list = emptyList()
    else {
      this.list = beans
      this.grid.setItems(this.list)
      //refresh()
    }
  }
  
  open fun refresh() {
    this.list.orEmpty().forEach { l -> this.grid.dataProvider.refreshItem(l) }
  }
  
  fun itemSelecionado(): BEAN? {
    val selectedItems = grid.selectedItems
    return selectedItems.firstOrNull()
  }
  
  fun listaSelecionado(): Set<BEAN> {
    val selectedItems = grid.selectedItems
    return selectedItems.orEmpty()
  }
  
  open fun updateView() {
    if (grid.editor.isOpen)
      grid.editor.cancel()
    setValue(model.list.orEmpty())
    model.itemSelected?.let {
      grid.select(it)
    }
  }
  
  open fun updateModel() {
    model.list = value
    // val selected = grid.selectedItems.toList()
    // model.itemSelected = if(selected.isEmpty()) null else selected[0]
  }
  
  fun selectedItems() = grid.selectedItems.toList()
}
