package br.com.astrosoft.view.enderecamento.vaadin.views.ordemServico

import br.com.astrosoft.model.enderecamento.domain.Rua
import br.com.astrosoft.model.enderecamento.domain.User
import br.com.astrosoft.view.enderecamento.vaadin.fields.FieldProduto
import br.com.astrosoft.viewmodel.enderecamento.presenters.ordemServico.HeaderModel
import com.vaadin.ui.ComboBox
import com.vaadin.ui.DateField
import com.vaadin.ui.HorizontalLayout
import com.vaadin.ui.ItemCaptionGenerator
import com.vaadin.ui.Panel
import com.vaadin.ui.VerticalLayout

class OrdemServicoHeader(val model: HeaderModel) : Panel() {
  private val empilhadores: List<User> = model.findEmpilhadores()
  private val ruas: List<Rua> = model.findRuas()
  
  private val dataInicial = DateField("Data Inicial").apply {
    dateFormat = "dd/MM/yyyy"
    placeholder = "dd/mm/aaaa"
    addValueChangeListener { if (it.isUserOriginated) atualizarGrid() }
  }
  
  private val dataFinal = DateField("Data Final").apply {
    dateFormat = "dd/MM/yyyy"
    placeholder = "dd/mm/aaaa"
    addValueChangeListener { if (it.isUserOriginated) atualizarGrid() }
  }
  
  private val confirmado = ComboBox<Boolean?>("Concluído").apply {
    isTextInputAllowed = false
    
    this.isEmptySelectionAllowed = false
    this.value = false
    setItems(true, false)
    itemCaptionGenerator = ItemCaptionGenerator {
      it?.let {value ->
        if (value) "Sim" else "Não"
      }
    }
    addValueChangeListener { if (it.isUserOriginated) atualizarGrid() }
  }
  
  private val empilhador = ComboBox<User>("Empilhador").apply {
    isTextInputAllowed = false
    this.emptySelectionCaption = "Todos"
    setItems(empilhadores)
    itemCaptionGenerator = ItemCaptionGenerator { it.userName }
    addValueChangeListener { if (it.isUserOriginated) atualizarGrid() }
  }
  
  val rua = ComboBox<Rua>("Ruas").apply {
    isTextInputAllowed = false
    this.emptySelectionCaption = "Todas"
    setItems(ruas)
    itemCaptionGenerator = ItemCaptionGenerator { "Rua " + it.numero }
    addValueChangeListener { if (it.isUserOriginated) atualizarGrid() }
  }
  
  val produto = FieldProduto().apply {
    saldoPulmaoTotal = model::saldoPulmaoTotal
    findBeanValue = model::findBeanValue
    addValueChangeEditorListener { atualizarGrid() }
  }
  
  init {
    val outerFiled = HorizontalLayout(dataInicial, dataFinal, confirmado, empilhador, rua)
    val horLayout = VerticalLayout(produto, outerFiled)
    content = horLayout
    updateView()
  }
  
  fun updateModel() {
    model.dataInicial = dataInicial.value
    model.dataFinal = dataFinal.value
    model.confirmado = confirmado.value ?: false
    model.empilhador = empilhador.value
    model.rua = rua.value
    model.produto = produto.value?.let {
      if (it.isEmpty()) null else it[0]
    }
  }
  
  fun updateView() {
    confirmado.value = model.confirmado
    empilhador.value = model.empilhador
    rua.value = model.rua
    produto.value = model.produto?.let { listOf(it) }
    dataInicial.value = model.dataInicial
    dataFinal.value = model.dataFinal
  }
  
  private fun atualizarGrid() {
    model.pesquisaOrdem()
  }
}

