package com.datalook.ezui.generate.plugin.model;

import java.lang.reflect.Field;

import javax.persistence.Id;

import org.apache.commons.lang3.StringUtils;
import org.apache.maven.project.MavenProject;

import com.datalook.ezui.generate.plugin.EzuiGenerateMojo;
import com.datalook.ezui.generate.plugin.model.bean.Javas;
import com.datalook.ezui.generate.plugin.model.bean.Pages;
import com.datalook.ezui.generate.plugin.model.bean.element.Element;
import com.datalook.ezui.generate.plugin.util.ReflectUtil;

public class EzuiHolder {
	public String projectPackage = EzuiGenerateMojo.projectPackage;
	public Class clazz;
	public Field idField;
	public String idFieldName;

	public MavenProject project;
	public String basePackage;

	public Pages pages = new Pages();
	public Javas javas = new Javas();

	public EzuiHolder(Class clazz, MavenProject project) {
		this.clazz = clazz;
		this.project = project;
		idField = autoSetIdField(clazz);
		idFieldName = idField.getName();
	}

	public void init() {
		pages.init(clazz, project);
		javas.init(clazz, project);
		basePackage = StringUtils.substringBefore(clazz.getName(), ".model.");
	}

	public void deepScan() {
		if (pages.form != null) {
			for (Element e : pages.form.formElements) {
				e.deepInit(this);
			}
		}
		if (pages.dataGrid != null) {
			for (Element e : pages.dataGrid.conditionElements) {
				e.deepInit(this);
			}
			for (Element e : pages.dataGrid.showElements) {
				e.deepInit(this);
			}
			// for(int i=0;i<pages.dataGrid.showElements.size();i++){
			// if(pages.dataGrid.showElements.get(i).elementType.equals(EzuiElementType.tree.name())){
			// pages.dataGrid.showElements.remove(i);
			// i--;
			// }
			// }
		}
	}

	public Field autoSetIdField(Class clazz) {
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			if (ReflectUtil.isFieldAnnotated(field, Id.class)) {
				return field;
			}
		}
		return null;
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

	public String getIdFieldName() {
		return idFieldName;
	}

	public void setIdFieldName(String idFieldName) {
		this.idFieldName = idFieldName;
	}

	public String getProjectPackage() {
		return projectPackage;
	}

	public void setProjectPackage(String projectPackage) {
		this.projectPackage = projectPackage;
	}

}
