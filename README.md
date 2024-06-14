# Eksamen i PGR112v24
> Fra 13.06.2024 kl. 10:00 (24 timers eksamen)
> 
> Karakter: Bestått / Ikke bestått

Dette er min besvarelse på eksamen i PGR112 Objektorientert programmering ved Høyskolen Kristiania våren 2024 
(del av mitt 2. semester på skolen).

### Kjør SQL-filene før start
Det ligger to .sql-filer i mappen "sql". Ved å kjøre disse har du riktig databaseoppsett til å kunne kjøre programmet.
Hvis ikke vil du få en feilmelding da det er gjort endringer i kolonnenavn i tabeller fra sql-filen som ble utlevert 
med eksamen originalt.

### Database tilkobling i prosjektet
Dette prosjektet er satt opp med at det må lages en _database.properties_-fil som ligger i mappen **resources**. 
Denne filen må ha følgende nøkler (hvor XXXXX er byttet ut med brukernavn og passord for en bruker
med tilgang til databasen): 
- url=jdbc:mysql://localhost:3306/Funn?allowPublicKeyRetrieval=true&useSSL=false
- username=XXXXX
- password=XXXXX