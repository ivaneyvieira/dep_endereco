package br.com.astrosoft.view.framework.vaadin.images

import com.vaadin.server.StreamResource
import org.apache.commons.codec.digest.DigestUtils

fun ByteArray.makeResource(): StreamResource {
  val nome = md5()
  val imagesource = BytesStreamSource(this)
  val streamResource = StreamResource(imagesource, "$nome.jpg")
  streamResource.cacheTime = 1000
  return streamResource
}

fun ByteArray.md5(): String {
  return DigestUtils.md5Hex(this)
  //val md5Digest = MessageDigest.getInstance("MD5")
  // val bytes = md5Digest.digest(this)
  // return DatatypeConverter.printHexBinary(bytes)
}