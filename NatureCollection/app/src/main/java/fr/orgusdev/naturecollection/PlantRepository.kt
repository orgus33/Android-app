package fr.orgusdev.naturecollection

import android.net.Uri
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import fr.orgusdev.naturecollection.PlantRepository.Singleton.databaseRef
import fr.orgusdev.naturecollection.PlantRepository.Singleton.downloadUri
import fr.orgusdev.naturecollection.PlantRepository.Singleton.plantList
import fr.orgusdev.naturecollection.PlantRepository.Singleton.storageReference
import java.net.URI
import java.util.*


class PlantRepository {

    object Singleton {
        // donner le lien pour acceder au bucket
        private val BUCKET_URL: String ="gs://naturecollection-f0fdb.appspot.com"

        // se connecter à notre espace de stockage
        val storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(BUCKET_URL)

        // se connecter à la référence "plants"
        val databaseRef = FirebaseDatabase.getInstance().getReference("plants")

        // créer une liste qui va contenir nos plantes
        val plantList = arrayListOf<PlantModel>()

        // contenir le lien de l'image courante
        var downloadUri: Uri? = null
    }


    fun updateData(callback: () -> Unit) {
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // retirer les anciennes
                plantList.clear()
                // recolter la liste
                for (ds in snapshot.children) {
                    // construire un objet plante
                    val plant = ds.getValue(PlantModel::class.java)

                    //verifier que la plante n'est pas null
                    if (plant != null) {
                        // ajouter la plante a notre base
                        plantList.add(plant)
                    }
                }
                // actionner le callback
                callback()
            }

            override fun onCancelled(error: DatabaseError) {}

        })
    }

    // créer une fonction pour envoyé des fichier sur le storage
    fun uploadImage(file: Uri, callback: () -> Unit) {
        // vérifier que ce fichier n'est pas null
        if (file != null) {
            val fileName = UUID.randomUUID().toString() + ".jpg"
            val ref = storageReference.child(fileName)
            val uploadTask = ref.putFile(file)

            // demarrer la tache d'envoi
            uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->

                // Si il y a eu un problème lors de l'envoi du fichier
                if (!task.isSuccessful) {
                    task.exception?.let { throw it }
                }
                return@Continuation ref.downloadUrl

            }).addOnCompleteListener { task ->
                // véeifier si tout a bien fonctionner
                if (task.isSuccessful) {
                    // récupérer l'image
                    downloadUri = task.result
                    callback()
                }
            }
        }
    }




    // mettre à jour un objet plante en base de donné
    fun updatePlant(plant:PlantModel) = databaseRef.child(plant.id).setValue(plant)

    // inserer une nouvelle plante en base de donné
    fun insertPlant(plant:PlantModel) = databaseRef.child(plant.id).setValue(plant)

    // Supprimer une plante de la base
    fun deletePlant(plant: PlantModel) = databaseRef.child(plant.id).removeValue()


}