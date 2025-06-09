package esp.project.europe

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.osmdroid.config.Configuration
import org.osmdroid.events.MapEventsReceiver
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.MapEventsOverlay
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.infowindow.InfoWindow

class MapFragment : Fragment(){

    private lateinit var map: MapView
    private var listener: OnNavigationButtonsListener? = null
    private lateinit var selectedCountry: String

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? OnNavigationButtonsListener
    }
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

        //Add the points to the map, a marker for each with a custom info window
        //and a click listener for going to the detail.
        for (point in points) {

            //Create a marker
            val marker = Marker(map)

            //Set the position and title of the marker
            marker.position = point.first
            marker.title = point.second

            //Get the info widget, with the listener for navigation
            val info = MyInfoWindow(map, point.second, listener!!)

            //Put the info on the marker
            marker.infoWindow = info

            //Set the click listener
            marker.setOnMarkerClickListener { m, _ ->
                //Firstly close the old ones
                InfoWindow.closeAllInfoWindowsOn(map)

                //Secondly, create the new
                m.showInfoWindow()
                selectedCountry = m.title
                true
            }

            //Add the marker to the map
            map.overlays.add(marker)

        }

        //Logic for closing the info window when clicking outside the map,
        //using a specific overlay as the osmdroid docs suggest
        val mapEvents = MapEventsOverlay(object : MapEventsReceiver {
            override fun singleTapConfirmedHelper(p: GeoPoint?): Boolean {

                //If a tap on the map occurs
                InfoWindow.closeAllInfoWindowsOn(map)

                //Return false because an other overlay is after (they are processed
                // from the bottom to the top) and it contains logic for the
                //closure of InfoWindows
                return false
            }
            override fun longPressHelper(p: GeoPoint?): Boolean {

                //Not used in this app the long taps
                return false
            }
        })

        //Add the overlay
        map.overlays.add(mapEvents)

        //If a info window was open, now restore the state
        if(savedInstanceState != null){

            //Get and set the initial position and zoom
            val center = GeoPoint(savedInstanceState.getDouble("map_latitude"),
                savedInstanceState.getDouble("map_longitude"))
            map.controller.setCenter(center)
            map.controller.setZoom(savedInstanceState.getDouble("map_zoom"))

            //Get the selected state, if any
            val marker = Marker(map)
            val stateName = savedInstanceState.getString("selected_state")

            //Check the esistance of the state and then open the marker
            if(stateName != null) {
                val state = Coordinates.getCoordinatesByName(stateName)
                marker.position = state!!.first
                marker.title = state.second

                //Get the info widget, with the listener for navigation
                val info = MyInfoWindow(map, state.second, listener!!)

                //Put the info on the marker
                marker.infoWindow = info

                //Set the click listener
                marker.setOnMarkerClickListener { m, _ ->
                    //Firstly close the old ones
                    InfoWindow.closeAllInfoWindowsOn(map)

                    //Secondly, create the new
                    m.showInfoWindow()
                    selectedCountry = m.title
                    true
                }

                //Add the marker to the map
                map.overlays.add(marker)
            }
        }


        //Invalidate to guarantee the changes
        map.invalidate()

        return mapview
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        // Save map position
        val center = map.mapCenter as GeoPoint
        outState.putDouble("map_latitude", center.latitude)
        outState.putDouble("map_longitude", center.longitude)

        // Save zoom
        outState.putDouble("map_zoom", map.zoomLevelDouble)

        // Save selected state, if any
        val selectedMarker = map.overlays
            .filterIsInstance<Marker>()
            .firstOrNull { it.isInfoWindowOpen }

        selectedMarker?.let {
            outState.putString("selected_state", it.title)
        }
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