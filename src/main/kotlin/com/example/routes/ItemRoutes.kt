package com.example.routes

import com.example.models.Item
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

// In-memory storage for items
private val itemStore = mutableListOf(
    Item(1, "Laptop", "High-performance laptop", 999.99),
    Item(2, "Mouse", "Wireless mouse", 29.99),
    Item(3, "Keyboard", "Mechanical keyboard", 79.99)
)

fun Route.itemRoutes() {
    route("/items") {
        // GET all items
        get {
            call.respond(itemStore)
        }

        // GET item by ID
        get("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, "Invalid ID")
                return@get
            }

            val item = itemStore.find { it.id == id }
            if (item == null) {
                call.respond(HttpStatusCode.NotFound, "Item not found")
            } else {
                call.respond(item)
            }
        }

        // POST create new item
        post {
            val item = call.receive<Item>()
            
            // Check if ID already exists
            if (itemStore.any { it.id == item.id }) {
                call.respond(HttpStatusCode.Conflict, "Item with this ID already exists")
                return@post
            }
            
            itemStore.add(item)
            call.respond(HttpStatusCode.Created, item)
        }

        // PUT update existing item
        put("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, "Invalid ID")
                return@put
            }

            val updatedItem = call.receive<Item>()
            val index = itemStore.indexOfFirst { it.id == id }
            
            if (index == -1) {
                call.respond(HttpStatusCode.NotFound, "Item not found")
            } else {
                itemStore[index] = updatedItem.copy(id = id)
                call.respond(itemStore[index])
            }
        }

        // DELETE item
        delete("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, "Invalid ID")
                return@delete
            }

            val removed = itemStore.removeIf { it.id == id }
            if (removed) {
                call.respond(HttpStatusCode.NoContent)
            } else {
                call.respond(HttpStatusCode.NotFound, "Item not found")
            }
        }
    }
}
