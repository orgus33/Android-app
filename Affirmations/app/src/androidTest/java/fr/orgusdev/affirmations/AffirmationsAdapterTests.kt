package fr.orgusdev.affirmations

import android.content.Context
import fr.orgusdev.affirmations.adatper.ItemAdapter
import fr.orgusdev.affirmations.model.Affirmation
import junit.framework.Assert.assertEquals
import org.junit.Test

class AffirmationsAdapterTests {
    private val context = mock(Context::class.java)

    @Test
    fun adaptater_size() {
        val data = listOf(
            Affirmation(R.string.affirmation1, R.drawable.image1),
            Affirmation(R.string.affirmation2, R.drawable.image2)
        )
        val adapter = ItemAdapter(context, data)
        assertEquals("ItemAdapter is not the correct size", data.size, adapter.itemCount)
    }

}