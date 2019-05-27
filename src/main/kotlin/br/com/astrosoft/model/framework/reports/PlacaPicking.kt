package br.com.astrosoft.model.framework.reports

import br.com.astrosoft.model.enderecamento.domain.OrdemServico
import br.com.astrosoft.model.framework.utils.toTimeStamp
import java.math.BigDecimal
import java.sql.Timestamp
import java.util.*

class PlacaPicking(
        val codigo: String,
        val descricao: String,
        val fornecedor: String,
        val endereco: String,
        val quantMov: BigDecimal,
        val dataHora: Timestamp
                  ) {
  
  companion object {
    fun createPlacaPicking(os: OrdemServico): List<PlacaPicking> {
      val produto = os.transferencia?.movProduto?.produto
      val endereco = os.transferencia?.enderecoE
      val placa = PlacaPicking(
              codigo = produto?.codigoGrade ?: "",
              descricao = produto?.nome ?: "",
              fornecedor = "${produto?.vendno ?: ""}",
              endereco = endereco?.localizacao ?: "",
              quantMov = os.transferencia?.quantMov ?: BigDecimal.ZERO,
              dataHora = os.transferencia?.dataHoraMov.toTimeStamp() ?: Timestamp(Date().time)
                              )
      return listOf(placa)
    }
  }
}