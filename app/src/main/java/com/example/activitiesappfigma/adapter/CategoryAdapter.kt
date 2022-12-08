package com.example.activitiesappfigma.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.activitiesappfigma.R
import com.example.activitiesappfigma.data.model.Category
import com.example.activitiesappfigma.ui.CategoryFragmentDirections


class CategoryAdapter() : RecyclerView.Adapter<CategoryAdapter.ItemViewHolder>() {


    private lateinit var dataset: List<Category>

    // IDEE EINES VIEWHOLDERS
    // der ViewHolder weiß welche Teile des Layouts beim Recycling angepasst werden
    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        var categoryCard: CardView = view.findViewById(R.id.category_card)
        var categoryImage: ImageView = view.findViewById(R.id.category_image)
        var categoryName: TextView = view.findViewById(R.id.category_name_text)
        var selectedCard: View = view.findViewById(R.id.selectedCard_view)
    }

    fun submitList(list: List<Category>) {
        dataset = list
        notifyDataSetChanged()
    }

    // ERSTELLEN DES VIEWHOLDERS
    // hier werden neue ViewHolder erstellt
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        // das itemLayout wird gebaut
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.category_list_item, parent, false)

        // und in einem ViewHolder zurückgegeben
        return ItemViewHolder(adapterLayout)
    }

    // BEFÜLLEN DES VIEWHOLDERS
    // hier findet der Recyclingprozess statt
    // die vom ViewHolder bereitgestellten Parameter werden verändert
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item: Category = dataset[position]
        holder.categoryName.text = item.name
        holder.categoryImage.setImageResource(item.imageResource)

        if (!item.selected) {
            holder.selectedCard.visibility = View.INVISIBLE
        } else {
            holder.selectedCard.visibility = View.VISIBLE
        }


        holder.categoryCard.setOnClickListener {


            if (!item.selected) {
                item.selected = true
                holder.selectedCard.visibility = View.VISIBLE
            } else {
                item.selected = false
                holder.selectedCard.visibility = View.INVISIBLE
            }


        }


    }

    // damit der LayoutManager weiß wie lang die Liste ist
    override fun getItemCount(): Int {
        return dataset.size
    }
}
