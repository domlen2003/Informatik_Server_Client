package utils

class IpHandler {
    companion object {
        fun create(ip: IpContainer): String {
            var raw = ""
            raw += ip.ip
            raw += ":${ip.port}"
            return raw
        }
    }

    data class IpContainer(
        val ip: String?,
        val port: Int
    )
}