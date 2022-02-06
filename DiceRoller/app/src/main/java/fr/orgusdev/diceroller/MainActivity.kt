package fr.orgusdev.diceroller

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rollButton: Button = findViewById(R.id.button)

        rollButton.setOnClickListener { rollDice() }

        // Do a dice roll when the app start
        rollDice()
    }




    // Roule le dé et met à jour l'écran avec le résultat
    private fun rollDice() {
        val dice = Dice(6)
        val diceRoll = dice.roll()
        val diceRoll2 = dice.roll()

        // Dé 1

        val diceImage: ImageView = findViewById(R.id.imageView)


        val drawableResource = when (diceRoll) {
            1 -> (R.drawable.dice_1)
            2 -> (R.drawable.dice_2)
            3 -> (R.drawable.dice_3)
            4 -> (R.drawable.dice_4)
            5 -> (R.drawable.dice_5)
            else -> (R.drawable.dice_6)

        }
        diceImage.setImageResource(drawableResource)
        diceImage.contentDescription = diceRoll.toString()


        // Dé 2
        val diceImage2:ImageView = findViewById(R.id.imageView2)

        val drawableResource2 = when (diceRoll2) {
            1 -> (R.drawable.dice_1)
            2 -> (R.drawable.dice_2)
            3 -> (R.drawable.dice_3)
            4 -> (R.drawable.dice_4)
            5 -> (R.drawable.dice_5)
            else -> (R.drawable.dice_6)

        }
        diceImage2.setImageResource(drawableResource2)
        diceImage2.contentDescription = diceRoll2.toString()


    }
}

class Dice(private val numSide: Int) {
    fun roll(): Int {
        return (1..numSide).random()
    }
}