package se.sunet.ati.ladok.rest.api.studiedeltagande;

public class SokDeltagareKurspaketeringstillfalleQuery {
	private final String kurspaketeringstillfalleUID;
	private final Integer page;
	private final Integer limit;
	private final String orderBy;
	private final String deltagareTillstand;

	public static Builder builder() {
		return new Builder();
	}

	public String getKurspaketeringstillfalleUID() {
		return kurspaketeringstillfalleUID;
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

	private SokDeltagareKurspaketeringstillfalleQuery(Builder builder) {
		kurspaketeringstillfalleUID = builder.kurspaketeringstillfalleUID;
		page = builder.page;
		limit = builder.limit;
		orderBy = builder.orderBy;
		deltagareTillstand = builder.deltagareTillstand;
	}

	public static final class Builder {
		private String kurspaketeringstillfalleUID;
		private Integer page;
		private Integer limit;
		private String orderBy;
		private String deltagareTillstand;

		private Builder() {
		}

		public Builder kurspaketeringstillfalleUID(String kurspaketeringstillfalleUID) {
			this.kurspaketeringstillfalleUID = kurspaketeringstillfalleUID;
			return this;
		}

		public Builder page(Integer page) {
			this.page = page;
			return this;
		}

		public Builder limit(Integer limit) {
			this.limit = limit;
			return this;
		}

		public Builder orderBy(String orderBy) {
			this.orderBy = orderBy;
			return this;
		}

		public Builder deltagareTillstand(String deltagareTillstand) {
			this.deltagareTillstand = deltagareTillstand;
			return this;
		}

		public SokDeltagareKurspaketeringstillfalleQuery build() {
			return new SokDeltagareKurspaketeringstillfalleQuery(this);
		}
	}
}
