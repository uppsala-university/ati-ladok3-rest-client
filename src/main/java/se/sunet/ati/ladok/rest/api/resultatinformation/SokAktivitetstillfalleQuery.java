package se.sunet.ati.ladok.rest.api.resultatinformation;

import java.util.ArrayList;
import java.util.List;

public class SokAktivitetstillfalleQuery {

    private final List<Integer> aktivitetstillfallestypID;
    private final String benamning;
    private final List<String> kurstillfalleUID;
    private final List<String> kurstillfalleskod;
    private final List<String> aktivitetUID;
    private final List<String> kurskod;
    private final List<String> organisation;
    private final String datumperiod;
    private final String orderby;
    private final Boolean skipCount;
    private final Boolean onlyCount;
    private final String sprakkod;
    private final Integer page;
    private final Integer limit;

    public List<Integer> getAktivitetstillfallestypID() {
        return aktivitetstillfallestypID;
    }

    public String getBenamning() {
        return benamning;
    }

    public List<String> getKurstillfalleUID() {
        return kurstillfalleUID;
    }

    public List<String> getKurstillfalleskod() {
        return kurstillfalleskod;
    }

    public List<String> getAktivitetUID() {
        return aktivitetUID;
    }

    public List<String> getKurskod() {
        return kurskod;
    }

    public List<String> getOrganisation() {
        return organisation;
    }

    public String getDatumperiod() {
        return datumperiod;
    }

    public String getOrderby() {
        return orderby;
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

    private SokAktivitetstillfalleQuery(BuilderImpl builderImpl) {
        aktivitetstillfallestypID = builderImpl.aktivitetstillfallestypID;
        benamning = builderImpl.benamning;
        kurstillfalleUID = builderImpl.kurstillfalleUID;
        kurstillfalleskod = builderImpl.kurstillfalleskod;
        aktivitetUID = builderImpl.aktivitetUID;
        kurskod = builderImpl.kurskod;
        organisation = builderImpl.organisation;
        datumperiod = builderImpl.datumperiod;
        orderby = builderImpl.orderby;
        skipCount = builderImpl.skipCount;
        onlyCount = builderImpl.onlyCount;
        sprakkod = builderImpl.sprakkod;
        page = builderImpl.page;
        limit = builderImpl.limit;
    }

    public static Builder builder() {
        return new BuilderImpl();
    }

    public interface Builder {
        Buildable addAktivitetstillfallestypID(Integer aktivitetstillfallestypID);
        Buildable addAktivitetstillfallestypIDList(List<Integer> aktivitetstillfallestypID);
        Buildable benamning(String benamning);
        Buildable addKurstillfalleUID(String kurstillfalleUID);
        Buildable addKurstillfalleUIDList(List<String> kurstillfalleUID);
        Buildable addKurstillfalleskod(String kurstillfalleskod);
        Buildable addKurstillfalleskodList(List<String> kurstillfalleskod);
        Buildable addAktivitetUID(String aktivitetUID);
        Buildable addAktivitetUIDList(List<String> aktivitetUID);
        Buildable addKurskod(String kurskod);
        Buildable addKurskodList(List<String> kurskod);
        Buildable addOrganisation(String organisation);
        Buildable addOrganisationList(List<String> organisation);
        Buildable datumperiod(String datumperiod);
        Buildable orderby(String orderby);
        Buildable skipCount(Boolean skipCount);
        Buildable onlyCount(Boolean onlyCount);
        Buildable sprakkod(String sprakkod);
        Buildable page(Integer page);
        Buildable limit(Integer limit);
    }

    public interface Buildable extends Builder{
        SokAktivitetstillfalleQuery build();
    }

    public static final class BuilderImpl  implements Builder, Buildable {
        private List<Integer> aktivitetstillfallestypID = new ArrayList<>();
        private String benamning;
        private List<String> kurstillfalleUID = new ArrayList<>();
        private List<String> kurstillfalleskod = new ArrayList<>();
        private List<String> aktivitetUID = new ArrayList<>();
        private List<String> kurskod = new ArrayList<>();
        private List<String> organisation = new ArrayList<>();
        private String datumperiod;
        private String orderby;
        private Boolean skipCount;
        private Boolean onlyCount;
        private String sprakkod;
        private Integer page;
        private Integer limit;

        private BuilderImpl() {
        }

        @Override
        public Buildable addAktivitetstillfallestypID(Integer aktivitetstillfallestypID) {
            this.aktivitetstillfallestypID.add(aktivitetstillfallestypID);
            return this;
        }

        @Override
        public Buildable addAktivitetstillfallestypIDList(List<Integer> aktivitetstillfallestypID) {
            this.aktivitetstillfallestypID.addAll(aktivitetstillfallestypID);
            return this;
        }

        @Override
        public Buildable benamning(String benamning) {
            this.benamning = benamning;
            return this;
        }

        @Override
        public Buildable addKurstillfalleUID(String kurstillfalleUID) {
            this.kurstillfalleUID.add(kurstillfalleUID);
            return this;
        }

        @Override
        public Buildable addKurstillfalleUIDList(List<String> kurstillfalleUID) {
            this.kurstillfalleUID.addAll(kurstillfalleUID);
            return this;
        }

        @Override
        public Buildable addKurstillfalleskod(String kurstillfalleskod) {
            this.kurstillfalleskod.add(kurstillfalleskod);
            return this;
        }

        @Override
        public Buildable addKurstillfalleskodList(List<String> kurstillfalleskod) {
            this.kurstillfalleskod.addAll(kurstillfalleskod);
            return this;
        }

        @Override
        public Buildable addAktivitetUID(String aktivitetUID) {
            this.aktivitetUID.add(aktivitetUID);
            return this;
        }

        @Override
        public Buildable addAktivitetUIDList(List<String> aktivitetUID) {
            this.aktivitetUID.addAll(aktivitetUID);
            return this;
        }

        @Override
        public Buildable addKurskod(String kurskod) {
            this.kurskod.add(kurskod);
            return this;
        }

        @Override
        public Buildable addKurskodList(List<String> kurskod) {
            this.kurskod.addAll(kurskod);
            return this;
        }

        @Override
        public Buildable addOrganisation(String organisation) {
            this.organisation.add(organisation);
            return this;
        }

        @Override
        public Buildable addOrganisationList(List<String> organisation) {
            this.organisation.addAll(organisation);
            return this;
        }

        @Override
        public Buildable datumperiod(String datumperiod) {
            this.datumperiod = datumperiod;
            return this;
        }

        @Override
        public Buildable orderby(String orderby) {
            this.orderby = orderby;
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
        public Buildable page(Integer page) {
            this.page = page;
            return this;
        }

        @Override
        public Buildable limit(Integer limit) {
            this.limit = limit;
            return this;
        }

        public SokAktivitetstillfalleQuery build() {
            return new SokAktivitetstillfalleQuery(this);
        }
    }
}
