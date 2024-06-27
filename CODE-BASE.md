# База по коду

Выжимка стандарта для разработки мэйнстрим приложений на Java | Kotlin

Эффективный код состоит из консистентных не изменяемых классов  
Которые легко тестировать  
Размер методов в классах составляет до 5 строк кода  
На вход в чистые методы поступает один аргумент  
Если метод класса взаимодействует с внепроцессорным миром по сети, то он всегда возвращает Result  
И классы в эффективном коде живет в Null Safety парадигме  

Жесткие рамки при проектировании кода, 
дарят простоту и код, который легко тестировать.  
Просто нужно создать меньше возможностей усложнить себе жизнь.  

## Код

## Не передавай null, не возвращай null
И точка

### Именование методов / функций

Давайте такие имена классам операциям, чтобы они описывали их назначенные и получаемый результат, но не способ
выполнения ими своих функций. Это избавляет разработчика клиентского кода от необходимости понимать "внутреннюю кухню"
этих объектов.

Формулируйте уравнение, но не численный метод его решения. Ставьте вопрос, по не раскрывайте средств поиска ответа.

```kotlin
    fun findClient(id: UUID): Client
```

### Используй Optional | Result если выполнение результата не гарантировано

Если код описывает взаимодействие с внешними системами - возможен, сбой - используй Result

```kotlin
    fun findClient(id: UUID): Result<Client>
```

- [Optional](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Optional.html)
- [Result](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-result/)

Если операция подразумевает сетевой сбой, отказ системы, в случае реализации метода Шлюза
возвращай Result, так как Result содержит информацию об ошибке

## Объекты

> Объект должен гарантировать, что никогда не окажется в невалидном состоянии.

Инкапсуляция - одна из наиболее неправильно понимаемых концепций ООП. Многие программисты считают, что это запрет на
прямое раскрытие полей классов поля классов должны быть «инкапсулированы» за геттерами и сеттерами. Наиболее важно то,
что объект должен гарантировать, что никогда не окажется в невалидном состоянии.

Проектирование от модели - эффективный путь к моделированию бизнес-процессов.

## Опирайся на уникальные типы предметной области
Объекты-значения сами по себе стоит рассматривать как типы наравне со стандартными типами языка, например
String, int и т.п. Создавая новые объекты для представления понятий, вы расширяете систему типов.
Компилятор и среда разработки помогают проверять соответствия типов, чтобы только правильные типы использовались при передаче значений через аргументы методов и в возвращаемых значениях. 

Описание модели предметной области - прекрасный способ тестировать бизнес-логику юнит-тестами.

### Объект значение
Базовый кирпичик моделирования предметной области.
Стандартый класс или примитив нам ничего не говорит про ограничения имени:
```kotlin
data class Client (val name: String)
```
Тогда как объект-значение гарантирует свое валидное состояние и его безопасно использовать как элемент, более сложного класса(типа):
```kotlin
data class Name private constructor(val value: String) {
    companion object {
        private val CYRILLIC_NAME_PATTERN = Regex("^[а-яА-я]{9}$")

        fun emerge(name: String): Result<String> {
            return if (CYRILLIC_NAME_PATTERN.matches(name)) {
                Result.success(name)
            } else {
                Result.failure(RuntimeException("Некорректное имя"))
            }
        }
    }
}
```

## Условия

Опирайся на явные логические выражения

```kotlin
    if (isValidName(name))
```

Не используй отрицания в логических выражениях

```kotlin
    if (!invalid(name))
```

## Коллекции

### Возвращай пустую коллекцию, если ничего не найдено

Не возвращай Optional<List> при фильтрации - возвращай пустой список

```kotlin
    fun findBlockedClients(clients: List<Client>): List<Client>
```
