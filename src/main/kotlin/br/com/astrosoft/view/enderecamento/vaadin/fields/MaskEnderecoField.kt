package br.com.astrosoft.view.enderecamento.vaadin.fields

import br.com.astrosoft.model.enderecamento.domain.Endereco
import com.vaadin.data.HasValue
import com.vaadin.data.HasValue.ValueChangeListener
import com.vaadin.shared.Registration
import com.vaadin.shared.ui.ValueChangeMode
import com.vaadin.ui.Component
import com.vaadin.ui.CustomField
import eu.maxschuster.vaadin.autocompletetextfield.AutocompleteQuery
import eu.maxschuster.vaadin.autocompletetextfield.AutocompleteSuggestion
import eu.maxschuster.vaadin.autocompletetextfield.AutocompleteSuggestionProvider
import eu.maxschuster.vaadin.autocompletetextfield.AutocompleteTextField

class MaskEnderecoField(caption: String, private val enderecos: List<Endereco>) : CustomField<String?>() {
  private val textEndereco: AutocompleteTextField = AutocompleteTextField()
  private var endereco: String? = null
  
  init {
    setCaption(caption)
  }
  
  override fun initContent(): Component {
    textEndereco.value = endereco ?: ""
    this.textEndereco.addValueChangeListener { this.valueChange(it) }
    this.textEndereco.valueChangeMode = ValueChangeMode.BLUR
    this.textEndereco.minChars = 1
    val suggestionProvider = MaskEnderecoField.EnderecosProvider(enderecos)
    this.textEndereco.suggestionProvider = suggestionProvider
    setFocusDelegate(this.textEndereco)
    return this.textEndereco
  }
  
  private fun valueChange(event: HasValue.ValueChangeEvent<String>) {
    value = event.value
    addValueChangeListener {  }
  }

  override fun addValueChangeListener(listener: ValueChangeListener<String?>?): Registration {
    return textEndereco.addValueChangeListener(listener)
  }
  
  override fun doSetValue(endereco: String?) {
    this.endereco = endereco
    textEndereco.value = endereco
  }
  
  override fun getValue(): String? {
    return endereco
  }
  
  private class EnderecosProvider(private val enderecos: List<Endereco>) : AutocompleteSuggestionProvider {
    
    override fun querySuggestions(query: AutocompleteQuery): Collection<AutocompleteSuggestion> {
      val endtext = addSep(query.term)
      return enderecos.map { e -> e.localizacao }.filter { e -> e.startsWith(endtext) }.map { e ->
        AutocompleteSuggestion(e)
      }
    }
    
    private fun addSep(term: String): String {
      var termRet = term.toUpperCase()
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
      return termRet
    }
  }
}
