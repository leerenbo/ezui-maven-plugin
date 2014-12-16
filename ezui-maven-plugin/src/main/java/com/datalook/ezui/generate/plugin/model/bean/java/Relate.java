package com.datalook.ezui.generate.plugin.model.bean.java;

import org.apache.commons.lang3.StringUtils;

public class Relate {
	public Class clazz;
	public Class relatedClass;
	public String actionName;
	public String relatedClassSimpleName;
	public String relatedClassFullName;
	public String relatedCollectionName;
	public Relate(Class clazz, Class relatedClass) {
		super();
		this.clazz = clazz;
		this.relatedClass = relatedClass;
		relatedClassSimpleName=relatedClass.getSimpleName();
		relatedClassFullName=relatedClass.getName();
	}
	public String getActionName() {
		return actionName;
	}
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}
	public String getRelatedClassSimpleName() {
		return relatedClassSimpleName;
	}
	public void setRelatedClassSimpleName(String relatedClassSimpleName) {
		this.relatedClassSimpleName = relatedClassSimpleName;
	}
	public String getRelatedClassFullName() {
		return relatedClassFullName;
	}
	public void setRelatedClassFullName(String relatedClassFullName) {
		this.relatedClassFullName = relatedClassFullName;
	}
	public String getRelatedCollectionName() {
		return relatedCollectionName;
	}
	public void setRelatedCollectionName(String relatedCollectionName) {
		this.relatedCollectionName = relatedCollectionName;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((actionName == null) ? 0 : actionName.hashCode());
		result = prime * result + ((relatedClassFullName == null) ? 0 : relatedClassFullName.hashCode());
		result = prime * result + ((relatedCollectionName == null) ? 0 : relatedCollectionName.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Relate other = (Relate) obj;
		if (actionName == null) {
			if (other.actionName != null)
				return false;
		} else if (!actionName.equals(other.actionName))
			return false;
		if (relatedClassFullName == null) {
			if (other.relatedClassFullName != null)
				return false;
		} else if (!relatedClassFullName.equals(other.relatedClassFullName))
			return false;
		if (relatedCollectionName == null) {
			if (other.relatedCollectionName != null)
				return false;
		} else if (!relatedCollectionName.equals(other.relatedCollectionName))
			return false;
		return true;
	}
	
}
