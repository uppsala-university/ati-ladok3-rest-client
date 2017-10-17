 package se.sunet.ati.ladok.rest.api.studiedeltagande;

import java.util.Objects;

public class SokDeltagareKurstillfalleQuery {

	private final String kurstillfalleUID;
	private final Integer kanRegistreraPaPeriod;
	private final Integer page;
	private final Integer limit;
	private final String orderBy;
	private final String deltagareTillstand;

	private SokDeltagareKurstillfalleQuery(SokDeltagareKurstillfalleQueryBuilderImpl builder){
		kurstillfalleUID = builder.nestedKurstillfalleUID;
		kanRegistreraPaPeriod = builder.nestedKanRegistreraPaPeriod;
		page = builder.nestedPage;
		limit = builder.nestedLimit;
		orderBy = builder.nestedOrderBy;
		deltagareTillstand = builder.deltagareTillstand;
	}

	public Integer getKanRegistreraPaPeriod() {
		return kanRegistreraPaPeriod;
	}

	public Integer getPage() {
		return page;
	}

	public Integer getLimit() {
		return limit;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public String getDeltagareTillstand() {
		return deltagareTillstand;
	}

	public String getKurstillfalleUID() {
		return kurstillfalleUID;
	}

	public static KurstillfalleUID builder(){
		return new SokDeltagareKurstillfalleQueryBuilderImpl();
	}

	public static class SokDeltagareKurstillfalleQueryBuilderImpl implements KurstillfalleUID, SokDeltagareKurstillfalleQueryBuilder {
		private String nestedKurstillfalleUID;
		private Integer nestedKanRegistreraPaPeriod;
		private Integer nestedPage;
		private Integer nestedLimit;
		private String nestedOrderBy;
		private String deltagareTillstand;

		private SokDeltagareKurstillfalleQueryBuilderImpl() { }

		public SokDeltagareKurstillfalleQueryBuilder kurstillfalleUID(String nestedKurstillfalleUID) {
			Objects.requireNonNull(nestedKurstillfalleUID);
			this.nestedKurstillfalleUID = nestedKurstillfalleUID;
			return this;
		}

		public SokDeltagareKurstillfalleQueryBuilder kanRegistreraPaPeriod(Integer nestedKanRegistreraPaPeriod) {
			this.nestedKanRegistreraPaPeriod = nestedKanRegistreraPaPeriod;
			return this;
		}

		public SokDeltagareKurstillfalleQueryBuilder page(Integer nestedPage) {
			this.nestedPage = nestedPage;
			return this;
		}

		public SokDeltagareKurstillfalleQueryBuilder limit(Integer nestedLimit) {
			this.nestedLimit = nestedLimit;
			return this;
		}

		public SokDeltagareKurstillfalleQueryBuilder orderBy(String nestedOrderBy) {
			this.nestedOrderBy = nestedOrderBy;
			return this;
		}

		public SokDeltagareKurstillfalleQueryBuilder tillstand(String deltagareTillstand) {
			this.deltagareTillstand = deltagareTillstand;
			return this;
		}

		public SokDeltagareKurstillfalleQuery build() {
			return new SokDeltagareKurstillfalleQuery(this);
		}

	}

	public interface KurstillfalleUID {
		SokDeltagareKurstillfalleQueryBuilder kurstillfalleUID(String kurstillfalleUID);
	}

	public interface SokDeltagareKurstillfalleQueryBuilder {
		SokDeltagareKurstillfalleQueryBuilder kanRegistreraPaPeriod(Integer nestedKanRegistreraPaPeriod);
		SokDeltagareKurstillfalleQueryBuilder page(Integer page);
		SokDeltagareKurstillfalleQueryBuilder limit(Integer limit);
		SokDeltagareKurstillfalleQueryBuilder orderBy(String orderBy);
		SokDeltagareKurstillfalleQueryBuilder tillstand(String deltagare);
		SokDeltagareKurstillfalleQuery build();
	}

}
