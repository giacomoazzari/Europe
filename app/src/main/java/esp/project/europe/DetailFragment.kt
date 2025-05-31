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

        val name = arguments?.getString("countryName") ?: "Unknown"
        val flagResId = arguments?.getInt("flagResId") ?: R.drawable.europe
        val capital = arguments?.getString("capital") ?: "N/A"

        view.findViewById<TextView>(R.id.countryNameTextView).text = name
        view.findViewById<TextView>(R.id.capitalTextView).text = "Capital: $capital"
        view.findViewById<ImageView>(R.id.flagImageView).setImageResource(flagResId)
    }
}