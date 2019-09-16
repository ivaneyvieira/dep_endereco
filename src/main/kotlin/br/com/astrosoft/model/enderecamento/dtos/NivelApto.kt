package br.com.astrosoft.model.enderecamento.dtos

import br.com.astrosoft.model.enderecamento.domain.Apto
import br.com.astrosoft.model.enderecamento.domain.EOcupacao
import br.com.astrosoft.model.enderecamento.domain.EOcupacao.NAO_OCUPADO
import br.com.astrosoft.model.enderecamento.domain.Nivel
import br.com.astrosoft.model.enderecamento.domain.RepositorioApto
import br.com.astrosoft.model.enderecamento.domain.RepositorioEndereco
import br.com.astrosoft.model.enderecamento.domain.RepositorioNivel
import br.com.astrosoft.model.enderecamento.domain.enderecoOcupado

data class NivelApto(
  val endereco: RepositorioEndereco
                    ) {
  val nivel = endereco.toNivel()
  val apto = endereco.toApto()
  val ocupado: EOcupacao
    get() = endereco.enderecoOcupado()
}
