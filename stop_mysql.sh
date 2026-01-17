#!/bin/bash

# Script pour arrêter MySQL
# Usage: ./stop_mysql.sh

echo "=== Arrêt de MySQL ==="

# Trouver le processus MySQL
MYSQL_PID=$(lsof -ti :3306)

if [ -z "$MYSQL_PID" ]; then
    echo "MySQL n'est pas en cours d'exécution sur le port 3306"
    exit 0
fi

echo "Arrêt de MySQL (PID: $MYSQL_PID)..."
kill $MYSQL_PID

sleep 2

# Vérifier si MySQL s'est arrêté
if lsof -i :3306 > /dev/null 2>&1; then
    echo "MySQL ne s'est pas arrêté, utilisation de kill -9..."
    kill -9 $MYSQL_PID
    sleep 1
fi

if lsof -i :3306 > /dev/null 2>&1; then
    echo "Erreur: Impossible d'arrêter MySQL"
    exit 1
else
    echo "MySQL arrêté avec succès"
fi

