package br.com.astrosoft.viewmodel.enderecamento.separacaoCarga

import br.com.astrosoft.model.enderecamento.domain.Endereco
import br.com.astrosoft.model.enderecamento.domain.MovProduto
import br.com.astrosoft.model.enderecamento.domain.Movimentacao
import br.com.astrosoft.model.enderecamento.domain.Transferencia
import br.com.astrosoft.model.enderecamento.domain.User
import br.com.astrosoft.model.enderecamento.dtos.Carga
import br.com.astrosoft.model.framework.exceptions.ViewException
import br.com.astrosoft.viewmodel.framework.viewmodel.ViewModel

class SeparacaoCargaViewModel(): ViewModel() {
  private lateinit var view: ISeparacaoCargaViewModel
  
  fun setView(view: ISeparacaoCargaViewModel) {
    this.view = view
  }
  
  val headerModel = HeaderModel(this)
  val gridModel = GridModel(this)
  
  override fun reloadModel() {
    gridModel.updateList()
  }
  
  fun enderecosExpedicao(): List<Endereco> {
    return Endereco.enderecosExpedicao()
  }
  
  fun processamento() = exec {
    if(view.listagemCarga.size == 0)
      throw ViewException("A carga não possui itens")
    val cargaNo = view.cargaNo ?: throw ViewException("Número da carga não foi informado")
    val separador = view.usuarioSeparador ?: throw ViewException("Usuário não selecionado")
    val doca = view.enderecoDoca ?: throw ViewException("Endereco da doca não foi informado")
    val movimentacao = Movimentacao.novaCarga("Transferencia para a carga $cargaNo")
    val agrupaProdutos =
      view.listagemCarga.filter {it.enderecoOrigem != null}
        .groupBy {it.produto}
        .filter {it.value.isNotEmpty()}
    
    agrupaProdutos.forEach {grupo ->
      grupo.key?.let {produto ->
        val carga = grupo.value
        val enderecoOrigem =
          carga.firstOrNull()
            ?.enderecoOrigem
        val quantTotal =
          carga.sumByDouble {it.quant}
            .toBigDecimal()
        val movProduto = MovProduto.novaMovimentacaoProduto(movimentacao, produto, quantTotal)
        enderecoOrigem?.let {origem ->
          Transferencia.novaTranferencia(origem, doca, movProduto, quantTotal)
          //TODO Falta atribuir o usuario à tranferencia do PICKING parta expedição
        }
      }
    }
  }
  
  fun sepradores(): List<User> {
    return User.separadores()
  }
}

interface ISeparacaoCargaViewModel {
  var cargaNo: Int?
  var enderecoDoca: Endereco?
  var usuarioSeparador: User?
  val listagemCarga: List<Carga>
}
