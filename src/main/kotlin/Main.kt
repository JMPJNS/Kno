import kotlinx.coroutines.runBlocking

fun main() {
    doTheThing("One Piece", null, 936)
}

fun doTheThing(showName: String, season: Number?, episode: Number) = runBlocking {
    val sab = SabnzbManager("http://sab.milim.jmp/sabnzbd/api", System.getenv("SAB_KEY"))
    val search = HydraSearch("http://hydra.milim.jmp/api", System.getenv("HYDRA_KEY"))
    val tvdb = TVDBManager(System.getenv("TVDB_KEY"))
    val hydraRes = search.SearchTV("$showName${if (season != null) " $season" else ""} $episode")

    if (hydraRes?.channel?.item == null) {
        return@runBlocking
    }

//    val tvdbSearch = tvdb.searchShow(showName)

    val episodes = tvdb.getEpisodes(81797)

//    val nzb = sab.addNzb(hydraRes.channel.item[0].link, priority = -2)
        // SABnzbd_nzo_kqfqrxc8


    val status = sab.getCategories()
    println(status)
}

