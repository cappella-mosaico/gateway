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
import khttp.delete as httpDelete


fun Route.compromissosRouting() {

  val ROOT = "http://compromissos:8080"

  route("/compromissos") {
    get {
      val ministerio = call.request.queryParameters["ministerio"]
      val passado = call.request.queryParameters["compromissosDoPassado"] ?: ""
      if (ministerio != null) {
        val response = httpGet(
          url = "$ROOT/compromissos",
          params = mapOf("ministerio" to ministerio, "compromissosDoPassado" to passado)
        )
        call.respondText(response.text, ContentType.Application.Json)
      } else {
        call.respond(HttpStatusCode.BadRequest, "Missing 'ministerio' parameter")
      }
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
        println(compromisso)

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

      delete("{id?}") {
          val id = call.parameters["id"] ?: return@delete call.respondText(
              "Missing id",
              status = HttpStatusCode.BadRequest
          )
          val headers : Map<String, String> = call.request.headers.entries()
              .associate { Pair(it.key, it.value.get(0)) }.toMutableMap()

          val response = httpDelete(
              headers = headers,
              url = "$ROOT/compromissos/$id",
          )
          call.respondBytes { response.content }
      }

    }
  
  }

}
