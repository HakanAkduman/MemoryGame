package com.example.memorygame.ui.theme

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import com.example.memorygame.Model.Card
import com.example.memorygame.R

fun getPhotos(context: Context,edgeNumber:Int):List<Card>{
    var list= mutableListOf<Card>()
    val packID= R.array.card_resources
    val packList=context.applicationContext.resources.getIntArray(packID)
    val cardNumber=edgeNumber*edgeNumber
    var arrayList=ArrayList<Card>(cardNumber)
    while (cardNumber>=0){
        val current=packList.random()
        arrayList.add(Card(current))
        arrayList.add(Card(current))
    }
    arrayList.shuffle()
    arrayList.shuffle()
    return arrayList

}