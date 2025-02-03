#!/bin/bash

# Create timestamp in YYYYMMDD_HHMMSS format
TIMESTAMP=$(date +"%Y%m%d%H%M%S")

FILENAME="src/main/java/com/example/demo/migrations/$1_${TIMESTAMP}.java"

touch "$FILENAME"

echo "✅ The migration file is created: $FILENAME"
