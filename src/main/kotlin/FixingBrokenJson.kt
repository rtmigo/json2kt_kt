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

enum class FragmentType {
    IDENTIFIER,
    STRING,
    OTHER
}

data class Fragment(val text: String, val type: FragmentType)

/** Возвращает строки или не-строки. Строки всегда в двойных кавычках.
 * Бэкслеш учитывается, двойной тоже. */
fun splitStringsNotStrings(text: String): Sequence<Fragment> {
    return Regex(
            listOf(
                    """"(?<STRING>((\\{2})*|(.*?[^\\](\\{2})*))")""",
                    """(?<IDENTIFIER>[_a-zA-Z][_a-zA-Z0-9]*)""",
                    """(?<OTHER>[^"_a-zA-Z]+)"""
            ).joinToString("|")
    )
            .findAll(text)
            .map {
                val namedGroups = (it.groups as MatchNamedGroupCollection)
                val matchType = FragmentType.values()
                        .first { type -> namedGroups[type.name] != null }
                Fragment(it.value, matchType)
            }
}

/**
 *  На входе
 *      {abc=1, def=2}
 *  На выходе
 *      {"abc": 1, "def": 2}
 */
fun kotlinStringifiedToJson(text: String) = splitStringsNotStrings(text).map { fragment ->
        when (fragment.type) {
            FragmentType.IDENTIFIER -> "\"${fragment.text}\""
            FragmentType.OTHER -> fragment.text.replace('=', ':')
            FragmentType.STRING -> fragment.text
        }
    }.joinToString("")
