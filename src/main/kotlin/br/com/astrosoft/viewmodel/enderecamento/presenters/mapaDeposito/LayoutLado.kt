package br.com.astrosoft.viewmodel.enderecamento.presenters.mapaDeposito

import br.com.astrosoft.model.enderecamento.domain.Apto
import br.com.astrosoft.model.enderecamento.domain.ELado
import br.com.astrosoft.model.enderecamento.domain.ELado.PAR
import br.com.astrosoft.model.enderecamento.domain.ETipoNivel.PICKING
import br.com.astrosoft.model.enderecamento.domain.Nivel
import br.com.astrosoft.model.enderecamento.domain.Predio
import br.com.astrosoft.model.enderecamento.domain.RepositorioApto
import br.com.astrosoft.model.enderecamento.domain.RepositorioNivel
import br.com.astrosoft.model.enderecamento.domain.RepositorioPredio
import br.com.astrosoft.model.enderecamento.domain.RepositorioRua
import br.com.astrosoft.model.enderecamento.domain.Rua
import br.com.astrosoft.model.enderecamento.dtos.NivelApto
import java.util.*

class LayoutLado(val rua: RepositorioRua, val lado: ELado, val predios: List<RepositorioPredio>, private val model:
MapaDepositoViewModel) {
  private val nivelAptos = ArrayList<NivelApto>()
  val altura: Int? by lazy {
    val intStream = predios.map {p -> alturaPredio(p)}
    intStream.max()
  }

  fun carregaNiveis() {
    nivelAptos.clear()
    nivelAptos.addAll(model.getNivelAptos(rua, lado))
  }

  private fun alturaPredio(predio: RepositorioPredio): Int {
    return niveis(predio).size + 1
  }

  fun niveis(predio: RepositorioPredio): List<RepositorioNivel> {
    return nivelAptos.map {it.nivel}
      .filter {n -> n.predio_id == predio.predio_id}
      .distinct()
  }

  private fun getQtPickinPredio(predio: RepositorioPredio): Int {
    return nivelAptos.map {it.nivel}
      .filter {n -> n.predio_id == predio.predio_id && n.tipo_nivel == "PICKING"}
      .distinct()
      .size
  }

  fun outrosNiveis(nivel: Nivel): List<RepositorioNivel> {
    val predio = nivel.predio?.id
    return nivelAptos.map {it.nivel}
      .distinct()
      .filter {n -> n.predio_id == predio}
      .sortedBy {n -> n.tipo_nivel + n.nivel}
  }

  fun getAptos(nivel: RepositorioNivel): List<RepositorioApto> {
    return nivelAptos.filter {na -> na.nivel.nivel_id == nivel.nivel_id}
      .map {na -> na.apto}
      .distinct()
  }

  fun calculaRow(nivel: RepositorioNivel, qtNiveis: Int): Int {
    val numero = Integer.valueOf(nivel.nivel)

    return if(nivel.tipo_nivel == "PICKING") qtNiveis - numero - 1
    else qtNiveis - qtPickin - numero - 1
  }

  private val qtPickin: Int by lazy {predios.map {p -> getQtPickinPredio(p)}.max() ?: 0}

  fun calculaCol(predio: RepositorioPredio): Int {
    val qtPredios = predios.size
    val numero = Integer.valueOf(predio.predio)
    return if(lado === PAR) {
      qtPredios - numero / 2 + 1
    }
    else {
      (numero + 1) / 2
    }
  }
}
