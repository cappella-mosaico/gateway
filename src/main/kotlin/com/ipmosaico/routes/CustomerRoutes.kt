
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.JsonObject
import khttp.delete as httpDelete
import khttp.get as httpGet
import khttp.post as httpPost
import khttp.put as httpPut

fun Route.customerRouting() {

    route("/pastorais") {
        get {
            val amount = call.request.queryParameters["amount"]
            val response = httpGet("http://localhost:7000/pastorais/public/latest?amount=$amount")
            call.respondBytes { response.content }
        }
        authenticate ("auth-jwt") {
            post {
                val pastoral = call.receive<JsonObject>()
                println(pastoral)

                val headers : Map<String, String> = call.request.headers.entries()
                    .associate { Pair(it.key, it.value.get(0)) }.toMutableMap()
                println(headers)
                val response = httpPost(
                    headers = headers,
                    url = "http://localhost:7000/pastorais",
                    data = pastoral.toString()
                )
                call.respondBytes { response.content }
            }
        }
        authenticate ("auth-jwt") {
            delete("{id?}") {
                val id = call.parameters["id"] ?: return@delete call.respondText(
                    "Missing id",
                    status = HttpStatusCode.BadRequest
                )

                val headers : Map<String, String> = call.request.headers.entries()
                    .associate { Pair(it.key, it.value.get(0)) }.toMutableMap()
                println(headers)
                val response = httpDelete(
                    headers = headers,
                    url = "http://localhost:7000/pastorais/$id",
                )
                call.respondBytes { response.content }
            }
        }
        authenticate ("auth-jwt") {
            put("{id?}") {
                val id = call.parameters["id"] ?: return@put call.respondText(
                    "Missing id",
                    status = HttpStatusCode.BadRequest
                )

                val headers : Map<String, String> = call.request.headers.entries()
                    .associate { Pair(it.key, it.value.get(0)) }.toMutableMap()
                println(headers)
                val response = httpPut(
                    headers = headers,
                    url = "http://localhost:7000/pastorais/notify/$id",
                )
                call.respondBytes { response.content }
            }
        }
    }

}