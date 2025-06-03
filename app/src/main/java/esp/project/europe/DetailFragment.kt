package esp.project.europe

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

class DetailFragment : Fragment() {

    private var isPlaying = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = DetailFragmentArgs.fromBundle(requireArguments())

        view.findViewById<TextView>(R.id.countryNameTextView).text = args.countryName
        view.findViewById<TextView>(R.id.capitalTextView).text = "Capital: ${args.capital}"
        view.findViewById<ImageView>(R.id.flagImageView).setImageResource(args.flagResId)
        view.findViewById<TextView>(R.id.populationTextView).text = "Population: ${args.population}"
        view.findViewById<TextView>(R.id.areaTextView).text = "Area: ${args.area}"
        view.findViewById<TextView>(R.id.callingCodeTextView).text = "Calling Code: ${args.callingCode}"
        view.findViewById<TextView>(R.id.currencyTextView).text = "Currency: ${args.currency}"

        //Require context
        val context = requireContext()

        //Set the listener for the button
        val playButton = view.findViewById<Button>(R.id.playHymnButton)
        playButton.setOnClickListener {

            if(!isPlaying) {
                Log.d("DEBUG", "Play button clicked")
                val i = Intent(context, HymnService::class.java)
                i.putExtra(HymnService.ACTION_PLAY, true)
                //countryName needs to be lowercase and spaces replaced with underscores to correspond with the raw files
                i.putExtra(HymnService.NATIONS_HYMN, args.countryName.lowercase().replace( " ", "_"))
                ContextCompat.startForegroundService(context, i)
                isPlaying = true
                playButton.text = "Stop Hymn"
            }

            else {
                val i = Intent(context, HymnService::class.java)
                i.putExtra(HymnService.ACTION_STOP, true)
                ContextCompat.startForegroundService(context, i)
                isPlaying = false
                playButton.text = "Play Hymn"
            }
        }

    }
}