Для сборки проекта в корневой директории выполнить gradlew.bat build (./gradlew build для Linux). В build/libs появится файл с раширением war.
Для развёртывания файл положить в tomcat_root_dir/webapps. 
Веб-приложение будет доступно по урлу tomcat_url/название_war_файла_без расширения