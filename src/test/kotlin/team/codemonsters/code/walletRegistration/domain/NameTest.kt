package team.codemonsters.code.walletRegistration.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class NameTest {
    //одно утверждение на тест
    @Test
    fun `should be valid name`() {
        //A
        val name = Name.emerge("Рома")
        //A
        assertValidName(name)
    }

    fun assertValidName(name: Result<Name>) {
        assertThat(name.isSuccess).isTrue
        assertThat(name.getOrThrow().value).isEqualTo("Рома")
    }

    @Test
    fun `should be invalid name cause longer`() {
        //A
        val name = Name.emerge("Имядлиннеечемдевятьсимволов")
        //A
        assertThat(name.isFailure).isTrue
    }

    @Test
    fun `should be invalid name cause lowercase`() {
        //A
        val name = Name.emerge("имя")
        //A
        assertThat(name.isFailure).isTrue
    }

}