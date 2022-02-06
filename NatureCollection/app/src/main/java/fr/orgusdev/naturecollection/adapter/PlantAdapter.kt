package fr.orgusdev.naturecollection.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import fr.orgusdev.naturecollection.*

class PlantAdapter(
    val context: MainActivity,
    private val plantList: List<PlantModel>,
    private val layoutId: Int
    ) : RecyclerView.Adapter<PlantAdapter.ViewHolder>() {

    // Boite pour ranger tous les composants à controler
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Image de la plante
        val plantImage = view.findViewById<ImageView>(R.id.image_item)
        val plantName:TextView? = view.findViewById(R.id.name_item)
        val plantDescription:TextView? = view.findViewById(R.id.description_item)
        val starIcon = view.findViewById<ImageView>(R.id.star_icon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(layoutId, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Récupérer les infos de la plante
        val currentPlant = plantList[position]

        // Récupéré le repository
        val repo = PlantRepository()

        // Utiliser glide pour récupérer l'image à partir de son lien -> composant
        Glide.with(context).load(Uri.parse(currentPlant.imageUrl)).into(holder.plantImage)

        // Mettre à jour le nom de la plante
        holder.plantName?.text = currentPlant.name

        // Mettre à jour la description de la plante
        holder.plantDescription?.text = currentPlant.description

        // verifier si la plante à été liké
        if (currentPlant.liked) {
            holder.starIcon.setImageResource(R.drawable.ic_star)
        } else {
            holder.starIcon.setImageResource(R.drawable.ic_unstar)
        }

        // rajouter une interaction sur cette étoile
        holder.starIcon.setOnClickListener {
            // Inverse si le bouton est like ou non
            currentPlant.liked = !currentPlant.liked
            // Mettre à jour l'objet plante
            repo.updatePlant(currentPlant)

        }

        // Interaction lors du clic sur une plante
        holder.itemView.setOnClickListener {
            // Afficher la popup
            PlantPopup(this, currentPlant).show()
        }

    }

    override fun getItemCount(): Int = plantList.size


}
