package models

import com.beust.klaxon.Json

// https://newznab.readthedocs.io/en/latest/misc/api/#tv-search

data class HydraTVSearch(
    val channel: Channel
)

data class Channel(
    val category: Category,
    val generator: String,
    val item: List<Item>,
    val link: String,
    val response: Response,
    val title: String,
    val webMaster: String
)

class Category(
)

data class Item(
    val attr: List<Attr>,
    val category: String,
    val comments: String,
    val description: String,
    val enclosure: Enclosure,
    val guid: String,
    val id: String,
    val link: String,
    val pubDate: String,
    val title: String
)

data class Response(
    @Json(name = "@attributes")
    val attributes: AttributesXX
)

data class Attr(
    @Json(name = "@attributes")
    val attributes: Attributes
)

data class Enclosure(
    @Json(name = "@attributes")
    val attributes: AttributesX
)

data class Attributes(
    val name: String,
    val value: String
)

data class AttributesX(
    val length: String,
    val type: String,
    val url: String
)

data class AttributesXX(
    val offset: String,
    val total: String
)