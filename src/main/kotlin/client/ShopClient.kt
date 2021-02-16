package client

import Launcher
import prefabs.Client
import utils.MessageHandler

class ShopClient(addr: String?, port: Int) : Client(addr, port) {
    override fun processMessage(message: String?) {
        if (message.isNullOrEmpty()) return
        val msg = MessageHandler.parse(message)
    }

    override fun send(message: String?) {
        super.send(message)
        log(text = "[Client]: ${message!!}")
    }

    fun log(text: String) {
        Launcher.instance!!.controller.log(text)
    }
}