package pwr.edu.myinfo


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun mainActivityTest() {
        val textInputEditText = onView(
            allOf(
                withId(R.id.bmiF_height_editText),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.bmiF_height_input),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText.perform(replaceText("187"), closeSoftKeyboard())

        val textInputEditText2 = onView(
            allOf(
                withId(R.id.bmiF_height_editText), withText("187"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.bmiF_height_input),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText2.perform(click())

        val textInputEditText3 = onView(
            allOf(
                withId(R.id.bmiF_mass_editText),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.bmiF_mass_input),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText3.perform(replaceText("82.5"), closeSoftKeyboard())

        val materialButton = onView(
            allOf(
                withId(R.id.bmiF_calcuateBt), withText("Calculate BMI"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.MyNavHostFragment),
                        0
                    ),
                    5
                ),
                isDisplayed()
            )
        )
        materialButton.perform(click())

        val materialTextView = onView(
            allOf(
                withId(R.id.bmiF_result), withText("23.592"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.MyNavHostFragment),
                        0
                    ),
                    6
                ),
                isDisplayed()
            )
        )
        materialTextView.perform(click())

        val appCompatImageButton = onView(
            allOf(
                withContentDescription("Navigate up"),
                childAtPosition(
                    allOf(
                        withId(R.id.action_bar),
                        childAtPosition(
                            withId(R.id.action_bar_container),
                            0
                        )
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        appCompatImageButton.perform(click())

        val actionMenuItemView = onView(
            allOf(
                withId(R.id.action_imperial), withText("Imperial"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.action_bar),
                        1
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        actionMenuItemView.perform(click())

        val materialButton2 = onView(
            allOf(
                withId(R.id.bmiF_calcuateBt), withText("Calculate BMI"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.MyNavHostFragment),
                        0
                    ),
                    5
                ),
                isDisplayed()
            )
        )
        materialButton2.perform(click())

        val overflowMenuButton = onView(
            allOf(
                withContentDescription("More options"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.action_bar),
                        1
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        overflowMenuButton.perform(click())

        val materialTextView2 = onView(
            allOf(
                withId(R.id.title), withText("About"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.content),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        materialTextView2.perform(click())

        val appCompatImageButton2 = onView(
            allOf(
                withContentDescription("Navigate up"),
                childAtPosition(
                    allOf(
                        withId(R.id.action_bar),
                        childAtPosition(
                            withId(R.id.action_bar_container),
                            0
                        )
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        appCompatImageButton2.perform(click())

        val textInputEditText4 = onView(
            allOf(
                withId(R.id.bmiF_height_editText), withText("187"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.bmiF_height_input),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText4.perform(replaceText("100"))

        val textInputEditText5 = onView(
            allOf(
                withId(R.id.bmiF_height_editText), withText("100"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.bmiF_height_input),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText5.perform(closeSoftKeyboard())

        val textInputEditText6 = onView(
            allOf(
                withId(R.id.bmiF_mass_editText), withText("82.5"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.bmiF_mass_input),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText6.perform(replaceText("300"))

        val textInputEditText7 = onView(
            allOf(
                withId(R.id.bmiF_mass_editText), withText("300"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.bmiF_mass_input),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText7.perform(closeSoftKeyboard())

        val actionMenuItemView2 = onView(
            allOf(
                withId(R.id.action_imperial), withText("Imperial"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.action_bar),
                        1
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        actionMenuItemView2.perform(click())

        val textInputEditText8 = onView(
            allOf(
                withId(R.id.bmiF_height_editText), withText("100"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.bmiF_height_input),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText8.perform(click())
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
