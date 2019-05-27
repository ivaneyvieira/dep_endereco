package br.com.astrosoft.viewmodel.enderecamento.reports

import br.com.astrosoft.model.enderecamento.domain.Produto
import br.com.astrosoft.model.framework.services.findAll
import br.com.astrosoft.viewmodel.framework.viewmodel.reports.LogoReport
import br.com.astrosoft.viewmodel.framework.viewmodel.reports.Report
import net.sf.dynamicreports.report.builder.DynamicReports.col
import net.sf.dynamicreports.report.builder.DynamicReports.type
import net.sf.dynamicreports.report.builder.column.ColumnBuilder
import net.sf.dynamicreports.report.builder.group.ColumnGroupBuilder
import net.sf.dynamicreports.report.builder.subtotal.SubtotalBuilder

class RelatorioProdutos : Report<Produto>() {
  override var logo: LogoReport = LogoPintos()
  override var tituloRelatorio = "Relatório de produtos"
  override var dados: List<Produto> = Produto.findAll().sortedBy { it.prdno }.filter { it.nome.startsWith("A") }
  override var subTotal: List<SubtotalBuilder<*, *>> = emptyList()
  override var grupo: List<ColumnGroupBuilder> = emptyList()
  override var colunas: List<ColumnBuilder<*, *>> = listOf(col.column("Código", "codigoGrade", type.stringType()),
                                                           col.column("Descricao", "nome", type.stringType()))
}