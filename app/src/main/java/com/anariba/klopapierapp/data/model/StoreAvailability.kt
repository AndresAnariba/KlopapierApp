package com.anariba.klopapierapp.data.model

data class StoreAvailabilityItem(
    val store: StoreItem,
    val stock: Int,
    val isOpened: Boolean,
    var distance: Float
)

data class StoreItem(
    val storeNumber: String,
    val phone: String,
    val address: AddressItem,
    val openingHours : ArrayList<OpeningHoursItems>,
    val location : LocationItem
)

data class AddressItem(
    val street: String,
    val zip: String,
    val city: String
)

data class OpeningHoursItems(
    val weekDay: Int,
    val timeRanges: ArrayList<TimeRangeItem>
)

data class TimeRangeItem(
    val opening: String,
    val closing: String
)

data class LocationItem(
    val lat : Double,
    val lon : Double
)