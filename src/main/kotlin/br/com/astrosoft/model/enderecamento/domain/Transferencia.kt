package br.com.astrosoft.model.enderecamento.domain

import br.com.astrosoft.model.enderecamento.domain.ESimNao.NAO
import br.com.astrosoft.model.enderecamento.domain.ESimNao.SIM
import br.com.astrosoft.model.enderecamento.domain.finder.TransferenciaFinder
import br.com.astrosoft.model.framework.exceptions.ViewException
import br.com.astrosoft.model.framework.services.BaseModel
import io.ebean.annotation.Index
import io.ebean.annotation.Length
import java.math.BigDecimal
import java.time.LocalDateTime
import javax.persistence.CascadeType.MERGE
import javax.persistence.CascadeType.PERSIST
import javax.persistence.CascadeType.REFRESH
import javax.persistence.CascadeType.REMOVE
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.PostPersist
import javax.persistence.PostRemove
import javax.persistence.PostUpdate
import javax.persistence.Table
import javax.persistence.Transient

@Entity
@Table(name = "transferencias")
@Index(unique = false, columnNames = ["mov_produto_id", "endereco_e_id", "endereco_s_id"])
class Transferencia : BaseModel() {
  var dataHoraMov: LocalDateTime = LocalDateTime.now()
  @Length(100)
  var observacao: String? = ""
  @Column(precision = 10, scale = 4)
  var quantMov: BigDecimal = BigDecimal.ZERO
  var confirmacao: Boolean = false
  @ManyToOne(cascade = [PERSIST, MERGE, REFRESH])
  var enderecoE: Endereco? = null
  @ManyToOne(cascade = [PERSIST, MERGE, REFRESH])
  var movProduto: MovProduto? = null
  @ManyToOne(cascade = [PERSIST, MERGE, REFRESH])
  var enderecoS: Endereco? = null
  @OneToMany(mappedBy = "transferencia", cascade = [PERSIST, MERGE, REFRESH, REMOVE])
  val ordemServico: List<OrdemServico>? = null
  var eConfirmacao: ESimNao
    @Transient get() = if (confirmacao) SIM else NAO
    @Transient set(eConfirmacao) {
      confirmacao = eConfirmacao == SIM
    }
  @get:Transient
  val tipoMov: EMovTipo?
    get() {
      return movProduto?.tipoMov
    }
  val paletE: EPalet?
    @Transient get() {
      val enderecoE = enderecoE
      val apto = enderecoE?.apto
      return apto?.tipoPalet
    }
  val tipoAltura: String
    @Transient get() {
      val apto = enderecoE?.apto
      return apto?.tipoAltura?.toString() ?: ""
    }

  override fun delete(): Boolean {
    val ordemServico = ordemServico
    if (confirmacao) {
      throw ViewException("Não é possível remover uma transferencia confirmada")
    }
    val produto = movProduto?.produto ?: throw ViewException("A tranferencia não possui produto")
    ordemServico.orEmpty().forEach { it.delete() }
    val ret = super.delete()
    produto.recalculaSaldo()
    return ret
  }

  fun save(palet: EPalet) {
    val isInsert = id == 0L
    super.save()
    val movProduto = movProduto ?: throw ViewException("A tranferencia não possui produto")
    val produto = movProduto.produto ?: throw ViewException("A tranferencia não possui produto")
    val enderecoE = enderecoE ?: throw ViewException("A tranferencia não possui endereco de entrada")
    if (isInsert) {
      val apto = enderecoE.apto
      apto?.let { ap ->
        ap.tipoPalet = palet
        ap.update()
      }
    }
    produto.recalculaSaldo()
  }

  companion object Find : TransferenciaFinder() {
    fun novaTranferencia(origem: Endereco, destino: Endereco, movProduto: MovProduto,
                         quantMov: BigDecimal): Transferencia {
      return Transferencia().apply {
        this.confirmacao = false
        this.enderecoE = destino
        this.enderecoS = origem
        this.movProduto = movProduto
        this.observacao = ""
        this.quantMov = quantMov
        save()
      }
    }
  }
}

enum class ESimNao(private val descricao: String) {
  SIM("Sim"),
  NAO("Não");

  override fun toString(): String {
    return this.descricao
  }
}
