package br.com.astrosoft.viewmodel.enderecamento.presenters.mapaDeposito

import br.com.astrosoft.model.enderecamento.domain.ELado
import br.com.astrosoft.model.enderecamento.domain.ELado.IMPAR
import br.com.astrosoft.model.enderecamento.domain.ELado.PAR
import br.com.astrosoft.model.enderecamento.domain.Predio
import br.com.astrosoft.model.enderecamento.domain.Rua
import br.com.astrosoft.model.enderecamento.dtos.RuaPredio

class LayoutRuaPredio(private val model: MapaDepositoViewModel) {
  private val ruaPredios: List<RuaPredio> = model.findRuasPredio()
  val largura: Int?
    get() = ruaPredios.map(RuaPredio::rua).distinct().map { r -> getLargura(r) }.sum()
  
  private fun getLargura(rua: Rua): Int {
    val countLado = ruaPredios.filter { rp -> rp.rua == rua }.map { rp -> rp.predio.lado }.distinct().count()
    return countLado + 1
  }
  
  val altura: Int?
    get() {
      val ladoPar = ruaPredios.asSequence().map(RuaPredio::rua).distinct().map {r ->
        getLado(r, PAR)
      }.map { l -> l.predios.size }.max() ?: 0
      val ladoImpar = ruaPredios.asSequence()
        .map(RuaPredio::rua)
        .distinct().map {r ->
        getLado(r, IMPAR)
      }.map { l -> l.predios.size }.max()
      return Math.max(ladoImpar ?: 0, ladoPar)
    }
  val ruas: List<Rua>
    get() = ruaPredios.map(RuaPredio::rua).distinct().sortedBy(Rua::numero)
  
  fun getLadoImpar(rua: Rua): LayoutLado {
    return getLado(rua, IMPAR)
  }
  
  fun getLadoPar(rua: Rua): LayoutLado {
    return getLado(rua, PAR)
  }
  
  private fun getLado(rua: Rua, lado: ELado): LayoutLado {
    val predios =
            ruaPredios.filter { rp -> rp.rua == rua }.map { rp -> rp.predio }.distinct().filter { p -> p.lado === lado }
    return LayoutLado(rua, lado, predios, model)
  }
  
  fun getLayoutLado(predio: Predio): LayoutLado? {
    val lado = predio.lado
    val rua = getRua(predio)
    return getLado(rua, lado)
  }
  
  private fun getRua(predio: Predio): Rua {
    return ruaPredios.first { rp -> rp.predio == predio }.rua
  }
}
