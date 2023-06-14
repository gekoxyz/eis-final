# Progetto Finale EIS

---

## Panoramica (implementazione delle funzionalità)

Il programma prende un generico articolo di giornale da una delle sorgenti disponibili e lo fa passare tramite
un ```Adapter```,
il cui compito è quello di convertire dal formato originale (JSON, CSV...) ad un oggetto ```Article```. La
classe ```Adapter```
è astratta e ciò permette di aggiungere nuovi Adapter personalizzati relativi a nuove sorgenti. Una volta ottenuto,
l'array di ```Article``` può essere passato al ```Serializer``` per essere
serializzato in xml e successivamente all'```Analyzer``` per essere analizzato.

## Compilare ed eseguire il software
Nel progetto consegnato su moodle il jar NON è già compilato. Vanno seguite queste istruzioni per compilare.

Aprire un terminale e entrare nella directory in cui si vuole scaricare il progetto ed eseguire i seguenti comandi

```
git clone https://github.com/gekoxyz/eis-final
cd eis-final
```

Registrare una key per l'API di The Guardian all'indirizzo https://open-platform.theguardian.com/access/

Scrivere questo comando nel terminale sostituendo key con la chiave fornita da The Guardian (su Windows creare manualmente il file con i contenuti specificati nell'echo)

```
echo "THEGUARDIAN_API_KEY=key" > .env
```

Per compilare il progetto e generare il jar

```
mvn package
```

oppure per farlo senza eseguire i test (si risparmia del tempo)

```
mvn package -DskipTests
```

spostare nella directory principale il jar, di modo da avere il corretto accesso alla cartella assets e al file .env

```
mv ./target/eis-final-1.0-SNAPSHOT.jar ./
```

e infine per eseguire

```
java -jar eis-final-1.0-SNAPSHOT.jar
 ```

## Librerie utilizzate

Come si può leggere dal ```pom.xml``` sono state utilizzate le seguenti librerie:

### OpenCsv

Utilizzato per il parsing dei file CSV nell'adapter degli articoli del New York Times

```
<dependency>
    <groupId>com.opencsv</groupId>
    <artifactId>opencsv</artifactId>
    <version>5.7.1</version>
</dependency>
```

### Jackson

Utilizzato per il parsing dei file JSON nell'adapter degli articoli di The Guardian

```
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.12.7.1</version>
</dependency>
```

### CoreNLP

Per tokenizzare gli articoli (Guardando nel pom ci sono anche altre dipendenze necessarie legata a questa ma
CoreNLP è la principale)

```
<dependency>
    <groupId>edu.stanford.nlp</groupId>
    <artifactId>stanford-corenlp</artifactId>
    <version>4.5.4</version>
</dependency>
```

### JUnit

Per testare il codice

```
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter</artifactId>
    <version>5.8.2</version>
    <scope>test</scope>
</dependency>
```

### DotEnv

Per caricare la key di The Guardian nelle variabili d'ambiente

```
<dependency>
    <groupId>io.github.cdimascio</groupId>
    <artifactId>dotenv-java</artifactId>
    <version>2.3.2</version>
</dependency>
```
