package com.example.memorygame.ViewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.memorygame.Model.Card
import com.example.memorygame.ui.theme.getPhotos

class GameScreenViewModel:ViewModel() {
    private var _cardList=MutableLiveData<List<List<Card>>>()

    val cardlist :LiveData<List<List<Card>>> =_cardList

    fun getAllCards(context: Context,edgeNumber: Int){
        _cardList.value= getPhotos(context,edgeNumber)

    }
    fun setSeen(card: Card,boolean: Boolean){
        val list=_cardList.value!!
        list.forEach { list->
            list.forEach {
                if(it.id==card.id){
                    it.seen=boolean
                }
            }
        }
        _cardList.value=list
    }
}