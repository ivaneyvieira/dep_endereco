package br.com.astrosoft.viewmodel.enderecamento.separacaoCarga

import br.com.astrosoft.model.enderecamento.domain.ETipoCarga
import br.com.astrosoft.model.enderecamento.domain.ETipoCarga.EXPEDICAO
import br.com.astrosoft.model.enderecamento.domain.Endereco
import br.com.astrosoft.model.enderecamento.domain.MovProduto
import br.com.astrosoft.model.enderecamento.domain.Movimentacao
import br.com.astrosoft.model.enderecamento.domain.OrdemServico
import br.com.astrosoft.model.enderecamento.domain.Produto
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
    if(view.listagemCarga.isEmpty())
      throw ViewException("A carga não possui itens")
    val cargaNo = view.cargaNo ?: throw ViewException("Número da carga não foi informado")
    val separador = view.usuarioSeparador ?: throw ViewException("Separador não selecionado")
    val empilhador = view.usuarioEmpilhador ?: throw ViewException("Empilhador não selecionado")
    val doca = view.enderecoDoca ?: throw ViewException("Endereco da doca não foi informado")
    val movimentacao = Movimentacao.novaCarga(cargaNo.toString(), "Transferencia para a carga $cargaNo")
    val agrupaProdutos =
      view.listagemCarga.filter {it.enderecoOrigem != null}
        .groupBy {ProdutoDestino(it.produto, it.destino)}
        .filter {it.value.isNotEmpty()}
  
    agrupaProdutos.forEach {grupo ->
      grupo.key.let {produtoDestino ->
        val produto = produtoDestino.produto ?: return@let null
        val destino = produtoDestino.destino ?: return@let null
        val carga = grupo.value
        val enderecoOrigem =
          carga.firstOrNull()
            ?.enderecoOrigem
        val quantTotal =
          carga.sumByDouble {it.quant}
            .toBigDecimal()
        val movProduto = MovProduto
          .novaMovimentacaoProduto(movimentacao, produto, quantTotal)
        enderecoOrigem?.let {origem ->
          Transferencia.novaTranferencia(origem, doca, movProduto, quantTotal)
          if(destino == EXPEDICAO)
            OrdemServico.updateOrdemServio(separador.id)
          else
            OrdemServico.updateOrdemServio(empilhador.id)
        }
        grupo.value.forEach {cg ->
          cg.marcaProcessado()
        }
      }
    }
    binder?.infoNotify("Operação realizada com sucesso!!!")
  }
  
  fun sepradores(): List<User> {
    return User.separadores()
  }
  
  fun enderecos(): List<Endereco> {
    return Endereco.enderecosPicking.sortedBy {it.localizacao}
  }
}

data class ProdutoDestino(val produto: Produto?, val destino: ETipoCarga?)

interface ISeparacaoCargaViewModel {
  var cargaNo: Int?
  var enderecoDoca: Endereco?
  var usuarioSeparador: User?
  var usuarioEmpilhador: User?
  val listagemCarga: List<Carga>
}
