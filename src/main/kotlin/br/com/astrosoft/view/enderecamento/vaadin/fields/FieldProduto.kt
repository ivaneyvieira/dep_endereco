package br.com.astrosoft.view.enderecamento.vaadin.fields

import br.com.astrosoft.model.enderecamento.domain.Produto
import br.com.astrosoft.view.framework.vaadin.fields.FieldListBean
import br.com.astrosoft.view.framework.vaadin.views.RowLayout
import com.vaadin.shared.Registration
import com.vaadin.ui.TextField

class FieldProduto : FieldListBean<Produto>() {
  lateinit var saldoPulmaoTotal: (Produto) -> Double
  private val lblNome: LabelField = LabelField("Descrição")
  private val lblSaldo: LabelField = LabelField("Saldo")
  
  override fun buidFieldMain(): TextField {
    return buildAutoComplete("Código")
  }
  
  override fun layoutComponets(layout: RowLayout) {
    layout.addComponent(this.fieldMain, 2)
    layout.addComponent(this.lblNome, 8)
    layout.addComponent(this.lblSaldo, 2)
  }
  
  fun addValueChangeEditorListener(listener: (String) -> Unit): Registration {
    return fieldMain.addBlurListener {
      val value = fieldMain.value
      listener(value)
    }
  }
  
  override fun nullValue(): List<Produto> {
    return emptyList()
  }
  
  override fun doSetValueEmpty() {
    this.lblNome.value = "NÃO ENCONTRADO"
    this.lblSaldo.value = ""
    this.fieldMain.value = this.textValue ?: ""
  }
  
  fun updateField(valor: List<Produto>) {
    value = emptyValue
    value = valor
  }
  
  override fun doSetValueBean(list: List<Produto>) {
    if (list.isEmpty()) {
      this.lblNome.value = ""
      this.fieldMain.value = ""
      this.lblSaldo.setValue(0.toDouble())
    } else {
      val prd = list[0]
      this.lblNome.value = prd.nome
      if (list.size == 1) this.fieldMain.value = prd.toString()
      else this.fieldMain.value = prd.prdno.trim { it <= ' ' }
      val saldo = list.map { saldoPulmaoTotal(it) }.sum()
      this.lblSaldo.setValue(saldo)
    }
  }
}
