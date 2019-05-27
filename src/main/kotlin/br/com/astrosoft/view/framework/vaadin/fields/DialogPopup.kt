package br.com.astrosoft.view.framework.vaadin.fields

import com.vaadin.data.BeanValidationBinder
import com.vaadin.ui.Button
import com.vaadin.ui.Component
import com.vaadin.ui.HorizontalLayout
import com.vaadin.ui.Label
import com.vaadin.ui.Panel
import com.vaadin.ui.UI
import com.vaadin.ui.VerticalLayout
import com.vaadin.ui.Window
import com.vaadin.ui.themes.ValoTheme
import kotlin.reflect.KClass

abstract class DialogPopup<BEAN : Any>(caption: String, classBean: KClass<BEAN>) : Window(caption) {
  private val binder: BeanValidationBinder<BEAN> = BeanValidationBinder(classBean.java)
  private val btnOk: Button = Button("Confirma")
  private val btnCancel: Button
  private var bean: BEAN? = null

  init {
    btnOk.addStyleName(ValoTheme.BUTTON_PRIMARY)
    this.btnCancel = Button("Cancela")
    isClosable = false
    isResizable = false
    isModal = true
  }

  fun show(bean: BEAN) {
    center()
    content = buildContent()
    this.bean = bean
    binder.readBean(bean)
    UI.getCurrent().addWindow(this)
  }

  private fun buildContent(): Component {
    val form = buildForm()
    configForm(form)
    val toolBar = buildToolBar()
    toolBar.setSizeFull()
    val layout = VerticalLayout(form, toolBar)
    binder.bindInstanceFields(this)
    return layout
  }

  protected abstract fun configForm(form: Panel)

  private fun buildToolBar(): Component {
    val espaco = Label()
    val tool = HorizontalLayout()
    tool.setWidth("100%")
    tool.isSpacing = true
    tool.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR)
    tool.addComponents(espaco, btnOk, btnCancel)
    tool.setExpandRatio(espaco, 1f)
    btnCancel.addClickListener { close() }
    return tool
  }

  fun addClickListenerOk(listener: (BEAN) -> Unit) {
    btnOk.addClickListener {
      if (binder.validate().isOk) {
        binder.writeBean(bean)
        bean?.let { listener(it) }
        close()
      }
    }
  }

  fun addClickListenerCancel(listener: () -> Unit) {
    btnCancel.addClickListener {
      listener()
      close()
    }
  }

  private fun buildForm(): Panel {
    val form = Panel()
    form.addStyleName(ValoTheme.PANEL_BORDERLESS)
    return form
  }
}
