# Application CBT

Une application desktop Java SQL pour gérer une tontine avec intégration de base de données MySQL.

## Structure du Projet

```
projet-java-sql/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── com/
│   │   │   │   └── CBTapp/
│   │   │   │       ├── Main.java              # Point d'entrée de l'application
│   │   │   │       ├── db/                    # Couche base de données
│   │   │   │       │   ├── DatabaseConnection.java
│   │   │   │       │   ├── DatabaseManager.java
│   │   │   │       │   └── procedures.sql
│   │   │   │       ├── models/                # Modèles de données
│   │   │   │       ├── dao/                   # Objets d'accès aux données
│   │   │   │       ├── services/              # Logique métier
│   │   │   │       └── ui/                    # Interface utilisateur
│   │   │   │           ├── MainFrame.java
│   │   │   │           |
│   │   │   │           └── components/
│   │   │   │               
│   │   └── resources/
│   │       └── config.properties              # Fichier de configuration
│   └── test/
│       └── java/
├── sql/
│   ├── schema.sql                             # Schéma de base de données
│   ├── triggers.sql                           # Déclencheurs de base de données
│   ├── procedures.sql                         # Procédures stockées
│   └── data/
│       └── seed.sql                           # Données d'exemple
├── lib/
│   └── mysql-connector-java-8.0.33.jar        # Pilote JDBC MySQL
├── .gitignore
└── README.md
```

## Prérequis

- Java JDK 8 ou supérieur
- MySQL 5.7 ou supérieur
- Maven ou configuration manuelle du classpath

## Instructions d'Installation

### 1. Configuration de la Base de Données

1. Installez MySQL si ce n'est pas déjà fait
2. Créez la base de données et les tables :
   ```bash
   mysql -u root -p < sql/schema.sql
   ```
3. Créez les procédures stockées :
   ```bash
   mysql -u root -p < sql/procedures.sql
   ```
4. Créez les déclencheurs :
   ```bash
   mysql -u root -p < sql/triggers.sql
   ```
5. (Optionnel) Chargez les données d'exemple :
   ```bash
   mysql -u root -p < sql/data/seed.sql
   ```

### 2. Configuration

Modifiez `src/main/resources/config.properties` pour correspondre à votre configuration de base de données :

```properties
db.url=jdbc:mysql://localhost:3306/cbtapp_db
db.username=root
db.password=votre_mot_de_passe
db.driver=com.mysql.cj.jdbc.Driver
```

### 3. Connecteur MySQL

Téléchargez le connecteur JDBC MySQL et placez-le dans le répertoire `lib/` :
- Téléchargement : https://dev.mysql.com/downloads/connector/j/
- Ou utilisez la gestion de dépendances Maven/Gradle

### 4. Compilation et Exécution

#### En Ligne de Commande (avec classpath) :

```bash
# Compilation
javac -cp "lib/mysql-connector-java-8.0.33.jar:." \
  src/main/java/com/CBTapp/**/*.java

# Exécution
java -cp "lib/mysql-connector-java-8.0.33.jar:src/main/java:." \
  com.CBTapp.Main
```

#### Avec un IDE :

1. Importez le projet dans IntelliJ IDEA, Eclipse ou NetBeans
2. Ajoutez `lib/mysql-connector-java-8.0.33.jar` au classpath du projet
3. Définissez `src/main/java` comme racine source
4. Exécutez `Main.java`


## Architecture

- **Couche Base de Données** : `DatabaseConnection` et `DatabaseManager` gèrent toutes les opérations de base de données
- **Couche Interface** : Composants Swing avec boutons stylisés personnalisés
- **Couche Modèle** : (À implémenter) Modèles de données pour clients, produits, commandes
- **Couche DAO** : (À implémenter) Objets d'accès aux données pour les opérations de base de données
- **Couche Service** : (À implémenter) Couche de logique métier


## Personnalisation

L'interface utilise un thème de couleur violet. Pour modifier les couleurs, éditez `CustomButton.java` et ajustez les valeurs de couleur :
- `defaultBackground` : Couleur principale du bouton
- `hoverBackground` : Couleur de l'état au survol
- `pressedBackground` : Couleur de l'état pressé

## Résolution de Problèmes

### Problèmes de Connexion à la Base de Données

1. Vérifiez que MySQL est en cours d'exécution
2. Vérifiez les paramètres de `config.properties`
3. Assurez-vous que la base de données `cbtapp_db` existe
4. Vérifiez que l'utilisateur a les permissions appropriées

### Erreur Class Not Found

Assurez-vous que le JAR du connecteur MySQL est dans le classpath :
```bash
java -cp "lib/mysql-connector-java-8.0.33.jar:src/main/java:." ...
```

## Améliorations Futures

- [ ] Implémenter les classes Model
- [ ] Implémenter le pattern DAO
- [ ] Ajouter la couche Service
- [ ] Ajouter plus de validation
- [ ] Ajouter la fonctionnalité d'export
- [ ] Ajouter les fonctionnalités de rapport
- [ ] Ajouter l'authentification

## Licence

Ce projet est à des fins éducatives.

## Auteur

Équipe de Développement de l'Application CBT
