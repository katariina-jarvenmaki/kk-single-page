package com.katariinatuulia.com.kk_single_page.api

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

data class DataEntry(val id: Int, val name: String, val category: String)

@RestController
@RequestMapping("/api/data")
class DataController {

    private val data = mutableListOf(
        DataEntry(1, "Linux", "System"),
        DataEntry(2, "React", "Language"),
        DataEntry(3, "Jpg", "Filetype"),
        DataEntry(2, "Kotlin", "Language"),
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
        println("category: $category, name: $name")

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
}