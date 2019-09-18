package br.com.astrosoft.viewmodel.enderecamento.presenters.mapaDeposito

import br.com.astrosoft.model.enderecamento.domain.ELado
import br.com.astrosoft.model.enderecamento.domain.Endereco
import br.com.astrosoft.model.enderecamento.domain.Produto
import br.com.astrosoft.model.enderecamento.domain.Rua
import br.com.astrosoft.model.enderecamento.dtos.NivelApto
import br.com.astrosoft.model.enderecamento.dtos.RuaPredio
import br.com.astrosoft.viewmodel.framework.viewmodel.ViewModel

class MapaDepositoViewModel : ViewModel() {
  override fun reloadModel() {
    mapaRuasPredios.updateLayoutRuaPredio()
  }
  
  //
  var mapaSelecionado: MapaModel? = null
  val mapaRuasPredios = MapaRuasPrediosModel(this)
  val mapaNiveisAptos = MapaNiveisAptosModel(this)
  //
  var produtosSelecionado: List<Produto>? = emptyList()
  
  fun saldoPulmaoTotal(produto: Produto): Double = execNull {
    produto.saldoPulmaoTotal
  } ?: 0.000
  
  fun findBeanValue(text: String): List<Produto> = execList {
    Produto.findProdutoQuery(text)
  }
  
  fun getProdutoQuery(query: String): List<Produto> = execList {
    Produto.findProdutoQuery(query)
  }
  
  fun selecionaEnderecosDosProdutos() = exec {
    val enderecos = getEnderecos()
    mapaRuasPredios.enderecosSelecionado = enderecos
    mapaNiveisAptos.enderecosSelecionado = enderecos
  }
  
  private fun getEnderecos(): List<Endereco> = execList {
    produtosSelecionado.orEmpty().flatMap { p ->
      p.enderecosComSaldo
    }
  }
  
  fun getNivelAptos(
          rua: Rua,
          lado: ELado
                   ): List<NivelApto> = execList {
    rua.findNivelAptos(lado)
  }
  
  fun findRuasPredio(): List<RuaPredio> {
    return Rua.ruasPredioDeposito
  }
}
