package com.example.basemvvm.base

import androidx.lifecycle.ViewModel
import com.example.mykotlin.net.repository.SystemRepository

open class BaseViewModel(val repository: SystemRepository) : ViewModel() {

}