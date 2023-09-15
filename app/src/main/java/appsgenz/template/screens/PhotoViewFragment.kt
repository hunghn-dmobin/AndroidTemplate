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

package appsgenz.template.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.transition.ChangeBounds
import androidx.transition.ChangeImageTransform
import androidx.transition.ChangeTransform
import androidx.transition.Transition
import appsgenz.template.LARGE_COLLAPSE_DURATION
import appsgenz.template.LARGE_EXPAND_DURATION
import appsgenz.template.R
import appsgenz.template.databinding.FragmentPhotoViewBinding
import appsgenz.template.transitions.SharedFade
import appsgenz.template.utilities.FAST_OUT_SLOW_IN
import appsgenz.template.utilities.plusAssign
import appsgenz.template.utilities.transitionTogether
import appsgenz.template.viewmodels.PhotoViewModel

class PhotoViewFragment : Fragment() {

    companion object {
        const val TRANSITION_NAME_IMAGE = "image"
        const val TRANSITION_NAME_NAME = "name"
    }

    private val photoViewModel: PhotoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = createSharedElementTransition(LARGE_EXPAND_DURATION)
        sharedElementReturnTransition = createSharedElementTransition(LARGE_COLLAPSE_DURATION)
    }

    private fun createSharedElementTransition(duration: Long): Transition {
        return transitionTogether {
            this.duration = duration
            interpolator = FAST_OUT_SLOW_IN
            this += SharedFade()
            this += ChangeImageTransform()
            this += ChangeBounds()
            this += ChangeTransform()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<FragmentPhotoViewBinding>(
            inflater,
            R.layout.fragment_photo_view,
            container,
            false
        ).apply {
//            postponeEnterTransition(500L, TimeUnit.MILLISECONDS)

            val name: TextView =  root.findViewById(R.id.photographer)
            val image: ImageView =  root.findViewById(R.id.plant_photo)

            // Transition names. Note that they don't need to match with the names of the selected grid
            // item. They only have to be unique in this fragment.
            ViewCompat.setTransitionName(image, TRANSITION_NAME_IMAGE)
            ViewCompat.setTransitionName(name, TRANSITION_NAME_NAME)

            viewModel = photoViewModel
            lifecycleOwner = viewLifecycleOwner

            toolbar.setNavigationOnClickListener { view ->
                view.findNavController().navigateUp()
            }

        }
        return binding.root
    }
}