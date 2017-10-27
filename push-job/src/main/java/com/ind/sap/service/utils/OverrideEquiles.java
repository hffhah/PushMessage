package com.ind.sap.service.utils;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public abstract class OverrideEquiles implements Serializable {

	private static final long serialVersionUID = 1L;

	@Override 
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, false);
	}

	@Override 
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	@Override 
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj, false);
	}
}
