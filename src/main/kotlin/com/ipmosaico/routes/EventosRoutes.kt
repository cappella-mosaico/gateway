import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import khttp.get as httpGet

@Serializable
data class Evento(
    val titulo: String,
    val dataInicial: String,
    val dataFim: String,
    val imagemURL: String,
    val sobre: String,
    val valor: String,
    val local: String,
    val endereco: String
)

fun Route.eventosRouting() {

    val ROOT = "http://eventos:8080"

    route("/eventos") {
        get {
            val amount = call.request.queryParameters["amount"]
            val response = httpGet("$ROOT/eventos?amount=$amount")
            call.respondBytes { response.content }
        }
    }

}