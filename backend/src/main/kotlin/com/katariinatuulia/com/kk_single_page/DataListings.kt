package com.katariinatuulia.com.kk_single_page.api

//******************** IMPORTS ********************//

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.http.ResponseEntity
import org.springframework.http.HttpStatus

data class DataEntry(val id: Int, val name: String, val category: String)

//******************** APP ********************//

@RestController
@RequestMapping("/api/data")
class DataController {

    private val data = mutableListOf(
        DataEntry(1, "Linux", "System"),
        DataEntry(2, "React", "Language"),
        DataEntry(3, "Jpg", "Filetype"),
        DataEntry(4, "Kotlin", "Language"),
        DataEntry(5, "Windows", "System"),
        DataEntry(6, "Java", "Language"),
        DataEntry(7, "Typescript", "Language"),
        DataEntry(8, "Mac", "System"),
        DataEntry(9, "Pdf", "Filetype"),
        DataEntry(10, "Android", "System"),
    )

    @GetMapping
    fun listData(
        @RequestParam(required = false) category: String?,
        @RequestParam(required = false) name: String?,
        @RequestParam(required = false) sortBy: String?,
        @RequestParam(required = false) sortOrder: String?
    ): List<DataEntry> {

        var filteredData = data.toList()

        // Filtering by category
        if (!category.isNullOrBlank()) {
            println("Filtering by category: $category")
            filteredData = filteredData.filter { it.category.equals(category, ignoreCase = true) }
        }

        // Filtering
        if (!name.isNullOrBlank()) {
            println("Filtering by name: $name")
            filteredData = filteredData.filter { it.name.contains(name, ignoreCase = true) }
        }

        // Sorting
        if (!sortBy.isNullOrBlank()) {
            filteredData = when (sortBy.lowercase()) {
                "name" -> if (sortOrder?.lowercase() == "desc") filteredData.sortedByDescending { it.name } else filteredData.sortedBy { it.name }
                "category" -> if (sortOrder?.lowercase() == "desc") filteredData.sortedByDescending { it.category } else filteredData.sortedBy { it.category }
                "id" -> if (sortOrder?.lowercase() == "desc") filteredData.sortedByDescending { it.id } else filteredData.sortedBy { it.id }
                else -> filteredData
            }
        }

        return filteredData
    }

    // Search for single entry
    @GetMapping("/{id}")
    fun getById(@PathVariable id: Int): ResponseEntity<DataEntry> {
        val entry = data.find { it.id == id }
        return if (entry != null) {
            ResponseEntity.ok(entry)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    // Add a record
    @PostMapping
    fun addData(@RequestBody newEntry: DataEntry): ResponseEntity<String> {
        if (data.any { it.id == newEntry.id }) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("ID already exists.")
        }

        data.add(newEntry)
        return ResponseEntity("Data added", HttpStatus.CREATED)
    }

    // Delete a single entry
    @DeleteMapping("/{id}")
    fun deleteData(@PathVariable id: Int): ResponseEntity<String> {
        val removed = data.removeIf { it.id == id }

        return if (removed) {
            ResponseEntity("Data with ID $id deleted.", HttpStatus.OK)
        } else {
            ResponseEntity("Data with ID $id not found.", HttpStatus.NOT_FOUND)
        }
    }

}