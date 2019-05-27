package br.com.astrosoft.model.enderecamento.domain

import br.com.astrosoft.model.enderecamento.domain.ETipoNivel.PULMAO
import br.com.astrosoft.model.enderecamento.domain.finder.NivelFinder
import br.com.astrosoft.model.framework.services.BaseModel
import io.ebean.annotation.Index
import io.ebean.annotation.Length
import java.math.BigDecimal
import javax.persistence.CascadeType.MERGE
import javax.persistence.CascadeType.PERSIST
import javax.persistence.CascadeType.REFRESH
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.FetchType.EAGER
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.OneToOne
import javax.persistence.Table

@Entity
@Table(name = "niveis")
@Index(unique = true, columnNames = ["predio_id", "numero", "tipo_nivel"])
class Nivel : BaseModel() {
  @Length(2)
  var numero: String = ""
  @Column(precision = 10, scale = 4)
  var altura: BigDecimal = BigDecimal.ZERO
  @Enumerated(EnumType.STRING)
  var tipoNivel: ETipoNivel = PULMAO
  @ManyToOne(cascade = [PERSIST, MERGE, REFRESH])
  var predio: Predio? = null
  @OneToMany(mappedBy = "nivel", cascade = [PERSIST, MERGE, REFRESH])
  val aptos: List<Apto>? = null
  @OneToOne(mappedBy = "nivel", cascade = [PERSIST, MERGE, REFRESH], fetch = EAGER)
  var layout: LayoutNivel? = null

  override fun toString(): String {
    return this.tipoNivel.toString() + " " + this.numero
  }

  fun erroLayout(): Boolean {
    return layout?.erroLayout() ?: false
  }

  fun total(): Int {
    return layout?.total ?: 0
  }

  companion object Find : NivelFinder()
}

enum class ETipoNivel(private val descricao: String) {
  PICKING("Picking"),
  PULMAO("Pulmão");
  
  override fun toString(): String {
    return descricao
  }
}
