package fr.orgusdev.affirmations

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.runner.RunWith
import java.util.regex.Pattern.matches

RunWith(AndroidJUnit4::class)
@get:Rule

fun affirmationListTests() {
    @
    val activity = ActivityScenarioRule(MainActivity::class.java)

    onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(9))
    onView(withText(R.string.affirmation10)).check(matches(isDisplayed()))
}

