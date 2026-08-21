# ItemsDrop

Plugin Paper qui donne un item aléatoire à tous les joueurs connectés, à intervalle régulier (configurable).

## Commandes

- `/itemsdrop start` — démarre la distribution automatique
- `/itemsdrop stop` — arrête la distribution
- `/itemsdrop reload` — recharge le config.yml (utile après modification)

Permission requise : `itemsdrop.use` (par défaut : opérateurs uniquement)

## Configuration (config.yml)

```yaml
interval-seconds: 10          # temps entre chaque distribution
broadcast-message: "%player% a recu un item aleatoire : %item% !"
amount-per-drop: 1             # quantité de l'item donné
items:                         # liste des items possibles (vide = totalement aléatoire)
  - DIAMOND
  - GOLD_INGOT
  ...
```

Si la liste `items` est vide, le plugin choisit un item complètement aléatoire parmi tous les items du jeu.

## Compiler le plugin

Ce projet est un projet Maven standard. Il te faut :
- Java 21 (JDK)
- Maven

Étapes :
1. Installe Maven si ce n'est pas déjà fait (https://maven.apache.org/download.cgi), ou utilise IntelliJ IDEA / Eclipse qui l'intègrent directement.
2. Ouvre un terminal dans le dossier du projet (là où se trouve `pom.xml`).
3. Lance : `mvn clean package`
4. Le fichier `.jar` compilé se trouve dans le dossier `target/ItemsDrop.jar`.

Le `pom.xml` télécharge automatiquement l'API Paper 1.21.4 depuis le dépôt officiel PaperMC — il faut donc une connexion internet lors de la compilation.

**Si ton serveur n'est pas en 1.21.x** : ouvre `pom.xml` et remplace la version `1.21.4-R0.1-SNAPSHOT` par celle correspondant à ton serveur (par ex. `1.20.4-R0.1-SNAPSHOT`), et adapte aussi `api-version` dans `plugin.yml`.

## Installation

1. Copie le fichier `ItemsDrop.jar` compilé dans le dossier `plugins/` de ton serveur Paper.
2. Redémarre ou relance le serveur.
3. Un fichier `config.yml` sera créé automatiquement dans `plugins/ItemsDrop/`.
4. Utilise `/itemsdrop start` en jeu (ou depuis la console) pour lancer la distribution.
