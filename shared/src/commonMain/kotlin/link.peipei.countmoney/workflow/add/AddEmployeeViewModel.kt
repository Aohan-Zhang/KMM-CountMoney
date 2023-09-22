package link.peipei.countmoney.workflow.add

class AddEmployeeViewModel() : EmployeeDetailViewModel() {
    override fun initUiState(): EmployeeDetailUiState {
        return EmployeeDetailUiState("新增员工","保存")
    }

}