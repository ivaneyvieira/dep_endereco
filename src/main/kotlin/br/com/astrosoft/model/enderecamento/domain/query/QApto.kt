package br.com.astrosoft.model.enderecamento.domain.query

import br.com.astrosoft.model.enderecamento.domain.Apto
import br.com.astrosoft.model.enderecamento.domain.EPalet
import br.com.astrosoft.model.enderecamento.domain.ETipoAltura
import br.com.astrosoft.model.enderecamento.domain.query.assoc.QAssocEndereco
import br.com.astrosoft.model.enderecamento.domain.query.assoc.QAssocNivel
import io.ebean.Database
import io.ebean.typequery.PEnum
import io.ebean.typequery.PInteger
import io.ebean.typequery.PLocalDateTime
import io.ebean.typequery.PLong
import io.ebean.typequery.PString
import io.ebean.typequery.TQRootBean
import io.ebean.typequery.TypeQueryBean

/**
 * Query bean for Apto.
 * 
 * THIS IS A GENERATED OBJECT, DO NOT MODIFY THIS CLASS.
 */
@TypeQueryBean
class QApto : TQRootBean<Apto, QApto> {

  companion object {
    /**
     * shared 'Alias' instance used to provide
     * properties to select and fetch clauses
     */
    val _alias = QApto(true)
  }

  lateinit var id: PLong<QApto>
  lateinit var createdAt: PLocalDateTime<QApto>
  lateinit var updatedAt: PLocalDateTime<QApto>
  lateinit var version: PInteger<QApto>
  lateinit var numero: PString<QApto>
  lateinit var tipoPalet: PEnum<QApto,EPalet>
  lateinit var tipoAltura: PEnum<QApto,ETipoAltura>
  lateinit var nivel: QAssocNivel<QApto>
  lateinit var endereco: QAssocEndereco<QApto>


  /**
   * Construct with a given Database.
   */
  constructor(database: Database) : super(Apto::class.java, database)

  /**
   * Construct using the default Database.
   */
  constructor() : super(Apto::class.java)

  /**
   * Construct for Alias.
   */
  private constructor(dummy: Boolean) : super(dummy)
}
