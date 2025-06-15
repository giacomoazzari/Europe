package esp.project.europe

// Data class for creating country objects
data class Country(
    val name: String,
    val flag: Int,
    val capital: String,
    val population: Int,
    val area: Int=0, // Default value for area is 0
    val callingCode: String,
    val currency: String,
    val anthem: String
    )
