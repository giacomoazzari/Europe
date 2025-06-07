# Europe

### Foldable devices
L'applicazione è stata realizzata con la finalità di esplorare le opportunità offerte dai dispositivi Android di nuova generazione con schermo pieghevole, quali foldables, rollables e dual-screen.
Questi dispositivi sono in grado di modificare la propria area di visualizzazione adattandosi a diversi scenari d’uso, e richiedono una riprogettazione delle interfacce utente per offrire un’esperienza coerente e ottimizzata.

### Descrizione del progetto
Europe è un’app Android pensata per esplorare e conoscere i paesi europei. L’interfaccia si apre con una schermata di benvenuto da cui è possibile accedere a due sezioni principali:
+ <b>Lista dei paesi europei</b>: visualizza i paesi in elenco, con nome e bandiera. Ogni elemento è cliccabile per accedere a una schermata di dettaglio, che mostra informazioni aggiuntive e consente di ascoltare l’inno nazionale.
+ <b>Mappa interattiva</b>: permette di esplorare l’Europa su mappa e selezionare i vari paesi con interazioni simili a quelle della lista.

L’app è progettata per adattarsi dinamicamente ai dispositivi pieghevoli, quando questi espandono il proprio schermo in <b>modalità libro</b> (schermo piegato in orientazione verticale) e <b>modalità da tavolo</b>  (schermo piegato a 90° in orizzontale), sfruttando la libreria Jetpack WindowManager per modificare il layout in base alla postura. 
In queste modalità, la navigazione e la presentazione dei contenuti vengono distribuite tra le due metà dello schermo per una fruizione più naturale.

















### Note sul progetto
+ L'app è stata sviluppata e testata utilizzando l'emulatore Pixel 9 Pro Fold configurato con posture avanzate.
+ 	Il layout si adatta dinamicamente a diverse configurazioni grazie all’utilizzo di WindowManager e alla gestione delle posture book e tabletop.
+ 	In modalità pieghevole:
  	+ La metà sinistra o inferiore gestisce il flusso principale di navigazione tramite NavHostFragment.
    + La metà destra o superiore si attiva solo alla selezione di un contenuto, mostrando il dettaglio in modo indipendente.
+ È stato implementato un Service per la riproduzione degli inni nazionali.
+ L’app è ovviamente compatibile con dispositivi tradizionali (smartphone, tablet) oltre che con dispositivi pieghevoli (es. Pixel Fold).

### Autori
Il progetto è stato sviluppato da:
- [Giacomo Azzari](https://github.com/giacomoazzari)
- [Sofia Dal Martello](https://github.com/badcodingsofia)
- [Andreas Rizzi](https://github.com/andreasrizzi)
