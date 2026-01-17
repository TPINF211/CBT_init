#!/bin/bash

# Script d'exécution pour l'application CBT
# Usage: ./run.sh

# Aller dans le répertoire du script
cd "$(dirname "$0")"

echo "=== Lancement de l'application CBT ==="
echo "Répertoire de travail: $(pwd)"

# Répertoires
OUT_DIR="out"
LIB_DIR="lib"
RES_DIR="src/main/resources"

# Vérifier si la compilation a été faite
if [ ! -d "$OUT_DIR" ]; then
    echo "Erreur: Le répertoire $OUT_DIR n'existe pas."
    echo "Veuillez d'abord compiler l'application avec: ./compile.sh"
    exit 1
fi

# Vérifier si le driver MySQL existe
MYSQL_JAR=""
if [ -f "$LIB_DIR/mysql-connector-java-8.0.33.jar" ]; then
    MYSQL_JAR="$LIB_DIR/mysql-connector-java-8.0.33.jar"
elif [ -f "$LIB_DIR/mysql-connector.jar" ]; then
    MYSQL_JAR="$LIB_DIR/mysql-connector.jar"
elif ls "$LIB_DIR"/*.jar 1> /dev/null 2>&1; then
    MYSQL_JAR=$(ls "$LIB_DIR"/*.jar | head -n 1)
else
    echo "Attention: Driver MySQL non trouvé dans $LIB_DIR"
    echo "L'application peut ne pas fonctionner correctement."
fi

# Exécuter
if [ -n "$MYSQL_JAR" ] && [ -f "$MYSQL_JAR" ]; then
    java -cp "$OUT_DIR:$MYSQL_JAR:$RES_DIR" com.CBTapp.Main
else
    java -cp "$OUT_DIR:$RES_DIR" com.CBTapp.Main
fi


