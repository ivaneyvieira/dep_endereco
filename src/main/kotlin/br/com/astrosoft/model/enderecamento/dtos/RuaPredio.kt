package br.com.astrosoft.model.enderecamento.dtos

import br.com.astrosoft.model.enderecamento.domain.Predio
import br.com.astrosoft.model.enderecamento.domain.RepositorioPredio
import br.com.astrosoft.model.enderecamento.domain.RepositorioRua
import br.com.astrosoft.model.enderecamento.domain.Rua

data class RuaPredio(val rua: RepositorioRua, val predio: RepositorioPredio)
