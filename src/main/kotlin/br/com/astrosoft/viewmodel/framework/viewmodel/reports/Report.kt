package br.com.astrosoft.viewmodel.framework.viewmodel.reports

import net.sf.dynamicreports.report.builder.DynamicReports.cmp
import net.sf.dynamicreports.report.builder.DynamicReports.exp
import net.sf.dynamicreports.report.builder.DynamicReports.report
import net.sf.dynamicreports.report.builder.DynamicReports.stl
import net.sf.dynamicreports.report.builder.column.ColumnBuilder
import net.sf.dynamicreports.report.builder.component.HorizontalListBuilder
import net.sf.dynamicreports.report.builder.group.ColumnGroupBuilder
import net.sf.dynamicreports.report.builder.style.StyleBuilder
import net.sf.dynamicreports.report.builder.subtotal.SubtotalBuilder
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment.CENTER
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment.LEFT
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment.RIGHT
import net.sf.dynamicreports.report.constant.VerticalTextAlignment.MIDDLE
import net.sf.dynamicreports.report.exception.DRException
import java.awt.Color
import java.io.FileOutputStream

abstract class Report<BEAN> {
  private val boldStyle: StyleBuilder = stl.style().bold()
  private val boldCenteredStyle: StyleBuilder = stl.style(boldStyle)
          .setHorizontalTextAlignment(CENTER)
  private val columnTitleStyle: StyleBuilder = stl.style(boldCenteredStyle)
          .setBorder(stl.pen1Point())
          .setBackgroundColor(Color.LIGHT_GRAY)
  private var titleStyle: StyleBuilder = stl.style(boldCenteredStyle)
          .setVerticalTextAlignment(MIDDLE)
          .setFontSize(15)
  abstract var logo: LogoReport
  private var tituloEmpresa: String = ""
  open var tituloRelatorio: String = ""
  private fun titulo(): HorizontalListBuilder = cmp.horizontalList()
          .add(cmp.image(logo.image).setFixedDimension(logo.width, logo.height),
               cmp.text(tituloEmpresa).setStyle(titleStyle).setHorizontalTextAlignment(LEFT),
               cmp.text(tituloRelatorio).setStyle(titleStyle).setHorizontalTextAlignment(RIGHT))
          .newRow()
          .add(cmp.filler().setStyle(stl.style().setTopBorder(stl.pen1Point())).setFixedHeight(10))
  
  private fun rodape(): HorizontalListBuilder = cmp.horizontalList()
          .add(cmp.filler().setStyle(stl.style().setTopBorder(stl.pen1Point())).setFixedHeight(10))
          .newRow()
          .add(cmp.pageXofY().setStyle(boldCenteredStyle))
  
  private var fileName: String? = null
  abstract var dados: List<BEAN>
  abstract var subTotal: List<SubtotalBuilder<*, *>>
  abstract var grupo: List<ColumnGroupBuilder>
  abstract var colunas: List<ColumnBuilder<*, *>>
  
  fun build() {
    try {
      grupo.forEach { g -> g.setPrintSubtotalsWhenExpression(exp.printWhenGroupHasMoreThanOneRow(g)) }
      fileName = "/tmp/" + Math.round(Math.random() * 10000) + ".pdf"
      val out = FileOutputStream(fileName)
      report()
              .setColumnTitleStyle(columnTitleStyle)
              .highlightDetailEvenRows()
              .title(titulo())
              .pageFooter(rodape())
              .columns(* colunas.toTypedArray())
              .groupBy(* grupo.toTypedArray())
              .subtotalsAtSummary(* subTotal.toTypedArray())
              .setDataSource(dados)
              .toPdf(out)
    } catch (e: DRException) {
      e.printStackTrace()
    }
  }
  
}