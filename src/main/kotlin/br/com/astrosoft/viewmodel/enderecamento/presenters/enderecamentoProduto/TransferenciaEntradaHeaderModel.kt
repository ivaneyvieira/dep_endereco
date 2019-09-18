package br.com.astrosoft.viewmodel.enderecamento.presenters.enderecamentoProduto

import br.com.astrosoft.model.enderecamento.domain.ELado
import br.com.astrosoft.model.enderecamento.domain.ELado.IMPAR
import br.com.astrosoft.model.enderecamento.domain.EPalet
import br.com.astrosoft.model.enderecamento.domain.EPalet.P
import br.com.astrosoft.model.enderecamento.domain.ETipoAltura
import br.com.astrosoft.model.enderecamento.domain.ETipoAltura.BAIXA
import br.com.astrosoft.model.enderecamento.domain.MovProduto
import br.com.astrosoft.model.enderecamento.domain.Produto
import br.com.astrosoft.model.enderecamento.domain.RepositorioEndereco
import br.com.astrosoft.model.enderecamento.domain.RepositorioRua
import br.com.astrosoft.model.enderecamento.domain.Rua
import br.com.astrosoft.model.enderecamento.domain.Saldo
import br.com.astrosoft.model.framework.exceptions.ViewException
import br.com.astrosoft.viewmodel.framework.viewmodel.IListModel
import java.math.BigDecimal

class TransferenciaEntradaHeaderModel(val model: EnderecamentoProdutoViewModel): IListModel<Saldo>() {
  var movProduto: MovProduto? = null
  var produto: Produto? = null
  var comboPalete: EPalet = P
  var comboAltura: ETipoAltura = BAIXA
  val tokenRuas: MutableList<RepositorioRua> = mutableListOf()
  var lado: ELado? = IMPAR
  override var list: List<Saldo>? = null
  private var codigo: String = ""
  var descricao: String = ""
  var quantPalete: BigDecimal = BigDecimal.ZERO
  var ruasPulmao: List<RepositorioRua>? = Rua.ruasPulmao
  override var itemSelected: Saldo? = null

  fun updateFields() {
    produto = movProduto?.produto
    comboPalete = P
    comboAltura = BAIXA
    tokenRuas.clear()
    codigo = produto?.codigoGrade ?: ""
    descricao = produto?.nome ?: ""
    quantPalete = movProduto?.quantPalete ?: BigDecimal.ZERO
    ruasPulmao = Rua.ruasPulmao
    updateList()
  }

  fun processaEnderecamento() = model.exec {
    when {
      quantPalete == BigDecimal.ZERO     -> throw ViewException("A quantidade de produtos pro palete está zerada")
      existemEnderecamentosConfirmados() -> throw ViewException("Existem endereçamentos confirmados")
      else                               -> {
        val produtosRecebidosModel = model.produtosRecebidosModel
        movProduto?.let {mp ->
          mp.quantPalete = quantPalete
          mp.save()
          mp.processaEnderecamento(comboPalete, comboAltura, tokenRuas, lado)
          produtosRecebidosModel.updateList()
          produtosRecebidosModel.transferenciaEntradaModel.grid.updateList()
        }
      }
    }
  }

  private fun existemEnderecamentosConfirmados(): Boolean {
    return model.produtosRecebidosModel.transferenciaEntradaModel.grid.list.orEmpty()
      .any {it.confirmacao}
  }

  override fun updateList() {
    list = produto?.saldosPulmao.orEmpty()
  }

  fun removeTransferencia() = model.exec {
    movProduto?.transferencias()
      ?.forEach {it.delete()}
    RepositorioEndereco.updateRegistros()
  }
}