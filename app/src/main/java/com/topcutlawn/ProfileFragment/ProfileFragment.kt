package com.topcutlawn.ProfileFragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.topcutlawn.HomeFragment.Adapters.HomeAdapter
import com.topcutlawn.HomeFragment.HomeActivity
import com.topcutlawn.HomeFragment.HomeViewModel
import com.topcutlawn.R
import com.topcutlawn.databinding.FragmentFirstBinding
import com.topcutlawn.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var propertyPictureAdapter: PropertyPictureAdapter
    private lateinit var picturelist: List<PropertyPictureViewModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root



    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        picturepropertylist()

        binding.rvPropertypicture.layoutManager = LinearLayoutManager(requireContext())
        binding.rvPropertypicture.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        propertyPictureAdapter = PropertyPictureAdapter(picturelist,requireContext())

        binding.rvPropertypicture.adapter = propertyPictureAdapter

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun picturepropertylist() {
        picturelist = listOf(
            PropertyPictureViewModel(
                "",
            ),
            PropertyPictureViewModel(
                "",
            ),
            PropertyPictureViewModel(
                "",
            ),
            PropertyPictureViewModel(
                "",
            ),
            PropertyPictureViewModel(
                "",
            ),
            PropertyPictureViewModel(
                "",
            ),
            PropertyPictureViewModel(
                "",
            ),
        )
    }
}