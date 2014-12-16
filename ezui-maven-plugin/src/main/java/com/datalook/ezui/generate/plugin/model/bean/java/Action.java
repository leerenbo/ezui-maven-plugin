package com.datalook.ezui.generate.plugin.model.bean.java;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class Action {
	//${javas.action.}
	public File file;
	public String packagee;
	public String className;
	public String beanName;
	public Boolean noSy_find=false;
	public Set<String> beforeSaveIdnullables=new HashSet<String>();
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public String getPackagee() {
		return packagee;
	}
	public void setPackagee(String packagee) {
		this.packagee = packagee;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getBeanName() {
		return beanName;
	}
	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}
	public Boolean getNoSy_find() {
		return noSy_find;
	}
	public void setNoSy_find(Boolean noSy_find) {
		this.noSy_find = noSy_find;
	}
	public Set<String> getBeforeSaveIdnullables() {
		return beforeSaveIdnullables;
	}
	public void setBeforeSaveIdnullables(Set<String> beforeSaveIdnullables) {
		this.beforeSaveIdnullables = beforeSaveIdnullables;
	}
	
}
