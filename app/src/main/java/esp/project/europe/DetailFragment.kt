package esp.project.europe

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController

class DetailFragment : Fragment() {

    private var isPlaying = false
    private lateinit var playButton : Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Get the args
        val args = DetailFragmentArgs.fromBundle(requireArguments())

        //Add the args to the slots
        view.findViewById<TextView>(R.id.countryNameTextView).text = args.countryName
        view.findViewById<TextView>(R.id.capitalTextView).text = "Capital: ${args.capital}"
        view.findViewById<ImageView>(R.id.flagImageView).setImageResource(args.flagResId)
        view.findViewById<TextView>(R.id.populationTextView).text = "Population: ${args.population}"
        view.findViewById<TextView>(R.id.areaTextView).text = "Area: ${args.area}"
        view.findViewById<TextView>(R.id.callingCodeTextView).text = "Calling Code: ${args.callingCode}"
        view.findViewById<TextView>(R.id.currencyTextView).text = "Currency: ${args.currency}"

        //Find the toolbar, the activity and the navigator
        val navController = findNavController()
        val activity = requireActivity() as AppCompatActivity
        val toolbar = view.findViewById<androidx.appcompat.widget.Toolbar>(R.id.detail_toolbar)

        //Color the backIcon black, as in a declarative way it doesn't work
        val backIcon = ContextCompat.getDrawable(requireContext(), R.drawable.arrow)?.mutate()
        toolbar.navigationIcon = backIcon

        //Set the toolbar with only the row and not the title
        activity.setSupportActionBar(toolbar)
        activity.supportActionBar?.setDisplayShowTitleEnabled(false)
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //Add the logic to the back button
        toolbar.setNavigationOnClickListener {
            navController.navigateUp()

        }

        //Require context
        val context = requireContext()

        //Set the listener for the button
        playButton = view.findViewById<Button>(R.id.playHymnButton)
        playButton.setOnClickListener {

            //Case 1: it's silent
            if(!isPlaying) {

                //Create the intent
                val i = Intent(context, HymnService::class.java)

                //Add the info of starting
                i.putExtra(HymnService.ACTION_PLAY, true)

                //Add the name of the country in order to find the song.
                //countryName needs to be lowercase and spaces replaced
                // with underscores to correspond with the raw files
                i.putExtra(HymnService.NATIONS_HYMN, args.countryName
                    .lowercase().replace( " ", "_"))

                //Start the foreground intent
                ContextCompat.startForegroundService(context, i)

                //Set the button text and state
                isPlaying = true
                playButton.text = getString(R.string.stop)
            }

            //Case 2: it's playing
            else {

                //Create the intent
                val i = Intent(context, HymnService::class.java)

                //Add the info of stopping
                i.putExtra(HymnService.ACTION_STOP, true)

                //Start the foreground service, but the logic beyond will understand
                //it has to be stopped
                ContextCompat.startForegroundService(context, i)

                //Set the button text and state
                isPlaying = false
                playButton.text = getString(R.string.play)
            }
        }

    }

    //When the fragment detail goes on Pause, stop the hymn reproduction
    override fun onPause() {
        super.onPause()

        //Only if the hymn is playing
        if(isPlaying){

            //Create the intent
            val i = Intent(context, HymnService::class.java)

            //Add info for stopping
            i.putExtra(HymnService.ACTION_STOP, true)

            //Start the foreground service
            ContextCompat.startForegroundService(context, i)

            //Set the variable and button text
            isPlaying = false
            playButton.text = getString(R.string.play)

        }
    }
}