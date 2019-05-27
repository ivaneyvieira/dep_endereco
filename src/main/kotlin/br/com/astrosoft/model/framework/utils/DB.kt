package br.com.astrosoft.model.framework.utils

import java.io.File
import java.io.StringReader
import java.util.*

class DB(banco: String) {
  private val prop = properties()
  val driver = prop?.getProperty("datasource.$banco.databaseDriver") ?: ""
  val url = prop?.getProperty("datasource.$banco.databaseUrl") ?: ""
  val username = prop?.getProperty("datasource.$banco.username") ?: ""
  val password = prop?.getProperty("datasource.$banco.password") ?: ""

  companion object {
    private fun properties(): Properties? {
      val properties = Properties()
      val propertieFile = System.getProperty("ebean.props.file")
      val file = File(propertieFile)
      val configFile = file.readText()
      properties.load(StringReader(configFile))
      return properties
    }
  }
}
