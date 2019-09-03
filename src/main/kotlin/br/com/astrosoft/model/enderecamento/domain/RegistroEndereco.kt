package br.com.astrosoft.model.enderecamento.domain

import br.com.astrosoft.model.enderecamento.domain.EOcupacao.CINZA
import br.com.astrosoft.model.enderecamento.domain.EOcupacao.ERRO
import br.com.astrosoft.model.enderecamento.domain.EOcupacao.NAO_OCUPADO
import br.com.astrosoft.model.enderecamento.domain.EOcupacao.OCUPADO
import br.com.astrosoft.model.enderecamento.domain.ETipoNivel.PICKING
import br.com.astrosoft.model.enderecamento.domain.OcupacaoEndereco.Find
import br.com.astrosoft.model.framework.utils.readFile
import io.ebean.Ebean
import java.math.BigDecimal

data class RegistroEndereco(
  val rua_id: Long,
  val rua: String,
  val predio_id: Long,
  val predio: String,
  val lado: String,
  val nivel_id: Long,
  val nivel: String,
  val altura: BigDecimal,
  val tipo_nivel: String,
  val layout: String?,
  val total: Int?,
  val apto_id: Long,
  val apto: String,
  val tipo_palet: String,
  val tipo_altura: String,
  val endereco_id: Long?,
  val tipo_endereco: String?,
  val observacao: String?,
  val localizacao: String?,
  val saldo_confirmado: BigDecimal,
  val saldo_nconfirmado: BigDecimal
                           ) {
  companion object {
    val enderecos = mutableListOf<RegistroEndereco>()

    fun updateRegistros() {
      val sql = "/sql/registroEndereco.sql".readFile()
      enderecos.clear()
      enderecos.addAll(Ebean.findDto(RegistroEndereco::class.java, sql).findList())
    }

    fun niveisCompativeis(alturasCompativeis: List<ETipoAltura>, lados: List<ELado>,
                          idRuas: List<Long>): List<RegistroNivel> {
      val alturasStr = alturasCompativeis.map {it.name}
      val ladosStr = lados.map {it.name}
      return enderecos.filter {reg ->
        alturasStr.contains(reg.tipo_altura) &&
        ladosStr.contains(reg.lado) &&
        idRuas.contains(reg.rua_id)
      }
        .map {it.toNivel()}
        .distinct()
    }
  }

  fun enderecoOcupado(): EOcupacao {
    val totalConfirmado = saldo_confirmado.toDouble()
    val totalNConfirmado = saldo_nconfirmado.toDouble()
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

  fun toNivel() = RegistroNivel(nivel_id,
                                nivel,
                                altura,
                                tipo_nivel,
                                layout,
                                total)

  fun toApto() = RegistroApto(apto_id,
                              apto,
                              tipo_palet,
                              tipo_altura,
                              endereco_id,
                              tipo_endereco,
                              observacao,
                              localizacao,
                              saldo_confirmado,
                              saldo_nconfirmado,
                              enderecoOcupado())
}

data class RegistroNivel(val nivel_id: Long,
                         val nivel: String,
                         val altura: BigDecimal,
                         val tipo_nivel: String,
                         val layout: String?,
                         val total: Int?) {
  fun primeiroEnderecoLivre(): RegistroApto? {
    return aptos.sortedBy {it.apto}
      .filter {it.endereco_id != null}
      .firstOrNull {it.enderecoOcupado == NAO_OCUPADO}
  }

  val aptos
    get() = RegistroEndereco.enderecos.filter {it.nivel_id == nivel_id}.map {it.toApto()}.distinct()
}

data class RegistroApto(val apto_id: Long,
                        val apto: String,
                        val tipo_palet: String,
                        val tipo_altura: String,
                        val endereco_id: Long?,
                        val tipo_endereco: String?,
                        val observacao: String?,
                        val localizacao: String?,
                        val saldo_confirmado: BigDecimal,
                        val saldo_nconfirmado: BigDecimal,
                        val enderecoOcupado: EOcupacao) {
}

enum class EOcupacao {
  NAO_OCUPADO,
  OCUPADO,
  CINZA,
  ERRO
}

fun Endereco.enderecoOcupado(): EOcupacao? {
  return RegistroEndereco.enderecos.firstOrNull {it.endereco_id == this.id}
    ?.enderecoOcupado()
}