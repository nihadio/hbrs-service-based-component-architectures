# ProductManagement – Übungsblatt Nr. 1

## FA0 – Port-Konzept

**Welche Aufgabe haben Ports?**
Ein Port ist die einzige Kommunikationsschnittstelle zwischen einer Komponente und ihrer Umgebung (Rupp, 2012, §9.1.3). Er isoliert die interne Struktur vom externen Aufrufer.

**Wie wird das in Java implementiert?**
`ProductManagement` implementiert das provided Interface `ProductManagementInt` und delegiert alle Aufrufe an den internen `ProductController`. Externe Clients kommunizieren ausschließlich über `ProductManagement`.

**Welches Design Pattern?**
Proxy / Delegate – der Port nimmt Aufrufe entgegen und leitet sie an die interne Implementierung weiter.

**Wie werden Interfaces injiziert?**
Über einen Setter auf dem Port: `setCaching(Caching<Product> caching)`. Der externe Client erzeugt die Implementierung und injiziert sie über den Port.

---

## FA1 – Lifecycle-Methoden

**Muss das Interface erweitert werden?**
Ja. `saveProduct()` wurde hinzugefügt – ohne diese Methode kann die Komponente nicht mit Daten befüllt werden.

**Reihenfolge der Methodenaufrufe:**
1. `openSession()` – öffnet die Datenbankverbindung
2. Beliebige Geschäftsmethoden (`getProductByName()`, `saveProduct()`)
3. `closeSession()` – schließt die Datenbankverbindung

**Zustandsmodell:**
`NEW → OPEN → CLOSED`

---

## FA2 – Implementierung & Caching

**Ist die Schnittstelle `Caching` hinreichend modelliert?**
Nein. Das Original definiert nur `cacheResult()`. Ohne Lesemethoden ist ein Cache nutzlos. Ergänzt wurden:
- `getCachedResult(String key)` – liest einen Eintrag aus dem Cache
- `isCached(String key)` – prüft ob ein Eintrag vorhanden ist

---

## FA3 – NullPointer-Vermeidung

**Mechanismus:** Null Object Pattern.
`ProductController` initialisiert `cache` mit `new NoOpCaching<>()` – einer leeren Implementierung von `Caching`, die nichts speichert und niemals `null` zurückgibt. Dadurch entfallen alle `null`-Prüfungen im Controller.

---

## FA4 – Logging

Logging wird im Port (`ProductManagement`) vor jeder Delegation ausgeführt – nicht im `ProductController`. Damit bleibt Logging eine Querschnittsfunktionalität außerhalb der Geschäftslogik.

Beispielausgabe:
```
26.04.26 21:22: Zugriff auf ProductManagement über Methode getProductByName. Suchwort: Motor
```

---

## FA5 – Deployment

Die Komponente wird als fat JAR gebaut (`mvn package` via `maven-shade-plugin`). Der H2-Treiber ist im JAR enthalten.

**Was passiert ohne H2-Treiber im JAR?**
Der externe Client wirft beim ersten Datenbankzugriff eine `ClassNotFoundException: org.h2.Driver`.

**Externer Client:**
```bash
cd client && mvn compile exec:java -Dexec.mainClass="org.hbrs.seka.client.Client"
```
