package com.patelestate.model.Residential

data class GetResidentialListItem(
    val propertyUniqid: String,
    val propertyType: String,
    val mlsId: String,
    val listingContractDate: String,
    val bathroomTotal: String,
    val bedroomsTotal: String,
    val totalFinishedArea: String,
    val address: String,
    val streetAddress: String,
    val city: String,
    val postalCode: String,
    val province: String,
    val communityName: String,
    val zoningDescription: String,
    val openHouse: String,
    val price: String,
    val latitude: String,
    val longitude: String,
    val transactionType: String,
    val photoPath: String,
    val exclusive: String,
    val forCloser: String
    )