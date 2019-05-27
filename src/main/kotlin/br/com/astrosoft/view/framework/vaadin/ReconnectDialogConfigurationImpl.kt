package br.com.astrosoft.view.framework.vaadin

import com.vaadin.ui.ReconnectDialogConfiguration

class ReconnectDialogConfigurationImpl : ReconnectDialogConfiguration {
  private var dialogText: String? = null
  private var dialogTextGaveUp: String? = null
  private var dialogGracePeriod: Int = 0
  private var reconnectAttempts: Int = 0
  private var reconnectInterval: Int = 0
  private var dialogModal: Boolean = false
  
  override fun getDialogText(): String? {
    return dialogText
  }
  
  override fun setDialogText(dialogText: String) {
    this.dialogText = dialogText
  }
  
  override fun getDialogTextGaveUp(): String? {
    return dialogTextGaveUp
  }
  
  override fun setDialogTextGaveUp(dialogTextGaveUp: String) {
    this.dialogTextGaveUp = dialogTextGaveUp
  }
  
  override fun getReconnectAttempts(): Int {
    return reconnectAttempts
  }
  
  override fun setReconnectAttempts(reconnectAttempts: Int) {
    this.reconnectAttempts = reconnectAttempts
  }
  
  override fun getReconnectInterval(): Int {
    return reconnectInterval
  }
  
  override fun setReconnectInterval(reconnectInterval: Int) {
    this.reconnectInterval = reconnectInterval
  }
  
  override fun getDialogGracePeriod(): Int {
    return dialogGracePeriod
  }
  
  override fun setDialogGracePeriod(dialogGracePeriod: Int) {
    this.dialogGracePeriod = dialogGracePeriod
  }
  
  override fun setDialogModal(dialogModal: Boolean) {
    this.dialogModal = dialogModal
  }
  
  override fun isDialogModal(): Boolean {
    return dialogModal
  }
}
