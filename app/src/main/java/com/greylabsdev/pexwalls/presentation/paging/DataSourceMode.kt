package com.greylabsdev.pexwalls.presentation.paging

sealed class DataSourceMode {
    class LIVEDATA: DataSourceMode()
    class RX: DataSourceMode()
}