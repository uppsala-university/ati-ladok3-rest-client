 package se.sunet.ati.ladok.rest.api.utbildningsinformation;

 import java.util.ArrayList;
 import java.util.Collection;

 public class SokUtbildningsinstansQuery {

     private final String utbildningUID;
     private final String utbildningsinstansUID;
     private final Collection<String> utbildningstypID;
     private final String studieordningID;
     private final Collection<String> utbildningskod;
     private final Collection<String> status;
     private final Collection<String> organisationUID;
     private final Collection<String> benamning;
     private final String overliggandeUtbildningUID;
     private final Boolean aktuellVersion;
     private final Boolean skipCount;
     private final Boolean onlyCount;
     private final String sprakkod;

     private final Integer page;
     private final Integer limit;

     public String getUtbildningUID() {
         return utbildningUID;
     }

     public String getUtbildningsinstansUID() {
         return utbildningsinstansUID;
     }

     public Collection<String> getUtbildningstypID() {
         return utbildningstypID;
     }

     public String getStudieordningID() {
         return studieordningID;
     }

     public Collection<String> getUtbildningskod() {
         return utbildningskod;
     }

     public Collection<String> getStatus() {
         return status;
     }

     public Collection<String> getOrganisationUID() {
         return organisationUID;
     }

     public Collection<String> getBenamning() {
         return benamning;
     }

     public String getOverliggandeUtbildningUID() {
         return overliggandeUtbildningUID;
     }

     public Boolean isAktuellVersion() {
         return aktuellVersion;
     }

     public Boolean isSkipCount() {
         return skipCount;
     }

     public Boolean isOnlyCount() {
         return onlyCount;
     }

     public String getSprakkod() {
         return sprakkod;
     }

     public Integer getPage() {
         return page;
     }

     public Integer getLimit() {
         return limit;
     }

     private SokUtbildningsinstansQuery(BuilderImpl builder) {
         utbildningUID = builder.utbildningUID;
         utbildningsinstansUID = builder.utbildningsinstansUID;
         utbildningstypID = builder.utbildningstypIDCollection;
         studieordningID = builder.studieordningID;
         utbildningskod = builder.utbildningskodCollection;
         status = builder.statusCollection;
         organisationUID = builder.organisationUIDCollection;
         benamning = builder.benamningCollection;
         overliggandeUtbildningUID = builder.overliggandeUtbildningUID;
         aktuellVersion = builder.aktuellVersion;
         skipCount = builder.skipCount;
         onlyCount = builder.onlyCount;
         sprakkod = builder.sprakkod;
         page = builder.page;
         limit = builder.limit;
     }

     public static Builder builder() {
         return new BuilderImpl();
     }

     public interface Builder {
         Buildable utbildningUID(String utbildningUID);
         Buildable utbildningsinstansUID(String utbildningsinstansUID);
         Buildable addUtbildningstypIDList(Collection<String> utbildningstypIDCollection);
         Buildable addUtbildningstypID(String utbildningstypID);
         Buildable studieordningID(String studieordningID);
         Buildable addUtbildningskodCollection(Collection<String> utbildningskodCollection);
         Buildable addUtbildningskod(String utbildningskod);
         Buildable addStatus(Collection<String> statusCollection);
         Buildable addStatus(String status);
         Buildable addOrganisationUIDCollection(Collection<String> organisationUIDCollection);
         Buildable addOrganisationUID(String organisationUID);
         Buildable addBenamningCollection(Collection<String> benamningCollection);
         Buildable addBenamning(String benamning);
         Buildable overliggandeUtbildningUID(String overliggandeUtbildningUID);
         Buildable aktuellVersion(Boolean aktuellVersion);
         Buildable skipCount(Boolean skipCount);
         Buildable onlyCount(Boolean onlyCount);
         Buildable sprakkod(String sprakkod);
         Buildable page(Integer page);
         Buildable limit(Integer limit);
     }

     public interface Buildable extends Builder {
         SokUtbildningsinstansQuery build();
     }

     public static final class BuilderImpl implements Buildable {
         private String utbildningUID;
         private String utbildningsinstansUID;
         private final Collection<String> utbildningstypIDCollection = new ArrayList<>();
         private String studieordningID;
         private final Collection<String> utbildningskodCollection = new ArrayList<>();
         private final Collection<String> statusCollection = new ArrayList<>();
         private final Collection<String> organisationUIDCollection = new ArrayList<>();
         private final Collection<String> benamningCollection = new ArrayList<>();
         private String overliggandeUtbildningUID;
         private Boolean aktuellVersion;
         private Boolean skipCount;
         private Boolean onlyCount;
         private String sprakkod;
         private Integer page;
         private Integer limit;

         private BuilderImpl() {
         }

         @Override
         public BuilderImpl utbildningUID(String utbildningUID) {
             this.utbildningUID = utbildningUID;
             return this;
         }

         @Override
         public BuilderImpl utbildningsinstansUID(String utbildningsinstansUID) {
             this.utbildningsinstansUID = utbildningsinstansUID;
             return this;
         }

         @Override
         public BuilderImpl addUtbildningstypIDList(Collection<String> utbildningstypIDCollection) {
             this.utbildningstypIDCollection.addAll(utbildningstypIDCollection);
             return this;
         }

         @Override
         public BuilderImpl addUtbildningstypID(String utbildningstypID) {
             this.utbildningstypIDCollection.add(utbildningstypID);
             return this;
         }

         @Override
         public BuilderImpl studieordningID(String studieordningID) {
             this.studieordningID = studieordningID;
             return this;
         }

         @Override
         public BuilderImpl addUtbildningskodCollection(Collection<String> utbildningskodCollection) {
             this.utbildningskodCollection.addAll(utbildningskodCollection);
             return this;
         }

         @Override
         public BuilderImpl addUtbildningskod(String utbildningskod) {
             this.utbildningskodCollection.add(utbildningskod);
             return this;
         }

         @Override
         public BuilderImpl addStatus(Collection<String> statusCollection) {
             this.statusCollection.addAll(statusCollection);
             return this;
         }

         @Override
         public BuilderImpl addStatus(String status) {
             this.statusCollection.add(status);
             return this;
         }

         @Override
         public BuilderImpl addOrganisationUIDCollection(Collection<String> organisationUIDCollection) {
             this.organisationUIDCollection.addAll(organisationUIDCollection);
             return this;
         }

         @Override
         public BuilderImpl addOrganisationUID(String organisationUID) {
             this.organisationUIDCollection.add(organisationUID);
             return this;
         }

         @Override
         public BuilderImpl addBenamningCollection(Collection<String> benamningCollection) {
             this.benamningCollection.addAll(benamningCollection);
             return this;
         }

         @Override
         public BuilderImpl addBenamning(String benamning) {
             this.benamningCollection.add(benamning);
             return this;
         }

         @Override
         public BuilderImpl overliggandeUtbildningUID(String overliggandeUtbildningUID) {
             this.overliggandeUtbildningUID = overliggandeUtbildningUID;
             return this;
         }

         @Override
         public BuilderImpl aktuellVersion(Boolean aktuellVersion) {
             this.aktuellVersion = aktuellVersion;
             return this;
         }

         @Override
         public BuilderImpl skipCount(Boolean skipCount) {
             this.skipCount = skipCount;
             return this;
         }

         @Override
         public BuilderImpl onlyCount(Boolean onlyCount) {
             this.onlyCount = onlyCount;
             return this;
         }

         @Override
         public BuilderImpl sprakkod(String sprakkod) {
             this.sprakkod = sprakkod;
             return this;
         }

         @Override
         public BuilderImpl page(Integer page) {
             this.page = page;
             return this;
         }

         @Override
         public BuilderImpl limit(Integer limit) {
             this.limit = limit;
             return this;
         }

         @Override
         public SokUtbildningsinstansQuery build() {
             return new SokUtbildningsinstansQuery(this);
         }
     }
 }
