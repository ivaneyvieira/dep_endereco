package br.com.astrosoft.model.enderecamento.domain

import br.com.astrosoft.model.enderecamento.domain.EOcupacao.CINZA
import br.com.astrosoft.model.enderecamento.domain.EOcupacao.ERRO
import br.com.astrosoft.model.enderecamento.domain.EOcupacao.NAO_OCUPADO
import br.com.astrosoft.model.enderecamento.domain.EOcupacao.OCUPADO
import br.com.astrosoft.model.enderecamento.domain.ETipoEndereco.DEPOSITO
import br.com.astrosoft.model.enderecamento.domain.ETipoEndereco.RECEBIMENTO
import br.com.astrosoft.model.enderecamento.domain.ETipoNivel.PICKING
import br.com.astrosoft.model.enderecamento.domain.ETipoNivel.PULMAO
import br.com.astrosoft.model.enderecamento.domain.finder.EnderecoFinder
import br.com.astrosoft.model.framework.entityManager.DB.scriptSql
import br.com.astrosoft.model.framework.exceptions.ViewException
import br.com.astrosoft.model.framework.services.BaseModel
import br.com.astrosoft.model.framework.services.findAll
import br.com.astrosoft.model.framework.utils.readFile
import io.ebean.annotation.Formula
import io.ebean.annotation.Index
import io.ebean.annotation.Length
import javax.persistence.CascadeType.MERGE
import javax.persistence.CascadeType.PERSIST
import javax.persistence.CascadeType.REFRESH
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.OneToMany
import javax.persistence.OneToOne
import javax.persistence.Table
import javax.persistence.Transient

@Entity
@Table(name = "enderecos")
@Index(unique = true, columnNames = ["tipo_endereco", "localizacao", "tipo_nivel"])
class Endereco : BaseModel() {
  @Enumerated(EnumType.STRING)
  var tipoEndereco: ETipoEndereco = DEPOSITO
  @Length(100)
  var observacao: String = ""
  @Length(25)
  var localizacao: String = ""
  @Enumerated(EnumType.STRING)
  var tipoNivel: ETipoNivel = PULMAO
  @OneToOne(mappedBy = "endereco", cascade = [PERSIST, MERGE, REFRESH])
  val apto: Apto? = null
  @OneToMany(mappedBy = "endereco", cascade = [PERSIST, MERGE, REFRESH])
  val saldos: List<Saldo>? = null
  var tipoPalet: EPalet?
    @Transient get() = apto?.tipoPalet
    @Transient set(value) {
      value?.let { tipoPalet ->
        apto?.tipoPalet = tipoPalet
        apto?.save()
      }
    }
  val tipoAltura: ETipoAltura?
    @Transient get() = apto?.tipoAltura

  override fun toString(): String {
    return descricao ?: ""
  }

  val nivel @Transient get() = apto?.nivel
  /*
    fun transferePara(target: Endereco) {
      val origem: Endereco = this
      origem.update()
      target.update()
      origem.saldos.orEmpty().forEach { saldo ->
        if (saldo.saldoConfirmado.toInt() > 0) {
          saldo.update()
          val produto = saldo.produto
          val mpTranferencia = produto?.movProdutoTransferencia
          if (mpTranferencia != null) {
            val transferencia = Transferencia().apply {
              this.movProduto = mpTranferencia
              this.enderecoE = target
              this.enderecoS = origem
              this.quantMov = saldo.saldoConfirmado
              this.observacao = "Tranferencia Drag and Drop"
            }
            transferencia.insert()
          }
        }
      }
    }
  */
  fun enderecoOcupado(): EOcupacao {
    val saldos = Saldo.where().endereco.equalTo(this).findList()
    val totalConfirmado = saldos.sumByDouble { it.saldoConfirmado.toDouble() }
    val totalNConfirmado = saldos.sumByDouble { it.saldoNConfirmado.toDouble() }
    val total = totalConfirmado + totalNConfirmado
    return if (tipoNivel == PICKING)
      if (totalConfirmado != 0.00 || totalNConfirmado != 0.00)
        OCUPADO
      else NAO_OCUPADO
    else
      when {
        totalConfirmado > 0 -> when {
          total > 0 -> OCUPADO
          total < 0 -> ERRO
          else      -> CINZA
        }
        totalConfirmado < 0 -> when {
          total > 0 -> CINZA
          total < 0 -> ERRO
          else      -> CINZA
        }
        else                -> when {
          total > 0 -> CINZA
          total < 0 -> ERRO
          else      -> NAO_OCUPADO
        }
      }
  }

  @Formula(select = "(CASE tipo_endereco\n" +
                    "         WHEN 'DEPOSITO' THEN CONCAT(tipo_nivel, ' ', localizacao)\n" +
                    "         WHEN 'RECEBIMENTO' THEN 'Recebimento'\n" +
                    "         WHEN 'EXPEDICAO' THEN CONCAT('Expedição ', RIGHT(localizacao, 2)) \n" +
                    "       END)")
  val descricao: String? = ""
  val rua @Transient get() = predio?.rua
  val predio @Transient get() = nivel?.predio

  companion object Find : EnderecoFinder() {
    fun recebimento(): Endereco {
      return where().tipoEndereco.eq(RECEBIMENTO).findOne() ?: throw ViewException(
        "Não há endereco de recebimento cadastrado")
    }

    fun enrederecoPickingQuebec(): Endereco? = findEndereco(ETipoNivel.PULMAO, "00-00-00-00")

    private fun findEndereco(
      tipoNivel: ETipoNivel, localizacao: String
                            ): Endereco? {
      return where().tipoNivel.eq(tipoNivel).and().localizacao.eq(localizacao).endAnd().findOne()
    }

    fun findEnderecoPiking(strEndereco: String): Endereco? = findEndereco(ETipoNivel.PICKING, strEndereco)

    val enderecosPicking: List<Endereco> by lazy {
      val enderecos: List<Endereco> = findAll()
      enderecos.filter { e ->
        e.tipoNivel == ETipoNivel.PICKING || e.tipoEndereco == ETipoEndereco.EXPEDICAO
      }
    }

    val enderecosPulmao: List<Endereco>
      get() {
        return where().tipoNivel.eq(PULMAO)
          .findList()
      }

    fun enderecoPiking(produto: Produto): List<Endereco> {
      return where().saldos.produto.id.eq(produto.id)
        .and()
        .tipoEndereco.eq(DEPOSITO)
        .endAnd().findList().distinct()
    }

    fun changeApto(origem: String, tipo: String, destino: String) {
      val sql = "/sql/transferenciaEndereco.sql"
      scriptSql(sql.readFile(), ("origem" to origem), ("tipo" to tipo), ("destino" to destino))
    }

    fun findEnderecoPulmao(localizacao: String?): Endereco? {
      localizacao ?: return null
      return where()
        .tipoNivel.eq(PULMAO)
        .localizacao.eq(localizacao)
        .findList()
        .firstOrNull()
    }
  }
}

enum class ETipoEndereco(private val descricao: String) {
  DEPOSITO("Depósito"),
  RECEBIMENTO("Recebimento"),
  EXPEDICAO("Expedição");

  override fun toString(): String {
    return descricao
  }
}

enum class EOcupacao {
  NAO_OCUPADO, OCUPADO, CINZA, ERRO
}