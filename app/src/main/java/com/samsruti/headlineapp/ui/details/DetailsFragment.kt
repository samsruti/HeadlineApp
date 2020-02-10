package com.samsruti.headlineapp.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.samsruti.headlineapp.databinding.DetailsFragmentBinding
import com.samsruti.headlineapp.util.convertToDatabaseModel

class DetailsFragment : Fragment() {

    private val detailsFragmentArgs: DetailsFragmentArgs by navArgs()

    private lateinit var viewModel: DetailsViewModel
    private lateinit var binding: DetailsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {




        binding = DetailsFragmentBinding.inflate(inflater)

        binding.lifecycleOwner = this

        binding.toolbar.setNavigationOnClickListener{view ->
            view.findNavController().navigateUp()
        }


        return binding.root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val selectedHeadline = detailsFragmentArgs.headlineDetails
        val viewModelFactory = DetailsViewModel.Factory(convertToDatabaseModel(selectedHeadline))
        viewModel = ViewModelProvider(this,viewModelFactory).get(DetailsViewModel::class.java)
        binding.viewModel = viewModel



//        viewModel.selectedHeadline.observe(this, Observer {
//            bindImage(activity!!.backgroundImage,selectedHeadline.urlToImage)
//        })

    }

}
