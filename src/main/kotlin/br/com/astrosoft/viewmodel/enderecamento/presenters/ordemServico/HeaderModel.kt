package br.com.astrosoft.viewmodel.enderecamento.presenters.ordemServico

import br.com.astrosoft.model.enderecamento.domain.Produto
import br.com.astrosoft.model.enderecamento.domain.Rua
import br.com.astrosoft.model.enderecamento.domain.User
import br.com.astrosoft.model.framework.services.findAll
import java.time.LocalDate

class HeaderModel(val model: OrdemServicoViewModel) {
  var confirmado: Boolean = false
  var empilhador: User? = null
  var rua: Rua? = null
  var produto: Produto? = null
  var dataInicial: LocalDate = LocalDate.now().minusMonths(1)
  var dataFinal: LocalDate = LocalDate.now()
  
  fun pesquisaOrdem() {
    model.pesquisaOrdem()
  }
  
  fun findEmpilhadores(): List<User> {
    return model.gridModel.findEmpilhadores()
  }
  
  fun findRuas(): List<Rua> {
    return Rua.findAll()
  }
  
  fun saldoPulmaoTotal(produto: Produto): Double {
    return produto.saldoPulmaoTotal
  }
  
  fun findBeanValue(text: String): List<Produto> {
    return Produto.findProdutoQuery(text)
  }
}