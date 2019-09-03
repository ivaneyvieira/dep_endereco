package br.com.astrosoft.model.enderecamento.domain

import br.com.astrosoft.model.enderecamento.domain.ETipoEndereco.DEPOSITO
import br.com.astrosoft.model.enderecamento.domain.finder.SaldoFinder
import br.com.astrosoft.model.framework.services.BaseModel
import io.ebean.annotation.Index
import java.math.BigDecimal
import java.math.BigDecimal.ZERO
import java.time.LocalDate
import javax.persistence.CascadeType.MERGE
import javax.persistence.CascadeType.PERSIST
import javax.persistence.CascadeType.REFRESH
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.ManyToOne
import javax.persistence.PostPersist
import javax.persistence.PostRemove
import javax.persistence.PostUpdate
import javax.persistence.Table

@Entity
@Table(name = "saldos")
@Index(unique = true, columnNames = ["endereco_id", "produto_id"])
class Saldo : BaseModel() {
  @Column(precision = 10, scale = 4)
  var capacidade: BigDecimal = BigDecimal.ZERO
  @Column(precision = 10, scale = 4)
  var saldoConfirmado: BigDecimal = BigDecimal.ZERO
  @Column(precision = 10, scale = 4)
  var saldoNConfirmado: BigDecimal = BigDecimal.ZERO
  var ultSaida: LocalDate? = null
  var ultEntrada: LocalDate? = null
  @ManyToOne(cascade = [PERSIST, MERGE, REFRESH])
  var endereco: Endereco? = null
  @ManyToOne(cascade = [PERSIST, MERGE, REFRESH])
  var produto: Produto? = null

  companion object Find : SaldoFinder() {
    fun findSaldos(
      tipoNivel: ETipoNivel?, rua: String?, lado: ELado?,
      predio: String?, nivel: String?, apto: String?
                  ): List<Saldo> {
      var predicado = where().or()
        .saldoConfirmado.ge(ZERO)
        .saldoNConfirmado.gt(ZERO)
        .endOr()
        .and()
        .endereco.tipoEndereco.eq(DEPOSITO)
        .endAnd()
      if (tipoNivel != null) predicado = predicado.and().endereco.tipoNivel.eq(tipoNivel).endAnd()
      if (rua != null) predicado = predicado.and().endereco.apto.nivel.predio.rua.numero.eq(rua).endAnd()
      if (lado != null) predicado = predicado.and().endereco.apto.nivel.predio.lado.eq(lado).endAnd()
      if (predio != null) predicado = predicado.and().endereco.apto.nivel.predio.numero.eq(predio).endAnd()
      if (nivel != null) predicado = predicado.and().endereco.apto.nivel.numero.eq(nivel).endAnd()
      if (apto != null) predicado = predicado.and().endereco.apto.numero.eq(apto).endAnd()
      val list = predicado.findList()
      println(predicado.generatedSql)
      return list
    }
  }

  fun savePicking(enderecoPiking: Endereco, quantidade: BigDecimal) {
    produto?.movProdutoPicking?.let { mp ->
      val transferencia = Transferencia().apply {
        this.movProduto = mp
        this.enderecoE = enderecoPiking
        this.enderecoS = endereco
        this.quantMov = quantidade
        this.observacao = ""
        this.confirmacao = false
      }
      transferencia.save()

      mp.produto?.recalculaSaldo()
    }
  }

  @PostRemove
  @PostUpdate
  @PostPersist
  fun updateRegistros() {
    RegistroEndereco.updateRegistros()
  }
}
