/*
 * Copyright 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package androidx.compose.material

import android.os.Build
import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.testutils.assertAgainstGolden
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.InputMode
import androidx.compose.ui.input.InputModeManager
import androidx.compose.ui.platform.LocalInputModeManager
import androidx.compose.ui.test.captureToImage
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performMouseInput
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import androidx.test.filters.SdkSuppress
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.screenshot.AndroidXScreenshotTestRule
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@MediumTest
@RunWith(AndroidJUnit4::class)
@SdkSuppress(minSdkVersion = Build.VERSION_CODES.O)
class ButtonScreenshotTest {

    @get:Rule val rule = createComposeRule()

    @get:Rule val screenshotRule = AndroidXScreenshotTestRule(GOLDEN_MATERIAL)

    // TODO(b/267253920): Add a compose test API to set/reset InputMode.
    @After
    fun resetTouchMode() =
        with(InstrumentationRegistry.getInstrumentation()) {
            if (SDK_INT < 33) setInTouchMode(true) else resetInTouchMode()
        }

    @Test
    fun default_button() {
        rule.setMaterialContent { Button(onClick = {}) { Text("Button") } }

        rule
            .onNode(hasClickAction())
            .captureToImage()
            .assertAgainstGolden(screenshotRule, "button_default")
    }

    @Test
    fun disabled_button() {
        rule.setMaterialContent { Button(onClick = {}, enabled = false) { Text("Button") } }

        rule
            .onNodeWithText("Button")
            .captureToImage()
            .assertAgainstGolden(screenshotRule, "button_disabled")
    }

    @Test
    fun ripple() {
        rule.setMaterialContent {
            Box(Modifier.requiredSize(200.dp, 100.dp).wrapContentSize()) { Button(onClick = {}) {} }
        }

        // Start ripple
        rule.onNode(hasClickAction()).performTouchInput { down(center) }

        rule.waitForIdle()
        // Ripples are drawn on the RenderThread, not the main (UI) thread, so we can't
        // properly wait for synchronization. Instead just wait until after the ripples are
        // finished animating.
        Thread.sleep(300)

        rule.onRoot().captureToImage().assertAgainstGolden(screenshotRule, "button_ripple")
    }

    @Test
    fun hover() {
        rule.setMaterialContent {
            Box(Modifier.requiredSize(200.dp, 100.dp).wrapContentSize()) { Button(onClick = {}) {} }
        }

        rule.onNode(hasClickAction()).performMouseInput { enter(center) }

        rule.waitForIdle()

        rule.onRoot().captureToImage().assertAgainstGolden(screenshotRule, "button_hover")
    }

    @Test
    fun focus() {
        val focusRequester = FocusRequester()
        var localInputModeManager: InputModeManager? = null

        rule.setMaterialContent {
            localInputModeManager = LocalInputModeManager.current
            Box(Modifier.requiredSize(200.dp, 100.dp).wrapContentSize()) {
                Button(onClick = {}, modifier = Modifier.focusRequester(focusRequester)) {}
            }
        }

        rule.runOnIdle {
            localInputModeManager!!.requestInputMode(InputMode.Keyboard)
            focusRequester.requestFocus()
        }

        rule.waitForIdle()

        rule.onRoot().captureToImage().assertAgainstGolden(screenshotRule, "button_focus")
    }
}
