package model

import java.io.Serializable

enum class CommandType : Serializable {
    ADD,
    ADD_IF_MIN,
    CLEAR,
    FILTER_BY_MANUFACTURER,
    FILTER_GREATER_THAN_MANUFACTURER,
    INFO,
    REMOVE_BY_ID,
    REMOVE_FIRST,
    SHOW,
    SUM_OF_PRICE,
    UPDATE,
}
