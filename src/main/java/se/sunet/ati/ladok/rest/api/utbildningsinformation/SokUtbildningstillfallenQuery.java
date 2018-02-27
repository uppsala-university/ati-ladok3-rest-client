package se.sunet.ati.ladok.rest.api.utbildningsinformation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * Query parameter for method Utbildningsinformation#sokUtbildningstillfallen(SokUtbildningstillfallenQuery query)
 */
public class SokUtbildningstillfallenQuery {
	private final String utbildningstypID;
	private final Collection<String> utbildningstillfallestypID;
	private final String studieordningID;
	private final String benamning;
	private final String studieperiod;
	private final String startperiodID;
	private final Integer page;
	private final Integer limit;
	private final Boolean skipCount;
	private final Boolean onlyCount;
	private final String sprakkod;
	private final Collection<String> utbildningstillfalleskod;
	private final Collection<String> utbildningskod;
	private final Collection<String> organisationUID;
	private final Collection<String> status;

	private SokUtbildningstillfallenQuery(Builder builder) {
		utbildningstypID = builder.utbildningstypID;
		utbildningstillfallestypID = builder.utbildningstillfallestypID;
		studieordningID = builder.studieordningID;
		benamning = builder.benamning;
		studieperiod = builder.studieperiod;
		startperiodID = builder.startperiodID;
		page = builder.page;
		limit = builder.limit;
		skipCount = builder.skipCount;
		onlyCount = builder.onlyCount;
		sprakkod = builder.sprakkod;
		utbildningstillfalleskod = builder.utbildningstillfalleskod;
		utbildningskod = builder.utbildningskod;
		organisationUID = builder.organisationUID;
		status = builder.status;
	}

	public static Builder builder() {
		return new Builder();
	}

	public String getUtbildningstypID() {
		return utbildningstypID;
	}

	/**
	 * @deprecated Use getUtbildningstillfallestypIDs instead. This method will be removed in future release.
	 */
	@Deprecated
	public String getUtbildningstillfallestypID() {
		if (utbildningstillfallestypID == null) {
			return null;
		} else if (utbildningstillfallestypID.size() != 1) {
			throw new IllegalStateException("Expects exactly one item in list, got: " + utbildningstillfallestypID.size());
		} else {
			return utbildningstillfallestypID.iterator().next();
		}
	}

	public Collection<String> getUtbildningstillfallestypIDs() {
		return utbildningstillfallestypID;
	}

	public String getStudieordningID() {
		return studieordningID;
	}

	public String getBenamning() {
		return benamning;
	}

	public String getStudieperiod() {
		return studieperiod;
	}

	public String getStartperiodID() {
		return startperiodID;
	}

	public Integer getPage() {
		return page;
	}

	public Integer getLimit() {
		return limit;
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

	public Collection<String> getUtbildningstillfalleskod() {
		return utbildningstillfalleskod;
	}

	public Collection<String> getUtbildningskod() {
		return utbildningskod;
	}

	public Collection<String> getOrganisationUID() {
		return organisationUID;
	}

	public Collection<String> getStatus() {
		return status;
	}

	public static final class Builder {
		private String utbildningstypID;
		private Collection<String> utbildningstillfallestypID;
		private String studieordningID;
		private String benamning;
		private String studieperiod;
		private String startperiodID;
		private Integer page;
		private Integer limit;
		private Boolean skipCount;
		private Boolean onlyCount;
		private String sprakkod;
		private Collection<String> utbildningstillfalleskod;
		private Collection<String> utbildningskod;
		private Collection<String> organisationUID;
		private Collection<String> status;

		private Builder() {
		}

		public Builder utbildningstypID(String val) {
			utbildningstypID = val;
			return this;
		}

		public Builder utbildningstillfallestypID(String id) {
			utbildningstillfallestypID = Arrays.asList(id);
			return this;
		}

		public Builder utbildningstillfallestypID(Collection<String> ids) {
			utbildningstillfallestypID = new ArrayList<>(ids);
			return this;
		}

		public Builder studieordningID(String val) {
			studieordningID = val;
			return this;
		}

		public Builder benamning(String val) {
			benamning = val;
			return this;
		}

		public Builder studieperiod(String val) {
			studieperiod = val;
			return this;
		}

		public Builder startperiodID(String val) {
			startperiodID = val;
			return this;
		}

		public Builder page(Integer val) {
			page = val;
			return this;
		}

		public Builder limit(Integer val) {
			limit = val;
			return this;
		}

		public Builder skipCount(Boolean val) {
			skipCount = val;
			return this;
		}

		public Builder onlyCount(Boolean val) {
			onlyCount = val;
			return this;
		}

		public Builder sprakkod(String val) {
			sprakkod = val;
			return this;
		}

		public Builder utbildningstillfalleskod(Collection<String> val) {
			utbildningstillfalleskod = val;
			return this;
		}

		public Builder utbildningskod(Collection<String> val) {
			utbildningskod = val;
			return this;
		}

		public Builder organisationUID(Collection<String> val) {
			organisationUID = val;
			return this;
		}

		public Builder status(Collection<String> val) {
			status = val;
			return this;
		}

		public SokUtbildningstillfallenQuery build() {
			return new SokUtbildningstillfallenQuery(this);
		}
	}
}
