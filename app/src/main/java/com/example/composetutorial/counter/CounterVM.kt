package com.example.composetutorial.counter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class CounterVM : ViewModel() {
    private val _counterValue = MutableLiveData(0)
    val counterValue: LiveData<Int>
        get() = _counterValue

    fun increment() {
        _counterValue.postValue(_counterValue.value?.plus(1))
    }

    fun decrement() {
        _counterValue.postValue(_counterValue.value?.minus(1))
    }

    fun setValue(value: Int) {
        _counterValue.postValue(value)
    }

    fun resetValue() {
        _counterValue.postValue(0)
    }
}