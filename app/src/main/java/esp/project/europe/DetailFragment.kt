package esp.project.europe

import android.content.Intent
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

class DetailFragment : Fragment() {

    //Variables for the arguments
    private var countryName: String = "N/D"
    private var flagResId: Int = 0
    private var capital: String = "N/D"
    private var population: Int = 0
    private var area: Int = 0
    private var callingCode: String  = "N/D"
    private var currency: String  = "N/D"

    //Variables for the hymn player
    private var isPlaying = false
    private lateinit var playButton: Button

    //Variable for knowing the state
    private var isDual: Boolean = false
    private var isTablet: Boolean = false

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
        isTablet = resources.configuration.smallestScreenWidthDp >= 600

        playButton = view.findViewById(R.id.playHymnButton)


        //----------- code for the arguments---------------------//
        //Check if there are args
        if(arguments != null || savedInstanceState != null) {

            //Get the arguments and show them
            getTheArguments(savedInstanceState)
            showCountryDetails()
        }
        else {

            //Show the placeholder
            showWelcomePlaceholder()
        }


        //----------- code for the back button---------------------//
        //If it is in single pane mode, a back button is necessary
        if(!isDual && !isTablet) {

            //Find the toolbar, the activity and the navigator
            val navController = findNavController()
            val activity = requireActivity() as AppCompatActivity
            val toolbar = view.findViewById<androidx.appcompat.widget.Toolbar>(R.id.detail_toolbar)

            //Put the icon in the toolbar
            val backIcon = ContextCompat.getDrawable(requireContext(), R.drawable.back_arrow)
            toolbar.navigationIcon = backIcon

            //Set the toolbar with only the arrow and not the title
            activity.setSupportActionBar(toolbar)
            activity.supportActionBar?.setDisplayShowTitleEnabled(false)
            activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)

            //Add the logic to the back button
            toolbar.setNavigationOnClickListener {
                navController.navigateUp()

            }
        }



        //----------- code for the hymn player---------------------//
        //TODO: Restoring state of open display (open to open, open to book, open to close et viceversa)
        //Require context
        val context = requireContext()

        //Set the listener for the button
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

    // Saving the state
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("countryName", countryName)
        outState.putInt("flagResId", flagResId)
        outState.putString("capital", capital)
        outState.putInt("population", population)
        outState.putInt("area", area)
        outState.putString("callingCode", callingCode)
        outState.putString("currency", currency)
        outState.putBoolean("isPlaying", isPlaying)
        // TODO: Save (and restore!) save of anthem playing (continue playing without interrupt): needs to be modified onPause
    }

    //Private fun for getting the arguments
    private fun getTheArguments(savedInstanceState: Bundle?) {

        //Three cases, one from saved state, one from bundle, one from arguments
        if(savedInstanceState != null) {
            //Case 1: get data from past state
            countryName = savedInstanceState.getString("countryName", "N/D")
            flagResId = savedInstanceState.getInt("flagResId", 0)
            capital = savedInstanceState.getString("capital", "N/D")
            population = savedInstanceState.getInt("population", 0)
            area = savedInstanceState.getInt("area", 0)
            callingCode = savedInstanceState.getString("callingCode", "N/D")
            currency = savedInstanceState.getString("currency", "N/D")
            isPlaying = savedInstanceState.getBoolean("isPlaying", false)

        }else if (isDual || isTablet) {
            //Case 2: get data from the bundle
            val args = requireArguments()
            countryName = args.getString(ARG_COUNTRY_NAME, "")
            flagResId = args.getInt(ARG_FLAG_RES_ID)
            capital = args.getString(ARG_CAPITAL,"")
            population = args.getInt(ARG_POPULATION)
            area = args.getInt(ARG_AREA)
            callingCode = args.getString(ARG_CALLING_CODE,"")
            currency = args.getString(ARG_CURRENCY, "")

        } else {
            //Case 3: get data from the arguments
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

    //Private fun for showing the welcome placeholder
    private fun showWelcomePlaceholder() {

        //Check the orientation first
        val orientation = resources.configuration.orientation

        if(orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE) {

            //In Landscape there are two containers to be shown
            view?.findViewById<View>(R.id.welcome_placeholder)?.visibility = View.VISIBLE
            view?.findViewById<View>(R.id.right_container)?.visibility = View.GONE
            view?.findViewById<View>(R.id.left_container)?.visibility = View.GONE

        } else {

            //Only one in portrait
            view?.findViewById<View>(R.id.welcome_placeholder)?.visibility = View.VISIBLE
            view?.findViewById<View>(R.id.detail_container)?.visibility = View.GONE
        }
    }

    //Private fun for showing the country details
    private fun showCountryDetails() {

        //Check the orientation first
        val orientation = resources.configuration.orientation

        if(orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE) {

            //In Landscape there are two containers to be shown
            view?.findViewById<View>(R.id.welcome_placeholder)?.visibility = View.GONE
            view?.findViewById<View>(R.id.right_container)?.visibility = View.VISIBLE
            view?.findViewById<View>(R.id.left_container)?.visibility = View.VISIBLE

        } else {

            //Only one in portrait
            view?.findViewById<View>(R.id.welcome_placeholder)?.visibility = View.GONE
            view?.findViewById<View>(R.id.detail_container)?.visibility = View.VISIBLE
        }


        //Update the UI
        val rootView = requireView()

        rootView.findViewById<TextView>(R.id.countryNameTextView).text = countryName
        rootView.findViewById<TextView>(R.id.capitalTextView).text = getString(R.string.capital, capital)
        rootView.findViewById<ImageView>(R.id.flagImageView).setImageResource(flagResId)
        rootView.findViewById<TextView>(R.id.populationTextView).text = getString(R.string.population, population)
        rootView.findViewById<TextView>(R.id.areaTextView).text = getString(R.string.area, area)
        rootView.findViewById<TextView>(R.id.callingCodeTextView).text = getString(R.string.callingCode, callingCode)
        rootView.findViewById<TextView>(R.id.currencyTextView).text = getString(R.string.currency, currency)
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