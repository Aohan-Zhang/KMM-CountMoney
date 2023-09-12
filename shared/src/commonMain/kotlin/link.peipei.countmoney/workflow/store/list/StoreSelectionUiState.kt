package link.peipei.countmoney.workflow.store.list

data class StoreSelectionUiState(
    val storeSelectionLoadingState: StoreSelectionLoadingState ,
    val items: List<StoreSelectionItem>
)

data class StoreSelectionLoadingState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
)

data class StoreSelectionItem(val name: String, val des: String)