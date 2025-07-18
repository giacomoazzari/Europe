package esp.project.europe

import android.widget.Button
import android.widget.TextView
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.infowindow.InfoWindow

class MyInfoWindow (
    mapView: MapView,
    private val countryName: String,
    private val listener: OnNavigationButtonsListener
) : InfoWindow(R.layout.info_window, mapView) {

    override fun onOpen(item: Any?) {

        //Get the marker
        val marker = item as Marker
        val view = mView

        //Get the views from the layout
        val titleView = view.findViewById<TextView>(R.id.widget_title)
        val button = view.findViewById<Button>(R.id.widget_button)

        //Set the title of the marker
        titleView.text = marker.title

        //Set the listener for the button
        button.setOnClickListener {

            //Find the country by name
            val nation = CountriesData.getCountryByName(countryName)

            listener.onCountrySelected(nation, Origin.MAP)
        }
    }

    override fun onClose() {
        //Written only because InfoWindow is abstract
    }
}