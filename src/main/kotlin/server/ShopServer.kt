package server

import prefabs.Server
import utils.MessageHandler

class ShopServer(port: Int) : Server(port) {
    override fun processMessage(clientIp: String?, clientPort: Int, message: String?) {
        if (message.isNullOrEmpty()) return
        val msg = MessageHandler.parse(message)
        when (msg.invoke) {
            "ping" -> {
                this.send(clientIp,clientPort,"pong")
            }
        }
    }
}