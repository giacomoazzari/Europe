package esp.project.europe

enum class Origin {
    LIST, MAP
}

interface OnCountrySelectedListener {
    fun onCountrySelected(country: Country?, provenience: Origin)
}