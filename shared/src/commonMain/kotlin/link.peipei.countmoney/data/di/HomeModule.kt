package link.peipei.countmoney.data.di

import dataStore
import link.peipei.countmoney.data.EmployeeRepository
import link.peipei.countmoney.data.UserManager
import link.peipei.countmoney.data.api.core.KtorInterceptor
import link.peipei.countmoney.data.api.interceptor.AppInterceptor
import link.peipei.countmoney.data.api.core.KtorfitFactory
import link.peipei.countmoney.data.database.Database
import link.peipei.countmoney.data.database.getDatabaseDriverFactory
import link.peipei.countmoney.data.repository.AccountRepository
import link.peipei.countmoney.data.repository.StoreRepository
import link.peipei.countmoney.workflow.home.event.EventPageViewModel
import link.peipei.countmoney.workflow.home.record.RecordPageViewModel
import link.peipei.countmoney.workflow.login.LoginViewModel
import link.peipei.countmoney.workflow.store.add.CreateStorePage
import link.peipei.countmoney.workflow.store.add.CreateStoreViewModel
import link.peipei.countmoney.workflow.store.list.StoreSelectionViewModel
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val homeModule = DI {
    bindSingleton {
        dataStore()
    }
    bindSingleton {
        UserManager(instance())
    }
    bindSingleton {
        AppInterceptor(instance())
    }
    bindSingleton {
        KtorfitFactory.create("https://api.peipei.link/", instance<AppInterceptor>())
    }
    bindProvider { getDatabaseDriverFactory() }
    bindSingleton { AccountRepository(instance(), instance()) }
    bindSingleton { Database(instance()) }
    bindSingleton { instance<Database>().employeeDao }
    bindSingleton { instance<Database>().salaryTimeLineDao }
    bindSingleton { EmployeeRepository(instance(), instance()) }
    bindSingleton { StoreRepository(instance(), instance()) }
    bindProvider { EventPageViewModel(instance(), instance()) }
    bindProvider { RecordPageViewModel(instance()) }
    bindProvider { LoginViewModel(instance()) }
    bindProvider { CreateStoreViewModel(instance()) }
    bindProvider { StoreSelectionViewModel(instance()) }
}