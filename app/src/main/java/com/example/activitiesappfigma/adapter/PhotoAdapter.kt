package com.example.activitiesappfigma.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.activitiesappfigma.R
import com.example.activitiesappfigma.data.model.Category
import com.example.activitiesappfigma.data.model.Photo

class PhotoAdapter() : RecyclerView.Adapter<PhotoAdapter.ItemViewHolder>() {


    private var dataset = mutableListOf<Photo>()

    // IDEE EINES VIEWHOLDERS
    // der ViewHolder weiß welche Teile des Layouts beim Recycling angepasst werden
    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        var detailPhoto: CardView = view.findViewById(R.id.photo_card)
        var detailPhotoImage: ImageView = view.findViewById(R.id.photo_image)
    }

    fun submitList(list: MutableList<Photo>) {
        dataset = list
        notifyDataSetChanged()
    }

    // ERSTELLEN DES VIEWHOLDERS
    // hier werden neue ViewHolder erstellt
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        // das itemLayout wird gebaut
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.eventdetail_photo_item, parent, false)

        // und in einem ViewHolder zurückgegeben
        return ItemViewHolder(adapterLayout)
    }

    // BEFÜLLEN DES VIEWHOLDERS
    // hier findet der Recyclingprozess statt
    // die vom ViewHolder bereitgestellten Parameter werden verändert
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item: Photo = dataset[position]
        holder.detailPhotoImage.setImageResource(R.drawable.app_logo)

    }

    // damit der LayoutManager weiß wie lang die Liste ist
    override fun getItemCount(): Int {
        return dataset.size
    }
}