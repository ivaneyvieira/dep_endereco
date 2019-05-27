package br.com.astrosoft.model.enderecamento

import io.ebean.typequery.generator.Generator
import io.ebean.typequery.generator.GeneratorConfig
import java.io.IOException

object MainQueryBeanGenerator {
  
  @Throws(IOException::class)
  @JvmStatic
  fun main() {
    
    val config = GeneratorConfig()
    config.lang = "kt"
    config.classesDirectory = "./out/production/classes/"
    config.destDirectory = "./src/main/kotlin"
    config.destResourceDirectory = "./src/main/resources"
  
    config.entityBeanPackage = "br.com.astrosoft.model.enderecamento.domain"
    config.destPackage = "br.com.astrosoft.model.enderecamento.domain.query"
  
    config.isOverwriteExistingFinders = true
    
    val generator = Generator(config)
    generator.generateQueryBeans()
    
    // Additionally generate 'finder's
    generator.generateFinders()
    generator.modifyEntityBeansAddFinderField()
  }
}