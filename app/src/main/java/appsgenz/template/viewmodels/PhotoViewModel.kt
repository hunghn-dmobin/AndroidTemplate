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

package appsgenz.template.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import appsgenz.template.data.models.UnsplashPhoto
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import appsgenz.template.screens.PhotoViewFragment

/**
 * The ViewModel used in [PhotoViewFragment].
 */
@HiltViewModel
class PhotoViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val photo: UnsplashPhoto = savedStateHandle.get<UnsplashPhoto>(PHOTO_SAVED_STATE_KEY)!!

    companion object {
        private const val PHOTO_SAVED_STATE_KEY = "photoKey"
    }
}
