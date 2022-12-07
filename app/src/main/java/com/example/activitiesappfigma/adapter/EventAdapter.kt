package com.example.activitiesappfigma.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.net.toUri
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.activitiesappfigma.MainViewModel
import com.example.activitiesappfigma.R
import com.example.activitiesappfigma.data.model.Event

class EventAdapter(private val viewModel: MainViewModel) : RecyclerView.Adapter<EventAdapter.ItemViewHolder>() {


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
        var eventListCard: CardView = view.findViewById(R.id.event_item_card)
        var weather: ImageView = view.findViewById(R.id.weather_icon)
        var weatherTemp: TextView = view.findViewById(R.id.weather_temp)
    }

    private var dataset = listOf<Event>()



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
        holder.eventNameText.text = item.name
        holder.eventItemDateText.text = item.dateAndTime
        holder.eventItemTimeText.text = "19 Uhr"
        holder.eventPlaceText.text = item.location
        holder.eventProfileName.text = "Profilname"
        holder.eventMemberCount.text = "10"
        holder.weatherTemp.text = item.temp



        // TODO
       // while(item.temp != null) {

     //   clear-day┃clear-night┃partly-cloudy-day┃partly-cloudy-night┃cloudy┃fog┃wind┃rain┃sleet┃snow┃hail┃thunderstorm┃

        val weather = when (item.weather) {
            "clear-day" -> holder.weather.setImageResource(R.drawable.ic_baseline_wb_sunny_24)
            "clear-night" -> holder.weather.setImageResource(R.drawable.ic_baseline_wb_sunny_24)
            "partly-cloudy-day" -> holder.weather.setImageResource(R.drawable.ic_baseline_wb_cloudy_24)
            "partly-cloudy-night" -> holder.weather.setImageResource(R.drawable.ic_baseline_wb_cloudy_24)
            "cloudy" -> holder.weather.setImageResource(R.drawable.ic_baseline_wb_cloudy_24)
            "fog" -> holder.weather.setImageResource(R.drawable.ic_baseline_wb_cloudy_24)
            "wind" -> holder.weather.setImageResource(R.drawable.ic_baseline_wb_cloudy_24)
            "snow" -> holder.weather.setImageResource(R.drawable.ic_baseline_snow)
            "rain" -> holder.weather.setImageResource(R.drawable.ic_baseline_water_drop_24)
            else -> holder.weather.setImageResource(R.drawable.ic_baseline_wb_cloudy_24)
        }

        val imgUri = item.image.toUri().buildUpon().scheme("https").build()

        holder.eventItemImage.load(imgUri) {
            error(R.drawable.app_logo)
            transformations(RoundedCornersTransformation(10f))
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














