package client

import Launcher
import prefabs.Client

class ShopClient(addr: String?, port: Int) : Client(addr, port) {
    override fun processMessage(message: String?) {
        if (message.isNullOrEmpty()) return
        log(text = "[Server]: $message")
    }

    override fun send(message: String?) {
        super.send(message)
        log(text = "[Client]: ${message!!.toUpperCase()}")
    }

    private fun log(text: String) {
        Launcher.instance!!.controller.log(text)
    }
}