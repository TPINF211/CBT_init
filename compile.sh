#!/bin/bash

# Script de compilation pour l'application CBT
# Usage: ./compile.sh

# Aller dans le répertoire du script
cd "$(dirname "$0")"

echo "=== Compilation de l'application CBT ==="
echo "Répertoire de travail: $(pwd)"

# Couleurs
GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Répertoires
SRC_DIR="src/main/java"
OUT_DIR="out"
LIB_DIR="lib"
RES_DIR="src/main/resources"

# Créer le répertoire de sortie
mkdir -p $OUT_DIR

# Trouver tous les fichiers Java
find $SRC_DIR -name "*.java" > /tmp/java_files.txt

# Vérifier si le driver MySQL existe
MYSQL_JAR=""
if [ -f "$LIB_DIR/mysql-connector-java-8.0.33.jar" ]; then
    MYSQL_JAR="$LIB_DIR/mysql-connector-java-8.0.33.jar"
elif [ -f "$LIB_DIR/mysql-connector.jar" ]; then
    MYSQL_JAR="$LIB_DIR/mysql-connector.jar"
elif [ -f "$LIB_DIR"/*.jar ]; then
    MYSQL_JAR="$LIB_DIR"/*.jar
else
    echo -e "${YELLOW}Attention: Driver MySQL non trouvé dans $LIB_DIR${NC}"
    echo -e "${YELLOW}Téléchargez mysql-connector-java depuis: https://dev.mysql.com/downloads/connector/j/${NC}"
    echo -e "${YELLOW}Et placez-le dans le répertoire $LIB_DIR${NC}"
    echo ""
    echo "Compilation sans driver MySQL (peut échouer)..."
fi

# Compiler
echo "Compilation en cours..."
if [ -n "$MYSQL_JAR" ] && [ -f "$MYSQL_JAR" ]; then
    javac -d $OUT_DIR -cp "$MYSQL_JAR:$RES_DIR" -sourcepath $SRC_DIR @/tmp/java_files.txt 2>&1
else
    javac -d $OUT_DIR -cp "$RES_DIR" -sourcepath $SRC_DIR @/tmp/java_files.txt 2>&1
fi

COMPILE_RESULT=$?
rm -f /tmp/java_files.txt

if [ $COMPILE_RESULT -eq 0 ]; then
    echo -e "${GREEN}✓ Compilation réussie!${NC}"
    echo ""
    echo "Pour exécuter l'application:"
    echo "  ./run.sh"
    echo ""
    if [ -n "$MYSQL_JAR" ] && [ -f "$MYSQL_JAR" ]; then
        echo "Ou directement:"
        echo "  java -cp \"$OUT_DIR:$MYSQL_JAR:$RES_DIR\" com.CBTapp.Main"
    else
        echo "Ou directement:"
        echo "  java -cp \"$OUT_DIR:$RES_DIR\" com.CBTapp.Main"
    fi
else
    echo -e "${RED}✗ Erreur de compilation${NC}"
    exit 1
fi


