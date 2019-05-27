package br.com.astrosoft.viewmodel.enderecamento.presenters.mapaDeposito

import br.com.astrosoft.model.enderecamento.domain.Apto
import br.com.astrosoft.model.enderecamento.domain.ELado
import br.com.astrosoft.model.enderecamento.domain.ELado.PAR
import br.com.astrosoft.model.enderecamento.domain.ETipoNivel.PICKING
import br.com.astrosoft.model.enderecamento.domain.Nivel
import br.com.astrosoft.model.enderecamento.domain.Predio
import br.com.astrosoft.model.enderecamento.domain.Rua
import br.com.astrosoft.model.enderecamento.dtos.NivelApto
import java.util.*

class LayoutLado(val rua: Rua, val lado: ELado, val predios: List<Predio>, private val model: MapaDepositoViewModel) {
  private val nivelAptos = ArrayList<NivelApto>()
  val altura: Int? by lazy {
    val intStream = predios.map { p -> alturaPredio(p) }
    intStream.max()
  }

  fun carregaNiveis() {
    nivelAptos.clear()
    nivelAptos.addAll(model.getNivelAptos(rua, lado))
  }

  private fun alturaPredio(predio: Predio): Int {
    return niveis(predio).size + 1
  }

  fun niveis(predio: Predio): List<Nivel> {
    return nivelAptos.map { (nivel) -> nivel }.distinct().filter { n -> n.predio?.id == predio.id }
  }

  private fun getQtPickinPredio(predio: Predio): Int {
    return nivelAptos.map { (nivel) -> nivel }.distinct()
      .filter { n -> n.predio?.id == predio.id && n.tipoNivel === PICKING }.size
  }

  fun outrosNiveis(nivel: Nivel): List<Nivel> {
    val predio = nivel.predio?.id
    return nivelAptos.map { (n) -> n }.distinct()
      .filter { n -> n.predio?.id == predio }
      .sortedBy { n -> n.tipoNivel.toString() + n.numero }
  }

  fun getAptos(nivel: Nivel): List<Apto> {
    return nivelAptos.filter { na -> na.nivel == nivel }.map { na -> na.apto }.distinct()
  }

  fun calculaRow(nivel: Nivel, qtNiveis: Int): Int {
    val numero = Integer.valueOf(nivel.numero)

    return if (nivel.tipoNivel == PICKING) qtNiveis - numero - 1
    else qtNiveis - qtPickin - numero - 1
  }

  private val qtPickin: Int by lazy { predios.map { p -> getQtPickinPredio(p) }.max() ?: 0 }

  fun calculaCol(predio: Predio): Int {
    val qtPredios = predios.size
    val numero = Integer.valueOf(predio.numero)
    return if (lado === PAR) {
      qtPredios - numero / 2 + 1
    } else {
      (numero + 1) / 2
    }
  }
}
