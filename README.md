# Pokedex Projekt
En konsolapplikation som kan hantera en samling
av Pokémons med deras respektiv attacker.

## Funktioner
- Applikationen skapar, läser, uppdaterar och ta bort
Pokemons.
- Applikationen sparar Pokemoner till och läser från fil. 
- Applikationen visar tillgänglig Pokemoner och attacker

## Version och bygg verktyg
- **Java Development Kit (JDK)** (version 17 eller senare rekommenderas)
- **Byggverktyg:** Maven 

## Kom igång

Följ dessa steg för att klona, bygga och köra projektet.

### 1. Klona projektet till en mapp
```bash
git clone https://github.com/miko-mode/pokemons.git
cd projekt mapp
```
#### Credentials for repo:

Generera Personal Access Token(PAT) för att klona repon om det begärs.

token:
### 2. Bygg projektet
Kör Maven kommandon:Säkerställ att Maven är redan installerat med

```bash
mvn --version
```
Sen kör:
```bash
mvn clean install
```
### 3. kör Applikationen
kör Main klassen direkt för att börja Applikationen
```bash
mvn exec:java -Dexec.mainClass="com.ui.Main"
```

