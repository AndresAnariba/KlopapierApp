package com.anariba.klopapierapp.data

import com.anariba.klopapierapp.data.model.DmResponseItem
import com.anariba.klopapierapp.data.model.StoreItem
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AnalyzeApi {

    @GET("store-availability/DE/availability")
    fun getAvailability(
        @Query("dans") dans:String,
        @Query("storeNumbers") storeId: Int
    ) : Single<DmResponseItem>

    @GET("stores/item/de/{storeId}")
    fun getStoreInfo(@Path("storeId") storeId: Int) : Single<StoreItem>

}