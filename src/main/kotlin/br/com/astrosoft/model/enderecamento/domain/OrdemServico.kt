package br.com.astrosoft.model.enderecamento.domain

import br.com.astrosoft.model.enderecamento.domain.finder.OrdemServicoFinder
import br.com.astrosoft.model.framework.entityManager.DB
import br.com.astrosoft.model.framework.services.BaseModel
import br.com.astrosoft.model.framework.utils.readFile
import io.ebean.annotation.Index
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.*
import javax.persistence.CascadeType.MERGE
import javax.persistence.CascadeType.PERSIST
import javax.persistence.CascadeType.REFRESH
import javax.persistence.Entity
import javax.persistence.ManyToOne
import javax.persistence.Table
import javax.persistence.Transient

@Entity
@Table(name = "ordensservico")
class OrdemServico: BaseModel() {
  @ManyToOne(cascade = [PERSIST, MERGE, REFRESH])
  var transferencia: Transferencia? = null
  @Index(unique = true)
  var numero: Long = Date().time
  var dataHora: LocalDateTime = LocalDateTime.now()
  var dataHoraConf: LocalDateTime? = null
  @ManyToOne(cascade = [PERSIST, MERGE, REFRESH])
  var user: User? = null
  @get:Transient
  val produto: Produto?
    get() {
      return transferencia?.movProduto?.produto
    }
  @get:Transient
  val confimacao: Boolean
    get() {
      return transferencia?.confirmacao ?: false
    }
  @get:Transient
  val tipoMov: EMovTipo?
    get() = transferencia?.tipoMov
  @get:Transient
  val status: String
    get() {
      return if(confimacao) "Concluído" else "Pendente"
    }
  
  fun confirmaOS(confirma: Boolean) {
    transferencia?.let {transferencia ->
      transferencia.confirmacao = confirma
      transferencia.save()
      transferencia.movProduto?.produto?.recalculaSaldo()
    }
  }
  
  companion object Find: OrdemServicoFinder() {
    fun findOrdemServicoUser(idUser: Long, osPendente: Boolean): List<OrdemServico> {
      val query = if(osPendente)
        where().user.id.eq(idUser).and().transferencia.confirmacao.eq(false)
      else
        where().user.id.eq(idUser)
      return query.findList()
    }
    
    fun findOrdemServico(
      dataInicial: LocalDate?, dataFinal: LocalDate?, confirmado: Boolean, empilhador: User?, rua: Rua?,
      produto: Produto?
                        ): List<OrdemServico> {
      updateOrdemServio()
      val dataI = dataInicial ?: LocalDate.now()
      val dataF = dataFinal ?: LocalDate.now()
      
      return where()
        .or()
        .user.id.notIn(User.quebec()?.id)
        .user.isNull
        .endOr()
        .transferencia.confirmacao.eq(confirmado)
        .dataHora.between(LocalDateTime.of(dataI, LocalTime.MIN), LocalDateTime.of(dataF, LocalTime.MAX))
        .let {where ->
          produto?.let {
            where.transferencia.movProduto.produto.id.eq(it.id)
          } ?: where
        }
        .let {where ->
          empilhador?.let {
            where.user.id.eq(it.id)
          } ?: where
        }
        .let {where ->
          rua?.let {
            where.transferencia.enderecoE.apto.nivel.predio.rua.id.eq(it.id)
          } ?: where
        }
        .findList()
    }
  
    fun updateOrdemServio(userId: Long? = null) {
      val sql = "/sql/findOrdemServico.sql".readFile()
      DB.scriptSql(sql, Pair("user_id", userId))
    }
  }
}

