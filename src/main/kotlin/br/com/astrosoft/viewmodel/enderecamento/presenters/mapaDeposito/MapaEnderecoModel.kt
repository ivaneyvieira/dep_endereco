package br.com.astrosoft.viewmodel.enderecamento.presenters.mapaDeposito

import br.com.astrosoft.model.enderecamento.domain.Apto
import br.com.astrosoft.model.enderecamento.domain.Endereco
import br.com.astrosoft.model.framework.exceptions.ViewException

class MapaEnderecoModel(val model: MapaDepositoViewModel) {
  var endereco: Endereco? = null
  val listEnderecoProduto = ListEnderecoProduto(model)
  var visible = false
  /*
  fun transferencia(source: Endereco, target: Endereco) = model.exec {
    source.transferePara(target)
  }
  */
  fun salvaEndereco() = model.exec {
    endereco?.save()
  }
  
  fun showDiagloEndereco() = model.exec {
    visible = true
  }
  
  fun aptosNivel(end: Endereco?): List<Apto> {
    val idNivel = end?.apto?.nivel?.id ?: 0
    return Apto.where().nivel.id.eq(idNivel).findList()
  }
  
  fun changeApto(endereco: Endereco, apto: Apto) = model.exec {
    endereco.let { end ->
      val enderecoDestino = apto.endereco ?: throw  ViewException("Endereço destino inválido")
      val origem = endereco.localizacao
      val tipo = endereco.tipoNivel.name
      val destino = enderecoDestino.localizacao
      Endereco.changeApto(origem, tipo, destino)
      end.saldos.orEmpty().forEach { saldo ->
        saldo.produto?.recalculaSaldo()
      }
    }
  }
}