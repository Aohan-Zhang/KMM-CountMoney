package link.peipei.countmoney.data.di

import de.jensklingenberg.ktorfit.converter.builtin.CallConverterFactory
import de.jensklingenberg.ktorfit.converter.builtin.FlowConverterFactory
import de.jensklingenberg.ktorfit.ktorfit
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import link.peipei.countmoney.data.EmployeeRepository
import link.peipei.countmoney.data.api.CountingMoneyApi
import link.peipei.countmoney.data.api.converter.ResultConverterFactory
import link.peipei.countmoney.data.database.Database
import link.peipei.countmoney.data.database.getDatabaseDriverFactory
import link.peipei.countmoney.data.repository.AccountRepository
import link.peipei.countmoney.workflow.home.event.EventPageViewModel
import link.peipei.countmoney.workflow.home.record.RecordPageViewModel
import link.peipei.countmoney.workflow.login.LoginViewModel
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val homeModule = DI {
    bindSingleton {
        ktorfit {
            baseUrl("https://api.peipei.link/")
            httpClient(HttpClient {
                install(ContentNegotiation) {
                    json(Json { isLenient = true; ignoreUnknownKeys = true })
                }
            })
            converterFactories(
//                FlowConverterFactory(),
//                CallConverterFactory(),
                ResultConverterFactory()
            )
        }.create<CountingMoneyApi>()
    }
    bindProvider { getDatabaseDriverFactory() }
    bindSingleton { AccountRepository(instance()) }
    bindSingleton { Database(instance()) }
    bindSingleton { instance<Database>().employeeDao }
    bindSingleton { instance<Database>().salaryTimeLineDao }
    bindSingleton { EmployeeRepository(instance(), instance()) }
    bindProvider { EventPageViewModel(instance()) }
    bindProvider { RecordPageViewModel(instance()) }
    bindProvider { LoginViewModel(instance()) }
}