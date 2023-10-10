package link.peipei.countmoney.data.di

import dataStore
import link.peipei.countmoney.data.UserManager
import link.peipei.countmoney.data.api.core.KtorfitFactory
import link.peipei.countmoney.data.api.interceptor.AppInterceptor
import link.peipei.countmoney.data.entities.EmployWithSalary
import link.peipei.countmoney.data.repository.AccountRepository
import link.peipei.countmoney.data.repository.EmployRepository
import link.peipei.countmoney.data.repository.GoodsRepository
import link.peipei.countmoney.data.repository.StoreRepository
import link.peipei.countmoney.workflow.add.employ.AddEmployeeViewModel
import link.peipei.countmoney.workflow.add.employ.EmployeeDetailViewModel
import link.peipei.countmoney.workflow.add.employ.UpdateEmployeeViewModel
import link.peipei.countmoney.workflow.home.event.EventPageViewModel
import link.peipei.countmoney.workflow.home.record.RecordPageViewModel
import link.peipei.countmoney.workflow.home.record.RecordRefreshUseCase
import link.peipei.countmoney.workflow.home.record.RecordUseCase
import link.peipei.countmoney.workflow.login.LoginViewModel
import link.peipei.countmoney.workflow.store.add.CreateStoreViewModel
import link.peipei.countmoney.workflow.store.list.StoreSelectionViewModel
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.bindProvider
import org.kodein.di.bindSingleton
import org.kodein.di.factory
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
    bindSingleton { AccountRepository(instance(), instance()) }
    bindSingleton { EmployRepository(instance(), instance()) }
    bindSingleton { StoreRepository(instance(), instance()) }
    bindSingleton { GoodsRepository(instance(), instance()) }

    bindProvider { RecordRefreshUseCase(instance(), instance()) }
    bindProvider { RecordUseCase(instance(), instance()) }


    bindProvider { EventPageViewModel(instance(), instance()) }
    bindProvider { RecordPageViewModel(instance(), instance(), instance(), instance()) }
    bindProvider { LoginViewModel(instance()) }
    bindProvider { CreateStoreViewModel(instance()) }
    bindProvider { StoreSelectionViewModel(instance()) }
    bind<EmployeeDetailViewModel>() with factory { params: EmployWithSalary ->
        if (params.salaries.isEmpty()) {
            AddEmployeeViewModel(instance())
        } else {
            UpdateEmployeeViewModel(params, instance())
        }
    }
}