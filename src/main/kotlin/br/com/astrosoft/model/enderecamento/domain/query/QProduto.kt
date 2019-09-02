package br.com.astrosoft.model.enderecamento.domain.query

import br.com.astrosoft.model.enderecamento.domain.Produto
import br.com.astrosoft.model.enderecamento.domain.query.assoc.QAssocMovProduto
import br.com.astrosoft.model.enderecamento.domain.query.assoc.QAssocSaldo
import io.ebean.Database
import io.ebean.typequery.PBigDecimal
import io.ebean.typequery.PInteger
import io.ebean.typequery.PLocalDateTime
import io.ebean.typequery.PLong
import io.ebean.typequery.PString
import io.ebean.typequery.TQRootBean
import io.ebean.typequery.TypeQueryBean

/**
 * Query bean for Produto.
 * 
 * THIS IS A GENERATED OBJECT, DO NOT MODIFY THIS CLASS.
 */
@TypeQueryBean
class QProduto : TQRootBean<Produto, QProduto> {

  companion object {
    /**
     * shared 'Alias' instance used to provide
     * properties to select and fetch clauses
     */
    val _alias = QProduto(true)
  }

  lateinit var id: PLong<QProduto>
  lateinit var createdAt: PLocalDateTime<QProduto>
  lateinit var updatedAt: PLocalDateTime<QProduto>
  lateinit var version: PInteger<QProduto>
  lateinit var codbar: PString<QProduto>
  lateinit var grade: PString<QProduto>
  lateinit var nome: PString<QProduto>
  lateinit var prdno: PString<QProduto>
  lateinit var clno: PInteger<QProduto>
  lateinit var vendno: PInteger<QProduto>
  lateinit var custo: PBigDecimal<QProduto>
  lateinit var preco: PBigDecimal<QProduto>
  lateinit var quantVolumes: PInteger<QProduto>
  lateinit var estoqueMinimo: PBigDecimal<QProduto>
  lateinit var saldos: QAssocSaldo<QProduto>
  lateinit var movProdutos: QAssocMovProduto<QProduto>


  /**
   * Construct with a given Database.
   */
  constructor(database: Database) : super(Produto::class.java, database)

  /**
   * Construct using the default Database.
   */
  constructor() : super(Produto::class.java)

  /**
   * Construct for Alias.
   */
  private constructor(dummy: Boolean) : super(dummy)
}
