package br.com.astrosoft.view.framework.vaadin.images

import com.vaadin.server.StreamResource.StreamSource

import java.io.ByteArrayInputStream
import java.io.InputStream

class BytesStreamSource(private val imagem: ByteArray) : StreamSource {
  
  override fun getStream(): InputStream {
    return ByteArrayInputStream(imagem)
  }
}
