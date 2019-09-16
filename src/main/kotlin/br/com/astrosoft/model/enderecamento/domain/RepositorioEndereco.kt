package br.com.astrosoft.model.enderecamento.domain

import br.com.astrosoft.model.enderecamento.domain.EOcupacao.CINZA
import br.com.astrosoft.model.enderecamento.domain.EOcupacao.ERRO
import br.com.astrosoft.model.enderecamento.domain.EOcupacao.NAO_OCUPADO
import br.com.astrosoft.model.enderecamento.domain.EOcupacao.OCUPADO
import br.com.astrosoft.model.enderecamento.domain.EPalet.P
import br.com.astrosoft.model.enderecamento.domain.ETipoAltura.BAIXA
import br.com.astrosoft.model.enderecamento.domain.ETipoAltura.Companion
import br.com.astrosoft.model.enderecamento.dtos.NivelApto
import br.com.astrosoft.model.enderecamento.dtos.RuaPredio
import br.com.astrosoft.model.framework.services.findById
import br.com.astrosoft.model.framework.utils.readFile
import io.ebean.Ebean
import java.math.BigDecimal
import com.vaadin.util.CurrentInstance.clearAll
import io.ebean.cache.ServerCacheManager

data class RepositorioEndereco(
  val rua_id: Long,
  val rua: String,
  val predio_id: Long,
  val predio: String,
  val lado: String,
  val nivel_id: Long,
  val nivel: String,
  val altura: BigDecimal,
  val tipo_nivel: String,
  val apto_id: Long,
  val apto: String,
  val tipo_palet: String,
  val tipo_altura: String,
  val endereco_id: Long?,
  val tipo_endereco: String?,
  val observacao: String?,
  val localizacao: String?
                              ) {
  val tipoAltura: ETipoAltura = ETipoAltura.value(tipo_altura)
  val layoutNivel = findLayout(nivel_id)
  val layout: String? get() = layoutNivel?.layout
  val total: Int? get() = layoutNivel?.total

  companion object {
    private val enderecos = mutableListOf<RepositorioEndereco>()
    private val layouts = mutableListOf<LayoutNivel>()

    fun findNivelAptos(rua_id: Long, lado: ELado): List<NivelApto> {
      return RepositorioEndereco.enderecos.filter {it.rua_id == rua_id && lado.name == it.lado}
        .map {
          NivelApto(it)
        }
        .distinct()
    }

    fun updateRegistros() {
      val serverCacheManager = Ebean.getServerCacheManager()
      serverCacheManager.clearAll()
      val sql = "/sql/registroEndereco.sql".readFile()
      layouts.clear()
      layouts.addAll(LayoutNivel.all())
      enderecos.clear()
      enderecos.addAll(Ebean.findDto(RepositorioEndereco::class.java, sql).findList())
    }

    fun findLayout(nivelId: Long): LayoutNivel? {
      return layouts.firstOrNull {it.nivel?.id == nivelId}
    }

    fun niveisCompativeis(alturasCompativeis: List<ETipoAltura>, lados: List<ELado>,
                          idRuas: List<Long>): List<RepositorioNivel> {
      val ladosStr = lados.map {it.name}
      return enderecos.filter {reg ->
        alturasCompativeis.contains(reg.tipoAltura) &&
        ladosStr.contains(reg.lado) &&
        idRuas.contains(reg.rua_id)
      }
        .map {it.toNivel()}
        .distinct()
    }

    fun aptosFromNiveis(nivel_id: Long) = enderecos.filter {it.nivel_id == nivel_id}.map {it.toApto()}.distinct()
    fun enderecoOcupado(endereco_id: Long) =
      enderecos.firstOrNull {it.endereco_id == endereco_id}?.enderecoOcupado() ?: NAO_OCUPADO

    fun ruasPredioDeposito() = enderecos.filter {it.rua != "00"}.map {
      RuaPredio(it.toRua(), it.toPredio())
    }.distinct()

    fun ruasPumao() = enderecos.filter {it.tipo_nivel == "PULMAO"}.map {it.toRua()}
  }

  fun findSaldoEndereco(): SaldoEndereco {
    val sql = "/sql/saldoEndereco.sql".readFile()
    return Ebean.findDto(SaldoEndereco::class.java, sql)
             .setParameter(1, endereco_id)
             .findList()
             .firstOrNull()
           ?: SaldoEndereco(0.toDouble(), 0.toDouble())
  }

  fun enderecoOcupado(): EOcupacao {
    val saldoEndereco = findSaldoEndereco()
    val totalConfirmado = saldoEndereco.saldo_confirmado
    val totalNConfirmado = saldoEndereco.saldo_nconfirmado
    val total = totalConfirmado + totalNConfirmado
    return if(tipo_nivel == "PICKING")
      if(totalConfirmado != 0.00 || totalNConfirmado != 0.00)
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

  fun toRua() = RepositorioRua(rua_id, rua)

  fun toPredio() = RepositorioPredio(predio_id, predio, lado)

  fun toNivel() = RepositorioNivel(nivel_id,
                                   predio_id,
                                   "$rua:$predio:$nivel",
                                   nivel,
                                   altura,
                                   tipo_nivel,
                                   layout,
                                   total,
                                   lado)

  fun toApto() = RepositorioApto(apto_id,
                                 nivel_id,
                                 apto,
                                 EPalet.value(tipo_palet),
                                 ETipoAltura.value(tipo_altura),
                                 endereco_id,
                                 tipo_endereco,
                                 observacao,
                                 localizacao)
}

data class RepositorioRua(
  val rua_id: Long,
  val rua: String
                         )

data class RepositorioPredio(
  val predio_id: Long,
  val predio: String,
  val lado: String
                            ) {
  fun lado() = ELado.values().firstOrNull {it.name == lado}
}

data class RepositorioNivel(val nivel_id: Long,
                            val predio_id: Long,
                            val localizacao: String,
                            val nivel: String,
                            val altura: BigDecimal,
                            val tipo_nivel: String,
                            val layout: String?,
                            val total: Int?,
                            val lado: String) {
  fun enderecosLivre(): List<RepositorioApto> {
    return aptos
      .filter {it.endereco_id != null}
      .filter {it.enderecoOcupado() == NAO_OCUPADO}
      .sortedBy {it.apto}
  }

  fun erroLayout(): Boolean {
    val value = total ?: 0
    return value > 30 || value == 25
  }

  val aptos
    get() = RepositorioEndereco.aptosFromNiveis(nivel_id)
}

data class RepositorioApto(val apto_id: Long,
                           val nivel_id: Long,
                           val apto: String,
                           var tipoPalet: EPalet,
                           var tipoAltura: ETipoAltura,
                           val endereco_id: Long?,
                           val tipo_endereco: String?,
                           val observacao: String?,
                           val localizacao: String?) {
  fun enderecoOcupado() =
    RepositorioEndereco.enderecoOcupado(endereco_id ?: 0)
}

enum class EOcupacao {
  NAO_OCUPADO,
  OCUPADO,
  CINZA,
  ERRO
}

fun Endereco.enderecoOcupado(): EOcupacao? {
  return RepositorioEndereco.enderecoOcupado(this.id)
}

data class SaldoEndereco(
  val saldo_confirmado: Double,
  val saldo_nconfirmado: Double
                        )