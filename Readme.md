Requirements:
- jUnit tests
- Javadoc
- UML
- zip/tar.gz archive
  
Deliverables:
- Use case rappresentati con diagrammi UML e descrizione in linguaggio naturale strutturato
- Domain model in UML
- Design model in UML
- Codice (java + class/jar)
- Test (documento + report dei test) ?quindi i test devono generare un log?
- manuale
  - breve panoramica ad alto livello del progetto (come sono state implementate le funzionalità)
  - come installare ed eseguire il software
  - quali funzioni sono state riutilizzate da librerie esistenti (con versione delle librerie)

Progetto:
    Sistema software per scaricare articoli da testate giornalistiche online e visualizzazione dei termini più importanti nell'insieme degli articoli scaricati

    (termine = parola che compare nel testo dell'articolo)

    estrarre i 50 termini con maggior peso e memorizzarli in un file txt dove ciascuna riga ha il formato "<termine> <peso>" come segue:

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
    - download -> salvataggio su file (con lo stesso formato per tutti gli articoli di tutte le sorgenti, scrivendo il serializzatore e deserializzatore)
    - supporto a nuove modalità di memorizzazione ed accesso agli articoli ?cosa vuol dire?
    - estrarre termini e peso a partire dal file in cui è memorizzato l'articolo
    - supporto a nuove strutture per memorizzare ed avere accesso ai termini più importanti
    - chiedere all'utente se vuole eseguire solo il download, solo l'estrazione dei termini a partire dal file o entrambe le azioni ?quindi ci saranno minimo 2 e massimo 3 file? ?quindi il file di download e quello dell'articolo serializzato sono diversi?

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