 package se.sunet.ati.ladok.rest.api.studiedeltagande;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class SokDeltagareKurstillfalleQuery {

	private final List<String> kurstillfalleUIDs;
	private final Integer kanRegistreraPaPeriod;
	private final Integer page;
	private final Integer limit;
	private final String orderBy;
	private final String deltagareTillstand;

	private SokDeltagareKurstillfalleQuery(SokDeltagareKurstillfalleQueryBuilderImpl builder){
		kurstillfalleUIDs = Collections.unmodifiableList(builder.nestedKurstillfalleUIDs);
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

	public List<String> getKurstillfalleUID() {
		return kurstillfalleUIDs;
	}

	public static KurstillfalleUID builder(){
		return new SokDeltagareKurstillfalleQueryBuilderImpl();
	}

	public static class SokDeltagareKurstillfalleQueryBuilderImpl implements KurstillfalleUID, SokDeltagareKurstillfalleQueryBuilder {
		private List<String> nestedKurstillfalleUIDs = new ArrayList<>();
		private Integer nestedKanRegistreraPaPeriod;
		private Integer nestedPage;
		private Integer nestedLimit;
		private String nestedOrderBy;
		private String deltagareTillstand;

		private SokDeltagareKurstillfalleQueryBuilderImpl() { }

		public SokDeltagareKurstillfalleQueryBuilder kurstillfalleUID(String kurstillfalleUID) {
			return addKurstillfalleUID(kurstillfalleUID);
		}

		public SokDeltagareKurstillfalleQueryBuilder addKurstillfalleUID(String kurstillfalleUID) {
			Objects.requireNonNull(kurstillfalleUID);
			nestedKurstillfalleUIDs.add(kurstillfalleUID);
			return this;
		}

		public SokDeltagareKurstillfalleQueryBuilder addKurstillfalleUIDList(List<String> kurstillfalleUIDs) {
			Objects.requireNonNull(kurstillfalleUIDs);
			nestedKurstillfalleUIDs.addAll(kurstillfalleUIDs);
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
		SokDeltagareKurstillfalleQueryBuilder addKurstillfalleUID(String kurstillfalleUID);
		SokDeltagareKurstillfalleQueryBuilder addKurstillfalleUIDList(List<String> kurstillfalleUIDs);
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
