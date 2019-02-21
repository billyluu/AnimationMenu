package com.billy.animationmenudemo


import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.view.View
import android.view.ViewGroup
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest2 {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun mainActivityTest2() {
        val floatingActionButton = onView(
            allOf(
                withId(R.id.menu_fab),
                childAtPosition(
                    allOf(
                        withId(R.id.animation_menu),
                        childAtPosition(
                            withClassName(`is`("android.support.constraint.ConstraintLayout")),
                            1
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        floatingActionButton.perform(click())

        val floatingActionButton2 = onView(
            allOf(
                childAtPosition(
                    allOf(
                        withId(R.id.animation_menu),
                        childAtPosition(
                            withClassName(`is`("android.support.constraint.ConstraintLayout")),
                            1
                        )
                    ),
                    5
                ),
                isDisplayed()
            )
        )
        floatingActionButton2.perform(click())

        val floatingActionButton3 = onView(
            allOf(
                childAtPosition(
                    allOf(
                        withId(R.id.animation_menu),
                        childAtPosition(
                            withClassName(`is`("android.support.constraint.ConstraintLayout")),
                            1
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        floatingActionButton3.perform(click())

        val floatingActionButton4 = onView(
            allOf(
                childAtPosition(
                    allOf(
                        withId(R.id.animation_menu),
                        childAtPosition(
                            withClassName(`is`("android.support.constraint.ConstraintLayout")),
                            1
                        )
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        floatingActionButton4.perform(click())

        val floatingActionButton5 = onView(
            allOf(
                childAtPosition(
                    allOf(
                        withId(R.id.animation_menu),
                        childAtPosition(
                            withClassName(`is`("android.support.constraint.ConstraintLayout")),
                            1
                        )
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        floatingActionButton5.perform(click())

        val floatingActionButton6 = onView(
            allOf(
                childAtPosition(
                    allOf(
                        withId(R.id.animation_menu),
                        childAtPosition(
                            withClassName(`is`("android.support.constraint.ConstraintLayout")),
                            1
                        )
                    ),
                    4
                ),
                isDisplayed()
            )
        )
        floatingActionButton6.perform(click())

        val floatingActionButton7 = onView(
            allOf(
                withId(R.id.menu_fab),
                childAtPosition(
                    allOf(
                        withId(R.id.animation_menu),
                        childAtPosition(
                            withClassName(`is`("android.support.constraint.ConstraintLayout")),
                            1
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        floatingActionButton7.perform(click())
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
