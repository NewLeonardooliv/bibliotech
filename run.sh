javac -d bin -cp .:src/lib/mysql-connector-j-8.1.0.jar src/*.java src/model/*.java src/view/*.java src/controller/*.java
java -cp bin:src/lib/mysql-connector-j-8.1.0.jar Main
