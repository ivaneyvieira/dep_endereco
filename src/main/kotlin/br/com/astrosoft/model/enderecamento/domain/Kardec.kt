package br.com.astrosoft.model.enderecamento.domain

import br.com.astrosoft.model.enderecamento.domain.EEntradaSaida.E
import br.com.astrosoft.model.enderecamento.domain.EOperacaoKardec.VD
import br.com.astrosoft.model.enderecamento.domain.ETipoKardec.CP
import br.com.astrosoft.model.enderecamento.domain.finder.KardecFinder
import br.com.astrosoft.model.framework.services.BaseModel
import io.ebean.annotation.Index
import java.math.BigDecimal
import java.time.LocalDate
import javax.persistence.CascadeType.MERGE
import javax.persistence.CascadeType.PERSIST
import javax.persistence.CascadeType.REFRESH
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "kardec")
class Kardec : BaseModel() {
  @ManyToOne(cascade = [PERSIST, MERGE, REFRESH])
  var procduto : Produto? = null
  @Index
  var date: LocalDate = LocalDate.now()
  @Enumerated(EnumType.STRING)
  var tipo: ETipoKardec = CP
  @Enumerated(EnumType.STRING)
  var operacao: EOperacaoKardec = VD
  @Enumerated(EnumType.STRING)
  var es: EEntradaSaida = E
  @Column(precision = 10, scale = 4)
  var quant: BigDecimal = BigDecimal.ZERO
  
  companion object Find : KardecFinder() {
    fun curvaABC(lista: List<Kardec>, percA: Int, percB: Int, percC: Int): List<ABC> {
      return emptyList()
    }
  }
}

data class ABC(val produto: Produto, val valor: BigDecimal, val classeABC: EABC)

enum class EABC {
  A, B, C
}

enum class EEntradaSaida(val descricao: String) {
  E("Entrada"),
  S("Saida");
  
  override fun toString(): String {
    return descricao
  }
}

enum class EOperacaoKardec(val descricao: String) {
  CP("Compra"),
  VD("Venda"),
  TF("Transferência"),
  DV("Devolução"),
  OU("Outra");
  
  override fun toString(): String {
    return descricao
  }
}

enum class ETipoKardec(val descricao: String) {
  CP("Cupom"),
  MG("Movimentação gerencial"),
  NT("Nota Fiscal");
  
  override fun toString(): String {
    return descricao
  }
}