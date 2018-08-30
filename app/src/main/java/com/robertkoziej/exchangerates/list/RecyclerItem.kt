package com.robertkoziej.exchangerates.list

sealed class RecyclerItem {
    abstract val viewType: Int
}

data class RateItem(val date: String,
                    val code: String,
                    val value: Double) : RecyclerItem() {
    override val viewType: Int = ItemViewType.RATE_VIEW
}
data class DateItem(val date: String) : RecyclerItem() {
    override val viewType: Int = ItemViewType.DATE_VIEW
}
data class LoadMoreItem(override val viewType: Int = ItemViewType.LOAD_INDICATOR_VIEW) : RecyclerItem()