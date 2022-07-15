package dev.dprice.productivity.todo.features.tasks.dataimpl.converters

import com.squareup.sqldelight.ColumnAdapter
import dev.dprice.productivity.todo.features.tasks.data.model.Colour

class ColourAdapter : ColumnAdapter<Colour, String> {

    override fun decode(databaseValue: String) = Colour.valueOf(databaseValue)

    override fun encode(value: Colour) = value.name
}