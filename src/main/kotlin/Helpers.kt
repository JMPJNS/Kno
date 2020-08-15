import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import java.io.ByteArrayInputStream
import java.net.URI
import java.net.URL

fun parse(str: String) : Any? {
    //    val res = parse(jsonStr) as JsonObject
    val stream = ByteArrayInputStream(str.toByteArray(Charsets.UTF_8))
    return Parser.default().parse(stream)
}

fun encodeURL(url: String): String {
    val u = URL(url)
    val uri = URI(u.protocol, u.userInfo, u.host, u.port, u.path, u.query, u.ref)

    return uri.toASCIIString()
}