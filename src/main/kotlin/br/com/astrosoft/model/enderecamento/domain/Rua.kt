package br.com.astrosoft.model.enderecamento.domain

import br.com.astrosoft.model.enderecamento.domain.ETipoNivel.PULMAO
import br.com.astrosoft.model.enderecamento.domain.finder.RuaFinder
import br.com.astrosoft.model.enderecamento.dtos.NivelApto
import br.com.astrosoft.model.enderecamento.dtos.RuaPredio
import br.com.astrosoft.model.framework.services.BaseModel
import io.ebean.annotation.Index
import io.ebean.annotation.Length
import javax.persistence.CascadeType.MERGE
import javax.persistence.CascadeType.PERSIST
import javax.persistence.CascadeType.REFRESH
import javax.persistence.Entity
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "ruas")
class Rua: BaseModel() {
  @Length(2)
  @Index(unique = true)
  var numero: String = ""
  @OneToMany(mappedBy = "rua", cascade = [PERSIST, MERGE, REFRESH])
  val predios: List<Predio>? = null

  override fun toString(): String {
    return "Rua " + this.numero
  }

  companion object Find: RuaFinder() {
    val ruasPredioDeposito
      get()= RepositorioEndereco.ruasPredioDeposito()


    val ruasPulmao
      get() = RepositorioEndereco.ruasPumao()
  }

  fun findNivelAptos(lado: ELado): List<NivelApto> {
    return RepositorioEndereco.findNivelAptos(id, lado)
  }
}
