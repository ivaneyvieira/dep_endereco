package br.com.astrosoft.viewmodel.enderecamento.presenters.ordemServico

import br.com.astrosoft.model.enderecamento.domain.OrdemServico
import br.com.astrosoft.viewmodel.framework.viewmodel.ViewModel

class OrdemServicoViewModel : ViewModel() {
  
  val headerModel = HeaderModel(this)
  val gridModel = GridModel(this)
  
  override fun reloadModel() {
    gridModel.updateList()
  }
  
  override fun processaParametros(parameters: Map<String, String?>) {
    headerModel.pesquisaOrdem()
  }
  
  fun pesquisaOrdem() = exec {
    gridModel.updateList()
  }
  
  fun removeOs(oss: List<OrdemServico>) = exec {
    val osNaoConfirmada = oss.filter { it.transferencia?.confirmacao == false }
    val transferencias = osNaoConfirmada.mapNotNull { it.transferencia }
    val produtos = transferencias
            .mapNotNull { it.movProduto }
            .mapNotNull { it.produto }
            .distinct()
    osNaoConfirmada.forEach { it.delete() }
    transferencias.forEach { it.delete() }
    produtos.forEach { it.recalculaSaldo() }
    gridModel.updateList()
  }
}