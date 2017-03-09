# Ladok 3 REST Client

Denna produkt innehåller en klient som pratar med Ladok 3:s REST-gränssnitt och
abstraherar all REST-kommunikation.

## Lägga till som ett beroende

För att använda denna produkt som ett beroende i ett Maven-bygge behöver du
lägga till två saker i ditt eget projekts ```pom.xml```

```
  <dependencies>
    <dependency>
      <groupId>se.sunet.ati.ladok</groupId>
      <artifactId>ati-ladok3-rest-client</artifactId>
      <version>1.0.0-SNAPSHOT</version>
      <type>bundle</type>
    </dependency>
    ...
  </dependencies>

  <build>
    <plugins>
      <plugin>
        <groupId>org.apache.felix</groupId>
        <artifactId>maven-bundle-plugin</artifactId>
        <version>2.3.7</version>
      </plugin>
      ...
    </plugins>
  </build>
```

Anledningen till att du behöver lägga till ```maven-bundle-plugin``` är att
denna produkt är av typen ```bundle``` (OSGi-bundle), vilket är en typ som
normalt inte stöds av Maven. Detta stöd tillhandahålls istället av ```maven-bundle-plugin```.

### Använda releaser

Om du vill använda en relase av denna produkt behöver du inte lägga till något
speciellt repository, då alla releaser publiceras i Maven Central Repository.

### Använda SNAPSHOTs

Om du vill använda en SNAPSHOT-version av denna produkt så behöver du lägga till
en konfiguration för detta. SHAPSHOTs publiceras i OSSRH. För Maven är det
enklast att lägga till en profil i ```settings.xml``` som ser ut så här:

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

## Att göra en release

För att göra en release behöver du lägga till nedanstående inställningar i din ```settings.xml```.

```
    <!-- OSSRH -->
    <server>
      <id>ossrh</id>
      <username>ATI:s användarnamn hos OSSRH</username>
      <password>Tillhörande lösenord</password>
    </server>
    ...
    <profile>
      <id>ati-release</id>
      <properties>
        <gpg.passphrase>Din GPG passphrase</gpg.passphrase>
      </properties>
    </profile>
```

För att göra releasen använder du dig av Maven Release Plugin:

    mvn clean release:prepare
    mvn release:perform
