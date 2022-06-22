package dev.dprice.productivity.todo.features.tasks.dataimpl.converters

import com.squareup.sqldelight.ColumnAdapter
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.json.Json

class LocalDateTimeAdapter : ColumnAdapter<LocalDateTime, String> {
    override fun decode(databaseValue: String): LocalDateTime {
        return Json.decodeFromString(LocalDateTime.serializer(), databaseValue)
    }

    override fun encode(value: LocalDateTime): String {
        return Json.encodeToString(LocalDateTime.serializer(), value)
    }
}
