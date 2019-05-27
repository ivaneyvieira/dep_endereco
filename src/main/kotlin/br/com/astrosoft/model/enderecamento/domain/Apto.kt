package br.com.astrosoft.model.enderecamento.domain

import br.com.astrosoft.model.enderecamento.domain.EOcupacao.NAO_OCUPADO
import br.com.astrosoft.model.enderecamento.domain.EPalet.P
import br.com.astrosoft.model.enderecamento.domain.finder.AptoFinder
import br.com.astrosoft.model.framework.services.BaseModel
import io.ebean.annotation.Index
import io.ebean.annotation.Length
import javax.persistence.CascadeType.MERGE
import javax.persistence.CascadeType.PERSIST
import javax.persistence.CascadeType.REFRESH
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.ManyToOne
import javax.persistence.OneToOne
import javax.persistence.Table

@Entity
@Table(name = "aptos")
@Index(unique = true, columnNames = ["nivel_id", "numero"])
class Apto : BaseModel() {
  @Length(2)
  var numero: String = ""
  @Enumerated(EnumType.STRING)
  var tipoPalet: EPalet = P
  @Enumerated(EnumType.STRING)
  var tipoAltura: ETipoAltura = ETipoAltura.BAIXA
  @ManyToOne(cascade = [PERSIST, MERGE, REFRESH])
  var nivel: Nivel? = null
  @OneToOne(cascade = [PERSIST, MERGE, REFRESH])
  var endereco: Endereco? = null
  val ocupacao
    get() = endereco?.enderecoOcupado() ?: NAO_OCUPADO

  companion object Find : AptoFinder()
}

enum class EPalet(val descricao: String, val sigla: String, val tamanho: Int) {
  P("Pallet Pequeno", "P", 10),
  G("Pallet Grande", "G", 15),
  T("Transbordando", "T", 20),
  X("Todo Apartamento", "X", 30);

  override fun toString(): String {
    return this.descricao
  }
}

data class EnderecoClassificado(val endereco: Endereco, val nota: Int)

enum class ETipoAltura(val altMinima: Double, val altMaxima: Double) {
  ALTA(180.01, 300.00),
  MEDIA(135.01, 180.00),
  BAIXA(0.00, 135.00);

  companion object {
    fun alturasCompativeis(altura: ETipoAltura) = when (altura) {
      ALTA  -> listOf(ALTA)
      MEDIA -> listOf(MEDIA, ALTA)
      BAIXA -> listOf(BAIXA, MEDIA, ALTA)
    }

    fun classificaAltura(altura: Double): ETipoAltura {
      return if (altura > BAIXA.altMinima && altura < BAIXA.altMaxima)
        BAIXA
      else if (altura > MEDIA.altMinima && altura < MEDIA.altMaxima)
        MEDIA
      else ALTA
    }
  }
}
