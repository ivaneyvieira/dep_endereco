package br.com.astrosoft.view.framework.vaadin.fields

import com.vaadin.server.StreamResource
import com.vaadin.server.StreamResource.StreamSource
import com.vaadin.ui.BrowserFrame
import com.vaadin.ui.VerticalLayout
import java.io.File
import java.io.FileInputStream
import java.io.InputStream

class ReportDialog(val title: String, private val filePDF: String) : DialogModal(title) {
  //val pdfViewer = WTPdfViewer()
  override fun buildForm(): VerticalLayout {
    val layout = VerticalLayout()
    val pdfViewer = createBrowserFrame()
    layout.setSizeFull()
    layout.removeAllComponents()
    layout.addComponents(pdfViewer)
    pdfViewer.setSizeFull()
    return layout
  }
  
  /*
    fun createWTPdfViewer(): WTPdfViewer {
      val file = File(filePDF)
      val dataStream = FileInputStream(file)
      val pdfViewer = WTPdfViewer()
      pdfViewer.setResource(StreamResource(InputStreamSource(dataStream), filePDF))
      return pdfViewer
    }
  */
  private fun createBrowserFrame(): BrowserFrame {
    val file = File(filePDF)
    val dataStream = FileInputStream(file)
    return BrowserFrame("", StreamResource(InputStreamSource(dataStream), filePDF))
  }
  
  /*
    fun createPdfViewer(): PdfViewer {
      val file = File(filePDF)
      return PdfViewer(file)
    }

  */
  override fun updateView() {}
  
  override fun updateModel() {}
}

class InputStreamSource(val data: InputStream?) : StreamSource {
  override fun getStream(): InputStream? {
    return data
  }
}