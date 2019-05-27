package br.com.astrosoft.model.enderecamento.domain

import br.com.astrosoft.model.enderecamento.domain.EMovTipo.ENTRADA
import br.com.astrosoft.model.enderecamento.domain.EMovTipo.TRANSFERENCIA
import br.com.astrosoft.model.enderecamento.domain.finder.MovimentacaoFinder
import br.com.astrosoft.model.enderecamento.domain.query.QMovProduto
import br.com.astrosoft.model.framework.services.BaseModel
import br.com.astrosoft.model.framework.utils.lpad
import io.ebean.annotation.Index
import io.ebean.annotation.Length
import java.time.LocalDate
import javax.persistence.CascadeType.MERGE
import javax.persistence.CascadeType.PERSIST
import javax.persistence.CascadeType.REFRESH
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "movimentacoes")
class Movimentacao : BaseModel() {
  @Length(20)
  @Index(unique = true)
  var chave: String = ""
  @Length(20)
  var documento: String = ""
  var data: LocalDate = LocalDate.now()
  @Length(100)
  var observacao: String = ""
  @Enumerated(EnumType.STRING)
  var tipoMov: EMovTipo = ENTRADA
  @OneToMany(mappedBy = "movimentacao", cascade = [PERSIST, MERGE, REFRESH])
  var movProdutos: List<MovProduto>? = null

  companion object Find : MovimentacaoFinder() {
    fun findNotaEntrada(invno: Int): Movimentacao? {
      val chaveEntrada = montaChaveEntrada(invno)
      return findMovimentacao(chaveEntrada)
    }

    fun findMovimentacao(chaveEntrada: String): Movimentacao? {
      return where().chave.eq(chaveEntrada).findOne()
    }

    fun montaChaveStr(prefixo: String, str: String): String {
      return prefixo + if (str.length > 8) return str
      else str.lpad(8, "0")
    }

    fun montaChaveEntrada(invno: Int): String {
      return montaChaveStr("NE", invno.toString())
    }

    private fun proximaChave(prefixo: String): String {
      val ultimaChave = where()
                          .select("max(chave)")
                          .chave.startsWith(prefixo)
                          .findSingleAttributeList<String>()
                          .firstOrNull() ?: "${prefixo}00000000"
      val numero = ultimaChave.substring(2).toIntOrNull() ?: 0
      val novoNumero = numero + 1
      return montaChaveStr(prefixo, novoNumero.toString())
    }

    fun novaTransferencia(novaObservacao : String): Movimentacao {
      val chaveNova = proximaChave("TR")
      return Movimentacao().apply {
        this.chave = chaveNova
        this.documento = chaveNova
        this.observacao = novaObservacao
        this.tipoMov = TRANSFERENCIA
        save()
      }
    }
  }

  fun findMovProduto(produto: Produto): MovProduto? {
    return QMovProduto().where().movimentacao.id.eq(id)
      .produto.id.eq(produto.id)
      .findOne()
  }
}

enum class EMovTipo(val descricao: String) {
  ENTRADA("Recebimento"),
  SAIDA("Expedição"),
  PICKING("Picking"),
  TRANSFERENCIA("Transferencia")
}

enum class EStatusEntrada(private val descricao: String) {
  NAO_RECEBIDA("Não recebida"),
  RECEBIDA("Recebida"),
  ENDERECADA("Endereçado"),
  CONFERIDA("Conferida"),
  INCONSISTENTE("Inconsistente");

  override fun toString(): String {
    return descricao
  }
}