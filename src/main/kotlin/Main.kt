import com.beust.klaxon.Parser
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.ByteArrayInputStream

fun main() {
    doTheThing("One Piece", null, 936)
}

fun doTheThing(showName: String, season: Number?, episode: Number) = runBlocking {
    val sab = SabnzbManager("http://sab.milim.jmp/sabnzbd/api", System.getenv("SAB_KEY"))
    val search = NewznabSearch("http://hydra.milim.jmp/api", System.getenv("HYDRA_KEY"))
    val tvdb = TVDBSearch(System.getenv("TVDB_KEY"))
    launch {
    val hydraRes = search.SearchTV("$showName${if (season != null) " $season" else ""} $episode")

    if (hydraRes?.channel?.item == null) {
        return@launch
    }

    val nzb = sab.addNzb(hydraRes.channel.item[0].link, priority = -2)
        // SABnzbd_nzo_kqfqrxc8


    val status = sab.getCategories()
    println(status)

    }
}

fun parse(str: String) : Any? {
    //    val res = parse(jsonStr) as JsonObject
    val stream = ByteArrayInputStream(str.toByteArray(Charsets.UTF_8))
    return Parser.default().parse(stream)
}
