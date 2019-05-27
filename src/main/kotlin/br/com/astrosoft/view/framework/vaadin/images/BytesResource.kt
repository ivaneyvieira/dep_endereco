package br.com.astrosoft.view.framework.vaadin.images

import com.vaadin.server.StreamResource
import java.security.MessageDigest
import javax.xml.bind.DatatypeConverter

fun ByteArray.makeResource(): StreamResource {
  val nome = md5()
  val imagesource = BytesStreamSource(this)
  val streamResource = StreamResource(imagesource, "$nome.jpg")
  streamResource.cacheTime = 1000
  return streamResource
}

fun ByteArray.md5(): String {
  val md5Digest = MessageDigest.getInstance("MD5")
  val bytes = md5Digest.digest(this)
  return DatatypeConverter.printHexBinary(bytes)
}