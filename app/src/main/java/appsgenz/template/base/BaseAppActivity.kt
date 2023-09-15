/*
 * Copyright 2023 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package appsgenz.template.base

import android.view.KeyEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import appsgenz.template.utilities.goBack
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author HungHN on 09/15/2023.
 */

@AndroidEntryPoint
abstract class BaseAppActivity : AppCompatActivity() {

    open val progressView: View?
        get() = null

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        val isHandled = doDuringOnKeyDown(keyCode)

        return isHandled || super.onKeyDown(keyCode, event)
    }

    override fun onBackPressed() {
        if (!doOnBackPress())
            super.onBackPressed()
    }

    private fun doDuringOnKeyDown(keyCode: Int): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_BACK) {
            callBackHandlerOnActivePage()
        } else false

    }

    private fun doOnBackPress(): Boolean {
        return callBackHandlerOnActivePage()
    }

    private fun callBackHandlerOnActivePage(): Boolean {
        return goBack()
    }

    override fun onSupportNavigateUp(): Boolean {
        // Allows NavigationUI to support proper up navigation or the drawer layout
        // drawer menu, depending on the situation
        return if (doOnBackPress()) true else super.onSupportNavigateUp()
    }
}
