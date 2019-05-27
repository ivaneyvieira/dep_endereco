package br.com.astrosoft.view.framework.vaadin.fields

import br.com.astrosoft.view.framework.vaadin.views.RowLayout
import com.vaadin.data.Binder
import com.vaadin.data.HasValue
import com.vaadin.ui.Component
import com.vaadin.ui.CustomField
import kotlin.reflect.KClass

abstract class FormBinder<B : Any>(classBean: KClass<B>) : CustomField<B>() {
  internal val layout: RowLayout = RowLayout()
  private val bindBean: Binder<B?> = Binder(classBean.java)
  protected fun addFieldLayout(component: Component, size: Int) {
    layout.addComponent(component, size)
  }
  
  protected abstract fun configFields()
  override fun doSetValue(bean: B?) {
    bindBean.bean = bean
  }
  
  protected fun <F> forField(field: HasValue<F?>): Binder.BindingBuilder<B?, F?> {
    return bindBean.forField(field)
  }
  
  override fun getValue(): B? {
    return bindBean.bean
  }
  
  override fun initContent(): Component {
    configFields()
    return layout
  }
}
