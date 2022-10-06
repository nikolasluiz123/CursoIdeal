package br.com.cursoideal.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AppStateViewModel : ViewModel() {

    val components: LiveData<ComponentsViewControll> get() = _components

    private var _components: MutableLiveData<ComponentsViewControll> =
        MutableLiveData<ComponentsViewControll>().also {
            it.value = hasComponents
        }

    var hasComponents: ComponentsViewControll = ComponentsViewControll()
        set(value) {
            field = value
            _components.value = value
        }
}

class ComponentsViewControll(val showAppBar: Boolean = true)