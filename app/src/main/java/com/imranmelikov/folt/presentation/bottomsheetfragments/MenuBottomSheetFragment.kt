package com.imranmelikov.folt.presentation.bottomsheetfragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.imranmelikov.folt.databinding.FragmentMenuBottomSheetBinding
import com.imranmelikov.folt.domain.model.VenueDetails
import com.imranmelikov.folt.presentation.venuedetails.VenueDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MenuBottomSheetFragment:BottomSheetDialogFragment() {
    private lateinit var binding:FragmentMenuBottomSheetBinding
     lateinit var venueDetails: VenueDetails
//    private val viewModel: VenueDetailsViewModel by viewModels()
    var onItemClick:((Double)->Unit)?=null
    private var count=1
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentMenuBottomSheetBinding.inflate(inflater,container,false)

        binding.downBtn.setOnClickListener {
            dismiss()
        }
        getVenueDetail(venueDetails)
        return binding.root
    }

   private fun getVenueDetail(venueDetails: VenueDetails){
        binding.menuTitleText.text=venueDetails.menuName
        binding.menuText.text=venueDetails.about
        binding.menuPriceText.text=venueDetails.price.toDouble().toString()
        "Add order ${venueDetails.price.toDouble()} Azn".also { binding.orderBtn.text = it }

       binding.plusBtn.setOnClickListener {
           if (count<venueDetails.stock.toInt()){
               count+=1
               "Add order ${count * venueDetails.price.toDouble()} Azn".also { binding.orderBtn.text = it }
               binding.countText.text=count.toString()
           }
       }
       binding.minusBtn.setOnClickListener {
           if (count>1){
               count-=1
               "Add order ${count * venueDetails.price.toDouble()} Azn".also { binding.orderBtn.text = it }
               binding.countText.text=count.toString()
           }
       }
       binding.orderBtn.setOnClickListener {
           onItemClick?.let {
               it(count*venueDetails.price.toDouble())
               count=1
           }
           dismiss()
       }
    }
}