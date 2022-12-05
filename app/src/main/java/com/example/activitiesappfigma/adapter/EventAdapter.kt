package com.example.activitiesappfigma.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.activitiesappfigma.MainViewModel
import com.example.activitiesappfigma.R
import com.example.activitiesappfigma.data.model.Event
import com.example.activitiesappfigma.data.model.Profile
import com.example.activitiesappfigma.data.model.WeatherData
import com.example.activitiesappfigma.databinding.FragmentEventeditBinding
import com.google.firebase.firestore.FirebaseFirestore

class EventAdapter(private val viewModel: MainViewModel) :
    RecyclerView.Adapter<EventAdapter.ItemViewHolder>() {


    // IDEE EINES VIEWHOLDERS
    // der ViewHolder weiß welche Teile des Layouts beim Recycling angepasst werden
    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        var eventItemImage: ImageView = view.findViewById(R.id.event_item_image)
        var eventItemTimeText: TextView = view.findViewById(R.id.event_item_time_text)
        var eventItemDateText: TextView = view.findViewById(R.id.event_item_date_text)
        var eventNameText: TextView = view.findViewById(R.id.event_item_name_text)
        var eventProfileName: TextView = view.findViewById(R.id.event_item_profile_name_text)
        var eventPlaceText: TextView = view.findViewById(R.id.event_item_place_text)
        var eventMemberCount: TextView = view.findViewById(R.id.event_item_member_count)
        var eventListCard: CardView = view.findViewById((R.id.event_item_card))
        var weatherIconSunny: ImageView = view.findViewById(R.id.weather_icon_sun)
        var weatherIconRainy: ImageView = view.findViewById(R.id.weather_icon_rain)
        var weatherIconCloudy: ImageView = view.findViewById(R.id.weather_icon_cloud)
        var weatherTempSunny: TextView = view.findViewById(R.id.weather_temp_text_sunny)
        var weatherTempRainy: TextView = view.findViewById(R.id.weather_temp_text_rainy)
        var weatherTempCloudy: TextView = view.findViewById(R.id.weather_temp_text_cloudy)
        var weatherCelsiusSunny: TextView = view.findViewById(R.id.weather_celsius_text_sunny)
        var weatherCelsiusRainy: TextView = view.findViewById(R.id.weather_celsius_text_rainy)
        var weatherCelsiusCloudy: TextView = view.findViewById(R.id.weather_celsius_text_cloudy)
    }

    private var dataset = listOf<Event>()
    private var weatherDataset = listOf<WeatherData>()

    fun submitList(list: List<Event>) {
        dataset = list
        notifyDataSetChanged()
    }


    // ERSTELLEN DES VIEWHOLDERS
    // hier werden neue ViewHolder erstellt
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        // das itemLayout wird gebaut
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.event_list_item, parent, false)

        // und in einem ViewHolder zurückgegeben
        return ItemViewHolder(adapterLayout)
    }


    // BEFÜLLEN DES VIEWHOLDERS
    // hier findet der Recyclingprozess statt
    // die vom ViewHolder bereitgestellten Parameter werden verändert
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item: Event = dataset[position]
        holder.eventItemImage.setImageResource(R.drawable.app_logo)
        holder.eventNameText.text = item.name
        holder.eventItemDateText.text = item.dateAndTime
        holder.eventItemTimeText.text = "19 Uhr"
        holder.eventPlaceText.text = item.location
        holder.eventProfileName.text = "Profilname"
        holder.eventMemberCount.text = "10"

        when(item.weather) {

        }

        holder.eventListCard.setOnClickListener {
            viewModel.setEvent(item)
            holder.itemView.findNavController().navigate(R.id.eventDetailFragment)
        }

    }

    // damit der LayoutManager weiß wie lang die Liste ist
    override fun getItemCount(): Int {
        return dataset.size
    }
}


