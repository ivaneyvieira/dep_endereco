package br.com.astrosoft.model.enderecamento.domain

import br.com.astrosoft.model.enderecamento.domain.finder.UserFinder
import br.com.astrosoft.model.framework.legado.querySaci
import br.com.astrosoft.model.framework.legado.queryTotvs
import br.com.astrosoft.model.framework.services.BaseModel
import br.com.astrosoft.model.framework.utils.SystemUtils
import io.ebean.annotation.Index
import io.ebean.annotation.Length
import javax.persistence.Basic
import javax.persistence.CascadeType.MERGE
import javax.persistence.CascadeType.PERSIST
import javax.persistence.CascadeType.REFRESH
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.JoinTable
import javax.persistence.Lob
import javax.persistence.ManyToMany
import javax.persistence.Table

@Entity
@Table(name = "users")
class User : BaseModel() {
  @Length(20)
  @Index(unique = true)
  var userName: String = ""
  @ManyToMany(cascade = [PERSIST, MERGE, REFRESH])
  @JoinTable(name = "users_roles")
  var roles: MutableSet<Role> = HashSet()
  @Lob
  @Basic(fetch = FetchType.LAZY)
  var fotoPerfil: ByteArray? = ByteArray(0)
  @Length(10)
  @Index(unique = true)
  var chapa: String = ""
  @Length(15)
  @Index(unique = true)
  var userSaci: String = ""
  @Length(60)
  var firstName: String = ""
  @Length(60)
  var lastName: String = ""
  @Length(60)
  var title: String = ""
  @Length(60)
  var passw: String = ""
  
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false
    if (!super.equals(other)) return false
    
    other as User
    
    if (userName != other.userName) return false
    
    return true
  }
  
  override fun toString(): String {
    return userName
  }
  
  override fun hashCode(): Int {
    var result = super.hashCode()
    result = 31 * result + userName.hashCode()
    return result
  }
  
  fun hasTag(tag: String): Boolean {
    return roles.any { it.tag == tag }
  }
  
  override fun update() {
    if (this.fotoPerfil == null || this.fotoPerfil?.isEmpty() == true) {
      val foto = queryTotvs.imagemChapa(chapa)
      foto.let { imagem ->
        val fotoByte = SystemUtils.resize(imagem?.imagem, 80, 100)
        fotoPerfil = fotoByte ?: kotlin.ByteArray(0)
      }
    }
    //val senha = querySaci.userSenha(userSaci)?.senha ?: ""
    //#passw = senha
    userSaci = userName
    super.update()
  }
  
  override fun insert() {
    if (this.fotoPerfil == null || this.fotoPerfil?.isEmpty() == true) {
      val foto = queryTotvs.imagemChapa(chapa)
      foto.let { imagem ->
        val fotoByte = SystemUtils.resize(imagem?.imagem, 80, 100)
        fotoPerfil = fotoByte ?: kotlin.ByteArray(0)
      }
    }
    userSaci = userName
    val senha = querySaci.userSenha(userSaci)?.senha ?: ""
    passw = senha
    super.insert()
  }
  
  companion object Find : UserFinder() {
    fun findUser(name: String?): User? {
      return where().userName.eq(name).findOne()
    }
    
    fun findEmpilhadores(): List<User> {
      return findByRole("Empilhador")
    }
    
    fun findByRole(roleName : String) : List<User>{
      return where().roles.name.eq(roleName).findList()
    }
  
    fun quebec(): User? {
      return User.where().userName.eq("QUEBEC").findOne()
    }
  }
}

