package br.com.astrosoft.model.enderecamento.domain

import br.com.astrosoft.model.enderecamento.domain.EStatusEntrada.CONFERIDA
import br.com.astrosoft.model.enderecamento.domain.EStatusEntrada.ENDERECADA
import br.com.astrosoft.model.enderecamento.domain.EStatusEntrada.INCONSISTENTE
import br.com.astrosoft.model.enderecamento.domain.EStatusEntrada.NAO_RECEBIDA
import br.com.astrosoft.model.enderecamento.domain.EStatusEntrada.RECEBIDA
import br.com.astrosoft.model.enderecamento.domain.finder.MovProdutoFinder
import br.com.astrosoft.model.framework.exceptions.ViewException
import br.com.astrosoft.model.framework.services.BaseModel
import io.ebean.annotation.Aggregation
import io.ebean.annotation.Index
import java.math.BigDecimal
import java.math.BigDecimal.ZERO
import javax.persistence.CascadeType.MERGE
import javax.persistence.CascadeType.PERSIST
import javax.persistence.CascadeType.REFRESH
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.Table
import javax.persistence.Transient

@Entity
@Table(name = "movprodutos")
@Index(unique = true, columnNames = ["produto_id", "movimentacao_id"])
class MovProduto : BaseModel() {
  @Column(precision = 10, scale = 4)
  var quantCan: BigDecimal = BigDecimal.ZERO
  @Column(precision = 10, scale = 4)
  var quantMov: BigDecimal = BigDecimal.ZERO
  @Column(precision = 10, scale = 4)
  var quantPalete: BigDecimal = BigDecimal.ZERO
  @ManyToOne(cascade = [PERSIST, MERGE, REFRESH])
  var movimentacao: Movimentacao? = null
  @ManyToOne(cascade = [PERSIST, MERGE, REFRESH])
  var produto: Produto? = null
  @OneToMany(mappedBy = "movProduto", cascade = [PERSIST, MERGE, REFRESH])
  var transferencias: List<Transferencia>? = null
  @Aggregation("SUM(transferencias.quant_mov)")
  var sumQuantTransf: BigDecimal? = null

  companion object Find : MovProdutoFinder() {
    fun movProdutosNaoEnderecados(): List<MovProduto> {
      val sql = """select M.id, M.quant_can, M.quant_mov, M.quant_palete,
        M.created_at, M.updated_at, M.version, M.movimentacao_id, M.produto_id
        from movprodutos AS M
  left join transferencias AS T
    ON M.id = T.mov_produto_id
    and T.confirmacao = 1
GROUP BY M.id
HAVING M.quant_mov > IFNULL(SUM(T.quant_mov), 0)"""
      return MovProduto.nativeSql(sql).findList()
    }

    fun novaMovimentacaoProduto(movimentacao: Movimentacao, produto: Produto, quantidade : BigDecimal): MovProduto {
      return MovProduto().apply {
        this.movimentacao = movimentacao
        this.produto = produto
        this.quantCan = ZERO
        this.quantMov = quantidade
        this.quantPalete = ZERO
        save()
      }
    }
  }

  fun transferencias() = Transferencia.where().movProduto.equalTo(this).findList()

  @get:Transient
  val tipoMov: EMovTipo?
    get() {
      refresh()
      return movimentacao?.tipoMov
    }
  val statusEntrada: EStatusEntrada
    @Transient get() {
      val confirmada = quantConfirmada
      val enderecada = quantEnderecada
      quantMov
      return when {
        quantMov.compareTo(BigDecimal.ZERO) == 0 -> NAO_RECEBIDA
        enderecada < quantMov                    -> RECEBIDA
        confirmada < enderecada                  -> ENDERECADA
        quantMov.compareTo(confirmada) == 0      -> CONFERIDA
        else                                     -> INCONSISTENTE
      }
    }

  fun processaEnderecamento(
    palet: EPalet,
    altura: ETipoAltura,
    ruas: List<RepositorioRua>,
    lado: ELado?
                           ) {
    deleteTransferencias()
    produto?.recalculaSaldo()
    val totalConfirmado = transferencias.orEmpty()
      .filter { t -> t.confirmacao }
      .map { t -> t.quantMov.toDouble() }.sum()
    val quantMov = quantMov.toDouble() - totalConfirmado
    //Enderecos livres
    val disponiveis = OcupacaoEndereco.enderecosDisponiveis(palet, altura, ruas, lado)
    //Estou criando uma lista onde os enderecos provaives apacrecem na frente dos endereços disponíveis
    val endS = Endereco.recebimento()
    if (quantMov <= quantPalete.toDouble() * disponiveis.size) {
      var quant = quantMov
      var index = 0
      while (quant > 0) {
        val endE = disponiveis[index++]
        endE.apto?.let { apto ->
          apto.tipoPalet = palet
          apto.tipoAltura = altura
          apto.save()
        }
        val quantEnd = if (quant > quantPalete.toDouble())
          quantPalete.toDouble()
        else
          quant
        quant -= quantEnd
        val movimentacao = Transferencia().apply {
          this.movProduto = this@MovProduto
          this.enderecoE = endE
          this.enderecoS = endS
          this.quantMov = BigDecimal.valueOf(quantEnd)
          this.observacao = ""
          this.confirmacao = false
        }

        movimentacao.save()
      }

      produto?.recalculaSaldo()
    } else throw ViewException("Não há espaço para endereçar todos os ítens")
  }

  private fun deleteTransferencias() {
    transferencias().forEach { it.delete() }
  }

  @get:Transient
  val quantEnderecada: BigDecimal
    get() {
      val doubleValue = transferencias.orEmpty().map { t -> t.quantMov.toDouble() }.sum()
      return BigDecimal.valueOf(doubleValue)
    }
  @get:Transient
  val quantConfirmada: BigDecimal
    // @Transactional
    get() {
      return try {
        val doubleValue =
          transferencias.orEmpty().filter { t -> t.confirmacao }.map { t -> t.quantMov.toDouble() }.sum()
        BigDecimal.valueOf(doubleValue)
      } catch (e: Exception) {
        return ZERO
      }
    }
  @get:Transient
  val quantNaoEnderecada: BigDecimal
    get() {
      val quant = quantMov.toDouble() - quantEnderecada.toDouble()
      return BigDecimal.valueOf(quant)
    }
}
