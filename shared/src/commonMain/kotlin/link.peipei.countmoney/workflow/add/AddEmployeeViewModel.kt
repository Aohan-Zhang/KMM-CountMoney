package link.peipei.countmoney.workflow.add

import link.peipei.countmoney.data.repository.EmployRepository

class AddEmployeeViewModel(private val repo: EmployRepository) : EmployeeDetailViewModel() {
    override fun initUiState(): EmployeeDetailUiState {
        return EmployeeDetailUiState("新增员工", "保存", "入职时间")
    }

    override fun update() {
        verifyInputDate { }
    }

}