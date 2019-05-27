package br.com.astrosoft.view.enderecamento.vaadin.views.enderecamentoProduto

import br.com.astrosoft.model.enderecamento.domain.Endereco
import br.com.astrosoft.viewmodel.enderecamento.presenters.enderecamentoProduto.TransferenciaEntradaGridModel
import com.vaadin.data.HasValue
import com.vaadin.ui.Component
import com.vaadin.ui.CustomField
import eu.maxschuster.vaadin.autocompletetextfield.AutocompleteQuery
import eu.maxschuster.vaadin.autocompletetextfield.AutocompleteSuggestion
import eu.maxschuster.vaadin.autocompletetextfield.AutocompleteSuggestionProvider
import eu.maxschuster.vaadin.autocompletetextfield.AutocompleteTextField

class EnderecoField(private val model: TransferenciaEntradaGridModel, caption: String) : CustomField<Endereco>() {
  private var enderecosDisponiveis: List<String>? = null
  private val textEndereco: AutocompleteTextField = AutocompleteTextField(caption)
  private var endereco: Endereco? = null
  override fun initContent(): Component {
       // this.enderecosDisponiveis = model.enderecosDisponiveis
    this.textEndereco.addValueChangeListener {
      if (it.isUserOriginated) {
        val endereco = it.value?.let { value ->
          val valueUp = value.toUpperCase()
          if (valueUp.startsWith("PI") && valueUp.length <= "PICKING ".length)
            "PICKING "
          else
            if (valueUp.startsWith("PU") && valueUp.length <= "PULMAO ".length)
              "PULMAO "
            else
              if (valueUp.matches("^[0-9]".toRegex()))
                "PULMAO $valueUp"
              else valueUp
        }
        textEndereco.value = endereco
        this.valueChange(it)
      }
    }
    //textEndereco.valueChangeMode = BLUR
    this.textEndereco.addFocusListener { this.focusListener() }
    this.textEndereco.minChars = 1
    val suggestionProvider = EnderecosDisponiveisProvider()
    this.textEndereco.suggestionProvider = suggestionProvider
    setFocusDelegate(this.textEndereco)
    return this.textEndereco
  }
  
  private fun focusListener() {
    val enderecos = model.enderecosDisponiveis + model.enderecosPicking
    this.enderecosDisponiveis = enderecos.distinct().sortedBy { it.descricao }.mapNotNull { it.descricao }
  }
  
  private fun valueChange(event: HasValue.ValueChangeEvent<String>) {
    val valor = getEndereco(event.value)
    value = valor
  }
  
  private fun getEndereco(descricao: String): Endereco? {
    return model.getEndereco(descricao)
  }
  
  override fun doSetValue(endereco: Endereco?) {
    this.endereco = endereco
    val descricao = endereco?.descricao ?: ""
    this.textEndereco.value = descricao
  }
  
  override fun getValue(): Endereco? {
    return this.endereco
  }
  
  inner class EnderecosDisponiveisProvider : AutocompleteSuggestionProvider {
    override fun querySuggestions(query: AutocompleteQuery): Collection<AutocompleteSuggestion> {
      val term = query.term.toUpperCase()
      return this@EnderecoField.enderecosDisponiveis.orEmpty().filter { e ->
        e.toUpperCase().startsWith(term) || e.toUpperCase().replace("-", "").startsWith(term)
      }.map { e -> AutocompleteSuggestion(e) }
    }
    
    private fun addSep(term: String): String {
      val partes = term.split(" +")
      val tipo = if (partes.isNotEmpty()) partes[0] else ""
      var termRet = if (partes.size >= 2) partes[1] else ""
      if (termRet.matches("^[0-9]+$".toRegex())) {
        val buf = StringBuilder()
        val termArray = termRet.toCharArray()
        for (i in termArray.indices) {
          val num = termArray[i]
          if (i == 2 || i == 4 || i == 6) buf.append("-")
          buf.append(num)
        }
        termRet = buf.toString()
      }
      return "$tipo $termRet"
    }
  }
}
