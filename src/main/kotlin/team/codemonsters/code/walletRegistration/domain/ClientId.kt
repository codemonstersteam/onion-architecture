package team.codemonsters.code.walletRegistration.domain

import java.util.*

data class ClientId private constructor(
    val value: UUID
) {
    companion object {
        fun emerge(clientId: String): Result<ClientId> =
            Result.runCatching { UUID.fromString(clientId) }
                .map { ClientId(it) }
    }

}
