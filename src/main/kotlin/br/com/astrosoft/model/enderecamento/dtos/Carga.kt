package br.com.astrosoft.model.enderecamento.dtos

import br.com.astrosoft.model.framework.legado.querySaci
import java.time.LocalDate

class Carga(
  val cargano: Int,
  val data: LocalDate,
  val xano: String,
  val custno: Int,
  val cliente: String,
  val prdno: String,
  val grade: String,
  val quant: Double
           ) {
  val tipoNivel: String = ""
  var endereco: String = ""

  companion object {
    fun findCarga(numeroCarga: Int?): List<Carga> {
      numeroCarga ?: return emptyList()
      return querySaci.findCarga(numeroCarga)
    }
  }
}