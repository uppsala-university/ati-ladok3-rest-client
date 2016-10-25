# Klient för REST-gränssnitt
För att använda klienten och kunna hämta information och utföra uppgifter i Ladoks REST-gränssnitt måste ett certifikat användas. 

Kopiera ett giltigt klientcertifikat för Ladok3 till katalogen `src/main/resources/`. Certifikatet ska vara på PKCS 12-format.

I `src/main/resources` finns en exempelfil för fordrade egenskaper. Använd den genom

    cp restclient.properties.sample restclient.properties

Redigera sedan filen `src/main/resources/restclient.properties` så att den innehåller rätt namn på certifikatfil och lösenord.

Därefter kan du köra integrationstesterna med detta kommando:

    mvn clean verify -Prun-its
