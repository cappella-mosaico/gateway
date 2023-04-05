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
import khttp.put as httpPut


fun Route.compromissosRouting() {

  val ROOT = "http://compromissos:8080"

  route("/compromissos") {
    get {
      val response = httpGet("$ROOT/compromissos")
      call.respondBytes { response.content }
    }

    route("/{compromissoId}") {
      get {
        val compromissoId = call.parameters["compromissoId"]
        val response = httpGet("$ROOT/compromissos/$compromissoId")
        call.respondBytes { response.content }
      }
    }

    authenticate ("auth-jwt") {

      post {
        val compromisso = call.receive<JsonObject>()
        println(evento)

        val headers : Map<String, String> = call.request.headers.entries()
          .associate { Pair(it.key, it.value.get(0)) }.toMutableMap()
        println(headers)
        val response = httpPost(
          headers = headers,
          url = "$ROOT/compromissos",
          data = compromisso.toString()
        )
        call.respondBytes { response.content }
      }

    }
  
  }

}
