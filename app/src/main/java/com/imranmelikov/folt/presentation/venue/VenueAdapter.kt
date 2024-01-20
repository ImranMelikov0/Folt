package com.imranmelikov.folt.presentation.venue

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.imranmelikov.folt.R
import com.imranmelikov.folt.databinding.VenuesRvBinding
import com.imranmelikov.folt.domain.model.Venue
import com.imranmelikov.folt.util.ArgumentConstants
import com.imranmelikov.folt.util.VenueCategoryConstants

class VenueAdapter:RecyclerView.Adapter<VenueAdapter.VenueViewHolder>() {
    class VenueViewHolder(val binding:VenuesRvBinding):RecyclerView.ViewHolder(binding.root)

    // viewType for Fragment
    var viewType:String=""
    // viewType for Venue
    var viewTypeVenue:String=""

    // DiffUtil for efficient RecyclerView updates
    private val diffUtil=object : DiffUtil.ItemCallback<Venue>(){
        override fun areItemsTheSame(oldItem: Venue, newItem: Venue): Boolean {
            return oldItem==newItem
        }

        override fun areContentsTheSame(oldItem: Venue, newItem: Venue): Boolean {
            return oldItem==newItem
        }
    }
    private val recyclerDiffer= AsyncListDiffer(this,diffUtil)

    // Getter and setter for the list of VenueList
    var venueList:List<Venue>
        get() = recyclerDiffer.currentList
        set(value) = recyclerDiffer.submitList(value)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VenueViewHolder {
        val binding=VenuesRvBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return VenueViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return venueList.size
    }

    override fun onBindViewHolder(holder: VenueViewHolder, position: Int) {
        val venueArraylist=venueList[position]
        holder.binding.restaurantName.text=venueArraylist.venueName
        holder.binding.restaurantText.text=venueArraylist.venueText
        holder.binding.venuesDeliveryTimeBtn.text=venueArraylist.delivery.deliveryTime
        "${venueArraylist.delivery.deliveryPrice} Azn".also { holder.binding.deliveryText.text = it }
        holder.binding.ratingText.text=venueArraylist.venuePopularity.rating.toString()

        clickFavIcon(venueArraylist,holder.binding.favIcon)
        setRatingIcon(venueArraylist,holder.binding.ratingIcon)
        setDeliveryTextColor(venueArraylist,holder)

        val bundleRestaurant = Bundle().apply {
            putSerializable(ArgumentConstants.venues,venueArraylist)
            putString(VenueCategoryConstants.Venue,VenueCategoryConstants.Restaurant)
        }
        val bundleStore = Bundle().apply {
            putSerializable(ArgumentConstants.venues,venueArraylist)
            putString(VenueCategoryConstants.Venue,VenueCategoryConstants.Store)
        }
        holder.itemView.setOnClickListener {
            when(viewType){
                VenueCategoryConstants.Restaurant->{
                    Navigation.findNavController(it).navigate(R.id.action_restaurantFragment_to_venueDetailsFragment,bundleRestaurant)
                }
                VenueCategoryConstants.Store->{
                    Navigation.findNavController(it).navigate(R.id.action_storeFragment_to_venueDetailsFragment,bundleStore)
                }
                VenueCategoryConstants.Venue->{
                    when(viewTypeVenue){
                        VenueCategoryConstants.Restaurant->{
                            Navigation.findNavController(it).navigate(R.id.action_venueFragment_to_venueDetailsFragment,bundleRestaurant)
                        }
                        VenueCategoryConstants.Store->{
                            Navigation.findNavController(it).navigate(R.id.action_venueFragment_to_venueDetailsFragment,bundleStore)
                        }
                    }
                }
            }
        }
    }

    // click favIcon
    private fun clickFavIcon(venueArraylist:Venue,favIcon: ImageView){
        favIcon.setOnClickListener {
            // Remove venue from favorites
            if (venueArraylist.venuePopularity.favorite){
                favIcon.setImageResource(R.drawable.heart_outline)
                venueArraylist.venuePopularity.favorite=false
            }else{
                // Add venue to favorites
                favIcon.setImageResource(R.drawable.heart_inline)
                venueArraylist.venuePopularity.favorite=true
            }
        }
    }
    // set RatingIcon
    private fun setRatingIcon(venueArraylist: Venue,ratingIcon: ImageView){
        when {
            // Rating less than 5,00
            venueArraylist.venuePopularity.rating < 5.00 ->{
                ratingIcon.setImageResource(R.drawable.emoticon_sad)
            }
            venueArraylist.venuePopularity.rating <9.00 -> {
                //Rating less than 9.00
                ratingIcon.setImageResource(R.drawable.emoticon_happy)
            }
            else ->{
                //Rating more than 9.00
                ratingIcon.setImageResource(R.drawable.emoticon_very_happy)
            }
        }
    }
    // set deliveryTextColor
    private fun setDeliveryTextColor(venueArraylist: Venue,holder: VenueViewHolder){
        // DeliveryPrice more than 0,00
        if (venueArraylist.delivery.deliveryPrice.toDouble()>0.00){
            holder.binding.deliveryText.setTextColor(
                ContextCompat.getColor(holder.itemView.context,
                    R.color.black))
        }else{
            // DeliveryPrice equal 0,00
            holder.binding.deliveryText.setTextColor(
                ContextCompat.getColor(holder.itemView.context,
                    R.color.open_blue))
        }
    }
}