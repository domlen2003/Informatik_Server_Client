package client

import prefabs.Client
import utils.MessageHandler

class ShopClient(addr: String?, port: Int) : Client(addr, port) {
    override fun processMessage(message: String?) {
        if(message.isNullOrEmpty()) return
        val msg = MessageHandler.parse(message)
    }
}