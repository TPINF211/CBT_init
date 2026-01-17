#!/bin/bash

# Script pour démarrer MySQL avec Anaconda
# Usage: ./start_mysql.sh

echo "=== Démarrage de MySQL (Anaconda) ==="

# Vérifier si MySQL est déjà en cours d'exécution
if lsof -i :3306 > /dev/null 2>&1; then
    echo "MySQL est déjà en cours d'exécution sur le port 3306"
    exit 0
fi

# Trouver mysqld
MYSQLD_PATH=$(which mysqld)
if [ -z "$MYSQLD_PATH" ]; then
    echo "Erreur: mysqld non trouvé. Vérifiez que MySQL est installé via Anaconda."
    exit 1
fi

echo "mysqld trouvé: $MYSQLD_PATH"

# Créer le répertoire de données MySQL s'il n'existe pas
MYSQL_DATA_DIR="$HOME/mysql_data"
if [ ! -d "$MYSQL_DATA_DIR" ]; then
    echo "Création du répertoire de données MySQL: $MYSQL_DATA_DIR"
    mkdir -p "$MYSQL_DATA_DIR"
    
    # Initialiser MySQL
    echo "Initialisation de MySQL..."
    mysqld --initialize-insecure --datadir="$MYSQL_DATA_DIR" --user=$(whoami)
    
    if [ $? -ne 0 ]; then
        echo "Erreur lors de l'initialisation de MySQL"
        exit 1
    fi
fi

# Démarrer MySQL en arrière-plan
echo "Démarrage de MySQL..."
mysqld --datadir="$MYSQL_DATA_DIR" --user=$(whoami) --port=3306 > /tmp/mysql.log 2>&1 &

MYSQL_PID=$!
sleep 3

# Vérifier si MySQL a démarré
if ps -p $MYSQL_PID > /dev/null; then
    echo "MySQL démarré avec succès (PID: $MYSQL_PID)"
    echo "Les logs sont dans /tmp/mysql.log"
    echo ""
    echo "Pour arrêter MySQL: kill $MYSQL_PID"
    echo "Ou utilisez: ./stop_mysql.sh"
else
    echo "Erreur: MySQL n'a pas pu démarrer. Vérifiez /tmp/mysql.log"
    exit 1
fi

# Créer la base de données si elle n'existe pas
echo "Vérification de la base de données..."
sleep 2
mysql -u root -e "CREATE DATABASE IF NOT EXISTS cbt_db;" 2>/dev/null

if [ $? -eq 0 ]; then
    echo "Base de données 'cbt_db' prête"
else
    echo "Attention: Impossible de créer la base de données. Vous devrez peut-être le faire manuellement."
fi

echo ""
echo "MySQL est maintenant prêt à être utilisé!"

