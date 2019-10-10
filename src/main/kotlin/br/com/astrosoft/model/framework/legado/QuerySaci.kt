package br.com.astrosoft.model.framework.legado

import br.com.astrosoft.model.enderecamento.dtos.Carga
import br.com.astrosoft.model.framework.legado.beans.NotaEntrada
import br.com.astrosoft.model.framework.legado.beans.ProdutoNotaEntrada
import br.com.astrosoft.model.framework.legado.beans.ProdutoSaci
import br.com.astrosoft.model.framework.legado.beans.UserSenha
import br.com.astrosoft.model.framework.utils.DB
import br.com.astrosoft.model.framework.utils.lpad
import java.math.BigDecimal

class QuerySaci: QueryDB(driver, url, username, password) {
  fun notaEntrada(
    invno: Int,
    nfname: String,
    invse: String
                 ): NotaEntrada? {
    val nfInvno = notaEntrada(invno) ?: notaEntrada(nfname, invse)
    nfInvno?.addProdutos(produtoNotaEntrada(nfInvno.invno ?: 0))
    return nfInvno
  }

  fun notaEntrada(invno: Int): NotaEntrada? {
    val sql = "/sql/notaEntradaInv.sql"
    val notaEntrada = query(sql) {q ->
      q.addParameter("invno", invno)
        .executeAndFetchFirst(NotaEntrada::class.java)
    }
    notaEntrada?.let {
      it.addProdutos(produtoNotaEntrada(it.invno ?: 0))
    }
    return notaEntrada
  }

  private fun produtoNotaEntrada(invno: Int): List<ProdutoNotaEntrada> {
    val sql = "/sql/produtoNotaEntrada.sql"
    return query(sql) {q ->
      q.addParameter("invno", invno)
        .executeAndFetch(ProdutoNotaEntrada::class.java)
    }
  }

  private fun notaEntrada(
    nfname: String,
    invse: String
                         ): NotaEntrada? {
    val sql = "/sql/notaEntradaNF.sql"
    return query(sql) {q ->
      q.addParameter("nfname", nfname)
        .addParameter("invse", invse)
        .executeAndFetchFirst(NotaEntrada::class.java)
    }
  }

  fun notaEntrada(notaSerie: String): NotaEntrada? {
    if(notaSerie.contains("/")) {
      val nota = notaSerie.split("/".toRegex()).dropLastWhile {it.isEmpty()}.toTypedArray()[0]
      val serie = notaSerie.split("/".toRegex()).dropLastWhile {it.isEmpty()}.toTypedArray()[1]
      return notaEntrada(nota, serie)
    }
    return null
  }

  fun produto(codbar: String): ProdutoSaci? {
    val sql = "/sql/produtoCodbar.sql"
    return query(sql) {q ->
      q.addParameter("codbar", codbar)
        .executeAndFetchFirst(ProdutoSaci::class.java)
    }
  }

  fun produtos(prdno: String): List<ProdutoSaci> {
    val sql = "/sql/produtoCodGrade.sql"
    return query(sql) {q ->
      q.addParameter("prdno", prdno.lpad(16, " "))
        .addParameter("grade", "")
        .executeAndFetch(ProdutoSaci::class.java)
    }
  }

  fun produto(
    prdno: String,
    grade: String?
             ): ProdutoSaci? {
    val sql = "/sql/produtoCodGrade.sql"
    return query(sql) {q ->
      q.addParameter("prdno", prdno.lpad(16, " "))
        .addParameter("grade", grade ?: "")
        .executeAndFetchFirst(ProdutoSaci::class.java)
    }
  }

  fun userSenha(login: String): UserSenha? {
    val sql = "/sql/userSenha.sql"
    return query(sql) {q ->
      q.addParameter("login", login)
        .executeAndFetchFirst(UserSenha::class.java)
    }
  }

  fun saldoProduto(
    prdno: String,
    grade: String
                  ): BigDecimal {
    val sql = "/sql/saldoProduto.sql"
    return query(sql) {q ->
      q.addParameter("prdno", prdno.lpad(16, " "))
        .addParameter("grade", grade)
        .executeScalar(BigDecimal::class.java)
    } ?: BigDecimal.ZERO
  }

  fun notaEntradaAll(query: String): List<NotaEntrada> {
    val sql = "/sql/notaEntradaAll.sql"

    return query(sql) {q ->
      q.addParameter("query", "%$query%")
        .executeAndFetch(NotaEntrada::class.java)
    }.orEmpty()
  }

  fun findCarga(cargano: Int): List<Carga> {
    val sql = "/sql/cargaSeparacao.sql"
    return query(sql) {q ->
      q.addParameter("cargano", "$cargano")
        .executeAndFetch(Carga::class.java)
    }.orEmpty()
  }

  companion object {
    private val db = DB("saci")
    internal val driver = db.driver
    internal val url = db.url
    internal val username = db.username
    internal val password = db.password
  }
}

val querySaci = QuerySaci()