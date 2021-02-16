package ui.controller

import javafx.application.Platform
import javafx.collections.FXCollections
import tornadofx.Controller
import java.time.LocalDateTime

class UIController : Controller() {
    val values = FXCollections.observableArrayList(String())!!

    fun log(text: String) {
        Platform.runLater {
            values.add("$text   Time:${LocalDateTime.now()}")
        }
    }

}
