package se.sunet.ati.ladok.rest.api.studiedeltagande;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SokDeltagareKurspaketeringstillfalleQuery {
	private final String kurspaketeringstillfalleUID;
	private final Integer page;
	private final Integer limit;
	private final String orderBy;
	private final List<String> deltagareTillstands;

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

	public List<String> getDeltagareTillstands() {
		return deltagareTillstands;
	}

	private SokDeltagareKurspaketeringstillfalleQuery(Builder builder) {
		kurspaketeringstillfalleUID = builder.kurspaketeringstillfalleUID;
		page = builder.page;
		limit = builder.limit;
		orderBy = builder.orderBy;
		deltagareTillstands = Collections.unmodifiableList(builder.deltagareTillstands);
	}

	public static final class Builder {
		private String kurspaketeringstillfalleUID;
		private Integer page;
		private Integer limit;
		private String orderBy;
		private List<String> deltagareTillstands = new ArrayList<>();

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
			return addDeltagareTillstand(deltagareTillstand);
		}

		public Builder addDeltagareTillstand(String deltagareTillstand) {
			this.deltagareTillstands.add(deltagareTillstand);
			return this;
		}

		public Builder addDeltagareTillstandList(List<String> deltagareTillstand) {
			this.deltagareTillstands.addAll(deltagareTillstand);
			return this;
		}

		public SokDeltagareKurspaketeringstillfalleQuery build() {
			return new SokDeltagareKurspaketeringstillfalleQuery(this);
		}
	}
}
