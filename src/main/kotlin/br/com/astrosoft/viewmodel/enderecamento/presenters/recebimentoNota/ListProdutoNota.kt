package br.com.astrosoft.viewmodel.enderecamento.presenters.recebimentoNota

import br.com.astrosoft.model.enderecamento.domain.MovProduto
import br.com.astrosoft.model.enderecamento.domain.Movimentacao
import br.com.astrosoft.model.framework.exceptions.ViewException
import br.com.astrosoft.model.framework.legado.QuerySaci
import br.com.astrosoft.model.framework.legado.beans.ProdutoNotaEntrada
import br.com.astrosoft.viewmodel.framework.viewmodel.IListModel
import java.math.BigDecimal

class ListProdutoNota(val model: RecebimentoNFViewModel) : IListModel<ProdutoNotaEntrada>() {
  private var saci = QuerySaci()
  override var list: List<ProdutoNotaEntrada>? = null
  override var itemSelected: ProdutoNotaEntrada? = null
  
  override fun updateList() {
    list = model.notaEntrada?.produtos.orEmpty()
  }
  
  fun recebeTudo() = model.exec {
    model.notaEntrada?.let { entrada ->
      val produtoNotaEntradas = entrada.produtos
      
      for (produto in produtoNotaEntradas) {
        itemSelected = produto
        recebeProduto()
      }
    }
  }
  
  fun recebeProduto() = model.exec {
    itemSelected?.let { produto ->
      val invno = produto.invno ?: throw ViewException("O número interno da nota não foi encontrado")
      val prdno = produto.prdno ?: throw ViewException("O código do produto da nota não foi localizado")
      val grade = produto.grade
      val movimentacaoOpt = Movimentacao.findNotaEntrada(invno)
      val movimentacao = if (movimentacaoOpt == null) {
        val entrada = saci.notaEntrada(invno) ?: throw ViewException("A nota de entrada não foi encontrada")
        entrada.saveNotaEntradaSaci()
      } else movimentacaoOpt
      val produtoSaci = saci.produto(prdno, grade) ?: throw ViewException("O produto não foi encontrado no saci")
      val produtoSave = produtoSaci.saveProdutoSaci()
      val optMovPrd = movimentacao.findMovProduto(produtoSave)
      val movP: MovProduto = optMovPrd ?: MovProduto()
      movP.movimentacao = movimentacao
      movP.produto = produtoSave
      val quant: Double = produto.quant ?: 0.000
      val quantRecebido = produto.quantRecebido.toDouble()
      movP.quantCan = BigDecimal.valueOf(quant - quantRecebido)
      movP.quantMov = produto.quantRecebido
      movP.quantPalete = produto.quantPalete ?: BigDecimal.ZERO
      movP.save()
      movP.statusEntrada.let { produto.status = it }
    }
  }
}