package br.com.astrosoft.viewmodel.enderecamento.presenters.ordemServico

import br.com.astrosoft.model.enderecamento.domain.OrdemServico
import br.com.astrosoft.model.enderecamento.domain.User
import br.com.astrosoft.viewmodel.framework.viewmodel.IListModel

class GridModel(val model: OrdemServicoViewModel) : IListModel<OrdemServico>() {
  override var list: List<OrdemServico>? = null
  override var itemSelected: OrdemServico? = null
  
  override fun updateList() {
    list = OrdemServico.findOrdemServico(dataInicial = model.headerModel.dataInicial,
                                         dataFinal = model.headerModel.dataFinal,
                                         confirmado = model.headerModel.confirmado,
                                         empilhador = model.headerModel.empilhador,
                                         rua = model.headerModel.rua,
                                         produto = model.headerModel.produto)
  }
  
  fun findEmpilhadores(): List<User> = User.findEmpilhadores()
  
  fun saveOrdemServico(transferencias: List<OrdemServico>) = model.exec {
    transferencias.forEach { ordem ->
      ordem.save()
    }
    
    transferencias.mapNotNull { it.transferencia?.movProduto?.produto }.distinct().forEach { it.recalculaSaldo() }
  }
}