import client.ShopClient
import ui.controller.UIController
import javafx.stage.Stage
import server.ShopServer
import tornadofx.App
import tornadofx.launch
import ui.views.MainView
import ui.views.MyStyle

fun main(args: Array<String>) {
    launch<Launcher>()
}

class Launcher : App(MainView::class, MyStyle::class) {
    val controller = UIController()
    val server = ShopServer(port = 49160)
    val client = ShopClient(port = 49160, addr = "")
    override fun start(stage: Stage) {
        with(stage) {
            width = 600.0
            height = 600.0
        }
        super.start(stage)
    }

    init {
        instance = this
    }

    companion object {
        var instance: Launcher? = null
    }
}



