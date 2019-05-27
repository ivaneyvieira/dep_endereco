package br.com.astrosoft.model.enderecamento.domain

import br.com.astrosoft.model.enderecamento.domain.EOcupacao.NAO_OCUPADO
import br.com.astrosoft.model.enderecamento.domain.finder.LayoutNivelFinder
import io.ebean.annotation.View
import javax.persistence.CascadeType.MERGE
import javax.persistence.CascadeType.PERSIST
import javax.persistence.CascadeType.REFRESH
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.OneToOne

@Entity
@View(name = "layout_niveis", dependentTables = ["aptos", "niveis", "enderecos", "saldos"])
class LayoutNivel {
  @Id
  @Column(name = "nivel_id")
  var id: Long = 0
  var layout: String = ""
  var total: Int = 0
  @OneToOne(cascade = [PERSIST, MERGE, REFRESH])
  var nivel: Nivel? = null

  companion object Find : LayoutNivelFinder()

  fun erroLayout(): Boolean {
    return total > 30 || total == 25
  }

  fun primeiroEnderecoLivre(): Endereco? {
    val aptos = nivel?.aptos ?: return null
    return aptos.sortedBy { it.numero }
      .mapNotNull { it.endereco }
      .firstOrNull { it.enderecoOcupado() == NAO_OCUPADO }
  }
}
