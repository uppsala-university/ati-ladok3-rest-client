package se.sunet.ati.ladok.rest.api.studiedeltagande;

import se.sunet.ati.ladok.rest.api.exceptions.SaknarObligatoriskParameterException;

public class SokDeltagareKurstillfalleQuery {

	private static final String TJANST_NAMN = "studiedeltagande.sokDeltagareKurstillfalle";

	private final String kurstillfalleUID;
	private final Integer kanRegistreraPaPeriod;
	private final Integer page;
	private final Integer limit;
	private final String orderBy;
	private final String deltagareTillstand;

	private SokDeltagareKurstillfalleQuery(SokDeltagareKurstillfalleQueryBuilder builder){
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

	public static SokDeltagareKurstillfalleQueryBuilder builder(){
		return new SokDeltagareKurstillfalleQueryBuilder();
	}

	public static class SokDeltagareKurstillfalleQueryBuilder {
		private String nestedKurstillfalleUID;
		private Integer nestedKanRegistreraPaPeriod;
		private Integer nestedPage;
		private Integer nestedLimit;
		private String nestedOrderBy;
		private String deltagareTillstand;

		private SokDeltagareKurstillfalleQueryBuilder() { }

		public SokDeltagareKurstillfalleQueryBuilder kurstillfalleUID(String nestedKurstillfalleUID) {
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

		public SokDeltagareKurstillfalleQuery build() throws SaknarObligatoriskParameterException {
			verifyParameters();
			return new SokDeltagareKurstillfalleQuery(this);
		}

		private void verifyParameters() throws SaknarObligatoriskParameterException {
			if(isNull(nestedKurstillfalleUID)){
				throw new SaknarObligatoriskParameterException(TJANST_NAMN, "kurstillfalleUID");
			}
		}

		private static boolean isNull(Object o) {
			return null == o;
		}

	}

}
