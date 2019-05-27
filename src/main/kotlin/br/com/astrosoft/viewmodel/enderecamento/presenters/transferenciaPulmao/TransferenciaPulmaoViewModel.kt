package br.com.astrosoft.viewmodel.enderecamento.presenters.transferenciaPulmao

import br.com.astrosoft.model.enderecamento.domain.Endereco
import br.com.astrosoft.model.enderecamento.domain.MovProduto
import br.com.astrosoft.model.enderecamento.domain.Movimentacao
import br.com.astrosoft.model.enderecamento.domain.Transferencia
import br.com.astrosoft.model.framework.exceptions.ViewException
import br.com.astrosoft.viewmodel.framework.viewmodel.ViewModel

class TransferenciaPulmaoViewModel : ViewModel() {
  var enderecoOrigem: Endereco? = null
  var enderecoDestino: Endereco? = null

  override fun reloadModel() {
  }

  fun processaTranferencia() = exec {
    val origem = enderecoDestino ?: throw ViewException("O endereco de origem não foi informado")
    val destino = enderecoDestino ?: throw ViewException("O endereco de destino não foi informado")
    val saldoOrigem = origem.saldos?.filter { it.saldoConfirmado.toDouble() > 0 && it.produto != null }.orEmpty()
    if (saldoOrigem.isEmpty())
      throw ViewException("O endereco de origem está vazio")
    val saldoDestino = destino.saldos?.filter { it.saldoConfirmado.toDouble() > 0 && it.produto != null }.orEmpty()
    if (saldoDestino.isNotEmpty())
      throw ViewException("O endereco de destino não está vazio")
    val movimentacao = Movimentacao.novaTransferencia("")
    saldoOrigem.forEach { saldo ->
      val movTranferencia = MovProduto.novaMovimentacaoProduto(movimentacao, saldo.produto!!, saldo.saldoConfirmado)
      Transferencia.novaTranferencia(origem, destino, movTranferencia, saldo.saldoConfirmado)
    }
  }
}