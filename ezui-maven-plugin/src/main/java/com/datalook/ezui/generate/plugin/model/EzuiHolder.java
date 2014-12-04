package com.datalook.ezui.generate.plugin.model;

import org.apache.commons.lang3.StringUtils;
import org.apache.maven.project.MavenProject;

import com.datalook.ezui.generate.plugin.model.bean.Javas;
import com.datalook.ezui.generate.plugin.model.bean.Pages;

public class EzuiHolder {
	public Class clazz;

	public MavenProject project;
	public String basePackage;
	
	public Pages pages=new Pages();
	public Javas javas=new Javas();
	public EzuiHolder(Class clazz, MavenProject project) {
		this.clazz = clazz;
		this.project = project;
	}

	public void init() {
		pages.init(clazz,project);
		javas.init(clazz,project);
		basePackage=StringUtils.substringBefore(clazz.getName(), ".model.");
	}

	public Class getClazz() {
		return clazz;
	}

	public void setClazz(Class clazz) {
		this.clazz = clazz;
	}

	public MavenProject getProject() {
		return project;
	}

	public void setProject(MavenProject project) {
		this.project = project;
	}

	public Pages getPages() {
		return pages;
	}

	public void setPages(Pages pages) {
		this.pages = pages;
	}

	public Javas getJavas() {
		return javas;
	}

	public void setJavas(Javas javas) {
		this.javas = javas;
	}

	public String getBasePackage() {
		return basePackage;
	}

	public void setBasePackage(String basePackage) {
		this.basePackage = basePackage;
	}
	
}
