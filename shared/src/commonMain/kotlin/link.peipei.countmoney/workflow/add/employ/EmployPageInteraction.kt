package link.peipei.countmoney.workflow.add.employ

interface EmployPageInteraction {
    fun onNameUpdate(name: String)

    fun onPhoneUpdate(phone: String)

    fun onPositionUpdate(position: String)


    fun onHireDateUpdate(dateMillis: Long?)

    fun onGenderUpdate(gender: Boolean)

    fun onSalaryUpdate(salary: Int)

    fun onAllowanceUpdate(allowance: Int)

    fun onBonusUpdate(bonus: Int)

}