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

package appsgenz.template.screens.garden

import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import appsgenz.template.R
import appsgenz.template.base.BaseAppBindingFragment
import appsgenz.template.databinding.FragmentGardenBinding
import appsgenz.template.screens.home.PLANT_LIST_PAGE_INDEX
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GardenFragment : BaseAppBindingFragment<FragmentGardenBinding>() {

    override val layoutRes: Int
        get() = R.layout.fragment_garden

    private val viewModel: GardenPlantingListViewModel by viewModels()

    override fun onBindingView(viewBinding: FragmentGardenBinding) {
        val adapter = GardenPlantingAdapter()
        binding.gardenList.adapter = adapter

        binding.addPlant.setOnClickListener {
            navigateToPlantListPage()
        }

        subscribeUi(adapter, binding)
    }

    private fun subscribeUi(adapter: GardenPlantingAdapter, binding: FragmentGardenBinding) {
        viewModel.plantAndGardenPlantings.observe(viewLifecycleOwner) { result ->
            binding.hasPlantings = result.isNotEmpty()
            adapter.submitList(result) {
                // At this point, the content should be drawn
                activity?.reportFullyDrawn()
            }
        }
    }

    // TODO: convert to data binding if applicable
    private fun navigateToPlantListPage() {
        requireActivity().findViewById<ViewPager2>(R.id.view_pager).currentItem =
            PLANT_LIST_PAGE_INDEX
    }
}
