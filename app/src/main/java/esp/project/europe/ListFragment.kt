package esp.project.europe

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import esp.project.europe.CountriesData.getCountries

class ListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CountryAdapter
    private var listener: OnNavigationButtonsListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? OnNavigationButtonsListener
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize the RecyclerView
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Retrieve the list of countries
        val countryList = getCountries()

        //Set the adapter
        adapter = CountryAdapter(countryList, listener!!)
        recyclerView.adapter = adapter


        // Restore the state, if any
        if (savedInstanceState != null) {
            val savedState = savedInstanceState.getParcelable<Parcelable>("scroll_position")
            recyclerView.layoutManager?.onRestoreInstanceState(savedState)
        }
    }

    // Saving the state
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (::recyclerView.isInitialized) { // avoids the call if the recycle view is not yet initialized
            recyclerView.layoutManager?.let {
                outState.putParcelable("scroll_position", it.onSaveInstanceState())
            }
        }
    }
}