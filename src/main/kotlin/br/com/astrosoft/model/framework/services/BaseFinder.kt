package br.com.astrosoft.model.framework.services

import io.ebean.Finder

fun <M> Finder<Long, M>.findAll(): List<M> {
  return this.all()
}

fun <M> Finder<Long, M>.findById(id : Long) : M? {
  return this.byId(id)
}