
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

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

    route("/eventos") {
        get {
            call.respond(listOf(
                Json.decodeFromString<Evento>("""
                  {
                    "titulo" : "acampamento",
                    "dataInicial" : "new Date(2021, 12, 8, 20, 0)",
                    "dataFim" : "new Date(2021, 12, 8)",
                    "imagemURL" : "https://reactjs.org/logo-og.png",
                    "sobre" : "A Igreja Presbiteriana Mosaico existe para acolher pessoas e formar discípulos de Cristo através de relacionamentos saudáveis e uma pregação bíblica contemporânea no bairro Setor Bueno, na cidade de Goiânia e no mundo",
                    "valor" : "R${'$'} 15,00 adulto R${'$'} 10,00 até 12 anos",
                    "local" : "Na Igreja Presbiteriana Mosaico",
                    "endereco" : "Rua T-53, 480, Setor Bueno Goiânia/Go - Cep 74810-210"
                  }
            """.trimIndent())))
        }
    }

}