package esp.project.europe

data class Country(
    val name: String,
    val flag: Int,
    val capital: String,
    val population: Int,
    val area: Int=0,
    val callingCode: String,
    val currency: String
    )
