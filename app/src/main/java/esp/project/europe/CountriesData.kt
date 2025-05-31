package esp.project.europe

object CountriesData {
    private val countries: List<Country> = listOf(
        Country("Albania", R.drawable.albania, "Tirana", 2877797),
        Country("Andorra", R.drawable.andorra, "Andorra la Vella", 79824),
        Country("Austria", R.drawable.austria, "Vienna", 8917205),
        Country("Belarus", R.drawable.belarus, "Minsk", 9449323),
        Country("Belgium", R.drawable.belgium, "Brussels", 11686162),
        Country("Bosnia and Herzegovina", R.drawable.bosnia_and_herzegovina, "Sarajevo", 3280819),
        Country("Bulgaria", R.drawable.bulgaria, "Sofia", 6721525),
        Country("Croatia", R.drawable.croatia, "Zagreb", 3845459),
        Country("Czech Republic", R.drawable.czech_republic, "Prague", 10510785),
        Country("Denmark", R.drawable.denmark, "Copenhagen", 5910914),
        Country("England", R.drawable.england, "London", 56400452),
        Country("Estonia", R.drawable.estonia, "Tallinn", 1322766),
        Country("Europe", R.drawable.europe, "Brussels", 745173769),
        Country("Finland", R.drawable.finland, "Helsinki", 5540745),
        Country("France", R.drawable.france, "Paris", 68042591),
        Country("Georgia", R.drawable.georgia, "Tbilisi", 3729500),
        Country("Germany", R.drawable.germany, "Berlin", 83149300),
        Country("Greece", R.drawable.greece, "Athens", 10341133),
        Country("Greenland", R.drawable.greenland, "Nuuk", 56483),
        Country("Hungary", R.drawable.hungary, "Budapest", 9634664),
        Country("Iceland", R.drawable.iceland, "Reykjavík", 376248),
        Country("Ireland", R.drawable.ireland, "Dublin", 5231340),
        Country("Italy", R.drawable.italy, "Rome", 58870762),
        Country("Ladinia", R.drawable.ladinia, "Sèn Jan", 38026),
        Country("Kosovo", R.drawable.kosovo, "Pristina", 1831000),
        Country("Latvia", R.drawable.latvia, "Riga", 1843885),
        Country("Liechtenstein", R.drawable.liechtenstein, "Vaduz", 38949),
        Country("Lithuania", R.drawable.lithuania, "Vilnius", 2790969),
        Country("Luxembourg", R.drawable.luxembourg, "Luxembourg", 672050),
        Country("Malta", R.drawable.malta, "Valletta", 519562),
        Country("Moldova", R.drawable.moldova, "Chișinău", 2535286),
        Country("Monaco", R.drawable.monaco, "Monaco", 39242),
        Country("Montenegro", R.drawable.montenegro, "Podgorica", 619211),
        Country("Netherlands", R.drawable.netherlands, "Amsterdam", 17636000),
        Country("North Macedonia", R.drawable.north_macedonia, "Skopje", 2083821),
        Country("Norway", R.drawable.norway, "Oslo", 5487912),
        Country("Poland", R.drawable.poland, "Warsaw", 37958138),
        Country("Portugal", R.drawable.portugal, "Lisbon", 10305564),
        Country("Romania", R.drawable.romania, "Bucharest", 19016447),
        Country("Scotland", R.drawable.scotland, "Edinburgh", 5463300),
        Country("Serbia", R.drawable.serbia, "Belgrade", 6744976),
        Country("Slovakia", R.drawable.slovakia, "Bratislava", 5456362),
        Country("Slovenia", R.drawable.slovenia, "Ljubljana", 2118679),
        Country("Spain", R.drawable.spain, "Madrid", 47615034),
        Country("Sweden", R.drawable.sweden, "Stockholm", 10542336),
        Country("Switzerland", R.drawable.switzerland, "Bern", 8796669),
        Country("Ukraine", R.drawable.ukraine, "Kyiv", 37079000),
        Country("United Kingdom", R.drawable.united_kingdom, "London", 67886011),
        Country("Vatican City", R.drawable.vatican_city, "Vatican City", 764),
        Country("Veneto", R.drawable.veneto, "Venexia", 4858193),
        Country("Wales", R.drawable.wales, "Cardiff", 3136000)
    )

    fun getCountries(): List<Country> {
        return countries
    }

    fun getCountryByName(name: String): Country? {
        return countries.find { it.name == name }
    }
}