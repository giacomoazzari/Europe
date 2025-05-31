package esp.project.europe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import esp.project.europe.CountriesData.getCountries

class ListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CountryAdapter

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
        adapter = CountryAdapter(countryList) { selectedCountry ->
            val bundle = Bundle().apply {
                putString("countryName", selectedCountry.name)
                putInt("flagResId", selectedCountry.flag)
                putString("capital", selectedCountry.capital ?: "N/A")
            }

            findNavController().navigate(R.id.action_listFragment_to_detailFragment, bundle)
        }

        recyclerView.adapter = adapter
    }
}