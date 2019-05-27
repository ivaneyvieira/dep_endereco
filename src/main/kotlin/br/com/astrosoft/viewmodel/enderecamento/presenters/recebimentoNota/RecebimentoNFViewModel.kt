package br.com.astrosoft.viewmodel.enderecamento.presenters.recebimentoNota

import br.com.astrosoft.model.enderecamento.domain.EStatusEntrada.NAO_RECEBIDA
import br.com.astrosoft.model.enderecamento.domain.Movimentacao
import br.com.astrosoft.model.enderecamento.domain.Produto
import br.com.astrosoft.model.framework.legado.QuerySaci
import br.com.astrosoft.model.framework.legado.beans.NotaEntrada
import br.com.astrosoft.viewmodel.framework.viewmodel.ViewModel
import java.math.BigDecimal

class RecebimentoNFViewModel : ViewModel() {
  val listProdutoNota = ListProdutoNota(this)
  
  private var saci = QuerySaci()
  var notaEntrada: NotaEntrada? = null
  
  fun processaListProdutos() = exec { listProdutoNota.updateList() }
  
  override fun reloadModel() {
    listProdutoNota.updateList()
  }
  
  override fun processaParametros(parameters: Map<String, String?>) {
    val invnoStr = parameters["invno"].orEmpty()
    if (invnoStr.matches(Regex("^[0-9]+$"))) {
      val invno = Integer.valueOf(invnoStr)
      notaEntrada = getNotaEntrada(invno)
    }
  }
  
  fun findNotaEntrada(text: String): NotaEntrada? = execNull {
    if (text.matches("[0-9]+".toRegex()))
      getNotaEntrada(Integer.parseInt(text))
    else null
  }
  
  fun getNotaEntrada(invno: Int): NotaEntrada? = execNull {
    val notaSaci = saci.notaEntrada(invno)
    
    notaSaci?.let { notaEntrada ->
      val movimentacao = Movimentacao.findNotaEntrada(invno)
      mescraProdutosNota(notaEntrada, movimentacao)
    }
  }
  
  private fun mescraProdutosNota(
          notaSaci: NotaEntrada,
          movimentacao: Movimentacao?
                                ): NotaEntrada {
    val listaProdutos = notaSaci.produtos
    val movProdutos = movimentacao?.movProdutos ?: ArrayList()
    listaProdutos.forEach { produto ->
      produto.quantRecebido = BigDecimal.valueOf(produto.quant ?: 0.000)
      produto.quantPalete = BigDecimal.ZERO
      Produto.quantidadePalete(produto.prdno ?: "").let { qt -> produto.quantPalete = qt }
      produto.status = NAO_RECEBIDA
      val movProduto = movProdutos.firstOrNull { p ->
        val prod = p.produto
        val prdno1 = produto.prdno
        val prdno2 = prod?.prdno?.trim()
        val grade1 = produto.grade
        val grade2 = prod?.grade
        prdno1 == prdno2 && grade1 == grade2
      }
      movProduto?.let {
        produto.quantRecebido = movProduto.quantMov
        produto.status = movProduto.statusEntrada
        produto.quantPalete = movProduto.quantPalete
      }
    }
    return notaSaci
  }
  
  fun saldoPulmaoTotal(produto: Produto): Double = execNull {
    produto.saldoPulmaoTotal
  } ?: 0.000
}
