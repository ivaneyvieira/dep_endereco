package br.com.astrosoft.view.framework.vaadin.menu

import com.vaadin.navigator.View
import tools.devnull.trugger.scan.ClassScan

class BeanViewManager(private val javaPackage: String) {
  val availableViews: List<BeanMenuItem<View>>
    get() {
      val list = getClasses(View::class.java)
      val listBean = list.map { BeanMenuItem(it) }
      return listBean.sortedBy { it.order }
    }
  
  private inline fun <reified CLASS : Class<View>> getClasses(superClass: CLASS): List<CLASS> {
    val mutableList = ClassScan.scan().classes().deep().`in`(javaPackage)
    val classes: List<CLASS> = mutableList.toList().mapNotNull { it as? CLASS }
    val classeMenuItem = ViewMenuItem::class.java
    return classes.filter { c -> c.isAnnotationPresent(classeMenuItem) }.filter { c -> c != superClass && superClass.isAssignableFrom(c) }
  }
}
