package esp.project.europe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment

class DetailFragment : Fragment() {

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
    }
}