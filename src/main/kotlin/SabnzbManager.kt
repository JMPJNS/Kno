import com.beust.klaxon.Klaxon
import models.SabCategories
import models.SabNZBObj
import models.SabQueue
import java.net.URL

class SabnzbManager(private val url: String, private val apiKey: String) {
    fun getStatus(): SabQueue? {
        val jsonStr = URL("$url?output=json&apikey=$apiKey&mode=queue").readText()
        return Klaxon().parse<SabQueue>(jsonStr)
    }

    fun getCategories(): SabCategories? {
        val jsonStr = URL("$url?output=json&apikey=$apiKey&mode=get_cats").readText()
        return Klaxon().parse<SabCategories>(jsonStr)
    }

    fun addNzb(nzbUrl: String, category: String = "*", priority: Int = 0, postProcessing: Int = -1): SabNZBObj? {
        val jsonStr = URL("$url?output=json&apikey=$apiKey&mode=addurl&name=$nzbUrl&priority=$priority&pp=$postProcessing").readText()
        return Klaxon().parse<SabNZBObj>(jsonStr)
    }
}