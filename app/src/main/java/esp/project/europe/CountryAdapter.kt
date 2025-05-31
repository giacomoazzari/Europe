package esp.project.europe

import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater

class CountryAdapter(private val countryList: List<Country>) :
    RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {
    /*when an user tap on the country, the details of the country will be shown
        private val onClickListener: View.OnClickListener{
        }
    */
    private val mTAG = this::class.simpleName

    class CountryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val flagImageView: ImageView = view.findViewById(R.id.flag_image)
        val countryNameTextView: TextView = view.findViewById(R.id.country_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        Log.d(mTAG, "New View Holder Created")

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_country, parent, false)
        //when an user tap on the country, the details of the country will be shown
        //val tv: TextView = view.findViewById(R.id.country_name)
        //tv.setOnClickListener(onClickListener)
        return CountryViewHolder(view)
    }

    // Displays data at a certain position
    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        val country = countryList[position]
        holder.flagImageView.setImageResource(country.flag)
        holder.countryNameTextView.text = country.name
    }

    //returns the size of items in the list
    override fun getItemCount(): Int {
        return countryList.size
    }
}
