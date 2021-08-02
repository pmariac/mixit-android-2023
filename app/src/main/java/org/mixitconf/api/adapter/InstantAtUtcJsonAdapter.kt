package org.mixitconf.api.adapter

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import java.time.Instant

/**
 * A JSON adapter for [Instant]s in Moshi
 */
class InstantAtUtcJsonAdapter : JsonAdapter<Instant>() {
    override fun fromJson(reader: JsonReader): Instant? {
        if (reader.peek() === JsonReader.Token.NULL) {
            return reader.nextNull()
        }
        return Instant.parse(reader.nextString())
    }

    override fun toJson(writer: JsonWriter, value: Instant?) {
        value?.also {
            writer.value(it.toString())
        } ?: writer.nullValue()
    }
}