package team.codemonsters.code.walletRegistration.domain

//public final class Name {

// private Name()
//
data class Name private constructor(val value: String) {
    companion object {
        private val CYRILLIC_NAME_PATTERN = Regex("[А-Я][а-я]{1,8}$")

        fun emerge(name: String): Result<Name> {
            return if (CYRILLIC_NAME_PATTERN.matches(name)) {
                Result.success(Name(name))
            } else {
                Result.failure(RuntimeException("Некорректное имя"))
            }
        }
    }
}