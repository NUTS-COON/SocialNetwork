Тренировочный проект API на Spring простейшей соц сети со следующтм функционалом:
+ Регистрация
+ Редактирование личной информации
+ Поиск пользователей
+ Обмен сообщениями между пользователями
+ Публичные сообщение (что-то вроде стены)
+ Добавление/удаление пользователя в друзья
+ Просмотр информации о пользователе (профиля)
+ Сообщества

Проект написан за 25 часов с использованием Spring MVC, Spring Security, Spring Data JPA

Для сборки проекта в корневой директории выполнить gradlew.bat build (./gradlew build для Linux). В build/libs появится файл с раширением war.
Для развёртывания файл положить в tomcat_root_dir/webapps. 
Веб-приложение будет доступно по урлу tomcat_url/название_war_файла_без расширения
