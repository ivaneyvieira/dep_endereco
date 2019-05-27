package br.com.astrosoft.viewmodel.enderecamento.reports

import br.com.astrosoft.viewmodel.framework.viewmodel.reports.LogoReport

class LogoPintos : LogoReport() {
  init {
    image = LogoPintos::class.java.getResourceAsStream("/img/logoPintos.png")
    height = 25
    width = 84
  }
}