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

package appsgenz.template.screens.gallery

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.transition.Explode
import androidx.transition.Slide
import appsgenz.template.LARGE_COLLAPSE_DURATION
import appsgenz.template.LARGE_EXPAND_DURATION
import appsgenz.template.R
import appsgenz.template.base.BaseAppBindingFragment
import appsgenz.template.base.BaseAppFragment
import appsgenz.template.databinding.FragmentGalleryBinding
import appsgenz.template.utilities.FAST_OUT_LINEAR_IN
import appsgenz.template.utilities.LINEAR_OUT_SLOW_IN
import appsgenz.template.utilities.plusAssign
import appsgenz.template.utilities.transitionTogether
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class GalleryFragment : BaseAppBindingFragment<FragmentGalleryBinding>() {

    override val layoutRes: Int
        get() = R.layout.fragment_gallery

    private val adapter = GalleryAdapter()

    private val args: GalleryFragmentArgs by navArgs()
    private var searchJob: Job? = null
    private val viewModel: GalleryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // This is the transition to be used for non-shared elements when we are opening the detail
        // screen.
        exitTransition = transitionTogether {
            duration = LARGE_EXPAND_DURATION / 2
            interpolator = FAST_OUT_LINEAR_IN
            // The app bar.
            this += Slide(Gravity.TOP).apply {
                mode = Slide.MODE_OUT
                addTarget(R.id.appbar)
            }
            // The grid items.
            this += Explode().apply {
                mode = Explode.MODE_OUT
                excludeTarget(R.id.appbar, true)
            }
        }

        // This is the transition to be used for non-shared elements when we are return back from
        // the detail screen.
        reenterTransition = transitionTogether {
            duration = LARGE_COLLAPSE_DURATION / 2
            interpolator = LINEAR_OUT_SLOW_IN
            // The app bar.
            this += Slide(Gravity.TOP).apply {
                mode = Slide.MODE_IN
                addTarget(R.id.appbar)
            }
            // The grid items.
            this += Explode().apply {
                // The grid items should start imploding after the app bar is in.
                startDelay = LARGE_COLLAPSE_DURATION / 2
                mode = Explode.MODE_IN
                excludeTarget(R.id.appbar, true)
            }
        }
    }

    override fun onBindingView(viewBinding: FragmentGalleryBinding) {
        binding.photoList.adapter = adapter
        search(args.plantName)

        binding.toolbar.setNavigationOnClickListener { view ->
            view.findNavController().navigateUp()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (adapter.expectsTransition) {
            // We are transitioning back from CheeseDetailFragment.
            // Postpone the transition animation until the destination item is ready.
            postponeEnterTransition(500L, TimeUnit.MILLISECONDS)
        }
    }

    private fun search(query: String) {
        // Make sure we cancel the previous job before creating a new one
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            viewModel.searchPictures(query).collectLatest {
                adapter.submitData(it)
            }
        }
    }
}
