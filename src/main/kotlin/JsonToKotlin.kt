/**
 *
 * Copyright (c) 2022 Artёm IG <github.com/rtmigo>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */


import kotlinx.serialization.SerializationException
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.*

class JsonParsingError(var m: String): Exception(m)

/** пропускаем строку, начинающуются с "JSON input:" */
fun serializationExceptionToMessage(e: SerializationException) =
    e.message!!.lines()
            .filter { !it.startsWith("JSON input:") }
            .joinToString("\n")


fun jsonToKotlin(json: String): String {
    var parsed: JsonElement? = null
    try {
        parsed = Json.parseToJsonElement(json)
    } catch (e: SerializationException) {
        throw JsonParsingError(serializationExceptionToMessage(e))
    }

    return elementToKotlin(parsed)
}

private fun fixDollar(txt: String) = txt.replace("\$", "\\$")

private fun toQuotedString(txt: String) = fixDollar(Json.encodeToString(txt))

private fun toQuotedString(txt: JsonPrimitive) = fixDollar(txt.toString())

private fun toXtoY(entry: Map.Entry<String, JsonElement>): String =
    "${toQuotedString(entry.key)} to ${elementToKotlin(entry.value)}"

private fun primitiveToKotlin(element: JsonPrimitive): String {
    if (element.isString) {
        return toQuotedString(element)
    }
    return element.toString()
}

internal fun elementToKotlin(element: JsonElement): String {
    return when (element) {
        is JsonArray -> "listOf(${element.joinToString(", ") { elementToKotlin(it) }})"
        is JsonPrimitive -> primitiveToKotlin(element)
        is JsonObject -> "mapOf(${element.entries.joinToString(", ") { toXtoY(it) }})"
        else ->
            throw RuntimeException("Unexpected type of element: ${element::class.simpleName}")
    }
}