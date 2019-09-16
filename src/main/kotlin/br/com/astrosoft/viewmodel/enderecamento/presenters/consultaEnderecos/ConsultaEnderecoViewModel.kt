package br.com.astrosoft.viewmodel.enderecamento.presenters.consultaEnderecos

import br.com.astrosoft.model.enderecamento.domain.Apto
import br.com.astrosoft.model.enderecamento.domain.ELado
import br.com.astrosoft.model.enderecamento.domain.ETipoNivel
import br.com.astrosoft.model.enderecamento.domain.Nivel
import br.com.astrosoft.model.enderecamento.domain.Predio
import br.com.astrosoft.model.enderecamento.domain.RepositorioEndereco
import br.com.astrosoft.model.enderecamento.domain.Rua
import br.com.astrosoft.model.framework.services.findAll
import br.com.astrosoft.viewmodel.framework.viewmodel.ViewModel
import java.util.*

class ConsultaEnderecoViewModel : ViewModel() {
  var rua: String? = null
  var predio: String? = null
  var nivel: String? = null
  var apto: String? = null
  var lado: ELado? = null
  var tipoNivel: ETipoNivel? = null
  val grid = GridConsultaEndereco(this)

  init {
    RepositorioEndereco.updateRegistros()
  }

  override fun reloadModel() {
    grid.processaGrid()
  }
  
  fun ruas(): List<String> = execList {
    Rua.findAll().map(Rua::numero).distinct().sortedBy { it }
  }
  
  fun predios(): List<String> = execList {
    Predio.findAll().map { it.numero }.distinct().sortedBy { it }
  }
  
  fun niveis(): List<String> = execList {
    Nivel.findAll().map { it.numero }.distinct().sortedBy { it }
  }
  
  fun aptos(): List<String> = execList {
    Apto.findAll().map { it.numero }.distinct().sortedBy { it }
  }
  
  fun ladosRuas(): List<ELado> {
    return Arrays.asList(*ELado.values())
  }
  
  fun tipoNiveis(): List<ETipoNivel> {
    return Arrays.asList(*ETipoNivel.values())
  }
}
