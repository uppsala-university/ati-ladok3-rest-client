package se.sunet.ati.ladok.rest.api.resultatinformation;

import java.util.List;

public class SokResultatResultatUppfoljningQuery {

    private final List<String> kurstillfallen;
    private final String kursinstansUID;
    private final String grupp;
    private final String tillstand;
    private final String orderby;
    private final String orderBetygsgradAvserUtbildningUID;
    private final String orderExaminationsdatumAvserUtbildningUID;

    private final int page;
    private final int limit;

    private SokResultatResultatUppfoljningQuery(Builder builder) {
        this.kurstillfallen = builder.kurstillfallen;
        this.kursinstansUID = builder.kursinstansUID;
        this.grupp = builder.grupp;
        this.tillstand = builder.tillstand;
        this.orderby = builder.orderby;
        this.orderBetygsgradAvserUtbildningUID = builder.orderBetygsgradAvserUtbildningUID;
        this.orderExaminationsdatumAvserUtbildningUID = builder.orderExaminationsdatumAvserUtbildningUID;
        this.page = builder.page;
        this.limit = builder.limit;
    }

    public List<String> getKurstillfallen() {
        return kurstillfallen;
    }

    public String getKursinstansUID() {
        return kursinstansUID;
    }

    public String getGrupp() {
        return grupp;
    }

    public String getTillstand() {
        return tillstand;
    }

    public String getOrderby() {
        return orderby;
    }

    public String getOrderBetygsgradAvserUtbildningUID() {
        return orderBetygsgradAvserUtbildningUID;
    }
    public String getOrderExaminationsdatumAvserUtbildningUID() {
        return orderExaminationsdatumAvserUtbildningUID;
    }

    public int getPage() {
        return page;
    }

    public int getLimit() {
        return limit;
    }

    public static final class Builder {
        private List<String> kurstillfallen;
        private String kursinstansUID;
        private String grupp;
        private String tillstand;
        private String orderby;
        private String orderBetygsgradAvserUtbildningUID;
        private String orderExaminationsdatumAvserUtbildningUID;
        private int page;
        private int limit;

        public Builder() {
        }

        public Builder kurstillfallen(List<String> val) {
            kurstillfallen = val;
            return this;
        }

        public Builder kursinstansUID(String val) {
            kursinstansUID = val;
            return this;
        }

        public Builder grupp(String val) {
            grupp = val;
            return this;
        }

        public Builder tillstand(String val) {
            tillstand = val;
            return this;
        }

        public Builder orderby(String val) {
            orderby = val;
            return this;
        }

        public Builder orderBetygsgradAvserUtbildningUID(String val) {
            orderBetygsgradAvserUtbildningUID = val;
            return this;
        }

        public Builder orderExaminationsdatumAvserUtbildningUID(String val) {
            orderExaminationsdatumAvserUtbildningUID = val;
            return this;
        }

        public Builder page(int val) {
            page = val;
            return this;
        }

        public Builder limit(int val) {
            limit = val;
            return this;
        }

        public SokResultatResultatUppfoljningQuery build() {
            return new SokResultatResultatUppfoljningQuery(this);
        }
    }
}
