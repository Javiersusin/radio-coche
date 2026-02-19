#!/bin/bash

echo "Creating project structure..."

mkdir -p app/src/main/java/com/javi/radio/ui
mkdir -p app/src/main/java/com/javi/radio/domain
mkdir -p app/src/main/java/com/javi/radio/data
mkdir -p app/src/main/java/com/javi/radio/util
mkdir -p app/src/main/java/com/javi/radio/launcher

mkdir -p app/src/test/java/com/javi/radio
mkdir -p app/src/androidTest/java/com/javi/radio

mkdir -p docs
mkdir -p scripts

touch README.md
touch docs/ARCHITECTURE.md
touch docs/PERFORMANCE_RULES.md
touch docs/TESTING_POLICY.md

echo "Project structure created successfully."
