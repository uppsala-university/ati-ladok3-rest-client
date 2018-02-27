package se.sunet.ati.ladok.rest.api.resultinformation;

public class SokAktivitetstillfallesmojlighetQuery {

    private final String aktivitetstillfalleUID;
    private final Boolean anmalda;
    private final String personnummer;
    private final String fornamn;
    private final String efternamn;

    private final Boolean skipCount;
    private final Boolean onlyCount;
    private final String sprakkod;
    private final Integer page;
    private final Integer limit;

    public String getAktivitetstillfalleUID() {
        return aktivitetstillfalleUID;
    }

    public Boolean getAnmalda() {
        return anmalda;
    }

    public String getPersonnummer() {
        return personnummer;
    }

    public String getFornamn() {
        return fornamn;
    }

    public String getEfternamn() {
        return efternamn;
    }

    public Boolean getSkipCount() {
        return skipCount;
    }

    public Boolean getOnlyCount() {
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

    public static Builder builder(){
        return new Builder();
    }

    private SokAktivitetstillfallesmojlighetQuery(Builder builder) {
        aktivitetstillfalleUID = builder.aktivitetstillfalleUID;
        anmalda = builder.anmalda;
        personnummer = builder.personnummer;
        fornamn = builder.fornamn;
        efternamn = builder.efternamn;
        skipCount = builder.skipCount;
        onlyCount = builder.onlyCount;
        sprakkod = builder.sprakkod;
        page = builder.page;
        limit = builder.limit;
    }


    public static final class Builder {
        private String aktivitetstillfalleUID;
        private Boolean anmalda;
        private String personnummer;
        private String fornamn;
        private String efternamn;
        private Boolean skipCount;
        private Boolean onlyCount;
        private String sprakkod;
        private Integer page;
        private Integer limit;

        public Builder() {
        }

        public Builder aktivitetstillfalleUID(String val) {
            aktivitetstillfalleUID = val;
            return this;
        }

        public Builder anmalda(Boolean val) {
            anmalda = val;
            return this;
        }

        public Builder personnummer(String val) {
            personnummer = val;
            return this;
        }

        public Builder fornamn(String val) {
            fornamn = val;
            return this;
        }

        public Builder efternamn(String val) {
            efternamn = val;
            return this;
        }

        public Builder skipCount(Boolean val) {
            skipCount = val;
            return this;
        }

        public Builder onlyCount(Boolean val) {
            onlyCount = val;
            return this;
        }

        public Builder sprakkod(String val) {
            sprakkod = val;
            return this;
        }

        public Builder page(Integer val) {
            page = val;
            return this;
        }

        public Builder limit(Integer val) {
            limit = val;
            return this;
        }

        public SokAktivitetstillfallesmojlighetQuery build() {
            return new SokAktivitetstillfallesmojlighetQuery(this);
        }
    }
}
