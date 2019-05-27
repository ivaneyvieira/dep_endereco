package br.com.astrosoft.viewmodel.framework.viewmodel

abstract class IListModel<BEAN : Any> {
  abstract var list: List<BEAN>?
  abstract var itemSelected: BEAN?
  abstract fun updateList()
}