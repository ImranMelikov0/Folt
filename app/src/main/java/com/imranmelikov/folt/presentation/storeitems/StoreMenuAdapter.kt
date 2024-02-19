package com.imranmelikov.folt.presentation.storeitems

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.imranmelikov.folt.databinding.StoreMenuRvBinding
import com.imranmelikov.folt.domain.model.VenueDetails
import com.imranmelikov.folt.presentation.bottomsheetfragments.MenuBottomSheetFragment
import javax.inject.Inject

class StoreMenuAdapter @Inject constructor(private val appCompatActivity: AppCompatActivity):RecyclerView.Adapter<StoreMenuAdapter.StoreMenuViewHolder>() {
    class StoreMenuViewHolder(val binding:StoreMenuRvBinding):RecyclerView.ViewHolder(binding.root)

    // DiffUtil for efficient RecyclerView updates
    private val diffUtil=object : DiffUtil.ItemCallback<VenueDetails>(){
        override fun areItemsTheSame(oldItem: VenueDetails, newItem: VenueDetails): Boolean {
            return oldItem==newItem
        }

        override fun areContentsTheSame(oldItem: VenueDetails, newItem: VenueDetails): Boolean {
            return oldItem==newItem
        }
    }
    private val recyclerDiffer= AsyncListDiffer(this,diffUtil)

    // Getter and setter for the list of MenuList
    var storeMenuList:List<VenueDetails>
        get() = recyclerDiffer.currentList
        set(value) = recyclerDiffer.submitList(value)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreMenuViewHolder {
        val binding=StoreMenuRvBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return StoreMenuViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return storeMenuList.size
    }

    override fun onBindViewHolder(holder: StoreMenuViewHolder, position: Int) {
        val storeMenu=storeMenuList[position]
        holder.binding.menuName.text=storeMenu.menuName
        holder.binding.itemPrice.text=storeMenu.price.toString()
        Glide.with(holder.itemView.context)
            .load(storeMenu.imageUrl)
            .into(holder.binding.menuImage)

        val bottomSheetFragment = MenuBottomSheetFragment()
        holder.itemView.setOnClickListener {
            bottomSheetFragment.show((appCompatActivity).supportFragmentManager, bottomSheetFragment.tag)
            bottomSheetFragment.venueDetails=storeMenu
        }
    }
}