package com.patelestate.model

data class GetCommercialLIstItem(
    val address: String,
    val askingPrice: String,
    val baseRent: String,
    val city: String,
    val comRent: String,
    val exclusive: String,
    val hospitalityType: String,
    val landArea: String,
    val latitude: String,
    val listingDate: String,
    val longitude: String,
    val mlsId: String,
    val photoPath: String,
    val postalCode: String,
    val propertyType: String,
    val propertyUniqid: String,
    val state: String,
    val transactionType: String
)