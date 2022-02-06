package fr.orgusdev.affirmations

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import fr.orgusdev.affirmations.adatper.ItemAdapter
import fr.orgusdev.affirmations.data.Datasource

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize data
        val myDataSet = Datasource().loadAffirmations()

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.adapter = ItemAdapter(this, myDataSet)

        // Use this setting to improve performance if you know that change
        // in content don't change the layout size of the RecyclerView

        recyclerView.setHasFixedSize(true)

    }
}