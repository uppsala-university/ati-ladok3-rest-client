package se.sunet.ati.ladok.rest.api.kataloginformation;

public class SokOrganisationQuery {

    private final String kod;
    private final String benamning;
    private final String orderBy;
    private final Boolean skipCount;
    private final Boolean onlyCount;
    private final String sprakkod;

//    private final Integer page; ?
//    private final Integer limit; ?

    private SokOrganisationQuery(BuilderImpl builder) {
        kod = builder.kod;
        benamning = builder.benamning;
        orderBy = builder.orderBy;
        skipCount = builder.skipCount;
        onlyCount = builder.onlyCount;
        sprakkod = builder.sprakkod;
    }

    public static Builder builder() {
        return new BuilderImpl();
    }

    public String getKod() {
        return kod;
    }

    public String getBenamning() {
        return benamning;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public String getSprakkod() {
        return sprakkod;
    }

    public Boolean getSkipCount() {
        return skipCount;
    }

    public Boolean getOnlyCount() {
        return onlyCount;
    }

    public interface Builder {
        Buildable kod(String kod);
        Buildable benamning(String benamning);
        Buildable orderBy(String orderBy);
        Buildable skipCount(Boolean skipCount);
        Buildable onlyCount(Boolean onlyCount);
        Buildable sprakkod(String sprakkod);
    }

    public interface Buildable extends Builder {
        SokOrganisationQuery build();
    }

    public static final class BuilderImpl implements Builder, Buildable {
        private String kod;
        private String benamning;
        private String orderBy;
        private Boolean skipCount;
        private Boolean onlyCount;
        private String sprakkod;

        private BuilderImpl() {
        }

        @Override
        public Buildable kod(String kod) {
            this.kod = kod;
            return this;
        }

        @Override
        public Buildable benamning(String benamning) {
            this.benamning = benamning;
            return this;
        }

        @Override
        public Buildable orderBy(String orderBy) {
            this.orderBy = orderBy;
            return this;
        }

        @Override
        public Buildable skipCount(Boolean skipCount) {
            this.skipCount = skipCount;
            return this;
        }

        @Override
        public Buildable onlyCount(Boolean onlyCount) {
            this.onlyCount = onlyCount;
            return this;
        }

        @Override
        public Buildable sprakkod(String sprakkod) {
            this.sprakkod = sprakkod;
            return this;
        }

        @Override
        public SokOrganisationQuery build() {
            return new SokOrganisationQuery(this);
        }
    }

}
