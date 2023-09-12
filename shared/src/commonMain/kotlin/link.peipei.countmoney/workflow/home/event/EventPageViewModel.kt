package link.peipei.countmoney.workflow.home.event

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import link.peipei.countmoney.data.UserManager
import link.peipei.countmoney.data.repository.StoreRepository

class EventPageViewModel(
    private val userManager: UserManager,
    private val repository: StoreRepository
) : ScreenModel {
    val isLogin = userManager.getLoggingStatusFlow()
        .stateIn(coroutineScope, SharingStarted.WhileSubscribed(5000), false)
    val userResponse = userManager.getUser()
        .stateIn(coroutineScope, SharingStarted.WhileSubscribed(5000), null)

    fun logout(){
        coroutineScope.launch {
            repository.clean()
            userManager.logout()

        }

    }
}