package dev.dprice.productivity.todo.features.groups.dataimpl.converters

import com.squareup.sqldelight.ColumnAdapter
import dev.dprice.productivity.todo.features.groups.data.model.Icon

class IconAdapter : ColumnAdapter<Icon, String> {
    override fun decode(databaseValue: String) = Icon.valueOf(databaseValue)

    override fun encode(value: Icon): String = value.name
}