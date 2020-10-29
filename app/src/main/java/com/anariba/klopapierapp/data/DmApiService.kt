package com.anariba.klopapierapp.data

import com.anariba.klopapierapp.data.model.DmResponseItem
import com.anariba.klopapierapp.data.model.StoreItem
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class DmApiService {

    private val BASE_URL = "https://products.dm.de/"
    private val BASE_URL_STORE = "https://store-data-service.services.dmtech.com"

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(AnalyzeApi::class.java)

    private val apiStoreInfo = Retrofit.Builder()
        .baseUrl(BASE_URL_STORE)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(AnalyzeApi::class.java)

    fun getAvailabilityFromStoreId(storeId: Int) : Single<DmResponseItem> {
        val dans = "595420,708997,137425,28171,485698,799358,863567,452740,610544,846857,709006,452753,879536,452744,485695,853483,594080,504606,593761,525943,842480,535981,127048,524535"
        return api.getAvailability(dans, storeId)
    }

    fun getStoreInfo(storeId: Int) : Single<StoreItem>{
        return apiStoreInfo.getStoreInfo(storeId)
    }
}