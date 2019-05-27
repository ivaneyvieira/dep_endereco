package br.com.astrosoft.view.framework.vaadin.views

import br.com.astrosoft.view.framework.vaadin.mvp.VaadinView
import br.com.astrosoft.viewmodel.framework.viewmodel.ViewModel
import com.vaadin.icons.VaadinIcons
import com.vaadin.navigator.View
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent
import com.vaadin.server.Responsive
import com.vaadin.shared.ui.MarginInfo
import com.vaadin.ui.Alignment
import com.vaadin.ui.Button
import com.vaadin.ui.Component
import com.vaadin.ui.HorizontalLayout
import com.vaadin.ui.Label
import com.vaadin.ui.Panel
import com.vaadin.ui.VerticalLayout
import com.vaadin.ui.themes.ValoTheme

abstract class FormView<out MODEL : ViewModel>(model: MODEL) : VaadinView<MODEL>(model), View {
  private val titleLabel: Label = Label()
  private val toolBar = HorizontalLayout().apply {
    defaultComponentAlignment = Alignment.MIDDLE_RIGHT
  }
  
  abstract fun buildContentPanels(): Component
  
  fun buildEditButton(
          desc: String,
          icon: VaadinIcons,
          clickListener: () -> Unit
                     ) {
    val button = Button().apply {
      setSizeUndefined()
      this.icon = icon
      addStyleName("icon-edit")
      caption = desc
      addClickListener {
        clickListener()
      }
    }
    toolBar.addComponent(button)
    toolBar.setExpandRatio(button, 1.0f)
    toolBar.setComponentAlignment(button, Alignment.MIDDLE_RIGHT)
  }
  
  override fun enter(viewChangeEvent: ViewChangeEvent?) {
    setSizeFull()
    val layout = buildLayout()
    compositionRoot = layout
    
    val parameters = viewChangeEvent?.parameterMap?.toMap()
    
    enterView(parameters.orEmpty())
    
    model.openModel()
  }
  
  private fun buildLayout(): VerticalLayout {
    val header = buildHeader()
    val content = buildContent()
    val layout = VerticalLayout()
    layout.setSizeFull()
    
    Responsive.makeResponsive(layout)
    
    layout.addComponent(header)
    layout.addComponentsAndExpand(content)
    layout.setMargin(true)
    layout.isSpacing = true
    return layout
  }
  
  private fun buildContent(): Component {
    val panel = Panel()
    panel.content = buildContentPanels()
    panel.setSizeFull()
    panel.addStyleName(ValoTheme.LAYOUT_WELL)
    return panel
  }
  
  private fun buildHeader(): Component {
    val header = HorizontalLayout()
    this.titleLabel.id = FormView.TITLE_ID
    this.titleLabel.setSizeUndefined()
    this.titleLabel.addStyleName(ValoTheme.LABEL_HUGE)
    header.addComponentsAndExpand(this.titleLabel)
    header.addStyleName(ValoTheme.LAYOUT_WELL)
    
    header.addComponent(toolBar)
    header.margin = MarginInfo(true, true)
    return header
  }
  
  var title: String
    get() = this.titleLabel.value
    protected set(title) {
      this.titleLabel.value = title
    }
  
  companion object {
    private const val TITLE_ID = "dashboard-title"
  }
}
