package com.anariba.klopapierapp.data.model

import com.google.gson.annotations.SerializedName

data class DmResponseItem(
    val tenant: String,
    val storeAvailabilities: StoreToiletPItems
)

data class StoreToiletPItems(
    @SerializedName("595420")val toiletP1: ArrayList<ToiletPaperItem>,
    @SerializedName("708997")val toiletP2: ArrayList<ToiletPaperItem>,
    @SerializedName("137425")val toiletP3: ArrayList<ToiletPaperItem>,
    @SerializedName("28171")val toiletP4: ArrayList<ToiletPaperItem>,
    @SerializedName("485698")val toiletP5: ArrayList<ToiletPaperItem>,
    @SerializedName("799358")val toiletP6: ArrayList<ToiletPaperItem>,
    @SerializedName("863567")val toiletP7: ArrayList<ToiletPaperItem>,
    @SerializedName("452740")val toiletP8: ArrayList<ToiletPaperItem>,
    @SerializedName("610544")val toiletP9: ArrayList<ToiletPaperItem>,
    @SerializedName("846857")val toiletP10: ArrayList<ToiletPaperItem>,
    @SerializedName("709006")val toiletP11: ArrayList<ToiletPaperItem>,
    @SerializedName("452753")val toiletP12: ArrayList<ToiletPaperItem>,
    @SerializedName("879536")val toiletP13: ArrayList<ToiletPaperItem>,
    @SerializedName("452744")val toiletP14: ArrayList<ToiletPaperItem>,
    @SerializedName("485695")val toiletP15: ArrayList<ToiletPaperItem>,
    @SerializedName("853483")val toiletP16: ArrayList<ToiletPaperItem>,
    @SerializedName("594080")val toiletP17: ArrayList<ToiletPaperItem>,
    @SerializedName("504606")val toiletP18: ArrayList<ToiletPaperItem>,
    @SerializedName("525943")val toiletP19: ArrayList<ToiletPaperItem>,
    @SerializedName("842480")val toiletP20: ArrayList<ToiletPaperItem>,
    @SerializedName("535981")val toiletP21: ArrayList<ToiletPaperItem>,
    @SerializedName("127048")val toiletP22: ArrayList<ToiletPaperItem>,
    @SerializedName("524535")val toiletP23: ArrayList<ToiletPaperItem>
)

data class ToiletPaperItem(
    val inStock : Boolean,
    val stockLevel: Int
)
