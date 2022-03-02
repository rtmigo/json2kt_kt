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


import kotlin.system.exitProcess

var version = "0.0.0+0"  // это значение будет изменено в момент билда

fun readInput(): String {
    val lines = mutableListOf<String>()
    while (true) {
        val line = readlnOrNull() ?: break

        if (line.trimEnd().endsWith(";")) {
            lines.add(line.trimEnd().removeSuffix(";"))
            break
        }

        lines.add(line)
    }

    return lines.joinToString("\n")
}

fun main(args: Array<String>) {

    val argsList = args.toList()

    if (argsList.contains("--version")) {
        println("json2kt $version")
        println("(c) 2022 Artёm IG <ortemeo@gmail.com>")
        println("https://github.com/rtmigo/json2kt_kt/releases/tag/$version")
        exitProcess(0)
    }

    if (argsList.contains("--help")) {
        println("See https://github.com/rtmigo/json2kt_kt#readme")
        exitProcess(0)
    }

    System.err.println("""Type JSON and add ';' to the end.""")
    System.err.println()

    val originalInput = readInput()
    val modifiedInput = kotlinStringifiedToJson(originalInput)

    if (modifiedInput!=originalInput) {
        System.err.println()
        System.err.println("Interpreted as:")
        System.err.println(modifiedInput)
    }

    System.err.println()

    try {
        println(jsonToKotlin(modifiedInput))
    } catch (e: JsonParsingError) {
        System.err.println("JSON parsing error: ${e.message}")
        exitProcess(1)
    }

}