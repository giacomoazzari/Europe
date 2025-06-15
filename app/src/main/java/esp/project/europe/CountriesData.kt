package esp.project.europe

import android.content.Context

object CountriesData {
    private lateinit var countries: List<Country>

    fun initCountries(context: Context) {
        countries= listOf(
            Country(context.getString(R.string.albania), R.drawable.albania, context.getString(R.string.tirana), 2877797, 28748, "+355", context.getString(R.string.lek), "albania"),
            Country(context.getString(R.string.andorra), R.drawable.andorra, context.getString(R.string.andorra_la_vella), 79824, 468, "+376", context.getString(R.string.euro),"andorra"),
            Country(context.getString(R.string.austria), R.drawable.austria, context.getString(R.string.vienna), 8917205, 83879, "+43", context.getString(R.string.euro),"austria"),
            Country(context.getString(R.string.belarus), R.drawable.belarus, context.getString(R.string.minsk), 9449323, 207600, "+375", context.getString(R.string.belarusian_ruble),"belarus"),
            Country(context.getString(R.string.belgium), R.drawable.belgium, context.getString(R.string.brussels), 11686162, 30528, "+32", context.getString(R.string.euro),"belgium"),
            Country(context.getString(R.string.bosnia_and_herzegovina), R.drawable.bosnia_and_herzegovina, context.getString(R.string.sarajevo), 3280819, 51197, "+387", context.getString(R.string.convertible_mark),"bosnia_and_herzegovina"),
            Country(context.getString(R.string.bulgaria), R.drawable.bulgaria, context.getString(R.string.sofia), 6721525, 110994, "+359", context.getString(R.string.lev),"bulgaria"),
            Country(context.getString(R.string.croatia), R.drawable.croatia, context.getString(R.string.zagreb), 3845459, 56594, "+385", context.getString(R.string.euro),"croatia"),
            Country(context.getString(R.string.czech_republic), R.drawable.czech_republic, context.getString(R.string.prague), 10510785, 78866, "+420", context.getString(R.string.czech_koruna),"czech_republic"),
            Country(context.getString(R.string.denmark), R.drawable.denmark, context.getString(R.string.copenhagen), 5910914, 42933, "+45", context.getString(R.string.danish_krona),"denmark"),
            Country(context.getString(R.string.england), R.drawable.england, context.getString(R.string.london), 56400452, 130395, "+44", context.getString(R.string.pound_sterling),"england"),
            Country(context.getString(R.string.estonia), R.drawable.estonia, context.getString(R.string.tallinn), 1322766, 45227, "+372", context.getString(R.string.euro),"estonia"),
            Country(context.getString(R.string.europe), R.drawable.europe, context.getString(R.string.brussels), 745173769, 10180000, "", context.getString(R.string.varies),"europe"),
            Country(context.getString(R.string.finland), R.drawable.finland, context.getString(R.string.helsinki), 5540745, 338424, "+358", context.getString(R.string.euro),"finland"),
            Country(context.getString(R.string.france), R.drawable.france, context.getString(R.string.paris), 68042591, 551695, "+33", context.getString(R.string.euro),"france"),
            Country(context.getString(R.string.georgia), R.drawable.georgia, context.getString(R.string.tbilisi), 3729500, 69700, "+995", context.getString(R.string.lari),"georgia"),
            Country(context.getString(R.string.germany), R.drawable.germany, context.getString(R.string.berlin), 83149300, 357022, "+49", context.getString(R.string.euro),"germany"),
            Country(context.getString(R.string.greece), R.drawable.greece, context.getString(R.string.athens), 10341133, 131957, "+30", context.getString(R.string.euro),"greece"),
            Country(context.getString(R.string.greenland), R.drawable.greenland, context.getString(R.string.nuuk), 56483, 2166086, "+299", context.getString(R.string.danish_krona),"greenland"),
            Country(context.getString(R.string.hungary), R.drawable.hungary, context.getString(R.string.budapest), 9634664, 93030, "+36", context.getString(R.string.hungarian_forint),"hungary"),
            Country(context.getString(R.string.iceland), R.drawable.iceland, context.getString(R.string.reykjavík), 376248, 103000, "+354", context.getString(R.string.icelandic_crona),"iceland"),
            Country(context.getString(R.string.ireland), R.drawable.ireland, context.getString(R.string.dublin), 5231340, 70273, "+353", context.getString(R.string.euro),"ireland"),
            Country(context.getString(R.string.italy), R.drawable.italy, context.getString(R.string.rome), 58870762, 301340, "+39", context.getString(R.string.euro),"italy"),
            Country(context.getString(R.string.ladinia), R.drawable.ladinia, context.getString(R.string.sen_jan), 38026, 1400, "+39", context.getString(R.string.euro),"ladinia"),
            Country(context.getString(R.string.kosovo), R.drawable.kosovo, context.getString(R.string.pristina), 1831000, 10887, "+383", context.getString(R.string.euro),"kosovo"),
            Country(context.getString(R.string.latvia), R.drawable.latvia, context.getString(R.string.riga), 1843885, 64589, "+371", context.getString(R.string.euro),"latvia"),
            Country(context.getString(R.string.liechtenstein), R.drawable.liechtenstein, context.getString(R.string.vaduz), 38949, 160, "+423", context.getString(R.string.swiss_franc),"liechtenstein"),
            Country(context.getString(R.string.lithuania), R.drawable.lithuania, context.getString(R.string.vilnius), 2790969, 65300, "+370", context.getString(R.string.euro),"lithuania"),
            Country(context.getString(R.string.luxembourg), R.drawable.luxembourg, context.getString(R.string.luxembourg), 672050, 2586, "+352", context.getString(R.string.euro),"luxembourg"),
            Country(context.getString(R.string.malta), R.drawable.malta, context.getString(R.string.valletta), 519562, 316, "+356", context.getString(R.string.euro),"malta"),
            Country(context.getString(R.string.moldova), R.drawable.moldova, context.getString(R.string.chisinau), 2535286, 33846, "+373", context.getString(R.string.moldovan_leu),"moldova"),
            Country(context.getString(R.string.monaco), R.drawable.monaco, context.getString(R.string.monaco), 39242, 202, "+377", context.getString(R.string.euro),"monaco"),
            Country(context.getString(R.string.montenegro), R.drawable.montenegro, context.getString(R.string.podgorica), 619211, 13812, "+382", context.getString(R.string.euro),"montenegro"),
            Country(context.getString(R.string.netherlands), R.drawable.netherlands, context.getString(R.string.amsterdam), 17636000, 41543, "+31", context.getString(R.string.euro),"netherlands"),
            Country(context.getString(R.string.north_macedonia), R.drawable.north_macedonia, context.getString(R.string.skopje), 2083821, 25713, "+389", context.getString(R.string.macedonian_denar),"north_macedonia"),
            Country(context.getString(R.string.norway), R.drawable.norway, context.getString(R.string.oslo), 5487912, 385207, "+47", context.getString(R.string.norwegian_krone),"norway"),
            Country(context.getString(R.string.poland), R.drawable.poland, context.getString(R.string.warsaw), 37958138, 312696, "+48", context.getString(R.string.zloty),"poland"),
            Country(context.getString(R.string.portugal), R.drawable.portugal, context.getString(R.string.lisbon), 10305564, 92212, "+351", context.getString(R.string.euro),"portugal"),
            Country(context.getString(R.string.romania), R.drawable.romania, context.getString(R.string.bucharest), 19016447, 238397, "+40", context.getString(R.string.romanian_leu),"romania"),
            Country(context.getString(R.string.scotland), R.drawable.scotland, context.getString(R.string.edinburgh), 5463300, 77933, "+44", context.getString(R.string.pound_sterling),"scotland"),
            Country(context.getString(R.string.serbia), R.drawable.serbia, context.getString(R.string.belgrade), 6744976, 77474,"+381", context.getString(R.string.serbian_dinar),"serbia"),
            Country(context.getString(R.string.slovakia), R.drawable.slovakia, context.getString(R.string.bratislava), 5456362, 49035, "+421", context.getString(R.string.euro),"slovakia"),
            Country(context.getString(R.string.slovenia), R.drawable.slovenia, context.getString(R.string.ljubljana), 2118679, 20273, "+386", context.getString(R.string.euro),"slovenia"),
            Country(context.getString(R.string.spain), R.drawable.spain, context.getString(R.string.madrid), 47615034, 505990, "+34", context.getString(R.string.euro),"spain"),
            Country(context.getString(R.string.sweden), R.drawable.sweden, context.getString(R.string.stockholm), 10542336, 450295, "+46", context.getString(R.string.sweden_krone),"sweden"),
            Country(context.getString(R.string.switzerland), R.drawable.switzerland, context.getString(R.string.bern), 8796669, 41285, "+41", context.getString(R.string.swiss_franc),"switzerland"),
            Country(context.getString(R.string.ukraine), R.drawable.ukraine, context.getString(R.string.kyiv), 37079000, 603628, "+380", context.getString(R.string.grivnia),"ukraine"),
            Country(context.getString(R.string.united_kingdom), R.drawable.united_kingdom, context.getString(R.string.london), 67886011, 243610, "+44", context.getString(R.string.pound_sterling),"united_kingdom"),
            Country(context.getString(R.string.vatican_city), R.drawable.vatican_city, context.getString(R.string.vatican_city), 764, 0, "+379", context.getString(R.string.euro),"vatican_city"),
            Country(context.getString(R.string.veneto), R.drawable.veneto, context.getString(R.string.venexia), 4858193, 18399,"+39", context.getString(R.string.euro),"veneto"),
            Country(context.getString(R.string.wales), R.drawable.wales, context.getString(R.string.cardiff), 3136000, 20779, "+44", context.getString(R.string.pound_sterling),"wales")
        )
    }

    fun getCountries(): List<Country> {
        check(::countries.isInitialized) { "CountriesData not initialized. Call initCountries(context) first." }
        return countries
    }

    fun getCountryByName(name: String): Country? {
        check(::countries.isInitialized) { "CountriesData not initialized. Call initCountries(context) first." }
        return countries.find { it.name == name }
    }

}