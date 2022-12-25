package com.raywenderlich.android.emojicalculator

import android.provider.ContactsContract.CommonDataKinds.StructuredName
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.runner.AndroidJUnit4
import com.raywenderlich.android.emojicalculator.ScreenRobot.Companion.withRobot
import org.hamcrest.Matchers.allOf
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @Test
    fun appLaunchesSuccessfully() {
        ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun onLaunchCheckAmountInputIsDisplayed() {
        ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.inputAmount)).check(matches(isDisplayed()))
    }

    @Test
    fun onLaunchOkayButtonIsDisplayed() {
        ActivityScenario.launch(MainActivity::class.java)
        onView(withText(R.string.okay)).check(matches(isDisplayed()))
    }

    @Test
    fun whenOkayButtonIsPressedAndAmountIsEmptyTipIsEmpty() {
        ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.buttonOkay)).perform(click())

        onView(allOf(withId(R.id.textTip), withText("")))
            .check(matches(isDisplayed()))
    }

    @Test
    fun whenOkayButtonIsPressedAndAmountIsFilledTipIsSet() {
        ActivityScenario.launch(MainActivity::class.java)

//        onView(withId(R.id.inputAmount))
//            .perform(typeText("11"))
//
//        onView(withId(R.id.buttonOkay)).perform(click())
//
//        onView(withId(R.id.textTip)).check(matches(withText("1.98")))

        withRobot(CalculatorScreenRobot::class.java)
            .inputCheckAmountAndSelectOkayButton("11")
            .verifyTipIsCorrect("1.98")
    }

    @Test
    fun whenOkayButtonIsPressedAndRoundSwitchIsSelectedAmountIsCorrect() {
        ActivityScenario.launch(MainActivity::class.java)

//        onView(withId(R.id.switchRound)).perform(click())
//
//        onView(withId(R.id.inputAmount)).perform(typeText("11"))
//
//        onView(withId(R.id.buttonOkay)).perform(click())
//
//        onView(withId(R.id.textTip)).check(matches(withText("2.00")))

        withRobot(CalculatorScreenRobot::class.java)
            .clickOkOnView(R.id.switchRound)
            .inputCheckAmountAndSelectOkayButton("11")
            .verifyTipIsCorrect("2.00")

    }

    class CalculatorScreenRobot: ScreenRobot<CalculatorScreenRobot>() {

        fun verifyTipIsCorrect(tip: String): CalculatorScreenRobot {
            return checkViewHasText(R.id.textTip, tip)
        }

        fun inputCheckAmountAndSelectOkayButton(input: String): CalculatorScreenRobot {
            return enterTextIntoView(R.id.inputAmount, input)
                .clickOkOnView(R.id.buttonOkay)
        }

    }
}
