package br.com.astrosoft.model.framework.legado

import br.com.astrosoft.model.framework.legado.beans.ImagemChapa
import br.com.astrosoft.model.framework.utils.DB

class QueryTotvs : QueryDB(driver, url, username, password) {
  
  fun imagemChapa(chapa: String?): ImagemChapa? {
    val sql = "/sql/imagemChapa.sql"
    return query(sql) { q ->
      q.addParameter("chapa", chapa).executeAndFetchFirst(ImagemChapa::class.java)
    }
  }
  
  companion object {
    
    private val db = DB("totvs")
    internal val driver = db.driver
    internal val url = db.url
    internal val username = db.username
    internal val password = db.password
  }
}

val queryTotvs = QueryTotvs()