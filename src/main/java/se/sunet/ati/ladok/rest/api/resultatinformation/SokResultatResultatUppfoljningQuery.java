package se.sunet.ati.ladok.rest.api.resultatinformation;

import java.util.List;

public class SokResultatResultatUppfoljningQuery {

    private List<String> kurstillfallen;

    private String kursinstansUID;
    private String grupp;
    private String tillstand;
    private String orderby;
    private String orderBetygsgradAvserUtbildningUID;
    private String orderExaminationsdatumAvserUtbildningUID;

    int page;
    int limit;

    private SokResultatResultatUppfoljningQuery(Builder builder) {
        setKurstillfallen(builder.kurstillfallen);
        setKursinstansUID(builder.kursinstansUID);
        setGrupp(builder.grupp);
        setTillstand(builder.tillstand);
        setOrderby(builder.orderby);
        setOrderBetygsgradAvserUtbildningUID(builder.orderBetygsgradAvserUtbildningUID);
        setOrderExaminationsdatumAvserUtbildningUID(builder.orderExaminationsdatumAvserUtbildningUID);
        setPage(builder.page);
        setLimit(builder.limit);
    }

    public List<String> getKurstillfallen() {
        return kurstillfallen;
    }

    public void setKurstillfallen(List<String> kurstillfallen) {
        this.kurstillfallen = kurstillfallen;
    }

    public String getKursinstansUID() {
        return kursinstansUID;
    }

    public void setKursinstansUID(String kursinstansUID) {
        this.kursinstansUID = kursinstansUID;
    }

    public String getGrupp() {
        return grupp;
    }

    public void setGrupp(String grupp) {
        this.grupp = grupp;
    }

    public String getTillstand() {
        return tillstand;
    }

    public void setTillstand(String tillstand) {
        this.tillstand = tillstand;
    }

    public String getOrderby() {
        return orderby;
    }

    public void setOrderby(String orderby) {
        this.orderby = orderby;
    }

    public String getOrderBetygsgradAvserUtbildningUID() {
        return orderBetygsgradAvserUtbildningUID;
    }

    public void setOrderBetygsgradAvserUtbildningUID(String orderBetygsgradAvserUtbildningUID) {
        this.orderBetygsgradAvserUtbildningUID = orderBetygsgradAvserUtbildningUID;
    }

    public String getOrderExaminationsdatumAvserUtbildningUID() {
        return orderExaminationsdatumAvserUtbildningUID;
    }

    public void setOrderExaminationsdatumAvserUtbildningUID(String orderExaminationsdatumAvserUtbildningUID) {
        this.orderExaminationsdatumAvserUtbildningUID = orderExaminationsdatumAvserUtbildningUID;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
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
