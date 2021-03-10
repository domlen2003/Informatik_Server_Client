package server


import Launcher
import prefabs.Server
import utils.Artikel
import utils.Kunde
import utils.MessageHandler

class ShopServer(port: Int) : Server(port) {

    private val lager = ArrayList<Artikel>(
        listOf(
            Artikel("Shirt", 19.99),
            Artikel("Hose", 5.99),
            Artikel("Hut", 2.99)
        )
    )

    private val kunden = ArrayList<Kunde>()

    override fun processNewConnection(clientIp: String?, clientPort: Int) {
        super.processNewConnection(clientIp, clientPort)
        kunden.add(Kunde(clientIp))
        this.send(
            clientIp,
            clientPort,
            "Willkommen! Wählen Sie eine Größe und eine Farbe für ihr T-Shirt."
        )
    }

    override fun processMessage(clientIp: String?, clientPort: Int, message: String?) {
        if (message.isNullOrEmpty()) return
        val msg = MessageHandler.parse(message)

        when (msg.invoke.toLowerCase()) {
            "ping" -> {
                this.send(
                    clientIp,
                    clientPort,
                    "PONG"
                )
            }
            "lager" -> {
                for (artikel in lager) {
                    this.send(
                        clientIp,
                        clientPort,
                        "${artikel.artikelName} zu ${artikel.artikelPreis} €"
                    )
                }
            }
            "tshirt" -> {
                if (msg.args.size >= 2) {
                    if (!msg.args[0]!!.toLowerCase().matches(Regex("s|m|l|xl"))) {
                        this.send(
                            clientIp,
                            clientPort,
                            "Es sind nur die Größen S, M, L und Xl vorhanden."
                        )
                    } else if (!msg.args[1]!!.toLowerCase().matches(Regex("weiß|schwarz"))) {
                        this.send(
                            clientIp,
                            clientPort,
                            "Es sind nur die Farben Weiß und Schwarz vorhanden."
                        )
                    } else {
                        bestellungAufnehmen(clientIp, msg.args[0], msg.args[1])
                        this.send(
                            clientIp,
                            clientPort,
                            "Die Größe ist ${msg.args[0]}, die Farbe ist ${msg.args[1]} und es kostet 19,99 Euro. Bitte Bestätigen Sie die Bestellung."
                        )
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
                if (msg.args.isNotEmpty()) {
                    when (msg.args[0]!!.toLowerCase()) {
                        "ja" -> {
                            this.send(
                                clientIp,
                                clientPort,
                                "Danke Für ihre Bestellung. Auf Wiedersehen!"
                            )
                            this.closeConnection(clientIp, clientPort)
                        }
                        "nein" -> {
                            this.send(
                                clientIp,
                                clientPort,
                                "Auf Wiedersehen!"
                            )
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
            }
            else -> {
                this.send(
                    clientIp,
                    clientPort,
                    "Bitte korrigieren Sie ihre Eingabe."
                )
            }
        }
    }

    private fun bestellungAufnehmen(pIPIdnr: String?, pGroesse: String?, pFarbe: String?) {
        log("[Backend] Ein TShirt der Größe $pGroesse, mit der Farbe $pFarbe wurde für $pIPIdnr bestellt.")
    }

    private fun log(text: String) {
        Launcher.instance!!.controller.log(text)
    }
}
