import client.ShopClient
import javafx.scene.control.TextField
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import server.ShopServer
import tornadofx.*

fun main(args: Array<String>) {
    launch<Launcher>(args)
}

class Launcher : App(MyView::class) {
    init {
        ShopServer(port = 420)
        val client = ShopClient(port = 420, addr = "")
        client.send("ping")
    }
}

class MyView : View() {
    var firstNameField: TextField by singleAssign()
    var lastNameField: TextField by singleAssign()
    override val root = VBox()

    init {
        with(root) {
            hbox {
                label("First Name")
                firstNameField = textfield()
            }
            hbox {
                label("Last Name")
                lastNameField = textfield()
            }
            button("LOGIN") {
                useMaxWidth = true
                action {
                    println("Logging in as ${firstNameField.text} ${lastNameField.text}")
                }
            }
        }
    }
}
