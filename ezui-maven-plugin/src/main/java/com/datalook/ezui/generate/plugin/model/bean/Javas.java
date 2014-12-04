package com.datalook.ezui.generate.plugin.model.bean;

import java.io.File;

import org.apache.commons.lang3.StringUtils;
import org.apache.maven.project.MavenProject;

import com.datalook.ezui.generate.plugin.model.bean.java.Action;
import com.datalook.ezui.generate.plugin.model.bean.java.Service;

public class Javas {
	public String javaDir;
	public Action action=new Action();
	public Service service=new Service();

	public String genericClassFullName;
	public String genericClassSimpleName;

	public void init(Class clazz, MavenProject project){
		javaDir=project.getBasedir()+"\\src\\main\\java\\";
		action.packagee=clazz.getPackage().getName().replace(".model.", ".action.");
		action.beanName=StringUtils.uncapitalize(clazz.getSimpleName());
		action.className=clazz.getSimpleName()+"Action";
		action.file=new File(javaDir+clazz.getName().replace(".model.", ".action.").replace(".", "\\")+"Action.java");

		service.packagee=clazz.getPackage().getName().replace(".model.", ".service.");
		service.beanName=StringUtils.uncapitalize(clazz.getSimpleName())+"Service";
		service.className=clazz.getSimpleName()+"Service";
		service.file=new File(javaDir+clazz.getName().replace(".model.", ".service.").replace(".", "\\")+"Service.java");
		service.fileImpl=new File(javaDir+(clazz.getPackage().getName()+".implement.").replace(".model.", ".service.").replace(".", "\\")+"ServiceImpl.java");
		genericClassFullName=clazz.getName();
		genericClassSimpleName=clazz.getSimpleName();
	}

	public String getJavaDir() {
		return javaDir;
	}

	public void setJavaDir(String javaDir) {
		this.javaDir = javaDir;
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}

	public String getGenericClassFullName() {
		return genericClassFullName;
	}

	public void setGenericClassFullName(String genericClassFullName) {
		this.genericClassFullName = genericClassFullName;
	}

	public String getGenericClassSimpleName() {
		return genericClassSimpleName;
	}

	public void setGenericClassSimpleName(String genericClassSimpleName) {
		this.genericClassSimpleName = genericClassSimpleName;
	}
	
}
