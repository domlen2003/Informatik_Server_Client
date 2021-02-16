package server


import Launcher
import prefabs.Server
import utils.MessageHandler

class ShopServer(port: Int) : Server(port) {
    override fun processMessage(clientIp: String?, clientPort: Int, message: String?) {
        if (message.isNullOrEmpty()) return
        val msg = MessageHandler.parse(message)
        when (msg.invoke) {
            "ping" -> {
                this.send(clientIp, clientPort, "pong")
            }
        }
    }

    override fun send(clientIP: String?, clientPort: Int, message: String?) {
        super.send(clientIP, clientPort, message)
        log(text = "[Client]: ${message!!}")
    }

    fun log(text: String) {
        Launcher.instance!!.controller.log(text)
    }
}
