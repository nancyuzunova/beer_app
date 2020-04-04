package com.example.data.model

class PreparationMethod(val mashTemp: MashTemperature, val fermentation: Measurable)

class MashTemperature(val temperature: Measurable, val duration: Int)