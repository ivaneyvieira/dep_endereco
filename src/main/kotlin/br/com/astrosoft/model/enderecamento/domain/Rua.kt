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
class Rua : BaseModel() {
  @Length(2)
  @Index(unique = true)
  var numero: String = ""
  @OneToMany(mappedBy = "rua", cascade = [PERSIST, MERGE, REFRESH])
  val predios: List<Predio>? = null
  
  override fun toString(): String {
    return "Rua " + this.numero
  }
  
  companion object Find : RuaFinder() {
    val ruasPredioDeposito: List<RuaPredio> by lazy {
      Predio.where().rua.numero.ne("00").findList().mapNotNull { p ->
        val rua = p.rua
        if (rua != null && p != null)
          RuaPredio(rua, p)
        else null
      }
    }
    
    val ruasPulmao: List<Rua>
      get() = where().predios.niveis.tipoNivel.eq(PULMAO).findList()
  }
  
//  private fun findNiveis(lado: ELado): List<Nivel> {
 //   return Nivel.where().predio.rua.id.eq(id)
 //           .and().predio.lado.eq(lado)
 //           .findList().distinct()
 // }
  
  fun findNivelAptos(lado: ELado): List<NivelApto> {
    return Apto.where()
      .fetch("nivel")
      .fetch("nivel.layout")
      .fetch("nivel.predio")
     // .fetch("endereco")
      .nivel.predio.rua.id.eq(id)
            .and().nivel.predio.lado.eq(lado)
            .findList().mapNotNull { apto ->
              val nivel = apto.nivel ?: return@mapNotNull null

              NivelApto(nivel, apto)
            }
  }
}
