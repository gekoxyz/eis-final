# Progetto Finale EIS

---

## Panoramica (implementazione dele funzionalità)

Il programma prende un generico articolo di giornale da una delle sorgenti disponibili e lo fa passare tramite un ```Adapter```,
il cui compito è quello di convertire dal formato originale (JSON, CSV...) ad un oggetto ```Article```. La classe ```Adapter```
è astratta e ciò permette di aggiungere nuovi Adapter personalizzati relativi a nuove sorgenti. Una volta ottenuto,
l'array di ```Article``` può essere passato al ```Serializer``` per essere
serializzato in xml oppure all'```Analyzer``` per essere analizzato.

## Come installare il software
- Scaricare il programma
- Registrare una key per l'API di The Guardian all'indirizzo https://open-platform.theguardian.com/access/
- Nella directory principale del progetto, creare un file denominato ".env"
- Inserire all'interno del file .env la propria API key di The Guardian con la sintassi seguente: THEGUARDIAN_API_KEY=la-vostra-chiave
- Con il terminale aprire la directory principale del progetto e digitare il comando "mvn package", oppure "mvn package -DskipTests" per non eseguire test
- Spostare il file "./target/eis-final-1.0-SNAPSHOT.jar" nella directory principale del progetto 
- Eseguire "java -jar eis-final-1.0-SNAPSHOT.jar"

## Eseguire il software
Per eseguire il software aprire un terminale nella directory principale del programma e utilizzare:
```
comandi per eseguire
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

---

Requirements:

- jUnit tests
- Javadoc
- UML
- zip/tar.gz archive

Deliverables:

- [ ] Use case rappresentati con diagrammi UML e descrizione in linguaggio naturale strutturato
- [ ] Domain model in UML
- [ ] Design model in UML
- [ ] Codice (java + class/jar)
- [ ] Test (documento + report dei test) vd mail prof, + test analyzer e test callApi
- [ ] manuale
    - [x] breve panoramica ad alto livello del progetto (come sono state implementate le funzionalità)
    - [ ] come installare ed eseguire il software
    - [x] quali funzioni sono state riutilizzate da librerie esistenti (con versione delle librerie)

Progetto:
Sistema software per scaricare articoli da testate giornalistiche online e visualizzazione dei termini più importanti
nell'insieme degli articoli scaricati

(termine = parola che compare nel testo dell'articolo)

estrarre i 50 termini con maggior peso e memorizzarli in un file txt dove ciascuna riga ha il formato "<termine> <peso>"
come segue:

test 32
nuclear 16
fusion 7
power 7

(se pareggio in termine di peso si da preferenza in base all'ordine alfabetico)
per estrarre i termini: StringTokenizer oppure CoreNLP

https://docs.oracle.com/javase/8/docs/api/java/util/StringTokenizer.html

https://stanfordnlp.github.io/CoreNLP/
https://stanfordnlp.github.io/CoreNLP/pipeline.html
https://stanfordnlp.github.io/CoreNLP/tokenize.html

Sorgenti:

- CSV su moodle
- the guardian api

Richieste:

- supportare nuove sorgenti
- download -> salvataggio su file (con lo stesso formato per tutti gli articoli di tutte le sorgenti, scrivendo il
  serializzatore e deserializzatore)
- supporto a nuove modalità di memorizzazione ed accesso agli articoli ?cosa vuol dire?
- estrarre termini e peso a partire dal file in cui è memorizzato l'articolo
- supporto a nuove strutture per memorizzare ed avere accesso ai termini più importanti
- chiedere all'utente se vuole eseguire solo il download, solo l'estrazione dei termini a partire dal file o entrambe le
  azioni. L’utente deve poter specificare se eseguire solo il download, solo l’estrazione dei termini a
  partire dai file in cui sono stati memorizzati gli articoli, o entrambe le azioni in sequenza.

## Article

### The Guardian

- id
- ~~type~~ (useless since it's always an article)
- sectionId (us-news, uk-news, environment)
- sectionName  (the one above but with uppercase and spaces)
- webPublicationDate
- webTitle
- webUrl (html content url)
- apiUrl (raw content url)
- ~~isHosted~~ (even the guardian doesn't know what this means)

### NYTimes

- Identifier
- Url
- Title
- Fulltext
- Date
- Source set
- Source

### Article

- id
- webUrl
- apiUrl
- title
- body_text
- publication_date
- publisher (nytimes, the guardian...)
- source
