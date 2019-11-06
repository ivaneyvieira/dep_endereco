package br.com.astrosoft.model.enderecamento.dtos

import br.com.astrosoft.model.enderecamento.domain.Produto
import br.com.astrosoft.model.framework.legado.querySaci
import java.util.*
import javax.persistence.Transient

class Carga(
  val cargano: Int,
  val data: Date,
  val xano: String,
  val custno: Int,
  val cliente: String,
  val prdno: String,
  val grade: String,
  val quant: Double
           ) {
  val tipoNivel: String = ""
  var endereco: String = ""
  @Transient
  var destino: String = ""

  fun initDestino() {
    destino = Produto.findProduto(prdno, grade)?.tipoProdutoCarga() ?: ""
  }

  companion object {
    fun findCarga(numeroCarga: Int?): List<Carga> {
      numeroCarga ?: return emptyList()
      return querySaci.findCarga(numeroCarga)
    }
  }
}