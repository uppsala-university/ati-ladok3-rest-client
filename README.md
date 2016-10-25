# Klient för REST-gränssnitt
För att använda klienten som används för att hämta information och utföra uppgifter i Ladok's REST-gränssnitt måste ett certifikat användas. 

Kopiera ett giltigt klientcertifikat för Ladok3 till katalogen `src/main/resources/`. Certifikatet ska vara på PKCS 12-format.

I `src/main/resources` finns en exempelfil för fordrade egenskaper. Använd den genom

`mv restclient.properties.sample restclient.properties`

Redigera sedan filen `src/main/resources/restclient.properties` för att innehålla rätt namn på certifikatfil och lösenord.

Testa genom

`mvn test`
