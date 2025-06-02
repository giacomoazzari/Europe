package esp.project.europe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

class MapFragment : Fragment() {

    private lateinit var map: MapView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        //Inflate the fragment
        val mapview = inflater.inflate(R.layout.fragment_map, container, false)

        //Configurations for the map
        val context = requireContext().applicationContext
        Configuration.getInstance().load(context,
            androidx.preference.PreferenceManager.getDefaultSharedPreferences(context))

        //Create the map
        map = mapview.findViewById(R.id.map)
        map.setTileSource(TileSourceFactory.MAPNIK)

        //Set the initial point of the map
        map.controller.setZoom(5.0)
        map.controller.setCenter(GeoPoint(46.420128, 11.67925))

        //Allow for multi-finger controls
        map.setMultiTouchControls(true)

        //Get the list of the states
        val  points = Coordinates.getCoordinates()

        //Add the points to the map
        for (point in points) {

            //Create a marker
            val marker = Marker(map)

            //Set the position and title of the marker
            marker.position = point.first
            marker.title = point.second

            //TODO: Here code for marker icon and info window

            //TODO: Here code for the listener of the marker

            //Add the marker to the map
            map.overlays.add(marker)

        }

        map.invalidate()



        return mapview
    }

    override fun onPause() {
        super.onPause()
        map.onPause()
    }

    override fun onResume() {
        super.onResume()
        map.onResume()
    }
}