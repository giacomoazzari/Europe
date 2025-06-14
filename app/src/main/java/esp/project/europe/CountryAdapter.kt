package esp.project.europe

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CountryAdapter(
    private val countryList: List<Country>,
    private val listener: OnNavigationButtonsListener
    ) : RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {

        // TAG for logging
        private val mTAG = this::class.simpleName

    inner class CountryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val flagImageView: ImageView = view.findViewById(R.id.flag_image)
        private val countryNameTextView: TextView = view.findViewById(R.id.country_name)

        fun bind(country: Country) {
            flagImageView.setImageResource(country.flag)
            countryNameTextView.text = country.name
            itemView.setOnClickListener {
                listener.onCountrySelected(country, Origin.LIST)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_country, parent, false)

        return CountryViewHolder(view)
    }

    // Displays data at a certain position
    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bind(countryList[position])
    }

    //returns the size of items in the list
    override fun getItemCount(): Int = countryList.size
}