package br.com.astrosoft.viewmodel.enderecamento.reports

import br.com.astrosoft.model.enderecamento.domain.OrdemServico
import br.com.astrosoft.model.framework.reports.PlacaPicking
import br.com.astrosoft.model.framework.utils.SystemUtils
import net.sf.jasperreports.engine.JasperCompileManager
import net.sf.jasperreports.engine.JasperExportManager
import net.sf.jasperreports.engine.JasperFillManager
import net.sf.jasperreports.engine.JasperReport
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource

class RelatorioPlacaPicking {
  var fileName: String? = null
  private val jasperReport: JasperReport by lazy {
    val inputStream = SystemUtils.getResourceAsStream("/report/cartao_picking.jrxml")
    inputStream.let { JasperCompileManager.compileReport(it) }
  }
  
  fun build(transferencia: OrdemServico) {
    fileName = "/tmp/" + Math.round(Math.random() * 1000000) + ".pdf"
    val list = PlacaPicking.createPlacaPicking(transferencia)
    val beanColDataSource = JRBeanCollectionDataSource(list)
    val jasperPrint = JasperFillManager.fillReport(jasperReport, null, beanColDataSource)
    // exporta para pdf
    JasperExportManager.exportReportToPdfFile(jasperPrint, fileName)
  }
}