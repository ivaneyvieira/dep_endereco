package br.com.astrosoft.view.framework.vaadin.fields

import br.com.astrosoft.model.enderecamento.domain.Produto
import br.com.astrosoft.view.framework.vaadin.views.RowLayout
import com.vaadin.data.HasValue
import com.vaadin.shared.ui.ValueChangeMode
import com.vaadin.ui.Component
import com.vaadin.ui.CssLayout
import com.vaadin.ui.CustomField
import com.vaadin.ui.TextField
import com.vaadin.ui.themes.ValoTheme
import java.text.DecimalFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter

abstract class FieldBean<T> : CustomField<T>() {
  lateinit var findBeanValue: (String) -> T
  
  protected val fieldMain: TextField = createFieldMain().apply {
    addValueChangeListener { changeValueEvent(it) }
    valueChangeMode = ValueChangeMode.BLUR
  }
  
  var beanValue: T? = null
    protected set
  var changeValue: (T) -> Unit = {}
  
  private fun createFieldMain(): TextField {
    return buidFieldMain()
  }
  
  protected abstract fun buidFieldMain(): TextField

  protected var textValue: String? = null

  private fun changeValueEvent(event: HasValue.ValueChangeEvent<String>) {
    val text = event.value?.trim() ?: ""
    val beanValue = findBeanValue(text)
    textValue = event.value
    if (event.isUserOriginated) {
      doSetValue(beanValue)
    }
    this.changeValue(beanValue)
  }
  
  override fun initContent(): Component {
    val layout = RowLayout()
    layout.setSizeFull()
    layoutComponets(layout)
    val pnl = CssLayout()
    pnl.addComponent(layout)
    pnl.setWidth("100%")
    return pnl
  }
  
  protected abstract fun layoutComponets(layout: RowLayout)
  
  override fun getValue(): T? {
    return if (beanValue == null) nullValue() else this.beanValue
  }
  
  protected abstract fun nullValue(): T
  
  protected inner class LabelField(caption: String) : TextField(caption) {
    private val decimalFormat = DecimalFormat("#,##0.####")
    private val dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    
    init {
      isReadOnly = true
      isEnabled = false
    }
    
    fun setValue(value: Double?) {
      super.setValue(this.decimalFormat.format(value))
      addStyleName(ValoTheme.TEXTFIELD_ALIGN_RIGHT)
    }
    
    fun setValue(value: LocalDate) {
      super.setValue(this.dateFormat.format(value))
    }
  }
}

interface ControlerFieldBean<out T> {
  fun findBeanValue(text: String): T
  fun saldoPulmaoTotal(produto: Produto): Double
}
