package com.example.data.model

sealed class Ingredient(val name: String, val amount: Measurable)

class Malt(name: String, amount: Measurable) : Ingredient(name, amount)

class Hops(name: String, amount: Measurable, val add: String, val attriute: String)
    : Ingredient(name, amount)

class Yeast(name: String, amount: Measurable) : Ingredient(name, amount)



