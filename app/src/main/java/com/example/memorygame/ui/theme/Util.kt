package com.example.memorygame.ui.theme

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import com.example.memorygame.Model.Card
import com.example.memorygame.R

fun getPhotos(context: Context,edgeNumber:Int):List<List<Card>>{


    val packList= getAllPhotos()
    val cardNumber=edgeNumber*edgeNumber
    var i=cardNumber
    var arrayList=ArrayList<Card>(cardNumber)
    while (i>0){
        val current=packList.random()
        arrayList.add(Card(current))
        arrayList.add(Card(current))
        i-=2
    }
    arrayList.shuffle()
    arrayList.shuffle()
    var list= ArrayList<ArrayList<Card>>(edgeNumber)
    for (a in 0..edgeNumber-1){
        var temp= arrayListOf<Card>()
        for(k in 0..edgeNumber-1){
            temp.add(arrayList.get(a*edgeNumber+k))
        }
        list.add(temp)
    }
    return list

}
private fun getAllPhotos():List<Int>{
    return listOf(
        R.drawable.img,
        R.drawable.img_1,
        R.drawable.img_2,
        R.drawable.img_3,
        R.drawable.img_4,
        R.drawable.img_5,
        R.drawable.img_6,
        R.drawable.img_7,
        R.drawable.img_8,
        R.drawable.img_9,
        R.drawable.img_10,
        R.drawable.img_20,
        R.drawable.img_11,
        R.drawable.img_21,
        R.drawable.img_12,
        R.drawable.img_22,
        R.drawable.img_13,
        R.drawable.img_23,
        R.drawable.img_14,
        R.drawable.img_24,
        R.drawable.img_15,
        R.drawable.img_25,
        R.drawable.img_16,
        R.drawable.img_26,
        R.drawable.img_17,
        R.drawable.img_27,
        R.drawable.img_18,
        R.drawable.img_28,
        R.drawable.img_19,
        R.drawable.img_29,


    )
}