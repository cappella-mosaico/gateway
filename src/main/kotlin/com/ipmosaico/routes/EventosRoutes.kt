import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.JsonObject
import khttp.get as httpGet
import khttp.post as httpPost


fun Route.eventosRouting() {

    val ROOT = "http://eventos:8080"

    route("/eventos") {
	    get {
		val amount = call.request.queryParameters["amount"]
		val response = httpGet("$ROOT/eventos?amount=$amount")
		call.respondBytes { response.content }
	    }

	authenticate ("auth-jwt") {
	    post {
		val evento = call.receive<JsonObject>()
		println(evento)

		val headers : Map<String, String> = call.request.headers.entries()
		    .associate { Pair(it.key, it.value.get(0)) }.toMutableMap()
		println(headers)
		val response = httpPost(
		    headers = headers,
		    url = "$ROOT/eventos",
		    data = evento.toString()
		)
		call.respondBytes { response.content }
	    }
	}
	
    }

}
