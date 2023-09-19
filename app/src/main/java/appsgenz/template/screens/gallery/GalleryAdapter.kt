package appsgenz.template.screens.gallery

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import appsgenz.template.R
import appsgenz.template.data.models.UnsplashPhoto
import appsgenz.template.databinding.ListItemPhotoBinding
import appsgenz.template.screens.photo.PhotoViewFragment
import appsgenz.template.screens.photo.PhotoViewFragmentArgs

/**
 * Adapter for the [RecyclerView] in [GalleryFragment].
 */

class GalleryAdapter(
) : PagingDataAdapter<UnsplashPhoto, GalleryAdapter.GalleryViewHolder>(GalleryDiffCallback()) {

    private var lastSelectedId: String? = null

    val expectsTransition: Boolean
        get() = lastSelectedId != null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        return GalleryViewHolder(
            ListItemPhotoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        val photo = getItem(position)
        if (photo != null) {
            ViewCompat.setTransitionName(holder.photoImage, "photoImage-${photo.id}")
            ViewCompat.setTransitionName(holder.photographer, "photographer-${photo.id}")
            holder.bind(photo)
            if (photo.id == lastSelectedId) {
                lastSelectedId = null
            }
        }
    }

    inner class GalleryViewHolder(
        private val binding: ListItemPhotoBinding
    ) : RecyclerView.ViewHolder(binding.root) {


        val photoImage: ImageView = itemView.findViewById(R.id.plant_photo)
        val photographer: TextView = itemView.findViewById(R.id.photographer)

        init {
            binding.setClickListener { view ->
                binding.photo?.let { photo ->
                    lastSelectedId = photo.id
//                    val uri = Uri.parse(photo.user.attributionUrl)
//                    val intent = Intent(Intent.ACTION_VIEW, uri)
//                    view.context.startActivity(intent)
                    view.findNavController().navigate(
                        R.id.action_gallery_fragment_to_photo_view_fragment,
                        PhotoViewFragmentArgs(photo).toBundle(),
                        null,
                        FragmentNavigatorExtras(
                            // We expand the card into the background.
                            // The fragment will use this first element as the epicenter of all the
                            // fragment transitions, including Explode for non-shared elements.
                            // The image is the focal element in this shared element transition.
                            photoImage to PhotoViewFragment.TRANSITION_NAME_IMAGE,
                            photographer to PhotoViewFragment.TRANSITION_NAME_NAME,
                        )
                    )
                }
            }
        }

        fun bind(item: UnsplashPhoto) {
            binding.apply {
                photo = item
                executePendingBindings()
            }
        }
    }
}

private class GalleryDiffCallback : DiffUtil.ItemCallback<UnsplashPhoto>() {
    override fun areItemsTheSame(oldItem: UnsplashPhoto, newItem: UnsplashPhoto): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: UnsplashPhoto, newItem: UnsplashPhoto): Boolean {
        return oldItem == newItem
    }
}
