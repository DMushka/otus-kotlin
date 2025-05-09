import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals

/*
* Реализовать функцию, которая преобразует список словарей строк в ФИО
* Функцию сделать с использованием разных функций для разного числа составляющих имени
* Итого, должно получиться 4 функции
*
* Для успешного решения задания, требуется раскомментировать тест, тест должен выполняться успешно
* */
class HomeWork1Test {

    @Test
    //@Ignore
    fun mapListToNamesTest() {
        val input = listOf(
            mapOf(
                "first" to "Иван",
                "middle" to "Васильевич",
                "last" to "Рюрикович",
            ),
            mapOf(
                "first" to "Петька",
            ),
            mapOf(
                "first" to "Сергей",
                "last" to "Королев",
            ),
        )
        val expected = listOf(
            "Рюрикович Иван Васильевич",
            "Петька",
            "Королев Сергей",
        )
        val res = mapListToNames(input)
        assertEquals(expected, res)
    }

    private fun mapListToNames(input : List<Map<String, String>>) : List<String> {
        return mutableListOf<String>().apply {
            input.forEach { nameMap ->
                this.add(
                    when (nameMap.size) {
                        1 -> oneMem(nameMap)
                        2 -> twoMem(nameMap)
                        3 -> threeMem(nameMap)
                        else -> ""
                    }
                )
            }
        }.toList()
    }

    private fun oneMem(nameMap: Map<String, String>) : String {
        return nameMap.getOrDefault("first", "")
    }

    private fun twoMem(nameMap: Map<String, String>) : String {
        return "${nameMap.getOrDefault("last", "")} ${nameMap.getOrDefault("first", "")}"
    }

    private fun threeMem(nameMap: Map<String, String>) : String {
        return "${nameMap.getOrDefault("last", "")} ${nameMap.getOrDefault("first", "")} ${nameMap.getOrDefault("middle", "")}"
    }

}