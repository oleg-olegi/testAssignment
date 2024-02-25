Тестовое задание

Имеется датасет https://www.kaggle.com/datasets/cristaliss/ultimate-book-collection-top-100-books-up-to-2023?resource=download, 
содержащий лучшие 100 книг за каждый год с 1980 по 2023 по рейтингу Goodreads, представленный файлом CSV.

Требуется написать сервис, реализующий метод top10, возвращающий первые 10 книг из датасета, 
отфильтрованные в соответствии с параметрами запроса.

REST-эндпоинт: /api/top10
HTTP-метод: GET
Возможные параметры запроса:
year - необязательный параметр, при наличии выдавать книги только указанного года публикации,
column - обязательный параметр, наименование поля, по которому требуется отсортировать данные. 
Возможные значения: book, author, numPages, publicationDate, rating, numberOfVoters,
sort - обязательный параметр, сортировка по возрастанию/убыванию. Возможные значения: ASC, DESC.

В ответ на некорректные входные данные сервис должен реагировать понятными сообщениями об ошибках.

Пример запроса:

```
http://localhost:8080/api/top10?year=2010&column=author&sort=ASC
```

Пример ожидаемой структуры одного из возвращаемых значений:

```json 	
{
      "id":2507,
      "book":"Looking for Alaska",
      "series":"",
      "releaseNumber":"",
      "author":"John Green",
      "description":"Before.\n Miles “Pudge” Halter is done with his safe life at home. His whole life has been one big non-event, and his obsession with famous last words has only made him crave “the Great Perhaps” even more (Francois Rabelais, poet). He heads off to the sometimes crazy and anything-but-boring world of Culver Creek Boarding School, and his life becomes the opposite of safe. Because down the hall is Alaska Young. The gorgeous, clever, funny, sexy, self-destructive, screwed up, and utterly fascinating Alaska Young. She is an event unto herself. She pulls Pudge into her world, launches him into the Great Perhaps, and steals his heart. Then. . . . \nAfter.\n Nothing is ever the same.",
      "numPages":221,
      "format":"Paperback",
      "genres":[
         "Young Adult",
         "Fiction",
         "Contemporary",
         "Romance",
         "Realistic Fiction",
         "Coming Of Age",
         "Teen"
      ],
      "publicationDate":"2005-03-03",
      "rating":3.97,
      "numberOfVoters":1571933
}
```

Датасет требуется загрузить в память при старте программы. 
Путь к файлу с данными должен указываться в конфигурационном файле сервиса. 
В данных могут встречаться значения рейтинга, даты публикации и других полей, не поддающиеся парсингу (пустые строки, буквы, лишние слова и т.п.). 
В таком случае это значение заменяется на NULL. При сортировке по полю в результате запроса не должно появляться книг со значением NULL этого поля.

Обработка запроса также выполняется в памяти.

Результатом выполнения задания должен являться проект на GitHub, содержащий:
исходный код сервиса,
инструкции по сборке и запуску сервиса.

Оцениваться будет правильность выполнения задания и организация кода.

Плюсом будет упаковка приложения в Docker-контейнер и инструкция по сборке и запуску приложения в контейнере.

Используемые технологии:
Java 11+,
Spring Boot 2.7+,
Система сборки - Gradle.

