package esp.project.europe

object CountriesData {
    private val countries: List<Country> = listOf(
        Country("Albania", R.drawable.albania, "Tirana", 2877797, 28748, "+355", "Lek"),
        Country("Andorra", R.drawable.andorra, "Andorra la Vella", 79824, 468, "+376", "Euro"),
        Country("Austria", R.drawable.austria, "Vienna", 8917205, 83879, "+43", "Euro"),
        Country("Belarus", R.drawable.belarus, "Minsk", 9449323, 207600, "+375", "Belarusian ruble"),
        Country("Belgium", R.drawable.belgium, "Brussels", 11686162, 30528, "+32", "Euro"),
        Country("Bosnia and Herzegovina", R.drawable.bosnia_and_herzegovina, "Sarajevo", 3280819, 51197, "+387", "Convertible mark"),
        Country("Bulgaria", R.drawable.bulgaria, "Sofia", 6721525, 110994, "+359", "Lev"),
        Country("Croatia", R.drawable.croatia, "Zagreb", 3845459, 56594, "+385", "Euro"),
        Country("Czech Republic", R.drawable.czech_republic, "Prague", 10510785, 78866, "+420", "Czech koruna"),
        Country("Denmark", R.drawable.denmark, "Copenhagen", 5910914, 42933, "+45", "Danish krona"),
        Country("England", R.drawable.england, "London", 56400452, 130395, "+44", "Pound sterling"),
        Country("Estonia", R.drawable.estonia, "Tallinn", 1322766, 45227, "+372", "Euro"),
        Country("Europe", R.drawable.europe, "Brussels", 745173769, 10180000, "", "Varies"),
        Country("Finland", R.drawable.finland, "Helsinki", 5540745, 338424, "+358", "Euro"),
        Country("France", R.drawable.france, "Paris", 68042591, 551695, "+33", "Euro"),
        Country("Georgia", R.drawable.georgia, "Tbilisi", 3729500, 69700, "+995", "Lari"),
        Country("Germany", R.drawable.germany, "Berlin", 83149300, 357022, "+49", "Euro"),
        Country("Greece", R.drawable.greece, "Athens", 10341133, 131957, "+30", "Euro"),
        Country("Greenland", R.drawable.greenland, "Nuuk", 56483, 2166086, "+299", " Danish krone"),
        Country("Hungary", R.drawable.hungary, "Budapest", 9634664, 93030, "+36", "Hungarian forint"),
        Country("Iceland", R.drawable.iceland, "Reykjavík", 376248, 103000, "+354", "Icelandic crona"),
        Country("Ireland", R.drawable.ireland, "Dublin", 5231340, 70273, "+353", "Euro"),
        Country("Italy", R.drawable.italy, "Rome", 58870762, 301340, "+39", "Euro"),
        Country("Ladinia", R.drawable.ladinia, "Sèn Jan", 38026, 1400, "+39", "Euro"),
        Country("Kosovo", R.drawable.kosovo, "Pristina", 1831000, 10887, "+383", "Euro"),
        Country("Latvia", R.drawable.latvia, "Riga", 1843885, 64589, "+371", "Euro"),
        Country("Liechtenstein", R.drawable.liechtenstein, "Vaduz", 38949, 160, "+423", "Swiss franc"),
        Country("Lithuania", R.drawable.lithuania, "Vilnius", 2790969, 65300, "+370", "Euro"),
        Country("Luxembourg", R.drawable.luxembourg, "Luxembourg", 672050, 2586, "+352", "Euro"),
        Country("Malta", R.drawable.malta, "Valletta", 519562, 316, "+356", "Euro"),
        Country("Moldova", R.drawable.moldova, "Chișinău", 2535286, 33846, "+373", "Moldovan leu"),
        Country("Monaco", R.drawable.monaco, "Monaco", 39242, 202, "+377", "Euro"),
        Country("Montenegro", R.drawable.montenegro, "Podgorica", 619211, 13812, "+382", "Euro"),
        Country("Netherlands", R.drawable.netherlands, "Amsterdam", 17636000, 41543, "+31", "Euro"),
        Country("North Macedonia", R.drawable.north_macedonia, "Skopje", 2083821, 25713, "+389", "Macedonian denar"),
        Country("Norway", R.drawable.norway, "Oslo", 5487912, 385207, "+47", "Norwegian krone"),
        Country("Poland", R.drawable.poland, "Warsaw", 37958138, 312696, "+48", "Zloty"),
        Country("Portugal", R.drawable.portugal, "Lisbon", 10305564, 92212, "+351", "Euro"),
        Country("Romania", R.drawable.romania, "Bucharest", 19016447, 238397, "+40", "Romanian Leu"),
        Country("Scotland", R.drawable.scotland, "Edinburgh", 5463300, 77933, "+44", "Pound sterling"),
        Country("Serbia", R.drawable.serbia, "Belgrade", 6744976, 77474,"+381", "Serbian dinar"),
        Country("Slovakia", R.drawable.slovakia, "Bratislava", 5456362, 49035, "+421", "Euro"),
        Country("Slovenia", R.drawable.slovenia, "Ljubljana", 2118679, 20273, "+386", "Euro"),
        Country("Spain", R.drawable.spain, "Madrid", 47615034, 505990, "+34", "Euro"),
        Country("Sweden", R.drawable.sweden, "Stockholm", 10542336, 450295, "+46", "Sweden krone"),
        Country("Switzerland", R.drawable.switzerland, "Bern", 8796669, 41285, "+41", "Swiss franc"),
        Country("Ukraine", R.drawable.ukraine, "Kyiv", 37079000, 603628, "+380", "Grivnia"),
        Country("United Kingdom", R.drawable.united_kingdom, "London", 67886011, 243610, "+44", "Pound sterling"),
        Country("Vatican City", R.drawable.vatican_city, "Vatican City", 764, 0, "+379", "Euro"),
        Country("Veneto", R.drawable.veneto, "Venexia", 4858193, 18399,"+39", "Euro"),
        Country("Wales", R.drawable.wales, "Cardiff", 3136000, 20779, "+44", "Pound sterling")
    )


    fun getCountries(): List<Country> {
        return countries
    }

    fun getCountryByName(name: String): Country? {
        return countries.find { it.name == name }
    }
}