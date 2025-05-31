package esp.project.europe

object CountriesData {
    private val countries: List<Country> = listOf(
        Country("Albania", R.drawable.albania, "Tirana"),
        Country("Andorra", R.drawable.andorra, "Andorra la Vella"),
        Country("Austria", R.drawable.austria, "Vienna"),
        Country("Belarus", R.drawable.belarus, "Minsk"),
        Country("Belgium", R.drawable.belgium, "Brussels"),
        Country("Bosnia and Herzegovina", R.drawable.bosnia_and_herzegovina, "Sarajevo"),
        Country("Bulgaria", R.drawable.bulgaria, "Sofia"),
        Country("Croatia", R.drawable.croatia, "Zagreb"),
        Country("Czech Republic", R.drawable.czech_republic, "Prague"),
        Country("Denmark", R.drawable.denmark, "Copenhagen"),
        Country("England", R.drawable.england, "London"),
        Country("Estonia", R.drawable.estonia, "Tallinn"),
        Country("Europe", R.drawable.europe, "Brussels"),
        Country("Finland", R.drawable.finland, "Helsinki"),
        Country("France", R.drawable.france, "Paris"),
        Country("Georgia", R.drawable.georgia, "Tbilisi"),
        Country("Germany", R.drawable.germany, "Berlin"),
        Country("Greece", R.drawable.greece, "Athens"),
        Country("Greenland", R.drawable.greenland, "Nuuk"),
        Country("Hungary", R.drawable.hungary, "Budapest"),
        Country("Iceland", R.drawable.iceland, "Reykjavik"),
        Country("Ireland", R.drawable.ireland, "Dublin"),
        Country("Italy", R.drawable.italy, "Rome"),
        Country("Ladinia", R.drawable.ladinia, "Sèn Jan"),
        Country("Kosovo", R.drawable.kosovo, "Pristina"),
        Country("Latvia", R.drawable.latvia, "Riga"),
        Country("Liechtenstein", R.drawable.liechtenstein, "Vaduz"),
        Country("Lithuania", R.drawable.lithuania, "Vilnius"),
        Country("Luxembourg", R.drawable.luxembourg, "Luxembourg"),
        Country("Malta", R.drawable.malta, "Valletta"),
        Country("Moldova", R.drawable.moldova, "Chișinău"),
        Country("Monaco", R.drawable.monaco, "Monaco"),
        Country("Montenegro", R.drawable.montenegro, "Podgorica"),
        Country("Netherlands", R.drawable.netherlands, "Amsterdam"),
        Country("North Macedonia", R.drawable.north_macedonia, "Skopje"),
        Country("Norway", R.drawable.norway, "Oslo"),
        Country("Poland", R.drawable.poland, "Warsaw"),
        Country("Portugal", R.drawable.portugal, "Lisbon"),
        Country("Romania", R.drawable.romania, "Bucharest"),
        Country("Scotland", R.drawable.scotland, "Edinburgh"),
        Country("Serbia", R.drawable.serbia, "Belgrade"),
        Country("Slovakia", R.drawable.slovakia, "Bratislava"),
        Country("Slovenia", R.drawable.slovenia, "Ljubljana"),
        Country("Spain", R.drawable.spain, "Madrid"),
        Country("Sweden", R.drawable.sweden, "Stockholm"),
        Country("Switzerland", R.drawable.switzerland, "Bern"),
        Country("Ukraine", R.drawable.ukraine, "Kyiv"),
        Country("United Kingdom", R.drawable.united_kingdom, "London"),
        Country("Vatican City", R.drawable.vatican_city, "Vatican City"),
        Country("Veneto", R.drawable.veneto, "Venezia"),
        Country("Wales", R.drawable.wales, "Cardiff")
    )

    fun getCountries(): List<Country> {
        return countries
    }

    fun getCountryByName(name: String): Country? {
        return countries.find { it.name == name }
    }
}