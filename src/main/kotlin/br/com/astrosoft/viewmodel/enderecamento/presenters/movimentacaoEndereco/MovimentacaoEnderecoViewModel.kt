package br.com.astrosoft.viewmodel.enderecamento.presenters.movimentacaoEndereco

import br.com.astrosoft.model.enderecamento.domain.EOcupacao.NAO_OCUPADO
import br.com.astrosoft.model.enderecamento.domain.Endereco
import br.com.astrosoft.model.enderecamento.domain.MovProduto
import br.com.astrosoft.model.enderecamento.domain.Movimentacao
import br.com.astrosoft.model.enderecamento.domain.Produto
import br.com.astrosoft.model.enderecamento.domain.Transferencia
import br.com.astrosoft.model.enderecamento.domain.enderecoOcupado
import br.com.astrosoft.model.framework.exceptions.ViewException
import br.com.astrosoft.viewmodel.framework.viewmodel.ViewModel
import java.math.BigDecimal

class MovimentacaoEnderecoViewModel : ViewModel() {
  var enderecoOrigem: Endereco? = null
  var enderecoDestino: Endereco? = null
  val produtos = ArrayList<ProdutosGrid>()

  override fun reloadModel() {
    produtos.clear()
    addProdutos(enderecoOrigem)
    addProdutos(enderecoDestino)
    produtos.sortBy { it.localizacao.localizacao }
  }

  fun processaTranferencia() = exec {
    val origem = enderecoOrigem ?: throw ViewException("Endereço de origem não informado")
    val destino = enderecoDestino ?: throw ViewException("Endereço de destino não informado")
    if (origem.enderecoOcupado() == NAO_OCUPADO)
      throw ViewException("Endereco de origem não ocupado")
    if (destino.enderecoOcupado() != NAO_OCUPADO)
      throw ViewException("Endereco de destino já está ocupado")
    val mov = Movimentacao.novaTransferencia("Tranferencia de ${origem.localizacao} para ${destino.localizacao}")
    origem.saldos.orEmpty().filter { it.saldoConfirmado.toDouble() > 0 }.forEach { saldo ->
      saldo.produto?.let { produto ->
        val movTranferencia = MovProduto.novaMovimentacaoProduto(mov, produto, saldo.saldoConfirmado)
        Transferencia.novaTranferencia(origem, destino, movTranferencia, saldo.saldoConfirmado)
        produto.recalculaSaldo()
      }
    }
  }

  private fun addProdutos(endereco: Endereco?) {
    endereco?.saldos.orEmpty().forEach { saldo ->
      val produto = saldo.produto
      val quantidade = saldo.saldoConfirmado
      val end = saldo.endereco
      if (quantidade.toDouble() > 0 && produto != null && end != null) {
        produtos.add(
          ProdutosGrid(produto, quantidade,
                                                                                                end))
      }
    }
  }
}

data class ProdutosGrid(
  val produto: Produto,
  val quantidade: BigDecimal,
  val localizacao: Endereco
                       ){
  val codigo  = produto.codigoGrade
  val nome  = produto.nome
}