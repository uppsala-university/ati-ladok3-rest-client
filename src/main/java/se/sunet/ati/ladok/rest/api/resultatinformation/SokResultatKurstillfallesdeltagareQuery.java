package se.sunet.ati.ladok.rest.api.resultatinformation;

import java.util.List;

public class SokResultatKurstillfallesdeltagareQuery {
    private String aktivitetstillfalleUID;
    private List<String> kurstillfalleUIDer;
    private String gruppUID;
    private boolean skipCount;
    private boolean onlyCount;
    private String sprakkod;
    private int limit;
    private int page;
    private String orderby;

    private SokResultatKurstillfallesdeltagareQuery(Builder builder) {
        aktivitetstillfalleUID = builder.aktivitetstillfalleUID;
        kurstillfalleUIDer = builder.kurstillfalleUIDer;
        gruppUID = builder.gruppUID;
        skipCount = builder.skipCount;
        onlyCount = builder.onlyCount;
        sprakkod = builder.sprakkod;
        limit = builder.limit;
        page = builder.page;
        orderby = builder.orderby;
    }

    public String getAktivitetstillfalleUID() {
        return aktivitetstillfalleUID;
    }

    public List<String> getKurstillfalleUIDer() {
        return kurstillfalleUIDer;
    }

    public String getGruppUID() {
        return gruppUID;
    }

    public boolean getSkipCount() {
        return skipCount;
    }

    public boolean getOnlyCount() {
        return onlyCount;
    }

    public String getSprakkod() {
        return sprakkod;
    }

    public int getLimit() {
        return limit;
    }

    public int getPage() {
        return page;
    }

    public String getOrderby() {
        return orderby;
    }

    public static final class Builder {
        private String aktivitetstillfalleUID;
        private List<String> kurstillfalleUIDer;
        private String gruppUID;
        private boolean skipCount;
        private boolean onlyCount;
        private String sprakkod;
        private int limit;
        private int page;
        private String orderby;

        public Builder() {
        }

        public Builder aktivitetstillfalleUID(String val) {
            aktivitetstillfalleUID = val;
            return this;
        }

        public Builder kurstillfalleUIDer(List<String> vals) {
            kurstillfalleUIDer = vals;
            return this;
        }

        public Builder gruppUID(String val) {
            gruppUID = val;
            return this;
        }

        public Builder skipCount(boolean val) {
            skipCount = val;
            return this;
        }

        public Builder onlyCount(boolean val) {
            onlyCount = val;
            return this;
        }

        public Builder sprakkod(String val) {
            sprakkod = val;
            return this;
        }

        public Builder limit(int val) {
            limit = val;
            return this;
        }

        public Builder page(int val) {
            page = val;
            return this;
        }

        public Builder orderby(String val) {
            orderby = val;
            return this;
        }

        public SokResultatKurstillfallesdeltagareQuery build() {
            return new SokResultatKurstillfallesdeltagareQuery(this);
        }
    }
}
