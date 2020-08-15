package models

data class TVDBSearch(
    val data: List<Data> = emptyList()
)

data class Data(
    val aliases: List<String> = emptyList(),
    val banner: String = "",
    val firstAired: String = "",
    val id: Int = 0,
    val image: String = "",
    val network: String = "",
    val overview: String = "",
    val poster: String = "",
    val seriesName: String = "",
    val slug: String = "",
    val status: String = ""
)