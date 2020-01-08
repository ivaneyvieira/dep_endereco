package br.com.astrosoft.model.enderecamento.domain

import br.com.astrosoft.model.enderecamento.domain.EMovTipo.SAIDA
import br.com.astrosoft.model.enderecamento.domain.ETipoNivel.PULMAO
import br.com.astrosoft.model.enderecamento.domain.finder.ProdutoFinder
import br.com.astrosoft.model.framework.entityManager.DB.scriptSql
import br.com.astrosoft.model.framework.entityManager.DB.sqlScalar
import br.com.astrosoft.model.framework.legado.querySaci
import br.com.astrosoft.model.framework.services.BaseModel
import br.com.astrosoft.model.framework.services.cacheValue
import br.com.astrosoft.model.framework.services.findById
import br.com.astrosoft.model.framework.utils.lpad
import br.com.astrosoft.model.framework.utils.readFile
import io.ebean.DB
import io.ebean.annotation.Index
import io.ebean.annotation.Length
import io.ebean.annotation.Transactional
import java.math.BigDecimal
import java.time.LocalDate
import javax.persistence.CascadeType.MERGE
import javax.persistence.CascadeType.PERSIST
import javax.persistence.CascadeType.REFRESH
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.OneToMany
import javax.persistence.Table
import javax.persistence.Transient

@Entity
@Table(name = "produtos")
@Index(unique = true, columnNames = ["prdno", "grade"])
class Produto: BaseModel() {
  @Length(16)
  @Index
  var codbar: String = ""
  @Length(8)
  var grade: String = ""
  @Length(40)
  var nome: String = ""
  @Length(16)
  var prdno: String = ""
  var clno: Int = 0
  var vendno: Int = 0
  @Column(precision = 10, scale = 4)
  var custo: BigDecimal = BigDecimal.ZERO
  @Column(precision = 10, scale = 4)
  var preco: BigDecimal = BigDecimal.ZERO
  var quantVolumes: Int = 0
  @Column(precision = 10, scale = 4)
  var estoqueMinimo: BigDecimal = BigDecimal.ZERO
  @OneToMany(mappedBy = "produto", cascade = [PERSIST, MERGE, REFRESH])
  val saldos: List<Saldo>? = null
  @OneToMany(mappedBy = "produto", cascade = [PERSIST, MERGE, REFRESH])
  val movProdutos: List<MovProduto>? = null
  @get:Transient
  val codigoGrade by cacheValue {
    prdno.trim {it <= ' '} + " " + grade
  }
  val saldoSaci by cacheValue {saldoSaci(this)}

  override fun toString(): String {
    return codigoGrade ?: ""
  }

  companion object Find: ProdutoFinder() {
    fun findProduto(prdno: String, grade: String?): Produto? {
      return where().prdno.eq(prdno.lpad(16, " "))
        .and()
        .grade.eq(grade)
        .findOne()
    }

    fun findProdutoQuery(query: String): List<Produto> {
      if(query.trim().length <= 6) {
        val prdNorm = query.trim()
          .lpad(6, "0")
        val prdno = prdNorm.lpad(16, " ")
        return findProdutoPrdno(prdno)
      }
      val split = query.split(" +".toRegex())
        .dropLastWhile {it.isEmpty()}
        .toTypedArray()

      if(split.size == 2) {
        val prdNorm = split[0].lpad(6, "0")
        val prdno = prdNorm.lpad(16, " ")
        val grade = split[1]
        val produtoOpt = findProduto(prdno, grade)
        return produtoOpt?.let {listOf(it)} ?: emptyList()
      }

      return emptyList()
    }

    fun findProduto(barra: String): List<Produto> {
      return where().codbar.eq(barra)
        .findList()
    }

    private fun findProdutoPrdno(prdno: String): List<Produto> {
      return where().prdno.eq(prdno)
        .findList()
    }
  
    fun quantidadePalete(prdno: String): BigDecimal? {
      return sqlScalar<BigDecimal>("/sql/produtosPalete.sql".readFile(),
                                   ("prdno" to prdno))
        .firstOrNull()
    }
  }
  
  fun proximoEnderecoPulmao(): Endereco? {
    val sql = "/sql/pesquisaFilaPulmao.sql"
    val textFile = sql.readFile()
    val row = DB.sqlQuery(textFile)
      .setParameter(1, this.id)
      .findOne()
    val enderecoID = row?.getLong("endereco_id") ?: 0
    return Endereco.findById(enderecoID)
  }
  
  fun proximoEnderecoPiking(): Endereco? {
    val sql = "/sql/pesquisaFilaPicking.sql"
    val textFile = sql.readFile()
    val row = DB.sqlQuery(textFile)
      .setParameter(1, this.id)
      .findOne()
    val enderecoID = row?.getLong("endereco_id") ?: 0
    return Endereco.findById(enderecoID)
  }
  
  private fun saldoSaci(bean: Produto): BigDecimal {
    return querySaci.saldoProduto(bean.prdno, bean.grade)
  }
  
  fun tipoProdutoCarga(): String {
    val transferencias = Transferencia.where()
                           .movProduto.produto.eq(this)
                           .enderecoS.localizacao.ne("RECEBIMENTO")
                           .orderBy()
                           .dataHoraMov.desc()
                           .findList()
                           .firstOrNull() ?: return ""
    val endereco = transferencias.enderecoE ?: return ""
    val localizacao = endereco.localizacao
    val regex = """^[A-Z].+""".toRegex()
    return if(regex matches localizacao)
      "EMPILHADOR"
    else
      "EXPEDICAO"
  }

  @Transactional
  fun recalculaSaldo() {
    scriptSql("/sql/recalculaSaldo.sql".readFile(), Pair("idProduto", id))
  }

  val transferencias: List<Transferencia>
    @Transient
    get() = Transferencia.where()
      .movProduto.produto.id.eq(id)
      .findList()
  val transferenciasPicking: List<Transferencia>
    @Transient get() = movProdutoPicking.let {mp ->
      mp.refresh()
      mp.transferencias.orEmpty()
    }
  val enderecosComSaldo: List<Endereco>
    @Transient get() = Endereco.where()
      .saldos.produto.id.eq(id)
      .and()
      .saldos.saldoConfirmado.gt(BigDecimal.ZERO)
      .findList()
      .distinct()
  val enderecos: List<Endereco>
    @Transient get() {
      return Endereco.where()
        .saldos.produto.id.eq(id)
        .orderBy()
        .saldos.ultEntrada.desc()
        .findList()
        .distinct()
    }
  val saldosPulmao: List<Saldo>
    @Transient get() {
      return Saldo.where()
        .produto.id.eq(id)
        .endereco.tipoNivel.eq(PULMAO)
        .saldoConfirmado.ne(BigDecimal.ZERO)
        .findList()
    }

  fun saldoEm(endereco: Endereco): Saldo? {
    return Saldo.where()
      .produto.id.eq(id)
      .endereco.id.eq(endereco.id)
      .findOne()
  }

  val movProdutoPicking: MovProduto
    @Transient get() {
      val movPicking = movimentacaoPicking()
      val mov = movPicking.findMovProduto(this)
      return mov ?: MovProduto().apply {
        this.movimentacao = movPicking
        this.produto = this@Produto
        this.quantCan = BigDecimal.ZERO
        this.quantMov = BigDecimal.ZERO
        this.quantPalete = BigDecimal.ZERO
        this.save()
      }
    }

  private fun montaChavePicking(): String {
    return Movimentacao.montaChaveStr("PK", prdno.trim {it <= ' '}.lpad(6, "0") + grade)
  }

  private fun montaChaveTransferencia(): String {
    return Movimentacao.montaChaveStr("TI", prdno.trim {it <= ' '}.lpad(6, "0") + grade)
  }

  private fun movimentacaoPicking(): Movimentacao {
    val chavePicking = montaChavePicking()
    val mov = Movimentacao.findMovimentacao(chavePicking)
    return mov ?: Movimentacao().apply {
      this.documento = prdno.trim {it <= ' '}
      this.observacao = "Picking"
      this.chave = chavePicking
      this.data = LocalDate.now()
      this.tipoMov = EMovTipo.PICKING
      this.save()
    }
  }

  val movProdutoTransferencia: MovProduto
    @Transient get() {
      val movTransferencia = movimentacaoTransferencia()
      val mov = movTransferencia.findMovProduto(this)
      return if(mov == null) {
        val mp = MovProduto()
        mp.movimentacao = movTransferencia
        mp.produto = this
        mp.quantCan = BigDecimal.ZERO
        mp.quantMov = BigDecimal.ZERO
        mp.save()
        mp
      }
      else mov
    }

  private fun movimentacaoTransferencia(): Movimentacao {
    val chaveTransferencia = montaChaveTransferencia()
    val movChave = Movimentacao.findMovimentacao(chaveTransferencia)
    return if(movChave == null) {
      val movimentacao = Movimentacao()
      movimentacao.documento = prdno.trim {it <= ' '}
      movimentacao.observacao = "Tranferencia Interna"
      movimentacao.chave = chaveTransferencia
      movimentacao.data = LocalDate.now()
      movimentacao.tipoMov = SAIDA
      movimentacao.save()
      movimentacao
    }
    else movChave
  }

  @get:Transient
  val saldoPulmaoTotal: Double
    get() {
      return saldosPulmao.sumBy {s -> s.saldoConfirmado.toInt()}
        .toDouble()
    }

  private fun zeraSaldosDel() {
    Saldo.where()
      .produto.id.eq(id)
      .findList()
      .forEach {saldo ->
        saldo.run {
          this.saldoConfirmado = BigDecimal.ZERO
          this.saldoNConfirmado = BigDecimal.ZERO
          this.update()
        }
      }
  }
}
