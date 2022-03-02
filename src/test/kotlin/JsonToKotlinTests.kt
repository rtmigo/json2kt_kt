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


import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class JsonToKotlinTests {
    // todo test parsing errors

    @Test
    fun listOfInts() {
        assertEquals(
                "listOf(1, 2, 3)",
                elementToKotlin(Json.parseToJsonElement("[1,2,  3\n]"))
        )
    }

    @Test
    fun listOfFloats() {
        assertEquals(
                "listOf(1.1, 2.2, 3.3)",
                elementToKotlin(Json.parseToJsonElement("[1.1, 2.2, 3.3]"))
        )
    }

    @Test
    fun mapToInt() {
        assertEquals(
                """mapOf("abc" to 5, "def" to 23)""",
                elementToKotlin(Json.parseToJsonElement("""{"abc": 5, "def": 23}"""))
        )
    }

    @Test
    fun tree() {
        assertEquals(
                """mapOf("abc" to "x", "def" to listOf(1, 2, 3), 
                   |"xyz" to mapOf("x" to 1.0, "y" to 2.0, "z" to 3.0))"""
                        .trimMargin().replace("\n", ""),
                elementToKotlin(Json.parseToJsonElement(
                        """{"abc": "x", "def": [1,2,3], 
                            |"xyz": {"x": 1.0, "y": 2.0, "z": 3.0}}""".trimMargin()))
        )
    }

    @Test
    fun stringWithDollar() {
        assertEquals(
                """mapOf("price" to "15\${'$'}")""",
                elementToKotlin(Json.parseToJsonElement(
                        """{"price": "15$"}"""))
        )
    }

    @Test
    fun fieldNameWithDollar() {
        assertEquals(
                """mapOf("price\${'$'}" to 15)""",
                elementToKotlin(Json.parseToJsonElement(
                        """{"price$": 15}"""))
        )
    }

}