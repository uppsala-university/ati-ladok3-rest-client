# Klient för REST-gränssnitt

## Lägga till som ett beroende

Om du vill kunna använda en SNAPSHOT-version av denna klient så behöver du lägga
till en konfiguration för att kunna hämta dessa SNAPSHOTs från OSSRH. För Maven
är det enklaste att lägga till en profil i ```settings.xml``` som ser ut så här:

```
    <!-- For projects requiring SNAPSHOTs from OSSRH -->
    <profile>
      <id>ossrh-snapshots</id>
      <repositories>
        <repository>
          <id>ossrh-snapshots</id>
          <url>https://oss.sonatype.org/content/repositories/snapshots</url>
          <releases>
            <enabled>false</enabled>
          </releases>
          <snapshots>
            <enabled>true</enabled>
          </snapshots>
        </repository>
      </repositories>
    </profile>
```

När du sedan bygger din egen produkt med Maven så behöver du aktivera profilen:

    mvn clean verify -Possrh-snapshots

## Konfiguration

För att använda klienten och kunna hämta information och utföra uppgifter i Ladoks REST-gränssnitt måste ett certifikat användas. 

Kopiera ett giltigt klientcertifikat för Ladok3 till katalogen `src/main/resources/`. Certifikatet ska vara på PKCS 12-format.

I `src/main/resources` finns en exempelfil för fordrade egenskaper. Använd den genom

    cp restclient.properties.sample restclient.properties

Redigera sedan filen `src/main/resources/restclient.properties` så att den innehåller rätt namn på certifikatfil och lösenord.

## Integrationstester

### Generella integrationstester

När du har gjort konfigurationen enligt ovan kan du köra integrationstesterna med nedanstående kommando:

    mvn clean verify -Prun-its

### Integrationstester för Uppsala Universitet

Uppsala Universitet har restriktioner vilken institution man läser och skriver till.
Därför finns en särskild profil för att köra integrationstester mot Uppsalas MIT-demo-miljö:

    mvn clean verify -P uu-demo-it

