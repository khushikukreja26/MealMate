package com.example.mealmate.util

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class RxSchedulers {
    val io = Schedulers.io()
    val main = AndroidSchedulers.mainThread()
}
