package br.com.astrosoft.view.framework.vaadin.fields

import br.com.astrosoft.view.enderecamento.vaadin.Styles
import com.vaadin.icons.VaadinIcons
import com.vaadin.shared.ui.ContentMode
import com.vaadin.ui.Alignment
import com.vaadin.ui.Button
import com.vaadin.ui.Button.ClickListener
import com.vaadin.ui.HorizontalLayout
import com.vaadin.ui.Label
import com.vaadin.ui.Panel
import com.vaadin.ui.themes.ValoTheme

class HeaderDialog(title: String, actionVoltar: ClickListener) : Panel() {
  private val titleLabel: Label
  private val toolbar: HorizontalLayout
  
  init {
    val hor = HorizontalLayout()
    hor.setMargin(false)
    hor.addStyleName("titulo_app")
    hor.addStyleName(Styles.border_less)
    hor.defaultComponentAlignment = Alignment.MIDDLE_CENTER
    hor.isSpacing = false
    setWidth("100%")
    val btnVoltar = buildButton(VaadinIcons.ARROW_LEFT, actionVoltar)
    hor.addComponent(btnVoltar)
    hor.setSizeFull()
    this.titleLabel = buildTitle(title)
    titleLabel.addStyleName("titulo_app")
    hor.addComponent(this.titleLabel)
    hor.setExpandRatio(titleLabel, 1f)
    this.toolbar = buildToolButtons()
    hor.addComponent(toolbar)
    content = hor
  }
  
  private fun buildToolButtons(): HorizontalLayout {
    val tools = HorizontalLayout()
    tools.setMargin(false)
    tools.isSpacing = false
    // tools.addStyleName("toolbar");
    return tools
  }
  
  private fun buildTitle(title: String): Label {
    val lbl = Label(title, ContentMode.HTML)
    lbl.setSizeUndefined()
    lbl.addStyleName(ValoTheme.LABEL_H2)
    lbl.addStyleName(ValoTheme.LABEL_NO_MARGIN)
    return lbl
  }
  
  fun addButton(icon: VaadinIcons, action: ClickListener) {
    val btn = buildButton(icon, action)
    toolbar.addComponent(btn)
  }
  
  private fun buildButton(icon: VaadinIcons, action: ClickListener): Button {
    val btn = Button(icon)
    btn.addStyleName(ValoTheme.BUTTON_BORDERLESS)
    btn.addStyleName(ValoTheme.BUTTON_ICON_ONLY)
    //    btn.addStyleName(ValoTheme.BUTTON_TINY);
    btn.setSizeUndefined()
    btn.addClickListener(action)
    return btn
  }
}
