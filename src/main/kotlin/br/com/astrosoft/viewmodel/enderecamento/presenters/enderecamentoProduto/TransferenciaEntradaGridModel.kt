package br.com.astrosoft.viewmodel.enderecamento.presenters.enderecamentoProduto

import br.com.astrosoft.model.enderecamento.domain.Endereco
import br.com.astrosoft.model.enderecamento.domain.OcupacaoEndereco
import br.com.astrosoft.model.enderecamento.domain.Transferencia
import br.com.astrosoft.viewmodel.framework.viewmodel.IListModel

class TransferenciaEntradaGridModel(val model: EnderecamentoProdutoViewModel): IListModel<Transferencia>() {
  override var list: List<Transferencia>? = null
  override var itemSelected: Transferencia? = null
  val recebimento = Endereco.recebimento()
  val enderecos: MutableList<Endereco> = Endereco.all()
  val enderecosDisponiveis: List<Endereco>
    get() = model.produtosRecebidosModel.transferenciaEntradaModel.header.let {header ->
      val palet = header.comboPalete
      val altura = header.comboAltura
      val ruas = header.tokenRuas
      val lado = header.lado
      OcupacaoEndereco.enderecosDisponiveis(palet, altura, ruas, lado)
    }
  val enderecosPicking: List<Endereco>
    get() = Endereco.enderecosPicking

  fun deleteTransferencia() = model.exec {
    itemSelected?.delete()
    updateList()
    model.produtosRecebidosModel.transferenciaEntradaModel.header.updateList()
  }

  fun salvaTransferencia() = model.exec {
    val transferenciaEntradaModel = model.produtosRecebidosModel.transferenciaEntradaModel
    transferenciaEntradaModel.let {
      val palet = transferenciaEntradaModel.header.comboPalete
      itemSelected?.save(palet)
    }
    updateList()
    model.produtosRecebidosModel.transferenciaEntradaModel.header.updateList()
  }

  fun getEndereco(descricao: String): Endereco? = model.execNull {
    if(descricao.isBlank()) null
    else
      enderecos.firstOrNull {it.descricao == descricao}
  }

  override fun updateList() {
    val movProduto = model.produtosRecebidosModel.transferenciaEntradaModel.header.movProduto
    movProduto?.let {
      it.refresh()
      list = it.transferencias()
    }
    //list = Transferencia.where().movProduto.id.eq(movProduto?.id).findList()
    model.produtosRecebidosModel.updateList()
  }
}