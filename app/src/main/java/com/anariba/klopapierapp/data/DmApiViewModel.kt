package com.anariba.klopapierapp.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anariba.klopapierapp.data.model.DmResponseItem
import com.anariba.klopapierapp.data.model.StoreAvailabilityItem
import com.anariba.klopapierapp.data.model.StoreItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class DmApiViewModel : ViewModel(){

    private val apiService = DmApiService()
    private val disposable = CompositeDisposable()

    val responseCode = MutableLiveData<Int>()

    private val stores = StoreIds.STORES
    private val storeAvailabilities = ArrayList<StoreAvailabilityItem>()

    val storesList = MutableLiveData<ArrayList<StoreAvailabilityItem>>()
    val singleStore = MutableLiveData<StoreAvailabilityItem>()

    fun getAllStores(){
        for(storeId in stores){
            getStoreAvailability(storeId, false)
        }
    }

    fun getSingleStoreFromId(storeId: Int){
        getStoreAvailability(storeId, true)
    }

    private fun getStoreAvailability(storeId: Int, isSingle : Boolean) {

        disposable.add(
            apiService.getAvailabilityFromStoreId(storeId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<DmResponseItem>(){
                    override fun onSuccess(item: DmResponseItem) {

                        var totalAmount = 0
                        totalAmount += item.storeAvailabilities.toiletP1[0].stockLevel
                        totalAmount += item.storeAvailabilities.toiletP2[0].stockLevel
                        totalAmount += item.storeAvailabilities.toiletP3[0].stockLevel
                        totalAmount += item.storeAvailabilities.toiletP4[0].stockLevel
                        totalAmount += item.storeAvailabilities.toiletP5[0].stockLevel
                        totalAmount += item.storeAvailabilities.toiletP6[0].stockLevel
                        totalAmount += item.storeAvailabilities.toiletP7[0].stockLevel
                        totalAmount += item.storeAvailabilities.toiletP8[0].stockLevel
                        totalAmount += item.storeAvailabilities.toiletP9[0].stockLevel
                        totalAmount += item.storeAvailabilities.toiletP10[0].stockLevel
                        totalAmount += item.storeAvailabilities.toiletP11[0].stockLevel
                        totalAmount += item.storeAvailabilities.toiletP12[0].stockLevel
                        totalAmount += item.storeAvailabilities.toiletP13[0].stockLevel
                        totalAmount += item.storeAvailabilities.toiletP14[0].stockLevel
                        totalAmount += item.storeAvailabilities.toiletP15[0].stockLevel
                        totalAmount += item.storeAvailabilities.toiletP16[0].stockLevel
                        totalAmount += item.storeAvailabilities.toiletP17[0].stockLevel
                        totalAmount += item.storeAvailabilities.toiletP18[0].stockLevel
                        totalAmount += item.storeAvailabilities.toiletP19[0].stockLevel
                        totalAmount += item.storeAvailabilities.toiletP20[0].stockLevel
                        totalAmount += item.storeAvailabilities.toiletP21[0].stockLevel
                        totalAmount += item.storeAvailabilities.toiletP22[0].stockLevel
                        totalAmount += item.storeAvailabilities.toiletP23[0].stockLevel

                        getStoreInfo(storeId, totalAmount, isSingle)

                    }

                    override fun onError(e: Throwable) {
                        responseCode.value = 500
                    }

                })
        )

    }


    private fun getStoreInfo(storeId: Int, stock: Int, isSingle: Boolean) {

        disposable.add(
            apiService.getStoreInfo(storeId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<StoreItem>(){
                    override fun onSuccess(store: StoreItem) {

                        val currentDay = Date().day

                        val isOpened = if(currentDay in 1..6){
                            val opening = store.openingHours[currentDay-1].timeRanges[0].opening.split(":")
                            val todaysOpeningHour = opening[0].toFloat() + opening[1].toFloat()/60
                            val closing = store.openingHours[currentDay-1].timeRanges[0].closing.split(":")
                            val todaysClosingHour = closing[0].toFloat() + closing[1].toFloat()/60
                            val date = SimpleDateFormat("HH:mm", Locale.GERMANY).format(Date((System.currentTimeMillis()))).split(":")
                            val currentTime = date[0].toFloat() + date[1].toFloat()/60


                            if(storeId == 57){
                                Log.d("DmApiViewModel", "shop -> $store" )
                                Log.d("DmApiViewModel", "currentDay -> $currentDay" )
                                Log.d("DmApiViewModel", "currentTime -> $currentTime" )
                                Log.d("DmApiViewModel", "todaysOpeningHour -> $todaysOpeningHour" )
                                Log.d("DmApiViewModel", "todaysClosingHour -> $todaysClosingHour" )
                            }

                            (currentTime >= todaysOpeningHour) && (currentTime < todaysClosingHour)
                        } else {
                            false
                        }

                        if(isSingle){
                            singleStore.value = StoreAvailabilityItem(store, stock, isOpened, 0.0f)
                        } else {
                            storeAvailabilities.add(StoreAvailabilityItem(store, stock, isOpened, 0.0f))
                            storesList.value = storeAvailabilities
                        }

                    }

                    override fun onError(e: Throwable) {
                        responseCode.value = 500
                    }

                })
        )

    }


    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}