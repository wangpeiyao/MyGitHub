package com.sachin.login;

import java.io.Serializable;
import java.util.Map;

public class PkGetClassResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	private Map<Integer, String> classes;
	private String contextID;
	private PkGetClassState state;

	public enum PkGetClassState {
		getClassesSuccess, ClassNotExist
	}

	public Map<Integer, String> getClasses() {
		return classes;
	}

	public void setClasses(Map<Integer, String> classes) {
		this.classes = classes;
	}

	public String getContextID() {
		return contextID;
	}

	public void setContextID(String contextID) {
		this.contextID = contextID;
	}

	public PkGetClassState getState() {
		return state;
	}

	public void setState(PkGetClassState state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "PkGetClassResponse [classes=" + classes + ", contextID=" + contextID + ", state=" + state + "]";
	}

}
