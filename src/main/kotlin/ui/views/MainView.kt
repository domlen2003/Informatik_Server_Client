package ui.views

import Launcher
import javafx.geometry.Pos
import javafx.scene.control.TextField
import javafx.scene.paint.Color
import javafx.scene.paint.Paint
import tornadofx.*
import utils.MessageContainer
import utils.MessageHandler


class MainView : View() {
    var controller = Launcher.instance!!.controller

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
                            button("Ping") {
                                fitToParentSize()
                                addClass(MyStyle.tackyButton)
                                action {
                                    sendToServer(MessageContainer("ping", listOf()))
                                }
                            }
                            button("Test2") {
                                fitToParentSize()
                                addClass(MyStyle.tackyButton)
                                action {

                                }
                            }
                            button("Test3") {
                                fitToParentSize()
                                addClass(MyStyle.tackyButton)
                                action {

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

    fun sendToServer(message: MessageContainer) {
        Launcher.instance!!.client.send(
            MessageHandler.create(message)
        )
    }

    fun sendToServer(message: String) {
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
            endMargin = 5.px
            maxHeight = 15.px
            fontSize = 15.px
            borderColor += box(Paint.valueOf("Green"))
            borderWidth += box(2.px)
            baseColor = Color.LIGHTGREY
        }
        list {
            textFill = Color.RED
        }
    }
}