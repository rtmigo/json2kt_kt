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

import org.junit.Test
import kotlin.test.assertEquals

fun findStrings(text: String): Sequence<String> {
    return splitStringsNotStrings(text).filter { it.type == FragmentType.STRING }.map { it.text }

}

class ExtractStrings {
    @Test
    fun simple() {
        assertEquals(
                listOf("\"abc\"", "\"def\"", "\"gh\""),
                findStrings("""{"abc": 123, "def": "gh"}""").toList()
        )
    }

    @Test
    fun backslash() {
        assertEquals(
                listOf("\"quote_in_string\"", "\"abc\\\"def\""),
                findStrings("""{"quote_in_string": "abc\"def"}""").toList()
        )
    }

    @Test
    fun doubleBackslash() {
        assertEquals(
                listOf(
                        """     "double"    """.trim(),
                        """     "abc\\"     """.trim()),
                findStrings("""{"double": "abc\\"}""").toList()
        )
    }

    @Test
    fun fourBackslashes() {
        assertEquals(
                listOf(
                        """     "double"    """.trim(),
                        """     "abc\\\\"   """.trim()),
                findStrings("""{"double": "abc\\\\"}""").toList()
        )
    }
}

class SplitToStringsAndNotStrings {
    @Test
    fun splitJson() {
        assertEquals(
                listOf(
                        """{""",
                        """"abc"""",
                        """: 123, """,
                        """"def"""",
                        """: """,
                        """"gh"""",
                        """}"""
                ),
                splitStringsNotStrings("""{"abc": 123, "def": "gh"}""").map { it.text }.toList()
        )
    }

    @Test
    fun splitEquals() {
        assertEquals(
                listOf(
                        """{""",
                        """abc""",
                        """=[1,2,3]}""",
                ),
                splitStringsNotStrings("""{abc=[1,2,3]}""").map { it.text }.toList()
        )
    }
}

class StringifiedToJson {
    @Test
    fun one() {
        assertEquals(
                """{"abc":[1,2,3], "def":"zzz"}""",
                kotlinStringifiedToJson("""{abc=[1,2,3], def=zzz}""")
        )
    }

    @Test
    fun bidAsk() {
        assertEquals(
                """{"bid":[[125.1, 10.0], [125.2, 20.0], [125.3, 5.0]], "ask":[[125.1, 10.0], [125.2, 20.0], [125.3, 5.0]]}""",
                kotlinStringifiedToJson("""{bid=[[125.1, 10.0], [125.2, 20.0], [125.3, 5.0]], ask=[[125.1, 10.0], [125.2, 20.0], [125.3, 5.0]]}""")
        )
    }

}