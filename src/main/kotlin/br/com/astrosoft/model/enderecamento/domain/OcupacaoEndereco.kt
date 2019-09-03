package br.com.astrosoft.model.enderecamento.domain

import br.com.astrosoft.model.enderecamento.domain.EPalet.T
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
    fun enderecosDisponiveis(palet: EPalet, altura: ETipoAltura, ruas: List<Rua>, lado: ELado?): List<Endereco> {
      val lados = if(lado == null) ELado.values().toList() else listOf(lado)
      val idRuas = if(ruas.isEmpty())
        Rua.ruasPulmao.map {r -> r.id}
      else
        ruas.map {r -> r.id}
      val alturasCompativeis = ETipoAltura.alturasCompativeis(altura)
      val niveis = RegistroEndereco.niveisCompativeis(alturasCompativeis, lados, idRuas)
      return niveis.mapNotNull {nivel ->
        return@mapNotNull enderecoLivre(nivel, palet)
      }
        .sortedWith(compareBy({-it.nota}, {it.endereco?.localizacao}))
        .mapNotNull {it.endereco}
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

    fun enderecoLivre(nivel: RegistroNivel, palet: EPalet?): EnderecoClassificado? {
      palet ?: return null
      nivel.layout ?: return null
      val total = nivel.total ?: 0
      val resto = 30 - total
      val enderecoLivre = nivel.primeiroEnderecoLivre() ?: return null
      val restoFinal = resto - palet.tamanho

      return when {
        restoFinal < 0   -> null
        restoFinal == 5  -> null
        restoFinal == 0  -> EnderecoClassificado(enderecoLivre, 3)
        restoFinal == 10 ->
          if(palet == T && nivel.layout == "_P_")
            null
          else
            EnderecoClassificado(enderecoLivre, 2)
        restoFinal == 15 -> EnderecoClassificado(enderecoLivre, 4)
        restoFinal == 20 -> EnderecoClassificado(enderecoLivre, 1)
        else             -> null
      }
    }
  }
}