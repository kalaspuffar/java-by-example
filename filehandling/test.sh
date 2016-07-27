#!/bin/bash
while :
do
inotifywait -e modify org/example/filehandling/*.java
javac org/example/filehandling/*.java
java org.example.filehandling.PrintAnimals
done
