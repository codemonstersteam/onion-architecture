# Onion Architecture
Привет. 
Статью я написал после того, как в [Бэкенд академи](https://gpb.fut.ru/itfactory/backend) показал пример проектирования простого приложения студентам и увидел, что не просто донести суть разделения на слои приложения. 
Какую задачу решает разделение на слои? 
Какую задачу решаем на каждом слое?
Не просто раскрыть суть студенту Инкапсуляции в Объектоно Ориентированном Программированиио, о которой нам рассказывают после школы преподаватели.
Статья для самых маленьких и таит в себе основу проектирования надежного софта.  


Все что описано в этой статье - работает в реальных проектах.  
[Код приложения](https://github.com/codemonstersteam/onion-architecture) написан для того, чтобы под рукой у тебя был исходный код, который поможет докапаться до сути тому, кто хоче научиться проектировать и писать Качественный Код.

[Первоисточник Jeffrey Palermo 2008](https://jeffreypalermo.com/2008/07/the-onion-architecture-part-1/)

Луковая архитектура — это архитектурный шаблон программного обеспечения, 
который обеспечивает модульную и слабосвязанную конструкцию, 
уделяя особое внимание разделению задач и удобству сопровождения.

Подход помогает разработчикам создавать более гибкие приложения,  
которые просто тестировать и, как следствие, такой приклад легче развивать с течением времени. 
В этой статье мы углубимся в ключевые концепции луковой архитектуры и предоставим пример структуры папок, иллюстрирующий ее реализацию.

#  Ключевые концепции Луковой Архитектуры
В самом центре мы видим модель предметной области, которая представляет собой комбинацию состояния и поведения, 
моделирующую бизнес-процесс.

Вокруг модели предметной области существуют другие уровни приложения. 
Количество слоев в ядре приложения может различаться.
Модель предметной области — самый центр.
Поскольку все связи направлены к центру, **модель предметной области связана только сама с собой**. 

На краю "Луковицы" обычно располагаем интерфейсы, обеспечивающие сохранение и извлечение объектов.
В примере кода эти интерфейсы представлены Шлюзами ([ClientGateway](src/main/kotlin/team/codemonsters/code/walletRegistration/application/ClientGateway.kt), [WalletGateway](src/main/kotlin/team/codemonsters/code/walletRegistration/application/WalletGateway.kt)). 
Так называемый уровень Шлюзов и Адапперов. 
**Важно**, обратите внимание на это - поведение сохранения объектов (ClientGateway) не является ядром приложения, 
поскольку оно обычно задействует базу данных. 

В ядре приложения находится только интерфейс для работы с этим слоем. 
По краям мы видим пользовательский интерфейс, инфраструктуру. 
Внешний слой предназначен для вещей, 
которые часто меняются. 
Например, мы используем интерфейс командной строки, чтобы запустить бизнес-процесс,
мы можем легко доработать приложение и добавить интерфейс взаимодействия с приложением по REST API.

API интерфейс, инфру следует намеренно изолировать от ядра приложения. 
Так на краю лука мы найдем класс, реализующий интерфейс репозитория. 
Этот класс привязан к определенному методу доступа к данным и поэтому находится вне ядра приложения. 
Этот класс реализует интерфейс репозитория и, таким образом, связан с ним.

## Правило зависимости:

![onion.png](visual/onion.png)

Зависимости текут внутрь, при этом внутренние уровни не имеют информации о внешних слоях. Это гарантирует, что модули высокого уровня не зависят напрямую от модулей низкого уровня. Вместо этого оба зависят от абстракций, что обеспечивает взаимозаменяемость реализаций и уменьшает связанность.

## Слои
- Слой Предметной Области (Domain Layer): Содержит основную бизнес-логику, бизнес-правила приложения.
- Уровень приложения (Application Layer): Описывает бизнес-сценарий (use-case), взаимодействует с уровнями предметной области и инфраструктуры.  
- Уровень инфраструктуры (Infrastructure Layer): Управляет внешними для приложения зависимостями, такими как базы данных, файловые системы или внешние службы (Web-сервисы).  
- Уровень представления (Presentation Layer): Управляет пользовательскими интерфейсами и логикой, связанной с представлением. Точка входа нашего приложения. Например, REST API, терминал.

## База данных не является центром. Это внешний слой.

Вынесение наружу базы данных может стать значительным изменением для некоторых людей, 
привыкших думать о приложениях как о «приложениях баз данных». 
Это очень важный свдиг парадигмы восприятия проектирования приложения. 
Я замечаю, как не просто студентам это объяснить. Много новичков и старичков привыкли воспринимать приложения как «приложения баз данных» и теряют важную суть моделирования бизнес-процесса, не могут пробить путь к пониманию зачем нам эти абстракции и какую задачу они решают.
В луковой архитектуре нет приложений баз данных. Существуют приложения, которые могут использовать базу данных в качестве службы хранения, но только через некоторый код внешней инфраструктуры, реализующий интерфейс, **имеющий смысл для ядра приложения**. Отделение приложения от базы данных, файловой системы и т. д. снижает стоимость обслуживания на протяжении всего срока службы приложения.

Отделение приложения от базы данных, Позволяет сконцентророваться на моделировании бизнес-процесса и описать бизнес-логику (use-case), бизнес-правила (business-rules) объектами. 
Объекты мы будем тестировать юнит-тестами, а значит вся бизнес-логика у нас покрыта юнит-тестами - это недооценимый подарок данного подхода к проектированию, которые многие разработчики не ценят, а ведь он ведет нас прямиком к сбалансированной Пирамиде Тестирования, надежному софту и качественному ИТ производству. 

Alistair Cockburn написал о [гексагональной архитектуре](http://alistair.cockburn.us/index.php/Hexagonal_architecture) 
Шестиугольная архитектура и луковая архитектура имеют общую предпосылку: вынести инфраструктуру наружу и написать код адаптера, чтобы инфраструктура не становилась тесно связанной.

## Разделение границ
Каждый уровень имеет четкую ответственность, гарантируя, 
что бизнес-логика остается отделенной от проблем инфраструктуры или представления. 
Такое разделение повышает удобство сопровождения кода и облегчает тестирование.

## Пример структуры папок:

### Слой предметной-области (Domain Layer):

> src/domain/model #бизнес правила (business-rules)


### Уровень приложения (Application Layer):

> src/application/service
> src/application/usecase

Реализует бизнес-процесс (use-case). 
Управляет взаимодействием между уровнями предметной области и инфраструктуры 
по шагам бизнес-процесса.

### Уровень инфраструктуры (Infrastructure Layer)

Обрабатывает внешние зависимости и технические детали.
Примеры папок: 

>src/infrastructure/persistence
>src/infrastructure/service #rest, queue

 
## Преимущества луковой архитектуры:
### Модульность и Простота сопровождения:
Четкое разделение задач и разделение зависимостей упрощают сопровождение и модификацию кода, 
делая его более адаптируемым к меняющимся требованиям.

### Тестируемость:
Каждый уровень может быть протестирован независимо. 
Позволяет проводить комплексные модульные тесты (unit tests) и гарантировать, 
что бизнес-логика остается изолированной от внешних зависимостей и **покрыта 100% юнит-тестами**.

### Гибкость
Модульная конструкция облегчает внедрение новых фрэймворков и инструментов (переход с IBM MQ, Kafka), 
не затрагивая бизнес-логику,  
повышая масштабируемость и устойчивость приложения.


----

# Пример приложения в исходнике
Дело не в название архитектуры (Clean Architecture, Onion Architecture, Hexagonal Architecture).
Все это об одном - изоляции слоев друг от друга.

Есть три важные идеи, которые упускают даже опытные разработчики с опытом работе более 20-ти лет.

1. Бизнес логика описана в неизменяемых классах. Тестируется юнит-тестами.
2. Сервисный класс не содержит бизнес-правила, он описывает поток исполнения бизнес-сценария. Простой оркестратор.
   - Взаимодействует с моделями предметной области, которые описаны в центре круга.
   - Взаимодействует с внешним миром по бизнес-сценарию через шлюзы.
     
   _Код такого сервиса тривиален и его можно не тестировать._
3. База данных не является центром приложения. Мы не думаем о приложениях как о «приложениях баз данных». Мы моделируем бизнес-процесс в моделях(Классах), описываем use-case в простейшем сервисе. Сервис взаимодействует с внешним миром через Шлюзы. 

Поверьте,
Я не просто так показал вам луковичную архитектуру. Это важно. Это не случайность.
Не просто так пишу об этом. Огромное количество кодовой базы разработано без учета этих рекомендаций, что приводит к усложнению кода и тестирования.

### Визуализация потока управления (control-flow)

![control-flow.png](visual%2Fcontrol-flow.png)

### Описание потока управления программы:  
1. Приложение получает запрос из внешнего мира (DTO) [TerminalApp.kt](src%2Fmain%2Fkotlin%2Fteam%2Fcodemonsters%2Fcode%2FwalletRegistration%2Fpresentation%2FTerminalApp.kt)    
2. [Транслирует данные из DTO в модель, которая описывает непроверенный запрос](https://git.codemonsters.team/guides/effective-code/-/blob/main/src/main/kotlin/team/codemonsters/code/walletRegistration/presentation/TerminalApp.kt#L23)   
3. Передает данные упакованные в Непроверенный запрос в Сервис Приложения [WalletRegistrationService.kt](src%2Fmain%2Fkotlin%2Fteam%2Fcodemonsters%2Fcode%2FwalletRegistration%2Fapplication%2FWalletRegistrationService.kt#L16)    
4. Исполняем бизнес-логику (use-case) в сервисе уровня приложения [WalletRegistrationService.kt](src%2Fmain%2Fkotlin%2Fteam%2Fcodemonsters%2Fcode%2FwalletRegistration%2Fapplication%2FWalletRegistrationService.kt#L16)    
   5. [Проверяем данные, которые пришли на вход и создаем валидную модель](https://git.codemonsters.team/guides/effective-code/-/blob/main/src/main/kotlin/team/codemonsters/code/walletRegistration/application/WalletRegistrationService.kt#L17)   
   6. [Ищем клиента по идентификатору: используем ClientGateway](https://git.codemonsters.team/guides/effective-code/-/blob/main/src/main/kotlin/team/codemonsters/code/walletRegistration/application/WalletRegistrationService.kt#L23)  
   7. [Регистрируем кошелек в системе управления кошельками: используем WalletGateway](https://git.codemonsters.team/guides/effective-code/-/blob/main/src/main/kotlin/team/codemonsters/code/walletRegistration/application/WalletRegistrationService.kt#L27)      
   8. Привязываем кошелек к клиенту и возвращаем результат операции [WalletRegistrationService.kt](src%2Fmain%2Fkotlin%2Fteam%2Fcodemonsters%2Fcode%2FwalletRegistration%2Fapplication%2FWalletRegistrationService.kt#L38)  
9. Транслируем ответ из сервиса приложения в контракт API [TerminalApp.kt](src%2Fmain%2Fkotlin%2Fteam%2Fcodemonsters%2Fcode%2FwalletRegistration%2Fpresentation%2FTerminalApp.kt#L27)    


## 1. Уровень представления
### Какую проблему решает:  
Реализует публичный API приложения.  

Является Точкой входа в приложение. Инициирует исполнение бизнес-сценария.  

Пример публичного API:
- терминал
- REST API
- Подписка на очередь сообщений
и т.п. RPC подобные входы.

В нашем примере это: [TerminalApp.kt](src%2Fmain%2Fkotlin%2Fteam%2Fcodemonsters%2Fcode%2FwalletRegistration%2Fpresentation%2FTerminalApp.kt)

В такой архитектуре легко добавить дополнительную точку входа. Нужно просто подобрать адаптер.

### Что делаем на этом уровне?
1. Получаем запрос извне (ДТО)
2. Формируем из ДТО валидный запрос для сервиса приложения. 
     Проверяем запрос согласно бизнес-правилам доменной модели, которые описаны в API сервиса приложения.
     Транслируем результат ошибки при валидации в понятный ответ нашего API  
3. Передаем управление сервису приложения  
4. Транслируем результат работы сервиса в понятный ответ согласно API  

[WalletRegistrationService.kt](src%2Fmain%2Fkotlin%2Fteam%2Fcodemonsters%2Fcode%2FwalletRegistration%2Fapplication%2FWalletRegistrationService.kt)


## 2. Уровень приложения
Сервис приложения и шлюзы (адаптеры)

## 2.1 Сервис приложения
### Какую проблему решает:

Сервис приложения реализует бизнес-сценарий (use-case) по шагам и обеспечивает консистентность выполнения бизнес-сценария.  
Взаимодействует с консистентными моделями предметной области в которых описаны бизнес-правила
и передает управление на уровень инфраструктуры через Шлюзы, чтобы получить модели предметной области и передать их в уровень хранения.

#### Класс сервиса:  
Сервис Регистрации Кошелька
[WalletRegistrationService.kt](src%2Fmain%2Fkotlin%2Fteam%2Fcodemonsters%2Fcode%2FwalletRegistration%2Fapplication%2FWalletRegistrationService.kt)  

### Что делаем на этом уровне?
1. Исполняем бизнес-сценарий шаг за шагом (use-case)
2. Получаем нужные данные из шлюзов в виде моделей предметной области
3. Отправляем данные в шлюзы на сохранение или для запуска исполнения внешнего процесса по бизнес-сценарию в виде моделей предметной области

## 2.2 Шлюзы: 
Шлюзы обеспечивают взаимодействие с внешними системами (веб сервисы, очереди, и т.п.) 
и уровнем хранения (база данных)
[ClientGateway.kt](src%2Fmain%2Fkotlin%2Fteam%2Fcodemonsters%2Fcode%2FwalletRegistration%2Fapplication%2FClientGateway.kt)  
Для чего:
1. Чтобы получить модели предметной области
2. Чтобы передать модели предметной области во внешний мир, в том числе для сохранения.

### Что делаем на этом уровне?
Делаем только то, что нужно по бизнес-процессу. Ничего другого.

#### Получаем данные для сервиса приложения:

ClientGateway[ClientGateway.kt](src%2Fmain%2Fkotlin%2Fteam%2Fcodemonsters%2Fcode%2FwalletRegistration%2Fapplication%2FClientGateway.kt) > findClient

1. Получаем запрос из Сервиса Приложения в виде модели предметной области
2. Транслируем запрос из модели предметной области в соответствующий запрос для инфраструктурного сервиса (DTO, DB Entity)
3. Передаем запрос в инфраструктурный сервис
4. Транслируем ответ от инфраструктурного сервиса (DTO, DB Entity) в консистентную модель предметной области
5. Возвращаем ответ в виде модели предметной области [domain](src%2Fmain%2Fkotlin%2Fteam%2Fcodemonsters%2Fcode%2FwalletRegistration%2Fdomain)

#### Отправляем данные из сервиса приложения, например сохраняем модель в базе данных.

ClientGateway[ClientGateway.kt](src%2Fmain%2Fkotlin%2Fteam%2Fcodemonsters%2Fcode%2FwalletRegistration%2Fapplication%2FClientGateway.kt) > registerWallet

1. Получаем запрос из Сервиса Приложения в виде модели предметной области
2. Транслируем запрос из модели предметной области в соответствующий запрос для инфраструктурного сервиса (DTO, DB Entity)
3. Передаем запрос в инфраструктурные сервис
4. Транслируем ответ от инфраструктурного сервиса в консистентную модель предметной области
5. Возвращаем ответ в виде модели предметной области [domain](src%2Fmain%2Fkotlin%2Fteam%2Fcodemonsters%2Fcode%2FwalletRegistration%2Fdomain)

## 3. Уровень инфраструктуры
### Какую проблему решает:
Обеспечивает взаимодействие с внешним миром для приложения.

### Что делаем на этом уровне?

1. Интеграция с Базой Данных:  
   [ClientRepository.kt](src%2Fmain%2Fkotlin%2Fteam%2Fcodemonsters%2Fcode%2FwalletRegistration%2Finfrastructure%2FClientRepository.kt)

2. Клиенты для взаимодействия с веб-сервисами по REST API, брокерами сообщений, SOAP, smtp, и т.п.


## 4. Уровень модели предметной области (Domain Layer)
[domain](src%2Ftest%2Fkotlin%2Fteam%2Fcodemonsters%2Fcode%2FwalletRegistration%2Fdomain)

На этом уровне мы тщательно моделируем бизнес-процесс (MODEL DRIVEN DEVELOPMENT). 
Описываем бизнес-правила в объектах-значениях [ClientId.kt](src%2Fmain%2Fkotlin%2Fteam%2Fcodemonsters%2Fcode%2FwalletRegistration%2Fdomain%2FClientId.kt) 
и описываем более сложные отношения моделей в агрегирующих моделях [ClientWithWallet.kt](src%2Fmain%2Fkotlin%2Fteam%2Fcodemonsters%2Fcode%2FwalletRegistration%2Fdomain%2FClientWithWallet.kt) и бизнес-сущностях [ClientWithoutWallet.kt](src%2Fmain%2Fkotlin%2Fteam%2Fcodemonsters%2Fcode%2FwalletRegistration%2Fdomain%2FClientWithoutWallet.kt)  
[WalletRegistrationRequest.kt](src%2Fmain%2Fkotlin%2Fteam%2Fcodemonsters%2Fcode%2FwalletRegistration%2Fdomain%2FWalletRegistrationRequest.kt)


### Какую проблему решает:
Моделирования и юнит-тестирования бизнес-логики. Описываем бизнес-правила (business-rules)

### Что делаем на этом уровне?
Описываем модели предметной области, бизнес-правила в моделях и отношения моделей.
Гарантируем изоляцию моделей предметной области от инфры.

Модели предметной области знают только о моделях предметной области.

### Пример класса Клиент без кошелька:
[ClientWithoutWallet.kt](src%2Fmain%2Fkotlin%2Fteam%2Fcodemonsters%2Fcode%2FwalletRegistration%2Fdomain%2FClientWithoutWallet.kt)

````kotlin
data class ClientWithoutWallet(
    val clientId: ClientId,
    val name: Name,
    val walletId: WalletId
) {

    companion object {
        fun emerge(clientId: String, name: String, walletId: String?): Result<ClientWithoutWallet> {
            val validClientId = ClientId.emerge(clientId)
            if (validClientId.isFailure)
                return Result.failure(validClientId.exceptionOrNull()!!)

            val validName = Name.emerge(name)
            if (validName.isFailure)
                return Result.failure(validName.exceptionOrNull()!!)


            if (null == walletId) {
                return Result.success(ClientWithoutWallet(validClientId.getOrThrow(), validName.getOrThrow(), WalletId.Empty))
            }
            return Result.failure(RuntimeException("У клиента уже есть кошелек"))
        }
    }
}
````

# Делать правильно меня замедляем
Нет. Ради всего святого, это ускорит тебя.
Да я понимаю, что сразу делать правильно не просто. В любом мастерстве есть базовые вещи.
В единоборствах - это стоять в равновесии на одной ноге, включать правильные мышцы.
Да, сначала может быть не просто, но если ты поймешь какую задачу решать на каком слое, как моделировать бизнес-процессы консистентными объектами - ты начнешь побеждать качественно с малым TTM (time to market). 
Ты сможешь запускать тесты без задержек. 
Ты сможешь тестировать бизнес-правила без пользовательского интерфейса и внешних API. 
Такая гибкость и отсутствие связности всегда тебя ускоряют. 
Если одна вещь, которую мы уяснили о связывании за последние пятьдесят-шестьдесят лет - 
связывание лучшее, что может тебя замедлить в разработке.
И связные релизы - это то, что может тебя замедлить в производстве.
Знай это. Делай правильно.

## Links
- [Onion Architecture: Первоисточник Jeffrey Palermo 2008](https://jeffreypalermo.com/2008/07/the-onion-architecture-part-1/)
- [Clean Architecture](https://blog.cleancoder.com/uncle-bob/2011/11/22/Clean-Architecture.html)
- [Clean Architecture](https://herbertograca.com/2017/09/28/clean-architecture-standing-on-the-shoulders-of-giants/)