package com.patelestate.model

data class ResedentialListDataItem(
    val address: String,
    val bathroomTotal: Int,
    val bedroomsTotal: Int,
    val city: String,
    val communityName: String,
    val exclusive: Boolean,
    val forCloser: Boolean,
    val latitude: Double,
    val listingContractDate: String,
    val longitude: Double,
    val mlsId: String,
    val openHouse: Boolean,
    val photoPath: String,
    val postalCode: String,
    val price: Int,
    val propertyType: String,
    val propertyUniqid: String,
    val province: String,
    val streetAddress: String,
    val totalFinishedArea: String,
    val transactionType: String,
    val zoningDescription: String
)