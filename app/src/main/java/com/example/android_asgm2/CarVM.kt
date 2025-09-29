package com.example.android_asgm2

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class CarVM(application: Application) : AndroidViewModel(application){
    var credit : Int = 500
    var index : Int = 0
    val availableCarList = arrayListOf(
        Cars("The 8 Convertible", "The 8", 2025, 4.5f, 15000, 30,true, false, false, R.drawable.car1),
        Cars("The Z4", "Z4", 2024, 4.2f, 20000, 35, true, false,false,R.drawable.car2),
        Cars("The BMW X3", "X-Range", 2020, 4.0f, 18000, 25, true, false,false,R.drawable.car3),
        Cars("The BMW M3 Sedan", "BMW M", 2019, 4.8f, 10000, 40, true, false,false, R.drawable.car4),
        Cars("The BMW iX2", "BMW i", 2022, 4.3f, 12000, 30, true, false,false,R.drawable.car5)
    )
    val rentCar = arrayListOf<Cars>()

    val favoriteCars = arrayListOf<Cars>()

    fun next(){
        index = (index+1) % availableCarList.size
    }
    fun back(){
        if(index -1 < 0) index=availableCarList.size -1
        else index = index -1
    }
}