package br.com.astrosoft.viewmodel.enderecamento.presenters.empilhador

import br.com.astrosoft.model.enderecamento.domain.OrdemServico
import br.com.astrosoft.model.enderecamento.domain.RepositorioEndereco
import br.com.astrosoft.model.enderecamento.domain.User
import br.com.astrosoft.viewmodel.framework.viewmodel.ViewModel

class EmpilhadorModel : ViewModel() {
  val empilhadorHeaderModel = EmpilhadorHeaderModel()
  val empilhadorGridModel = EmpilhadorGridModel()

  init {
    RepositorioEndereco.updateRegistros()
  }

  override fun reloadModel() {
    updateGrid()
  }
  
  fun updateGrid() = exec {
    val user = empilhadorHeaderModel.user
    val osPendente = empilhadorHeaderModel.osPendente
    val ordensServico =
            OrdemServico
                    .findOrdemServicoUser(user?.id ?: 0, osPendente)
    empilhadorGridModel.ordensServico.let { listOs ->
      listOs.clear()
      listOs.addAll(ordensServico)
    }
  }
  
  fun confirmaOS(
          os: OrdemServico,
          confirma: Boolean
                ) = exec {
    os.confirmaOS(confirma)
  }
}

class EmpilhadorHeaderModel {
  var user: User? = null
  var osPendente: Boolean = true
}

class EmpilhadorGridModel {
  val ordensServico = mutableListOf<OrdemServico>()
}


