package esp.project.europe

enum class ListaEnum {
    LIST, MAP
}

interface OnCountrySelectedListener {
    fun onCountrySelected(country: Country?, provenienza: ListaEnum)
}