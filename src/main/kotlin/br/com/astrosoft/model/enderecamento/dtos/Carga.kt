package br.com.astrosoft.model.enderecamento.dtos

import br.com.astrosoft.model.enderecamento.domain.ETipoCarga
import br.com.astrosoft.model.enderecamento.domain.ETipoCarga.EMPILHADOR
import br.com.astrosoft.model.enderecamento.domain.ETipoCarga.EXPEDICAO
import br.com.astrosoft.model.enderecamento.domain.Endereco
import br.com.astrosoft.model.enderecamento.domain.Produto
import br.com.astrosoft.model.framework.legado.querySaci
import br.com.astrosoft.model.framework.utils.lpad
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
  val storenoStk: Int,
  val marca: String,
  val destinoCarga: Int,
  val enderecoCarga: String
           ) {
  val processado: Boolean
    get() = marca == "X"
  val tipoNivel: String = ""
  var endereco: String = ""
  @Transient
  var destino: ETipoCarga? = null
  @Transient
  var enderecoOrigem: Endereco? = null
  @Transient
  var produto: Produto? = null
  
  fun initDestino() {
    produto = Produto.findProduto(prdno, grade)
    destino = if(destinoCarga == 0) produto?.tipoProdutoCarga()
    else translateDestino(destinoCarga)
    
    enderecoOrigem = when {
      storenoStk != 10      -> null
      enderecoCarga != ""   -> translateEndereco(enderecoCarga)
      destino == EMPILHADOR -> produto?.proximoEnderecoPulmao()
      destino == EXPEDICAO  -> produto?.proximoEnderecoPiking()
      else                  -> null
    }
  }
  
  private fun translateDestino(destinoCarga: Int): ETipoCarga? {
    return ETipoCarga.values()
      .firstOrNull {it.numero == destinoCarga}
  }
  
  private fun translateEndereco(enderecoCarga: String): Endereco? {
    return Endereco.enderecosPicking.firstOrNull {
      it.localizacao == enderecoCarga
    }
  }
  
  fun marcaProcessado() {
    querySaci.updateItemCarga(cargano = cargano,
                              xano = xano,
                              prdno = prdno.lpad(16, " "),
                              grade = grade,
                              marca = true,
                              endereco = enderecoOrigem?.localizacao ?: "",
                              destino = destino?.numero ?: 0)
  }
  
  companion object {
    fun findCarga(numeroCarga: Int?): List<Carga> {
      numeroCarga ?: return emptyList()
      return querySaci.findCarga(numeroCarga)
    }
  }
}