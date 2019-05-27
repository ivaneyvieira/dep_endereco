package br.com.astrosoft.model.enderecamento.domain

import br.com.astrosoft.model.enderecamento.domain.finder.RoleFinder
import br.com.astrosoft.model.framework.services.BaseModel
import io.ebean.annotation.Index
import io.ebean.annotation.Length
import javax.persistence.CascadeType.MERGE
import javax.persistence.CascadeType.PERSIST
import javax.persistence.CascadeType.REFRESH
import javax.persistence.Entity
import javax.persistence.ManyToMany
import javax.persistence.Table

@Entity
@Table(name = "roles")
class Role : BaseModel() {
  
  companion object Find : RoleFinder()
  
  @Length(20)
  @Index(unique = true)
  var name: String = ""
  @Length(25)
  var tag: String = "USER"
  @ManyToMany(mappedBy = "roles", cascade = [PERSIST, MERGE, REFRESH])
  var users: List<User> = emptyList()
  
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false
    if (!super.equals(other)) return false
    
    other as Role
    
    if (name != other.name) return false
    
    return true
  }
  
  override fun hashCode(): Int {
    var result = super.hashCode()
    result = 31 * result + name.hashCode()
    return result
  }
}
