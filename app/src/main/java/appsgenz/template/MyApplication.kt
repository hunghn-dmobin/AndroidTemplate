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

@file:Suppress("unused")

package appsgenz.template

import android.app.Application
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application(), Configuration.Provider {
    override fun getWorkManagerConfiguration(): Configuration =
                Configuration.Builder()
                        .setMinimumLoggingLevel(if (BuildConfig.DEBUG) android.util.Log.DEBUG else android.util.Log.ERROR)
                        .build()

    private var appVisible = true

    override fun onCreate() {
        super.onCreate()
        appInstance = this
        val lifecycleObserver = AppLifecycleListener()
        ProcessLifecycleOwner.get().lifecycle.addObserver(lifecycleObserver)
    }

    val isAppVisible
        get() = appVisible

    companion object {

        private lateinit var appInstance: MyApplication

        fun get(): MyApplication {
            return appInstance
        }
    }

    inner class AppLifecycleListener : DefaultLifecycleObserver {

        override fun onStart(owner: LifecycleOwner) {
            super.onStart(owner)
            appVisible = true
        }

        override fun onStop(owner: LifecycleOwner) {
            super.onStop(owner)
            appVisible = false
        }
    }
}
