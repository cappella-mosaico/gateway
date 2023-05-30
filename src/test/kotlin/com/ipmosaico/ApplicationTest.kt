package com.ipmosaico

import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.plugins.*
import io.ktor.server.auth.*
import io.ktor.util.*
import io.ktor.server.auth.jwt.*
import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlin.test.*
import io.ktor.server.testing.*
import com.ipmosaico.plugins.*
import java.io.File
import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.Test

class ApplicationTest {

    companion object {
        private lateinit var tempFile: File

        @BeforeClass
        @JvmStatic
        fun setup() {
            // Create a temporary file
            tempFile = File("/ipmosaico-users/users.json")
            tempFile.createNewFile()

            // Write some content to the file
            tempFile.writeText("""{
                                   "ipmosaico": "sha256pass"
                               }""")
        }

        @AfterClass
        @JvmStatic
        fun cleanup() {
            // Delete the temporary file
            tempFile.delete()
        }
    }

    @Test
    fun testRoot() = testApplication {
        application {
            configureRouting()
        }
    }

}
