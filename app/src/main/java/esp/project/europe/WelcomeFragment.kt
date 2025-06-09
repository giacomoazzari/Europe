package esp.project.europe

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.content.res.Configuration
import androidx.navigation.findNavController

class WelcomeFragment : Fragment() {

    private var isDual : Boolean = false
    private var isTablet : Boolean = false
    private var listener: OnNavigationButtonsListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? OnNavigationButtonsListener
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_welcome, container, false)

        //Get the orientation and evaluate the position of the device
        val orientation = resources.configuration.orientation
        isDual = (activity as? MainActivity)?.isDualPane == true
        isTablet = resources.configuration.smallestScreenWidthDp >= 600
        val isTableTop = orientation == Configuration.ORIENTATION_LANDSCAPE && isDual
        val isBook = orientation == Configuration.ORIENTATION_PORTRAIT && isDual

        // Set the listener for the start button
        val button = view.findViewById<Button>(R.id.start)
        button.setOnClickListener {

            if(isTableTop) {
                listener!!.onDiscoverButtonClick(State.TABLETOP)
            }
            else if(isBook){
                listener!!.onDiscoverButtonClick(State.BOOK)
            }
            else if(isTablet){
                listener!!.onDiscoverButtonClick(State.TABLET)
            }
            else {
                view.findNavController().navigate(R.id.action_welcomeFragment_to_listFragment)
            }

        }

        return view
    }
}