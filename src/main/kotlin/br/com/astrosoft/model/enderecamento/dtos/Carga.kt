package br.com.astrosoft.model.enderecamento.dtos

import br.com.astrosoft.model.enderecamento.domain.Endereco
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
  val quant: Double,
  val storenoStk: Int
           ) {
  val tipoNivel: String = ""
  var endereco: String = ""
  @Transient
  var destino: String = ""
  @Transient
  var enderecoOrigem: Endereco? = null
  @Transient
  var produto: Produto? = null
  
  fun initDestino() {
    produto = Produto.findProduto(prdno, grade)
    destino = produto?.tipoProdutoCarga() ?: ""
    
    enderecoOrigem = when {
      storenoStk != 10        -> null
      destino == "EMPILHADOR" -> produto?.proximoEnderecoPulmao()
      destino == "EXPEDICAO"  -> produto?.proximoEnderecoPiking()
      else                    -> null
    }
  }
  
  companion object {
    fun findCarga(numeroCarga: Int?): List<Carga> {
      numeroCarga ?: return emptyList()
      return querySaci.findCarga(numeroCarga)
    }
  }
}