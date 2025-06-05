package esp.project.europe

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
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

class DetailFragment : Fragment() {

    private lateinit var countryName: String
    private var flagResId: Int = 0
    private lateinit var capital: String
    private var population: Int = 0
    private var area: Int = 0
    private lateinit var callingCode: String
    private lateinit var currency: String

    private var isPlaying = false
    private lateinit var playButton: Button

    private var isDual: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Check the state of the device
        isDual = (activity as? MainActivity)?.isDualPane == true

        //Get the arguments
        getTheArguments()

        //Update the UI
        view.findViewById<TextView>(R.id.countryNameTextView).text = countryName
        view.findViewById<TextView>(R.id.capitalTextView).text = "Capital: $capital"
        view.findViewById<ImageView>(R.id.flagImageView).setImageResource(flagResId)
        view.findViewById<TextView>(R.id.populationTextView).text = "Population: $population"
        view.findViewById<TextView>(R.id.areaTextView).text = "Area: $area"
        view.findViewById<TextView>(R.id.callingCodeTextView).text = "Calling Code: $callingCode"
        view.findViewById<TextView>(R.id.currencyTextView).text = "Currency: $currency"

        //If it is in single pane mode, a back button is necessary
        if(!isDual) {
            //Find the toolbar, the activity and the navigator
            val navController = findNavController()
            val activity = requireActivity() as AppCompatActivity
            val toolbar = view.findViewById<androidx.appcompat.widget.Toolbar>(R.id.detail_toolbar)

            //Color the backIcon black, as in a declarative way it doesn't work
            val backIcon = ContextCompat.getDrawable(requireContext(), R.drawable.back_arrow)
            toolbar.navigationIcon = backIcon

            //Set the toolbar with only the row and not the title
            activity.setSupportActionBar(toolbar)
            activity.supportActionBar?.setDisplayShowTitleEnabled(false)
            activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)

            //Add the logic to the back button
            toolbar.setNavigationOnClickListener {
                navController.navigateUp()

            }
        }
        //Require context
        val context = requireContext()

        //Set the listener for the button
        playButton = view.findViewById(R.id.playHymnButton)
        playButton.setOnClickListener {

            //Case 1: it's silent
            if (!isPlaying) {

                //Create the intent
                val i = Intent(context, HymnService::class.java)

                //Add the info of starting
                i.putExtra(HymnService.ACTION_PLAY, true)

                //Add the name of the country in order to find the song.
                //countryName needs to be lowercase and spaces replaced
                // with underscores to correspond with the raw files
                i.putExtra(
                    HymnService.NATIONS_HYMN, countryName
                        .lowercase().replace(" ", "_")
                )

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
        if (isPlaying) {

            //Create the intent
            val i = Intent(context, HymnService::class.java)

            //Add info for stopping
            i.putExtra(HymnService.ACTION_STOP, true)

            //Start the foreground service
            ContextCompat.startForegroundService(requireContext(), i)

            //Set the variable and button text
            isPlaying = false
            playButton.text = getString(R.string.play)

        }
    }

    //Private fun for getting the arguments
    private fun getTheArguments() {

        //Two cases, single or dual mode
        if (isDual) {
            //Case 1: get data from the bundle
            val args = requireArguments()
            countryName = args.getString(ARG_COUNTRY_NAME, "")
            flagResId = args.getInt(ARG_FLAG_RES_ID)
            capital = args.getString(ARG_CAPITAL,"")
            population = args.getInt(ARG_POPULATION)
            area = args.getInt(ARG_AREA)
            callingCode = args.getString(ARG_CALLING_CODE,"")
            currency = args.getString(ARG_CURRENCY, "")

        } else {
            //Case 2: get data from the arguments
            val args = DetailFragmentArgs.fromBundle(requireArguments())
            countryName = args.countryName
            flagResId = args.flagResId
            capital = args.capital
            population = args.population
            area = args.area
            callingCode = args.callingCode
            currency = args.currency

        }

    }
    //Contains all the arguments for the fragment
    companion object {
        const val ARG_COUNTRY_NAME = "countryName"
        const val ARG_FLAG_RES_ID = "flagResId"
        const val ARG_CAPITAL = "capital"
        const val ARG_POPULATION = "population"
        const val ARG_AREA = "area"
        const val ARG_CALLING_CODE = "callingCode"
        const val ARG_CURRENCY = "currency"

        fun newInstance(
            countryName: String,
            flagResId: Int,
            capital: String,
            population: Int,
            area: Int,
            callingCode: String,
            currency: String
        ): DetailFragment {
            val fragment = DetailFragment()
            val args = Bundle().apply {
                putString(ARG_COUNTRY_NAME, countryName)
                putInt(ARG_FLAG_RES_ID, flagResId)
                putString(ARG_CAPITAL, capital)
                putInt(ARG_POPULATION, population)
                putInt(ARG_AREA, area)
                putString(ARG_CALLING_CODE, callingCode)
                putString(ARG_CURRENCY, currency)
            }
            fragment.arguments = args
            return fragment
        }
    }
}