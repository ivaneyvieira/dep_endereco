package br.com.astrosoft.model.enderecamento.domain.query

import br.com.astrosoft.model.enderecamento.domain.ELado
import br.com.astrosoft.model.enderecamento.domain.Predio
import br.com.astrosoft.model.enderecamento.domain.query.assoc.QAssocNivel
import br.com.astrosoft.model.enderecamento.domain.query.assoc.QAssocRua
import io.ebean.Database
import io.ebean.typequery.PEnum
import io.ebean.typequery.PInteger
import io.ebean.typequery.PLocalDateTime
import io.ebean.typequery.PLong
import io.ebean.typequery.PString
import io.ebean.typequery.TQRootBean
import io.ebean.typequery.TypeQueryBean

/**
 * Query bean for Predio.
 * 
 * THIS IS A GENERATED OBJECT, DO NOT MODIFY THIS CLASS.
 */
@TypeQueryBean
class QPredio : TQRootBean<Predio, QPredio> {

  companion object {
    /**
     * shared 'Alias' instance used to provide
     * properties to select and fetch clauses
     */
    val _alias = QPredio(true)
  }

  lateinit var id: PLong<QPredio>
  lateinit var createdAt: PLocalDateTime<QPredio>
  lateinit var updatedAt: PLocalDateTime<QPredio>
  lateinit var version: PInteger<QPredio>
  lateinit var numero: PString<QPredio>
  lateinit var lado: PEnum<QPredio,ELado>
  lateinit var rua: QAssocRua<QPredio>
  lateinit var niveis: QAssocNivel<QPredio>


  /**
   * Construct with a given Database.
   */
  constructor(database: Database) : super(Predio::class.java, database)

  /**
   * Construct using the default Database.
   */
  constructor() : super(Predio::class.java)

  /**
   * Construct for Alias.
   */
  private constructor(dummy: Boolean) : super(dummy)
}
