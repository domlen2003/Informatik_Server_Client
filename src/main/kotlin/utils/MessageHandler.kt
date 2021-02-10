package utils

import java.util.*

class MessageHandler {
    companion object {
        fun parse(raw: String): MessageContainer {
            val split = LinkedList(raw.split(" ").toList().dropWhile { it == " " || it == "" })
            val invoke = split[0]
            val args = LinkedList(split.toList().subList(1, split.size))

            return MessageContainer(invoke, args)
        }

        fun create(message: MessageContainer): String {
            var raw = ""
            raw += message.invoke
            for (arg in message.args)
                raw += ":${arg}"

            return raw
        }
    }
}

data class MessageContainer(
    val invoke: String,
    val args: List<String?>
)