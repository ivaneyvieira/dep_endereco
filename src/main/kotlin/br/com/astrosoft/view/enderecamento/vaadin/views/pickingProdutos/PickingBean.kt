package br.com.astrosoft.view.enderecamento.vaadin.views.pickingProdutos

import br.com.astrosoft.model.enderecamento.domain.Saldo
import java.math.BigDecimal

data class PickingBean(
        var endereco: String,
        var quantidade: BigDecimal,
        var saldo: Saldo
                      )
