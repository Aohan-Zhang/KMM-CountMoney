package link.peipei.countmoney.data.repository

import co.touchlab.kermit.Logger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import link.peipei.countmoney.data.UserManager
import link.peipei.countmoney.data.api.CountingMoneyApi
import link.peipei.countmoney.data.entities.CreateEmployRequest
import link.peipei.countmoney.data.entities.EmployEntity

class EmployRepository(private val api: CountingMoneyApi, private val userManager: UserManager) {
    private val employeeFlow = MutableStateFlow(listOf<EmployEntity>())

    fun getEmployeeFlow() = employeeFlow.asStateFlow()

    suspend fun refreshEmployee(): Boolean {
        val storeId = userManager.getUserStore().firstOrNull() ?: return false
        Logger.d("storeid:$storeId")
        return try {
            val result = api.getEmployee(storeId)
            employeeFlow.update {
                result
            }
            true
        } catch (_: Exception) {
            false
        }
    }

    suspend fun addEmploy(createEmployRequest: CreateEmployRequest): Boolean {
        val storeId = userManager.getUserStore().firstOrNull()?.toLongOrNull() ?: return false
        val newCreateEmployRequest = createEmployRequest.copy(storeId = storeId)
        return try {
            val result = api.addEmploy(newCreateEmployRequest)
            employeeFlow.update {
                it + result
            }
            true
        } catch (_: Exception) {
            false
        }
    }
}