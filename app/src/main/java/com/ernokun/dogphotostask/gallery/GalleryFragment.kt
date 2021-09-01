package com.ernokun.dogphotostask.gallery

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.ernokun.dogphotostask.databinding.FragmentGalleryBinding
import com.ernokun.dogphotostask.gallery.model.DogPictureResponse
import com.ernokun.dogphotostask.util.readJsonAsset
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GalleryFragment : Fragment() {

    private var binding: FragmentGalleryBinding? = null

    private var adapter: DogPictureAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentBinding = FragmentGalleryBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = DogPictureAdapter { url: String ->
            val action = GalleryFragmentDirections.actionGalleryFragmentToDetailFragment(url)
            findNavController().navigate(action)
        }

        binding?.apply {
            dogPictureRecyclerview.addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(
                    outRect: Rect, view: View,
                    parent: RecyclerView, state: RecyclerView.State
                ) {
                    outRect.top = OFFSET_SIZE
                    outRect.left = OFFSET_SIZE
                    outRect.right = OFFSET_SIZE
                    outRect.bottom = OFFSET_SIZE
                }
            })

            dogPictureRecyclerview.adapter = adapter
            adapter!!.submitList(getDogPictureUrls())
        }
    }

    /**
     * I wasn't really sure on how to correctly store this data in a ViewModel,
     * so I just read this data and passed it to the adapter directly.
     */
    private fun getDogPictureUrls(): List<String> {
        val dogInfoJsonString = requireContext().readJsonAsset(DOG_URL_ASSET_FILE_NAME)
        val dogPictureResponse = Gson().fromJson(dogInfoJsonString, DogPictureResponse::class.java)
        return dogPictureResponse.urls
    }

    override fun onDestroyView() {
        adapter = null
        binding = null
        super.onDestroyView()
    }

    private companion object {
        const val DOG_URL_ASSET_FILE_NAME = "dog_urls.json"
        const val OFFSET_SIZE = 10
    }
}