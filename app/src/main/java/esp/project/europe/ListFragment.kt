package esp.project.europe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CountryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize the RecyclerView
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Retrieves data from Country class
        val countryList = listOf(
            Country("Albania", R.drawable.albania),
            Country("Andorra", R.drawable.andorra),
            Country("Austria", R.drawable.austria),
            Country("Belarus", R.drawable.belarus),
            Country("Belgium", R.drawable.belgium),
            Country("Bosnia and Herzegovina", R.drawable.bosnia_and_herzegovina),
            Country("Bulgaria", R.drawable.bulgaria),
            Country("Croatia", R.drawable.croatia),
            Country("Czech Republic", R.drawable.czech_republic),
            Country("Denmark", R.drawable.denmark),
            Country("England", R.drawable.england),
            Country("Estonia", R.drawable.estonia),
            Country("Europe", R.drawable.europe),
            Country("Finland", R.drawable.finland),
            Country("France", R.drawable.france),
            Country("Georgia", R.drawable.georgia),
            Country("Germany", R.drawable.germany),
            Country("Greece", R.drawable.greece),
            Country("Greenland", R.drawable.greenland),
            Country("Hungary", R.drawable.hungary),
            Country("Iceland", R.drawable.iceland),
            Country("Ireland", R.drawable.ireland),
            Country("Italy", R.drawable.italy),
            Country("Ladinia", R.drawable.ladinia),
            Country("Kosovo", R.drawable.kosovo),
            Country("Latvia", R.drawable.latvia),
            Country("Liechtenstein", R.drawable.liechtenstein),
            Country("Lithuania", R.drawable.lithuania),
            Country("Luxembourg", R.drawable.luxembourg),
            Country("Malta", R.drawable.malta),
            Country("Moldova", R.drawable.moldova),
            Country("Monaco", R.drawable.monaco),
            Country("Montenegro", R.drawable.montenegro),
            Country("Netherlands", R.drawable.netherlands),
            Country("North Macedonia", R.drawable.north_macedonia),
            Country("Norway", R.drawable.norway),
            Country("Poland", R.drawable.poland),
            Country("Portugal", R.drawable.portugal),
            Country("Romania", R.drawable.romania),
            Country("Scotland", R.drawable.scotland),
            Country("Serbia", R.drawable.serbia),
            Country("Slovakia", R.drawable.slovakia),
            Country("Slovenia", R.drawable.slovenia),
            Country("Spain", R.drawable.spain),
            Country("Sweden", R.drawable.sweden),
            Country("Switzerland", R.drawable.switzerland),
            Country("Ukraine", R.drawable.ukraine),
            Country("United Kingdom", R.drawable.united_kingdom),
            Country("Vatican City", R.drawable.vatican_city),
            Country("Veneto", R.drawable.veneto),
            Country("Wales", R.drawable.wales)
        )

        //Set the adapter
        adapter = CountryAdapter(countryList)
        recyclerView.adapter = adapter
    }

}
