package link.peipei.countmoney.data.di

import link.peipei.countmoney.data.EmployeeRepository
import link.peipei.countmoney.data.database.Database
import link.peipei.countmoney.data.database.getDatabaseDriverFactory
import link.peipei.countmoney.workflow.home.event.EventPageViewModel
import link.peipei.countmoney.workflow.home.record.RecordPageViewModel
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val homeModule = DI {
    bindProvider { getDatabaseDriverFactory() }
    bindSingleton { Database(instance()) }
    bindSingleton { instance<Database>().employeeDao }
    bindSingleton { instance<Database>().salaryTimeLineDao }
    bindSingleton { EmployeeRepository(instance(), instance()) }
    bindProvider { EventPageViewModel(instance()) }
    bindProvider { RecordPageViewModel(instance()) }
}