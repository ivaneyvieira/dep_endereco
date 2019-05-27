package br.com.astrosoft.model.framework.legado.beans

import br.com.astrosoft.model.enderecamento.domain.EMovTipo.ENTRADA
import br.com.astrosoft.model.enderecamento.domain.Movimentacao
import br.com.astrosoft.model.framework.exceptions.ViewException
import br.com.astrosoft.model.framework.utils.toLocalDate
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*

class NotaEntrada {
  val invno: Int? = null
  val nfname: String? = null
  val invse: String? = null
  val data: Date? = null
  val fornecedor: String? = null
  val cnpj: String? = null
  var produtos: MutableList<ProdutoNotaEntrada> = ArrayList()
  val documento: String? = null
  val valor: BigDecimal? = null
  
  fun addProdutos(produtos: List<ProdutoNotaEntrada>) {
    this.produtos.clear()
    this.produtos.addAll(produtos)
  }
  
  val localData: LocalDate
    get() = this.data.toLocalDate() ?: LocalDate.now()
  
  fun saveNotaEntradaSaci(): Movimentacao {
    invno ?: throw ViewException("O número interno da nota não foi informado")
    val mov = Movimentacao.findNotaEntrada(invno) ?: Movimentacao()
    mov.chave = Movimentacao.montaChaveEntrada(invno)
    mov.data = localData
    mov.documento = documento ?: ""
    mov.observacao = ""
    mov.tipoMov = ENTRADA
    mov.save()
    return mov
  }
}
