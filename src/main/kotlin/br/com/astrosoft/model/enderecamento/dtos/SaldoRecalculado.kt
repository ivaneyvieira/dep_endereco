package br.com.astrosoft.model.enderecamento.dtos

import br.com.astrosoft.model.enderecamento.domain.ETipoEndereco.DEPOSITO
import br.com.astrosoft.model.enderecamento.domain.Endereco
import br.com.astrosoft.model.enderecamento.domain.Produto
import br.com.astrosoft.model.enderecamento.domain.Saldo
import br.com.astrosoft.model.framework.services.findById
import java.math.BigDecimal
import java.math.BigInteger

class SaldoRecalculado(result: Array<Any>) {
  val idProduto: Long = (result[0] as BigInteger).longValueExact()
  var idEndereco: Long = (result[1] as BigInteger).longValueExact()
  private val saldoConf: BigDecimal = result[2] as BigDecimal
  private val saldoNConf: BigDecimal = result[3] as BigDecimal
  val produto: Produto? by lazy { Produto.findById(this.idProduto) }
  val endereco: Endereco? by lazy { Endereco.findById(this.idEndereco) }

  fun salva() {
    val prd = produto ?: return
    val end = endereco ?: return
    if (produto != null && endereco != null) {
      if (end.tipoEndereco == DEPOSITO) {
        val saldo = prd.saldoEm(end) ?: Saldo().apply {
          this.produto = prd
          this.endereco = end
        }
        saldo.saldoConfirmado = saldoConf
        saldo.saldoNConfirmado = saldoNConf
        saldo.save()
      }
    }
  }
}
