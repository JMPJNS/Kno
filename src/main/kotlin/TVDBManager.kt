import com.beust.klaxon.JsonObject
import com.beust.klaxon.Klaxon
import com.google.gson.Gson
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.post
import kotlinx.coroutines.*
import models.SabNZBObj
import models.TVDBSearch
import models.Token
import java.net.URI
import java.net.URL
import java.net.URLEncoder
import java.util.*
import kotlin.collections.HashMap
import kotlin.concurrent.schedule

class TVDBManager(private val apiKey: String) {
    private val url = "https://api.thetvdb.com"
    private var JWT = getJWT()

    fun searchShow(name: String) = runBlocking {
        val res: String = HttpClient().use { client ->
            client.get(encodeURL("$url/search/series?name=$name")) {
                headers["Authorization"] = "Bearer $JWT"
            }
        }
        return@runBlocking Klaxon().parse<TVDBSearch>(res)
    }

    fun getEpisodes(showId: Int) = runBlocking {
        val res: String = HttpClient().use { client ->
            client.get(encodeURL("$url/series/$showId/episodes/summary")) {
                headers["Authorization"] = "Bearer $JWT"
            }
        }
        return@runBlocking Klaxon().parse<TVDBSearch>(res)
    }


    private suspend fun updateJWT(token: String? = null) {
        val jwtString: String = HttpClient().use { client ->
            client.get("$url/refresh_token") {
                headers["Authorization"] = "Bearer ${token?:JWT}"
            }
        }
        val jwt = Klaxon().parse<Token>(jwtString) ?: throw Error("jwt is null")

        JWT = jwt.token

        delay(1000 * 60 * 60 * 10)
        updateJWT()
    }

    private fun getJWT(): String = runBlocking {
        val reqMap = HashMap<String, String>()
        reqMap["apikey"] = apiKey

        val jwtString: String = HttpClient().use { client ->
            client.post("$url/login") {
                headers["Content-Type"] = "application/json"
                body = Gson().toJson(reqMap).toString()
            }
        }


        val jwt = Klaxon().parse<Token>(jwtString) ?: throw Error("jwt is null")

        GlobalScope.launch {
            updateJWT(jwt.token)
        }

        return@runBlocking jwt.token
    }
}