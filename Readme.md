# EIS-FINAL

---

## Panoramica (come abbiamo implementato le funzionalità richieste)

Nel nostro programma un generico articolo di giornale, in qualsiasi formato sia salvato, passa tramite un ```Adapter```,
il cui compito è quello di convertire (o adattare) dal formato in cui è salvato l'articolo (JSON, CSV...) alla
classe ```Article``` che abbiamo appositamente creato. La classe ```Adapter``` è una classe astratta e quindi definisce
i metodi che un adapter generico deve avere per riuscire ad adattare gli articoli. Una volta che abbiamo ottenuto
l'array di Article possiamo gestirlo come vogliamo, e quindi può essere passato al ```Serializer``` per essere
serializzato in xml, oppure all'```Analyzer``` per essere analizzato.

## Come installare ed eseguire il software
- Creare nella directory principale del progetto un file denominato .env
- Inserire all'interno del file la propria key API di The Guardian THEGUARDIAN_API_KEY=la-vostra-chiave
- Aprire la directory principale del progetto e digitare mvn install 

## Librerie utilizzate

Come si può leggere dal ```pom.xml``` sono state utilizzate le seguenti librerie:

OpenCsv per l'adapter degli articoli del New York Times, per facilitare il parsing dei file CSV

```
<dependency>
    <groupId>com.opencsv</groupId>
    <artifactId>opencsv</artifactId>
    <version>5.7.1</version>
</dependency>
```

Jackson per l'adapter degli articoli di The Guardian, per facilitare il parsing dei file JSON

```
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.12.7.1</version>
</dependency>
```

CoreNLP per tokenizzare gli articoli (Guardando nel pom ci sono anche altre dipendenze necessarie legata a questa ma
questa è la principale)

```
<dependency>
    <groupId>edu.stanford.nlp</groupId>
    <artifactId>stanford-corenlp</artifactId>
    <version>4.5.4</version>
</dependency>
```

JUnit per testare il codice

```
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter</artifactId>
    <version>5.8.2</version>
    <scope>test</scope>
</dependency>
```

DotEnv per caricare la key di The Guardian nelle variabili d'ambiente

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
