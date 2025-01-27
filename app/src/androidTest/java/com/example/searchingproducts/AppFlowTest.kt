package com.example.searchingproducts

import android.view.View
import android.widget.EditText
import android.widget.SearchView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.runner.RunWith
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.Matchers.allOf
import org.junit.Test
import org.hamcrest.Matcher
import androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA

@RunWith(AndroidJUnit4::class)
class AppFlowTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @get:Rule
    val disableAnimationsRule = DisableAnimationRule()

    @Test
    fun testCompleteFlow() {
        // 1. Esperar el tiempo del splash
        Thread.sleep(3500)

        // 2. Verificar elementos del SearchFragment
        onView(withId(R.id.searchLayout))
            .check(matches(isDisplayed()))

        onView(withId(R.id.searchView))
            .check(matches(isDisplayed()))
            .perform(typeSearchViewText("iphone"))

        // 3. Verificar que aparece el loading
        onView(withId(R.id.progressBar))
            .check(matches(isDisplayed()))

        // 4. Esperar a que carguen los datos y desaparezca el loading
        Thread.sleep(2000)

        // 5. Ahora sí verificar el RecyclerView
        onView(withId(R.id.rvProducts))
            .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
    }


    private fun typeSearchViewText(text: String): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return allOf(
                    isDisplayed(),
                    isAssignableFrom(androidx.appcompat.widget.SearchView::class.java)
                )
            }

            override fun getDescription(): String {
                return "Type text into SearchView"
            }

            override fun perform(uiController: UiController, view: View) {
                val searchView = view as androidx.appcompat.widget.SearchView
                searchView.setQuery(text, true)
                uiController.loopMainThreadUntilIdle()
            }
        }
    }

    // Helper para esperar que una vista esté disponible
    private fun waitForView(viewId: Int, timeout: Long): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return isRoot()
            }

            override fun getDescription(): String {
                return "wait for a specific view with id $viewId"
            }

            override fun perform(uiController: UiController, view: View) {
                uiController.loopMainThreadForAtLeast(timeout)
            }
        }
    }
}