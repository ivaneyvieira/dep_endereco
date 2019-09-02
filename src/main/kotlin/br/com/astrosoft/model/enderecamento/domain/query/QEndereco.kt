package br.com.astrosoft.model.enderecamento.domain.query

import br.com.astrosoft.model.enderecamento.domain.ETipoEndereco
import br.com.astrosoft.model.enderecamento.domain.ETipoNivel
import br.com.astrosoft.model.enderecamento.domain.Endereco
import br.com.astrosoft.model.enderecamento.domain.query.assoc.QAssocApto
import br.com.astrosoft.model.enderecamento.domain.query.assoc.QAssocSaldo
import io.ebean.Database
import io.ebean.typequery.PEnum
import io.ebean.typequery.PInteger
import io.ebean.typequery.PLocalDateTime
import io.ebean.typequery.PLong
import io.ebean.typequery.PString
import io.ebean.typequery.TQRootBean
import io.ebean.typequery.TypeQueryBean

/**
 * Query bean for Endereco.
 * 
 * THIS IS A GENERATED OBJECT, DO NOT MODIFY THIS CLASS.
 */
@TypeQueryBean
class QEndereco : TQRootBean<Endereco, QEndereco> {

  companion object {
    /**
     * shared 'Alias' instance used to provide
     * properties to select and fetch clauses
     */
    val _alias = QEndereco(true)
  }

  lateinit var id: PLong<QEndereco>
  lateinit var createdAt: PLocalDateTime<QEndereco>
  lateinit var updatedAt: PLocalDateTime<QEndereco>
  lateinit var version: PInteger<QEndereco>
  lateinit var tipoEndereco: PEnum<QEndereco,ETipoEndereco>
  lateinit var observacao: PString<QEndereco>
  lateinit var localizacao: PString<QEndereco>
  lateinit var tipoNivel: PEnum<QEndereco,ETipoNivel>
  lateinit var apto: QAssocApto<QEndereco>
  lateinit var saldos: QAssocSaldo<QEndereco>
  lateinit var descricao: PString<QEndereco>


  /**
   * Construct with a given Database.
   */
  constructor(database: Database) : super(Endereco::class.java, database)

  /**
   * Construct using the default Database.
   */
  constructor() : super(Endereco::class.java)

  /**
   * Construct for Alias.
   */
  private constructor(dummy: Boolean) : super(dummy)
}
