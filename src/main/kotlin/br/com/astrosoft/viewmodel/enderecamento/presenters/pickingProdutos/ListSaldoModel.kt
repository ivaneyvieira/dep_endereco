package br.com.astrosoft.viewmodel.enderecamento.presenters.pickingProdutos

import br.com.astrosoft.model.enderecamento.domain.Endereco
import br.com.astrosoft.model.enderecamento.domain.Produto
import br.com.astrosoft.model.enderecamento.domain.RepositorioEndereco
import br.com.astrosoft.model.enderecamento.domain.Saldo
import br.com.astrosoft.model.framework.exceptions.ViewException
import br.com.astrosoft.viewmodel.framework.viewmodel.IListModel
import java.math.BigDecimal

class ListSaldoModel(val model: PickingViewModel) : IListModel<Saldo>() {
  override var list: List<Saldo>? = null
  override var itemSelected: Saldo? = null
  var endereco: String? = null
  var quantidade: BigDecimal = BigDecimal.ZERO
  
  override fun updateList() {
    list = model.produto?.saldosPulmao?.sortedBy { it.ultEntrada }
  }

  fun updateEnderecos(){
    RepositorioEndereco.updateRegistros()
  }
  
  fun savePicking() = model.exec {
    val enderecoPiking = endereco?.let { Endereco.findEnderecoPiking(it) } ?: throw ViewException("Endereço de Picking Não Encontrado")
    val saldo = itemSelected ?: throw ViewException("Endereço de Picking Não Encontrado")
    saldo.savePicking(enderecoPiking, quantidade)
    model.listTransferencia.updateList()
    updateList()
  }
  
  fun enderecosPicking(): List<Endereco> = model.execList {
    //val produto = model.produto;
    //val enderecos = if (produto == null) EnderecoService.enrederecosPicking else pickinRecentes(produto)
    //if (enderecos.isEmpty()) EnderecoService.enrederecosPicking else enderecos
    Endereco.enderecosPicking
  }
  
  private fun pickinRecentes(produto: Produto): List<Endereco> = model.execList {
    Endereco.enderecoPiking(produto)
  }
}