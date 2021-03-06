package br.com.astrosoft.model.framework.entityManager

import br.com.astrosoft.model.framework.exceptions.AppException
import br.com.astrosoft.model.framework.exceptions.DevException
import io.ebean.Ebean
import javax.persistence.PersistenceException

object DB {
  fun <R> xa(lambda: () -> R): R {
    return try {
      lambda()
    } catch(e: AppException) {
      throw e
    } catch(e: Throwable) {
      throw DevException(e, "Erro desconhecido")
    }
  }
  
  @Throws(PersistenceException::class)
  fun executeSqls(
    sqls: List<String>, vararg params: Pair<String, Any?>
                 ) {
    sqls.forEach {sql ->
      println(sql)
      val update = Ebean.createSqlUpdate(sql)
      params.forEach {param ->
        update.setParameter(param.first, param.second)
      }
      update.execute()
    }
  }
  
  fun String.split(): List<String> {
    return this/*.replace("::=", ":=").replace(":=", "::=")*/.split(";")
      .map {it.replace('\n', ' ')}
      .map {it.trim()}
      .filter {it.isNotEmpty()}
  }
  
  @Throws(PersistenceException::class)
  fun scriptSql(sqlScript: String, vararg params: Pair<String, Any?>) {
    xa {
      val sqls = sqlScript.split()
      executeSqls(sqls, * params)
    }
  }
  
  /*
  @Throws(PersistenceException::class)
  inline fun <reified T> sqlEntity(
          sqlScript: String, vararg params: Pair<String, Any?>
                                  ): List<T> {
    return xa {
      val sqls = sqlScript.split()
      val sqlsScript = sqls.dropLast(1)
      
      executeSqls(sqlsScript, * params)
      
      val sql = sqls.last()
      println(sql)
      
      when {
        T::class.isSubclassOf(BaseModel::class) -> {
          
          val rawSql = RawSqlBuilder.parse(sql).create()
          
          val query = Ebean.find(T::class.java).setRawSql(rawSql)
          params.forEach { param ->
            query.setParameter(param.first, param.second)
          }
          
          query.findList()
        }
        else                                    -> {
          val sqlQuery = Ebean.createSqlQuery(sql)
          val constructor = T::class.constructors.first()
          params.forEach { param ->
            sqlQuery.setParameter(param.first, param.second)
          }
          sqlQuery.findList().map { sqlRow ->
            val arrayPar: List<Any?> = constructor.parameters.map { par ->
              sqlRow[par.name]
            }
            
            constructor.call(arrayPar.toTypedArray())
          }
        }
      }
    }
  }*/
  @Throws(PersistenceException::class)
  inline fun <reified T> sqlScalar(
    sqlScript: String, vararg params: Pair<String, Any>
                                  ): List<T> {
    return xa {
      val sqls = sqlScript.split()
      val sqlsScript = sqls.dropLast(1)
  
      executeSqls(sqlsScript, * params)
      val sql = sqls.last()
      println(sql)
      val sqlQuery = Ebean.createSqlQuery(sql)
  
      params.forEach {param ->
        sqlQuery.setParameter(param.first, param.second)
      }
      sqlQuery.findList()
        .filter {it is T}
        .map {it as T}
    }
  }
}


