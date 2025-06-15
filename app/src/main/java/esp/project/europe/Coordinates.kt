package esp.project.europe

import android.content.Context
import org.osmdroid.util.GeoPoint

object Coordinates {
    private lateinit var points: List<Pair<GeoPoint, String>>

    fun initCoordinates(context: Context){
        points = listOf(
            GeoPoint(41.1533, 20.1683) to context.getString(R.string.albania),
            GeoPoint(42.5078, 1.5211) to context.getString(R.string.andorra),
            GeoPoint(47.5162, 14.5501) to context.getString(R.string.austria),
            GeoPoint(53.7098, 27.9534) to context.getString(R.string.belarus),
            GeoPoint(50.8503, 4.3517) to context.getString(R.string.belgium),
            GeoPoint(43.9159, 17.6791) to context.getString(R.string.bosnia_and_herzegovina),
            GeoPoint(42.7339, 25.4858) to context.getString(R.string.bulgaria),
            GeoPoint(45.1, 15.2) to context.getString(R.string.croatia),
            GeoPoint(49.8175, 15.4730) to context.getString(R.string.czech_republic),
            GeoPoint(56.2639, 9.5018) to context.getString(R.string.denmark),
            GeoPoint(52.3555, -1.1743) to context.getString(R.string.england),
            GeoPoint(58.5953, 25.0136) to context.getString(R.string.estonia),
            GeoPoint(54.5260, 15.2551) to context.getString(R.string.europe),
            GeoPoint(61.9241, 25.7482) to context.getString(R.string.finland),
            GeoPoint(46.6034, 1.8883) to context.getString(R.string.france),
            GeoPoint(42.3154, 43.3569) to context.getString(R.string.georgia),
            GeoPoint(51.1657, 10.4515) to context.getString(R.string.germany),
            GeoPoint(39.0742, 21.8243) to context.getString(R.string.greece),
            GeoPoint(71.7069, -42.6043) to context.getString(R.string.greenland),
            GeoPoint(47.1625, 19.5033) to context.getString(R.string.hungary),
            GeoPoint(64.9631, -19.0208) to context.getString(R.string.iceland),
            GeoPoint(53.4129, -8.2439) to context.getString(R.string.ireland),
            GeoPoint(41.8719, 12.5674) to context.getString(R.string.italy),
            GeoPoint(42.6026, 20.9020) to context.getString(R.string.kosovo),
            GeoPoint(46.5500, 11.8000) to context.getString(R.string.ladinia),
            GeoPoint(56.8796, 24.6032) to context.getString(R.string.latvia),
            GeoPoint(47.1660, 9.5554) to context.getString(R.string.liechtenstein),
            GeoPoint(55.1694, 23.8813) to context.getString(R.string.lithuania),
            GeoPoint(49.8153, 6.1296) to context.getString(R.string.luxembourg),
            GeoPoint(35.9375, 14.3754) to context.getString(R.string.malta),
            GeoPoint(47.4116, 28.3699) to context.getString(R.string.moldova),
            GeoPoint(43.7384, 7.4246) to context.getString(R.string.monaco),
            GeoPoint(42.7087, 19.3744) to context.getString(R.string.montenegro),
            GeoPoint(52.1326, 5.2913) to context.getString(R.string.netherlands),
            GeoPoint(41.9981, 21.4254) to context.getString(R.string.north_macedonia),
            GeoPoint(60.4720, 8.4689) to context.getString(R.string.norway),
            GeoPoint(51.9194, 19.1451) to context.getString(R.string.poland),
            GeoPoint(39.3999, -8.2245) to context.getString(R.string.portugal),
            GeoPoint(45.9432, 24.9668) to context.getString(R.string.romania),
            GeoPoint(56.4907, -4.2026) to context.getString(R.string.scotland),
            GeoPoint(44.0165, 21.0059) to context.getString(R.string.serbia),
            GeoPoint(48.6690, 19.6990) to context.getString(R.string.slovakia),
            GeoPoint(46.1512, 14.9955) to context.getString(R.string.slovenia),
            GeoPoint(40.4637, -3.7492) to context.getString(R.string.spain),
            GeoPoint(60.1282, 18.6435) to context.getString(R.string.sweden),
            GeoPoint(46.8182, 8.2275) to context.getString(R.string.switzerland),
            GeoPoint(48.3794, 31.1656) to context.getString(R.string.ukraine),
            GeoPoint(55.3781, -3.4360) to context.getString(R.string.united_kingdom),
            GeoPoint(41.9029, 12.4534) to context.getString(R.string.vatican_city),
            GeoPoint(45.4419, 12.3155) to context.getString(R.string.veneto),
            GeoPoint(52.1307, -3.7837) to context.getString(R.string.wales)
        )
    }

    fun getCoordinates() : List<Pair<GeoPoint, String>> {
        return points
    }

    fun getCoordinatesByName(name: String): Pair<GeoPoint, String>? {
        return points.find { it.second == name }
    }

}
