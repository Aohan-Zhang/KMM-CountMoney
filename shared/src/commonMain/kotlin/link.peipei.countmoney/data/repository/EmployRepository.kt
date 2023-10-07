package link.peipei.countmoney.data.repository

import co.touchlab.kermit.Logger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import link.peipei.countmoney.data.UserManager
import link.peipei.countmoney.data.api.CountingMoneyApi
import link.peipei.countmoney.data.entities.UpdateEmployRequest
import link.peipei.countmoney.data.entities.EmployEntity
import link.peipei.countmoney.data.entities.EmployWithSalary
import link.peipei.countmoney.data.entities.SalaryEntity

class EmployRepository(private val api: CountingMoneyApi, private val userManager: UserManager) {
    private val employeeFlow = MutableStateFlow(listOf<EmployWithSalary>())

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

    suspend fun addEmploy(createEmployRequest: UpdateEmployRequest): Boolean {
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

    suspend fun deleteEmploy(employId: String): Boolean {
        val storeId = userManager.getUserStore().firstOrNull()?.toLongOrNull() ?: return false
        val currentList = employeeFlow.value
        val newList = currentList.filter { it.employ.id != employId }
        employeeFlow.emit(newList)
        return try {
            val result = api.deleteEmploy(employId, storeId.toString()).result
            if (!result) {
                employeeFlow.emit(currentList)
                return false
            }
            true
        } catch (_: Exception) {
            employeeFlow.emit(currentList)
            false
        }
    }

    suspend fun updateEmploy(createEmployRequest: UpdateEmployRequest, employId: String): Boolean {
        val storeId = userManager.getUserStore().firstOrNull()?.toLongOrNull() ?: return false
        val newCreateEmployRequest = createEmployRequest.copy(storeId = storeId)
        return try {
            val result = api.updateEmploy(newCreateEmployRequest, employId).result
            if (!result) return false
            employeeFlow.update { employAndSalary ->
                employAndSalary.map {
                    if (it.employ.id == employId) {
                        getEmployWithSalaryByCreateEmployRequest(
                            createEmployRequest,
                            it.salaries,
                            employId,
                            storeId,
                            it.employ.hireDate
                        )
                    } else {
                        it
                    }
                }
            }
            true
        } catch (_: Exception) {
            false
        }
    }

    private fun getEmployWithSalaryByCreateEmployRequest(
        createEmployRequest: UpdateEmployRequest,
        salaries: List<SalaryEntity>,
        employId: String,
        storeId: Long,
        employCreateDate: String
    ): EmployWithSalary {
        val employ = EmployEntity(
            employId,
            storeId,
            createEmployRequest.phoneNumber,
            createEmployRequest.position,
            employCreateDate,
            createEmployRequest.gender,
            createEmployRequest.name
        )
        val salary = SalaryEntity(
            "",
            employId,
            createEmployRequest.basicSalary,
            createEmployRequest.allowance,
            createEmployRequest.bonus,
            createEmployRequest.updateDate

        )
        return EmployWithSalary(employ, salaries + salary)
    }
}