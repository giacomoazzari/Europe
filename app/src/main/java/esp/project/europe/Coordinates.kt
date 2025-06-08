package esp.project.europe

import org.osmdroid.util.GeoPoint

object Coordinates {
    private val points : List<Pair<GeoPoint, String>> = listOf(

        GeoPoint(41.1533, 20.1683) to "Albania",
        GeoPoint(42.5078, 1.5211) to "Andorra",
        GeoPoint(47.5162, 14.5501) to "Austria",
        GeoPoint(53.7098, 27.9534) to "Belarus",
        GeoPoint(50.8503, 4.3517) to "Belgium",
        GeoPoint(43.9159, 17.6791) to "Bosnia and Herzegovina",
        GeoPoint(42.7339, 25.4858) to "Bulgaria",
        GeoPoint(45.1, 15.2) to "Croatia",
        GeoPoint(49.8175, 15.4730) to "Czech Republic",
        GeoPoint(56.2639, 9.5018) to "Denmark",
        GeoPoint(52.3555, -1.1743) to "England",
        GeoPoint(58.5953, 25.0136) to "Estonia",
        GeoPoint(54.5260, 15.2551) to "Europe",
        GeoPoint(61.9241, 25.7482) to "Finland",
        GeoPoint(46.6034, 1.8883) to "France",
        GeoPoint(42.3154, 43.3569) to "Georgia",
        GeoPoint(51.1657, 10.4515) to "Germany",
        GeoPoint(39.0742, 21.8243) to "Greece",
        GeoPoint(71.7069, -42.6043) to "Greenland",
        GeoPoint(47.1625, 19.5033) to "Hungary",
        GeoPoint(64.9631, -19.0208) to "Iceland",
        GeoPoint(53.4129, -8.2439) to "Ireland",
        GeoPoint(41.8719, 12.5674) to "Italy",
        GeoPoint(42.6026, 20.9020) to "Kosovo",
        GeoPoint(46.5500, 11.8000) to "Ladinia",
        GeoPoint(56.8796, 24.6032) to "Latvia",
        GeoPoint(47.1660, 9.5554) to "Liechtenstein",
        GeoPoint(55.1694, 23.8813) to "Lithuania",
        GeoPoint(49.8153, 6.1296) to "Luxembourg",
        GeoPoint(35.9375, 14.3754) to "Malta",
        GeoPoint(47.4116, 28.3699) to "Moldova",
        GeoPoint(43.7384, 7.4246) to "Monaco",
        GeoPoint(42.7087, 19.3744) to "Montenegro",
        GeoPoint(52.1326, 5.2913) to "Netherlands",
        GeoPoint(41.9981, 21.4254) to "North Macedonia",
        GeoPoint(60.4720, 8.4689) to "Norway",
        GeoPoint(51.9194, 19.1451) to "Poland",
        GeoPoint(39.3999, -8.2245) to "Portugal",
        GeoPoint(45.9432, 24.9668) to "Romania",
        GeoPoint(56.4907, -4.2026) to "Scotland",
        GeoPoint(44.0165, 21.0059) to "Serbia",
        GeoPoint(48.6690, 19.6990) to "Slovakia",
        GeoPoint(46.1512, 14.9955) to "Slovenia",
        GeoPoint(40.4637, -3.7492) to "Spain",
        GeoPoint(60.1282, 18.6435) to "Sweden",
        GeoPoint(46.8182, 8.2275) to "Switzerland",
        GeoPoint(48.3794, 31.1656) to "Ukraine",
        GeoPoint(55.3781, -3.4360) to "United Kindgom",
        GeoPoint(41.9029, 12.4534) to "Vatican City",
        GeoPoint(45.4419, 12.3155) to "Veneto",
        GeoPoint(52.1307, -3.7837) to "Wales",

    )

    fun getCoordinates() : List<Pair<GeoPoint, String>> {
        return points
    }

}
