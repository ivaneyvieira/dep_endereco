package br.com.astrosoft.model.enderecamento.domain

data class RegistroEndereco (
    val rua_id : Long,
    val rua : String,
    val predio_id : Long,
    val predio : String,
    val lado : String,
    val nivel_id : Long,
    val nivel : String,
    val altura : Integer,
    val tipo_nivel : String,
    val apto_id : Long,
    val apto : String,
    val tipo_palet : String,
    val tipo_altura : String,
    val endereco_id : Long,
    val tipo_endereco : String,
    val observacao : String,
    val localizacao : String
){
  companion object {
    private var enderecos = mutableListOf<RegistroEndereco>()
  }
}