# [json2kt](https://github.com/rtmigo/json2kt_kt)

Command line utility converting JSON to Kotlin declarations.

Input:

```json
[ {"season": "Winter", "days": 90.25},
  {"season": "Spring", "days": 92},
  {"season": "Summer", "days": 92},
  {"season": "Autumn", "days": 91} ]
```

Output:

```kotlin
listOf(
    mapOf("season" to "Winter", "days" to 90.25), 
    mapOf("season" to "Spring", "days" to 92), 
    mapOf("season" to "Summer", "days" to 92),
    mapOf("season" to "Autumn", "days" to 91)
)
```

Actually the result will be one line. Here it is formatted for ease of reading.

# Install

Just download `json2kt.jar` attached to the [latest release](https://github.com/rtmigo/json2kt_kt/releases).

# Use

## From JSON to Kotlin

Run:

```
java -jar json2kt.jar
```

Enter the input code when prompted. Add `;` after the code to indicate there will be
no more input.

```json
[ {"season": "Winter", "days": 90.25},
  {"season": "Spring", "days": 92},
  {"season": "Summer", "days": 92},
  {"season": "Autumn", "days": 91} ]
;
```

Get output:

```kotlin
listOf(
    mapOf("season" to "Winter", "days" to 90.25),
    mapOf("season" to "Spring", "days" to 92),
    mapOf("season" to "Summer", "days" to 92),
    mapOf("season" to "Autumn", "days" to 91)
)
```

## From JSON-like text to Kotlin

Let's say you run the following Kotlin code:

```kotlin
val data = listOf(
    mapOf("season" to "Winter", "days" to 90.25),
    mapOf("season" to "Spring", "days" to 92),
    mapOf("season" to "Summer", "days" to 92),
    mapOf("season" to "Autumn", "days" to 91)
)

println(data)
```

It will print out the following:

```
[ {season=Winter, days=90.25}, 
  {season=Spring, days=92},
  {season=Summer, days=92},
  {season=Autumn, days=91} ]
```

Such a string is not JSON. And it cannot even be unambiguously interpreted. 

`json2kt` will accept such a string as input, and recognize it if possible.


## Read/write files 

The utility has no special arguments for working with files. But it works well with UNIX redirects.

Getting input from file:

```
java -jar json2kt.jar < file.json
```

Writing output to file:

```
java -jar json2kt.jar > output.kt
```
