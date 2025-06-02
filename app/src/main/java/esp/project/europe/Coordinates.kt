package esp.project.europe


import org.osmdroid.util.GeoPoint

object Coordinates {
    private val points : List<Pair<GeoPoint, String>> = listOf(

        GeoPoint(46.227638, 2.213749) to "France",
        GeoPoint(41.385064, 2.173403) to "Spain",
        GeoPoint(47.497913, 19.040236) to "Italy",
        GeoPoint(41.902782, 12.496365) to "Portugal"
        //TODO: add more points
    )

    fun getCoordinates() : List<Pair<GeoPoint, String>> {
        return points
    }

}
