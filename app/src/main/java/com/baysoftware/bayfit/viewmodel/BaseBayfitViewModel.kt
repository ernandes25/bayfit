package com.baysoftware.bayfit.viewmodel

import androidx.constraintlayout.motion.utils.ViewState
import androidx.lifecycle.ViewModel
import com.baysoftware.bayfit.settings.viewmodel.SettingsTimerCountTypeViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class BaseBayfitViewModel<VIEW_STATE_TYPE> : ViewModel() {

//    protected abstract val initialViewState: ViewState
//    protected abstract fun processAction(action: ViewAction)
//
//    protected var lastViewState: ViewState = initialViewState
//
//    private val _viewState = MutableStateFlow(initialViewState)
//    val viewState = _viewState.asStateFlow()
//
//    private val _viewEvents = Channel<ViewEvent?>(Channel.BUFFERED)
//    val viewEvents = _viewEvents.receiveAsFlow()
//
//    private val _viewActions = Channel<ViewAction>(Channel.BUFFERED)
//
//    init {
//        launch {
//            _viewActions.consumeEach { action ->
//                processAction(action)
//            }
//        }
//    }
//
//    open protected fun updateViewState(state: ViewState) {
//        _viewState.value = state
//        lastViewState = state
//    }
//
//    protected fun sendEvent(event: ViewEvent) = launch {
//        _viewEvents.send(null)
//        _viewEvents.send(event)
//    }
//
//    fun applyAction(action: ViewAction) = launch {
//        _viewActions.send(action)
//    }

}