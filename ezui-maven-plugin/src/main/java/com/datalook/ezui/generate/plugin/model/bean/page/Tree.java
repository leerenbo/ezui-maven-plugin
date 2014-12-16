package com.datalook.ezui.generate.plugin.model.bean.page;

import java.io.File;

import com.datalook.ezui.generate.plugin.model.EzuiHolder;

public class Tree {
	public File file;
	public String relatedClassSimpleName;
	public boolean haveStatus;
	public String relatedCollectionName;
	public String parentField;
	public String idField;
	public String treeField;
	public String webappURL;

	public String buttonText;
	public String actionMethodName;
	public String title;
	
	public EzuiHolder relateEzuiHolder;
	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getIdField() {
		return idField;
	}

	public void setIdField(String idField) {
		this.idField = idField;
	}

	public String getRelatedClassSimpleName() {
		return relatedClassSimpleName;
	}

	public void setRelatedClassSimpleName(String relatedClassSimpleName) {
		this.relatedClassSimpleName = relatedClassSimpleName;
	}

	public String getRelatedCollectionName() {
		return relatedCollectionName;
	}

	public void setRelatedCollectionName(String relatedCollectionName) {
		this.relatedCollectionName = relatedCollectionName;
	}

	public boolean isHaveStatus() {
		return haveStatus;
	}

	public void setHaveStatus(boolean haveStatus) {
		this.haveStatus = haveStatus;
	}

	public String getParentField() {
		return parentField;
	}

	public void setParentField(String parentField) {
		this.parentField = parentField;
	}

	public String getTreeField() {
		return treeField;
	}

	public void setTreeField(String treeField) {
		this.treeField = treeField;
	}

	public String getWebappURL() {
		return webappURL;
	}

	public void setWebappURL(String webappURL) {
		this.webappURL = webappURL;
	}

	public String getButtonText() {
		return buttonText;
	}

	public void setButtonText(String buttonText) {
		this.buttonText = buttonText;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getActionMethodName() {
		return actionMethodName;
	}

	public void setActionMethodName(String actionMethodName) {
		this.actionMethodName = actionMethodName;
	}

	public EzuiHolder getRelateEzuiHolder() {
		return relateEzuiHolder;
	}

	public void setRelateEzuiHolder(EzuiHolder relateEzuiHolder) {
		this.relateEzuiHolder = relateEzuiHolder;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((actionMethodName == null) ? 0 : actionMethodName.hashCode());
		result = prime * result + ((relatedClassSimpleName == null) ? 0 : relatedClassSimpleName.hashCode());
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
		Tree other = (Tree) obj;
		if (actionMethodName == null) {
			if (other.actionMethodName != null)
				return false;
		} else if (!actionMethodName.equals(other.actionMethodName))
			return false;
		if (relatedClassSimpleName == null) {
			if (other.relatedClassSimpleName != null)
				return false;
		} else if (!relatedClassSimpleName.equals(other.relatedClassSimpleName))
			return false;
		if (relatedCollectionName == null) {
			if (other.relatedCollectionName != null)
				return false;
		} else if (!relatedCollectionName.equals(other.relatedCollectionName))
			return false;
		return true;
	}

}
