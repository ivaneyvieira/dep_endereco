package br.com.astrosoft.model.enderecamento.domain

import br.com.astrosoft.model.enderecamento.domain.EPalet.G
import br.com.astrosoft.model.enderecamento.domain.EPalet.P
import br.com.astrosoft.model.enderecamento.domain.EPalet.T
import br.com.astrosoft.model.enderecamento.domain.EPalet.X
import br.com.astrosoft.model.enderecamento.domain.finder.OcupacaoEnderecoFinder
import io.ebean.annotation.View
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
@View(name = "ocupacao_enderecos", dependentTables = ["aptos", "niveis", "enderecos", "saldos"])
class OcupacaoEndereco {
  @Id
  @Column(name = "apto_id")
  var id: Long = 0
  @ManyToOne
  @Column(name = "nivel_id")
  var nivel: Nivel? = null
  @ManyToOne
  @Column(name = "predio_id")
  var predio: Predio? = null
  @ManyToOne
  @Column(name = "rua_id")
  var rua: Rua? = null
  @ManyToOne
  @Column(name = "endereco_id")
  var endereco: Endereco? = null
  var numero: String = ""
  @Enumerated(EnumType.STRING)
  var tipoPalet: EPalet = EPalet.P
  @Enumerated(EnumType.STRING)
  var tipoAltura: ETipoAltura = ETipoAltura.BAIXA
  var ocupado: String = ""
  @Enumerated(EnumType.STRING)
  var lado: ELado = ELado.IMPAR

  companion object Find: OcupacaoEnderecoFinder() {
    fun enderecosDisponiveis(palet: EPalet, altura: ETipoAltura, ruas: List<RepositorioRua>, lado: ELado?):
      List<Endereco> {
      val lados = if(lado == null) ELado.values().toList() else listOf(lado)
      val idRuas = if(ruas.isEmpty())
        Rua.ruasPulmao.map {r -> r.rua_id}
      else
        ruas.map {r -> r.rua_id}
      val alturasCompativeis = ETipoAltura.alturasCompativeis(altura)
      val niveis = RepositorioEndereco.niveisCompativeis(alturasCompativeis, lados, idRuas)
      return niveis.mapNotNull {nivel ->
        return@mapNotNull enderecoLivre(nivel, palet)
      }
        .sortedBy {it.classificacao}
        .flatMap { it.enderecos }
    }

    private fun niveisCompativeis(alturasCompativeis: List<ETipoAltura>, lados: List<ELado>,
                                  idRuas: List<Long>): List<Nivel> {
      val niveis = where()
        .tipoAltura.isIn(alturasCompativeis)
        .lado.isIn(lados)
        .rua.id.isIn(idRuas)
        .findList()
        .mapNotNull {it.nivel}
        .distinct()
      return niveis
    }

    fun enderecoLivre(nivel: RepositorioNivel, palet: EPalet?): EnderecoClassificado? {
      palet ?: return null
      val layout = nivel.layout ?: return null
      val total = nivel.total ?: return null
      val enderecosLivre = nivel.enderecosLivre()
      val enderecoLivre1 = enderecosLivre.getOrNull(0)
      val enderecoLivre2 = enderecosLivre.getOrNull(1)

      return when(total) {
        0 -> {
          when(palet) {
            P    -> EnderecoClassificado(nivel, enderecosLivre, "02")
            G    -> EnderecoClassificado(nivel, listOf(enderecoLivre1, enderecoLivre2), "02")
            T    -> EnderecoClassificado(nivel, listOf(enderecoLivre1), "03")
            X    -> EnderecoClassificado(nivel, listOf(enderecoLivre1), "01")
            else -> null
          }
        }
        10 -> {
          when(palet) {
            P    -> EnderecoClassificado(nivel, listOf(enderecoLivre1, enderecoLivre2), "02")
            T    -> EnderecoClassificado(nivel, listOf(enderecoLivre1), "01")
            else -> null
          }
        }
        15 -> {
          when(palet) {
            G    -> EnderecoClassificado(nivel, listOf(enderecoLivre1), "01")
            else -> null
          }
        }
        20 -> {
          when(palet) {
            P    -> EnderecoClassificado(nivel, listOf(enderecoLivre1), "01")
            else -> null
          }
        }
        else -> null
      }
    }
  }
}