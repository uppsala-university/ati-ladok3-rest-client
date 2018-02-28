package se.sunet.ati.ladok.rest.api.resultatinformation;

public class SokResultatKurstillfallesdeltagareQuery {
    private String aktivitetstillfalleUID;
    private String kurstillfalleUID;
    private String gruppUID;
    private boolean skipCount;
    private boolean onlyCount;
    private String sprakkod;
    private int limit;
    private int page;
    private String orderby;

    private SokResultatKurstillfallesdeltagareQuery(Builder builder) {
        aktivitetstillfalleUID = builder.aktivitetstillfalleUID;
        kurstillfalleUID = builder.kurstillfalleUID;
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

    public String getKurstillfalleUID() {
        return kurstillfalleUID;
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
        private String kurstillfalleUID;
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

        public Builder kurstillfalleUID(String val) {
            kurstillfalleUID = val;
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
