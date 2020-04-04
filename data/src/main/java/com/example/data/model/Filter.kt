package com.example.data.model

data class Filter(
    var name: String? = null,
    val yeast: String? = null,
    val hops: String? = null,
    val malt: String? = null,
    val food: String? = null,
    val ibuFrom: Double? = null,
    val ibuTo: Double? = null,
    val abvFrom: Double? = null,
    val abvTo: Double? = null,
    val ebcFrom: Double? = null,
    val ebcTo: Double? = null,
    val after: String? = null,
    val before: String? = null
)