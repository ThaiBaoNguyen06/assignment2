package com.example.android_asgm2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider


class FavoriteCarFragment : Fragment() {
    val vm: CarVM by activityViewModels {ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)}

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_favorite_car, container, false)
        val favContainer = view.findViewById<LinearLayout>(R.id.favoriteContainer)
        favContainer.removeAllViews()

        if (vm.favoriteCars.isNotEmpty()) {
            Toast.makeText(requireContext(), "Favorites size: ${vm.favoriteCars.size}", Toast.LENGTH_SHORT).show()
            for (car in vm.favoriteCars) {
                val itemView = layoutInflater.inflate(R.layout.item_favorite_car, favContainer, false)
                val carImage = itemView.findViewById<ImageView>(R.id.favCarImage)
                val carName = itemView.findViewById<TextView>(R.id.favCarName)

                carImage.setImageResource(car.image)
                carName.text = car.name

                favContainer.addView(itemView)
            }
        }
        else {
            val emptyText = TextView(requireContext())
            emptyText.text = "No favorite cars yet."
            emptyText.textSize = 16f
            favContainer.addView(emptyText)
        }

        return view
    }
}
