package br.com.astrosoft.model.enderecamento.domain

import br.com.astrosoft.model.enderecamento.domain.ELado.IMPAR
import br.com.astrosoft.model.enderecamento.domain.finder.PredioFinder
import br.com.astrosoft.model.framework.services.BaseModel
import io.ebean.annotation.Index
import io.ebean.annotation.Length
import javax.persistence.CascadeType.MERGE
import javax.persistence.CascadeType.PERSIST
import javax.persistence.CascadeType.REFRESH
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "predios")
@Index(unique = true, columnNames = ["rua_id", "numero"])
class Predio : BaseModel() {
  @Length(2)
  var numero: String = ""
  @Enumerated(EnumType.STRING)
  var lado: ELado = IMPAR
  @ManyToOne(cascade = [PERSIST, MERGE, REFRESH])
  var rua: Rua? = null
  @OneToMany(mappedBy = "predio", cascade = [PERSIST, MERGE, REFRESH])
  val niveis: List<Nivel>? = null
  
  companion object Find : PredioFinder()
}

enum class ELado(val descricao: String) {
  PAR("Par"),
  IMPAR("Impar");
  
  override fun toString(): String {
    return this.descricao
  }
}
