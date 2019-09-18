package br.com.astrosoft.model.enderecamento.dtos

import br.com.astrosoft.model.enderecamento.domain.Apto
import br.com.astrosoft.model.enderecamento.domain.EOcupacao
import br.com.astrosoft.model.enderecamento.domain.EOcupacao.NAO_OCUPADO
import br.com.astrosoft.model.enderecamento.domain.Nivel
import br.com.astrosoft.model.enderecamento.domain.enderecoOcupado

data class NivelApto(
  val nivel: Nivel,
  val apto: Apto
                    ) {
  val ocupado : EOcupacao
    get() {
      return apto.endereco?.enderecoOcupado() ?: return NAO_OCUPADO
    }
}
