package br.com.astrosoft.view.enderecamento.vaadin.views.recebimentoNota

import br.com.astrosoft.model.framework.exceptions.ViewException
import br.com.astrosoft.model.framework.legado.beans.NotaEntrada
import br.com.astrosoft.view.framework.vaadin.fields.FieldOptionalBean
import br.com.astrosoft.view.framework.vaadin.views.RowLayout
import com.vaadin.ui.TextField

class FieldNotaEntrada : FieldOptionalBean<NotaEntrada?>() {
  private val nomeFornecedor: LabelField = LabelField("Nome do Fornecedor")
  private val dataNota: LabelField = LabelField("Data da Nota")
  
  override fun buidFieldMain(): TextField {
    return TextField("Número Interno")
  }
  
  override fun layoutComponets(layout: RowLayout) {
    layout.addComponent(this.fieldMain, 2)
    layout.addComponent(this.nomeFornecedor, 8)
    layout.addComponent(this.dataNota, 2)
  }
  
  override fun nullValue(): NotaEntrada? {
    return null
  }
  
  override fun doSetValueEmpty() {
    this.nomeFornecedor.value = "NÃO ENCONTRADO"
    this.dataNota.caption = ""
  }
  
  override fun doSetValueBean(t: NotaEntrada?) {
    t ?: throw ViewException("Nota de entrada nao encontrada")
    this.fieldMain.value = t.invno.toString()
    this.nomeFornecedor.value = t.fornecedor
    this.dataNota.setValue(t.localData)
  }
}
