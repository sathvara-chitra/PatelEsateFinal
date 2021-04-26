package com.patelestate.model

import java.sql.Date

class Root {
    var propertyUniqid: String? = null
    var propertyType: String? = null
    var mlsId: String? = null
    var listingContractDate: String? = null
    var bathroomTotal = 0
    var bedroomsTotal = 0
    var totalFinishedArea: String? = null
    var address: String? = null
    var streetAddress: String? = null
    var city: String? = null
    var postalCode: String? = null
    var province: String? = null
    var communityName: String? = null
    var zoningDescription: String? = null
    var openHouse = false
    var price = 0
    var latitude = 0.0
    var longitude = 0.0
    var transactionType: String? = null
    var photoPath: String? = null
    var exclusive = false
    var forCloser = false
}