package com.imranmelikov.folt.presentation.address

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imranmelikov.folt.constants.ErrorMsgConstants
import com.imranmelikov.folt.domain.model.Address
import com.imranmelikov.folt.domain.model.CRUD
import com.imranmelikov.folt.domain.repository.FoltRepository
import com.imranmelikov.folt.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddressViewModel @Inject constructor(private val repository: FoltRepository):ViewModel() {
    private val mutableAddressLiveData= MutableLiveData<Resource<List<Address>>>()
    val addressLiveData: LiveData<Resource<List<Address>>>
        get() = mutableAddressLiveData

    private val exceptionHandlerAddress = CoroutineExceptionHandler { _, throwable ->
        println("${ErrorMsgConstants.error} ${throwable.localizedMessage}")
        mutableAddressLiveData.value= Resource.error(ErrorMsgConstants.errorViewModel,null)
    }

    private val mutableMessageLiveData= MutableLiveData<Resource<CRUD>>()
    val msgLiveData: LiveData<Resource<CRUD>>
        get() = mutableMessageLiveData

    private val exceptionHandlerMessage = CoroutineExceptionHandler { _, throwable ->
        println("${ErrorMsgConstants.error} ${throwable.localizedMessage}")
        mutableMessageLiveData.value= Resource.error(ErrorMsgConstants.errorViewModel,null)
    }

    fun getAddress(){
        mutableAddressLiveData.value=Resource.loading(null)
        viewModelScope.launch(Dispatchers.IO + exceptionHandlerAddress){
            val response=repository.getAddress()
            viewModelScope.launch(Dispatchers.Main + exceptionHandlerAddress){
                response.data?.let {
                    mutableAddressLiveData.value=Resource.success(it)
                }
            }
        }
    }
    fun insertAddress(address: Address){
        mutableMessageLiveData.value=Resource.loading(null)
        viewModelScope.launch(Dispatchers.IO + exceptionHandlerMessage){
           val response= repository.insertAddress(address)
            viewModelScope.launch(Dispatchers.Main + exceptionHandlerMessage){
                response.data?.let {
                    mutableMessageLiveData.value=Resource.success(it)
                }
            }
        }
        getAddress()
    }
    fun updateAddress(documentId:String,address: Address){
        mutableMessageLiveData.value=Resource.loading(null)
        viewModelScope.launch(Dispatchers.IO + exceptionHandlerMessage){
            val response=repository.updateAddress(documentId,address)
            viewModelScope.launch(Dispatchers.Main + exceptionHandlerMessage){
                response.data?.let {
                    mutableMessageLiveData.value=Resource.success(it)
                }
            }
        }
        getAddress()
    }
    fun deleteAddress(documentId: String){
        mutableMessageLiveData.value=Resource.loading(null)
        viewModelScope.launch(Dispatchers.IO + exceptionHandlerMessage){
            val response=repository.deleteAddress(documentId)
            viewModelScope.launch(Dispatchers.Main + exceptionHandlerMessage){
                response.data?.let {
                    mutableMessageLiveData.value=Resource.success(it)
                }
            }
        }
        getAddress()
    }
}