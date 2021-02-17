package server


import prefabs.Server
import utils.IpHandler
import utils.MessageHandler
import java.util.*

class ShopServer(port: Int) : Server(port) {

    private val clients: HashMap<String, ArrayList<String>> = HashMap()

    override fun processNewConnection(clientIp: String?, clientPort: Int) {
        super.processNewConnection(clientIp, clientPort)
        this.send(
            clientIp,
            clientPort,
            "Willkommen! Wählen Sie eine Größe und eine Farbe für ihr T-Shirt."
        )
    }

    override fun processMessage(clientIp: String?, clientPort: Int, message: String?) {
        if (message.isNullOrEmpty()) return
        val msg = MessageHandler.parse(message)
        val fullIp = IpHandler.create(IpHandler.IpContainer(clientIp, clientPort))
        val messageStack = getMessageStack(fullIp)

        when (msg.invoke.toLowerCase()) {
            "ping" -> {
                this.send(
                    clientIp,
                    clientPort,
                    "PONG"
                )
                messageStack.add(msg.invoke)
            }
            "tshirt" -> {
                if (msg.args.size >= 2) {
                    if (!msg.args[0]!!.toLowerCase().matches(Regex("(s|m|l|xl)"))) {
                        this.send(
                            clientIp,
                            clientPort,
                            "Es sind nur die Größen S, M, L und Xl vorhanden."
                        )
                    } else if (!msg.args[1]!!.toLowerCase().matches(Regex("(weiß|schwarz)"))) {
                        this.send(
                            clientIp,
                            clientPort,
                            "Es sind nur die Farben Weiß und Schwarz vorhanden."
                        )
                    } else {
                        this.send(
                            clientIp,
                            clientPort,
                            "Die Größe ist ${msg.args[0]}, die Farbe ist ${msg.args[1]} und es kostet 19,99 Euro. Bitte Bestätigen Sie die Bestellung."
                        )
                        messageStack.add(msg.invoke)
                    }
                } else {
                    this.send(
                        clientIp,
                        clientPort,
                        "Bitte geben sie Größe und Farbe an."
                    )
                }
            }
            "bestätigung" -> {
                if (!messageStack.contains("tshirt")) {
                    this.send(
                        clientIp,
                        clientPort,
                        "Wählen sie zunächst ein TShirt!"
                    )
                } else if (msg.args.isNotEmpty()) {
                    when (msg.args[0]!!.toLowerCase()) {
                        "ja" -> {
                            this.send(
                                clientIp,
                                clientPort,
                                "Danke Für ihre Bestellung. Auf Wiedersehen!"
                            )
                            this.closeConnection(clientIp, clientPort)
                            clients.remove(fullIp)
                        }
                        "nein" -> {
                            this.send(
                                clientIp,
                                clientPort,
                                "Auf Wiedersehen!"
                            )
                            this.closeConnection(clientIp, clientPort)
                            clients.remove(fullIp)
                        }
                    }
                } else {
                    this.send(
                        clientIp,
                        clientPort,
                        "Bitte bestätigen sie Ihre Bestellung mit ja oder nein."
                    )
                }
            }
            "abmelden" -> {
                this.send(
                    clientIp,
                    clientPort,
                    "Auf Wiedersehen!"
                )
                this.closeConnection(clientIp, clientPort)
                clients.remove(fullIp)
            }
            else -> {
                this.send(
                    clientIp,
                    clientPort,
                    "Bitte korrigieren Sie ihre Eingabe."
                )
            }
        }
        print(messageStack)
    }

    private fun getMessageStack(fullIp: String): ArrayList<String> {
        val messageStack: ArrayList<String>

        if (clients.keys.contains(fullIp)) {
            messageStack = clients[fullIp]!!
        } else {
            messageStack = ArrayList<String>()
            clients[fullIp] = messageStack
        }
        return messageStack
    }
}
