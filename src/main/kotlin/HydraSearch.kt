import com.beust.klaxon.Klaxon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import models.HydraTVSearch
import java.net.URL
import java.net.URLEncoder

class HydraSearch(private val url: String, private val apiKey: String) {
    suspend fun SearchTV(query: String) = withContext(Dispatchers.IO) {
        val encoded = URLEncoder.encode(query, "utf-8")
        val jsonStr = URL("$url?apikey=$apiKey&t=tvsearch&q=$encoded&o=JSON").readText()
        return@withContext Klaxon().parse<HydraTVSearch>(jsonStr)
    }
}