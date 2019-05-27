package br.com.astrosoft.view.framework.vaadin.fields

import br.com.astrosoft.model.framework.exceptions.AppException
import com.vaadin.icons.VaadinIcons
import com.vaadin.ui.Button
import com.vaadin.ui.Button.ClickEvent
import com.vaadin.ui.UI
import com.vaadin.ui.VerticalLayout
import com.vaadin.ui.Window

abstract class DialogModal(private val title: String) : Window() {
  private var onClose: (DialogModal) -> Unit = {}
  
  private fun buildDialog() {
    val layout = VerticalLayout()
    val header = buildHeader(this.title)
    layout.addComponents(header)
    val layoutForm = buildForm()
    layoutForm.setSizeFull()
    layoutForm.setMargin(true)
    layout.addComponents(layoutForm)
    layout.setExpandRatio(layoutForm, 1f)
    layout.setSizeFull()
    layout.setMargin(false)
    content = layout
    isClosable = false
    isModal = true
    setSizeFull()
    isResizable = false
  }
  
  protected abstract fun buildForm(): VerticalLayout
  
  private fun buildHeader(title: String): HeaderDialog {
    val header = HeaderDialog(title, Button.ClickListener { this.voltar() })
    addContonButons()
    header.addButton(VaadinIcons.CHECK, Button.ClickListener { this.salvar(it) })
    return header
  }
  
  private fun addContonButons() {}
  
  fun onClose(onClose: (DialogModal) -> Unit) {
    this.onClose = onClose
  }
  
  fun show() {
    try {
      buildDialog()
      UI.getCurrent().addWindow(this)
      updateView()
    } catch (e: AppException) {
    }
  }
  
  override fun close() {
    super.close()
    this.onClose(this)
  }
  
  private fun voltar() {
    close()
  }
  
  protected open fun salvar(event: ClickEvent) {
    close()
  }
  
  abstract fun updateView()
  
  abstract fun updateModel()
}
