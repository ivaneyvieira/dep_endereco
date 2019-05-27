package br.com.astrosoft.viewmodel.enderecamento.reports

import br.com.astrosoft.model.enderecamento.domain.Produto
import br.com.astrosoft.model.framework.services.findAll

object Reports {
  @JvmStatic
  fun main() {
    println("Produtos")
    produtos.forEach { p -> println(p) }
  }
  
  val produtos: List<Produto>
    get() = Produto.findAll()
}
