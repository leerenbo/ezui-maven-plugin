package com.datalook.ezui.generate.plugin.model.bean.java;

import java.io.File;

public class Service {
	//${javas.service.}
	public File file;
	public File fileImpl;
	public String packagee;
	public String className;
	public String beanName;
	
	
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
	
}
