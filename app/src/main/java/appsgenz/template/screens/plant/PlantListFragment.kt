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

package appsgenz.template.screens.plant

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import appsgenz.template.R
import appsgenz.template.base.BaseAppBindingFragment
import appsgenz.template.base.BaseAppFragment
import appsgenz.template.databinding.FragmentPlantListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlantListFragment : BaseAppBindingFragment<FragmentPlantListBinding>() {

    override val layoutRes: Int
        get() = R.layout.fragment_plant_list

    private val viewModel: PlantListViewModel by viewModels()

    override fun onBindingView(viewBinding: FragmentPlantListBinding) {
        val adapter = PlantAdapter()
        binding.plantList.adapter = adapter
        subscribeUi(adapter)

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_plant_list, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.filter_zone -> {
                updateData()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun subscribeUi(adapter: PlantAdapter) {
        viewModel.plants.observe(viewLifecycleOwner) { plants ->
            adapter.submitList(plants)
        }
    }

    private fun updateData() {
        with(viewModel) {
            if (isFiltered()) {
                clearGrowZoneNumber()
            } else {
                setGrowZoneNumber(9)
            }
        }
    }
}
