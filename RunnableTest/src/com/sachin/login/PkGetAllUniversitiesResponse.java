package com.sachin.login;

import java.io.Serializable;
import java.util.Map;

public class PkGetAllUniversitiesResponse implements Serializable {

	public PkGetAllUniversitiesResponse(Map<Integer, String> universities) {
		super();
		this.universities = universities;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Map<Integer, String> universities;

	public Map<Integer, String> getUniversities() {
		return universities;
	}

	public void setUniversities(Map<Integer, String> universities) {
		this.universities = universities;
	}

	@Override
	public String toString() {
		return "PkGetAllUniversitiesResponse [universities=" + universities + "]";
	}

}
