package ui.views

import Launcher
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.geometry.Pos
import javafx.scene.control.TextField
import javafx.scene.paint.Color
import javafx.scene.paint.Paint
import tornadofx.*
import utils.MessageContainer
import utils.MessageHandler


class MainView : View() {
    private var controller = Launcher.instance!!.controller

    private val sizes = FXCollections.observableArrayList(
        "S",
        "M",
        "L",
        "XL"
    )
    private val selectedSize = SimpleStringProperty()
    private val colors = FXCollections.observableArrayList(
        "Schwarz",
        "Weiß"
    )
    private val selectedColor = SimpleStringProperty()
    private val answers = FXCollections.observableArrayList(
        "Ja",
        "Nein"
    )
    private val selectedAnswer = SimpleStringProperty()

    override val root = stackpane {
        borderpane {
            prefHeight = 700.0
            prefWidth = 600.0
            top = scrollpane {
                label("My items")
                listview(controller.values) {
                    prefHeight = 300.0
                    prefWidth = 580.0
                    addClass(MyStyle.list)
                }
            }
            left = hbox {
                fitToParentSize()
                tabpane {
                    fitToParentSize()
                    minWidth = 600.0
                    padding = insets(0)
                    tab("Easy Interaction") {
                        tabMinWidth = 250.0
                        isClosable = false
                        vbox {
                           /* button("Ping") {
                                fitToParentSize()
                                addClass(MyStyle.tackyButton)
                                action {
                                    sendToServer(MessageContainer("ping", listOf()))
                                }
                            }*/
                            button("Lager") {
                                fitToParentSize()
                                addClass(MyStyle.tackyButton)
                                action {
                                    sendToServer(MessageContainer("lager", listOf()))
                                }
                            }
                            button {
                                fitToParentSize()
                                addClass(MyStyle.tackyButton)
                                hbox {
                                    alignment = Pos.CENTER
                                    text("T-Shirt ")
                                    combobox(selectedSize, sizes)
                                    combobox(selectedColor, colors)
                                }
                                action {
                                    if (selectedSize.value != null && selectedColor.value != null) {
                                        sendToServer(
                                            MessageContainer(
                                                "tshirt",
                                                listOf(selectedSize.value, selectedColor.value)
                                            )
                                        )
                                    }
                                }
                            }
                            button {
                                fitToParentSize()
                                addClass(MyStyle.tackyButton)
                                hbox {
                                    alignment = Pos.CENTER
                                    text("Bestätigen ")
                                    combobox(selectedAnswer, answers)
                                }
                                action {
                                    if (selectedAnswer.value != null) {
                                        sendToServer(
                                            MessageContainer(
                                                "bestätigung",
                                                listOf(selectedAnswer.value)
                                            )
                                        )
                                    }
                                }
                            }
                            button {
                                fitToParentSize()
                                addClass(MyStyle.tackyButton)
                                hbox {
                                    alignment = Pos.CENTER
                                    text("Abmelden")
                                }
                                action {
                                    sendToServer(
                                        MessageContainer(
                                            "abmelden",
                                            listOf()
                                        )
                                    )
                                }
                            }
                        }

                    }
                    tab("Raw Messages") {
                        tabMinWidth = 250.0
                        isClosable = false
                        var message: TextField by singleAssign()
                        stackpane {
                            vbox {
                                alignment = Pos.BASELINE_CENTER
                                paddingTop = 20
                                label("Raw Message")
                                message = textfield {
                                    maxWidth = 400.0
                                }
                            }
                            button("SEND") {
                                action {
                                    sendToServer(message.text)
                                }
                            }

                        }
                    }
                }
            }
        }
    }

    private fun sendToServer(message: MessageContainer) {
        Launcher.instance!!.client.send(
            MessageHandler.create(message)
        )
    }

    private fun sendToServer(message: String) {
        Launcher.instance!!.client.send(
            message
        )
    }
}

class MyStyle : Stylesheet() {

    companion object {
        val tackyButton by cssclass()
        val list by cssclass()
    }

    init {
        tackyButton {
            startMargin = 5.px
            endMargin = 5.px
            maxHeight = 40.px
            prefHeight = maxHeight
            fontSize = 15.px
            borderColor += box(Paint.valueOf("Grey"))
            borderWidth += box(2.px)
            baseColor = Color.LIGHTGREY
        }
        list {
            textFill = Color.RED
        }
    }
}