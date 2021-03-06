package br.com.astrosoft.view.framework.vaadin.views.crud

import br.com.astrosoft.view.framework.vaadin.views.FormView
import br.com.astrosoft.viewmodel.framework.viewmodel.CrudControler
import br.com.astrosoft.viewmodel.framework.viewmodel.ViewModel
import com.vaadin.icons.VaadinIcons
import com.vaadin.ui.Component
import com.vaadin.ui.Grid
import org.vaadin.crudui.crud.CrudOperation
import org.vaadin.crudui.crud.impl.GridCrud
import org.vaadin.crudui.form.AbstractAutoGeneratedCrudFormFactory
import org.vaadin.crudui.form.impl.form.factory.VerticalCrudFormFactory
import org.vaadin.crudui.layout.impl.HorizontalSplitCrudLayout
import java.util.*
import kotlin.reflect.KClass

abstract class CrudView<out VIEWMODEL, BEAN : Any>(
        model: VIEWMODEL,
        private val beanClass: KClass<BEAN>
                                                  ) :
        FormView<VIEWMODEL>(model) where VIEWMODEL : ViewModel, VIEWMODEL : CrudControler<BEAN> {
  
  private val crudFields = ArrayList<CrudFields>().apply {
    addBuildCrudFields(this)
  }
  
  private var crud: GridCrud<BEAN> = GridCrud(beanClass.java, HorizontalSplitCrudLayout())
  
  protected abstract fun addBuildCrudFields(fields: MutableList<CrudFields>)
  override fun buildContentPanels(): Component {
    crud.setCrudListener(CrudUserListener(model))
    val formFactory = VerticalCrudFormFactory(beanClass.java)
    formFactory.setVisibleProperties(CrudOperation.READ,
                                     *crudFields.filter { c -> c.isRead }.map { c -> c.name }.toTypedArray())
    formFactory.setFieldCaptions(CrudOperation.READ,
                                 *crudFields.filter { c -> c.isRead }.map { c -> c.caption }.toTypedArray())
    formFactory.setVisibleProperties(CrudOperation.ADD,
                                     *crudFields.filter { c -> c.isInsert }.map { c -> c.name }.toTypedArray())
    formFactory.setFieldCaptions(CrudOperation.ADD,
                                 *crudFields.filter { c -> c.isInsert }.map { c -> c.caption }.toTypedArray())
    formFactory.setVisibleProperties(CrudOperation.UPDATE,
                                     *crudFields.filter { c -> c.isUpdate }.map { c -> c.name }.toTypedArray())
    formFactory.setFieldCaptions(CrudOperation.UPDATE,
                                 *crudFields.filter { c -> c.isUpdate }.map { c -> c.caption }.toTypedArray())
    formFactory.setVisibleProperties(CrudOperation.DELETE,
                                     *crudFields.filter { c -> c.isDelete }.map { c -> c.name }.toTypedArray())
    formFactory.setFieldCaptions(CrudOperation.DELETE,
                                 *crudFields.filter { c -> c.isDelete }.map { c -> c.caption }.toTypedArray())
    crud.crudFormFactory = formFactory
    configFormFactory(formFactory)
    formFactory.setButtonCaption(CrudOperation.ADD, "Salvar")
    formFactory.setButtonCaption(CrudOperation.DELETE, "Apagar")
    formFactory.setButtonCaption(CrudOperation.READ, "OK")
    formFactory.setButtonCaption(CrudOperation.UPDATE, "Salvar")
    formFactory.setCancelButtonCaption("Cancela")
    //Buttons
    formFactory.setButtonIcon(CrudOperation.ADD, VaadinIcons.PLUS_CIRCLE_O)
    formFactory.setButtonIcon(CrudOperation.DELETE, VaadinIcons.CLOSE_CIRCLE_O)
    formFactory.setButtonIcon(CrudOperation.READ, VaadinIcons.OPEN_BOOK)
    formFactory.setButtonIcon(CrudOperation.UPDATE, VaadinIcons.EDIT)
    crud.setRowCountCaption("%d $nomeRegistro(s) encontrador")
    crud.setSavedMessage("$nomeRegistro salvo")
    crud.setDeletedMessage("$nomeRegistro apagado")
    crud.grid.removeAllColumns()
    configGrid(crud.grid)
    return crud
  }
  
  abstract val nomeRegistro: String
  abstract fun configFormFactory(formFactory: AbstractAutoGeneratedCrudFormFactory<BEAN>)
  abstract fun configGrid(grid: Grid<BEAN>)
}
