import kotlinx.serialization.Serializable

@Serializable
data class Customer(var id: String, val firstName: String, val lastName: String, val email: String)

val customerStorage = mutableListOf<Customer>()