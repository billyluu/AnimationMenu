package com.billy.animationmenudemo

import android.support.test.espresso.Espresso
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.matcher.ViewMatchers.withId
import org.junit.After
import org.junit.Before

import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

public class AnimationMenuTest {

    @Rule
    var activityTestRule = AnimationMenuTest()

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun test() {
        Espresso.onView(withId(R.id.menu_fab)).perform(click())
    }
}