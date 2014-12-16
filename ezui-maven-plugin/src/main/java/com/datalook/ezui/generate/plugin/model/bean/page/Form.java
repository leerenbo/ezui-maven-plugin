package com.datalook.ezui.generate.plugin.model.bean.page;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.datalook.ezui.annotation.declare.EzuiTransparent;
import com.datalook.ezui.generate.plugin.model.bean.element.Element;
import com.datalook.ezui.generate.plugin.model.bean.element.ElementFactory;

public class Form {
	//${pages.form.}

	public File file;
	public String fileName;
	public String webappURL;
	public List<Element> formElements = new ArrayList<Element>();
	public Form init(Class clazz){
		Field[] fields = clazz.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			EzuiTransparent ezuiTransparent = field.getAnnotation(EzuiTransparent.class);
			if (ezuiTransparent != null) {
				continue;
			}
			Element eForm = ElementFactory.instance(field, ElementFactory.FORM);
			if (eForm != null)
				formElements.add(eForm);
		}
		if(formElements.size()>0){
			return this;
		}
		return null;
	}
	
	public String getWebappURL() {
		return webappURL;
	}
	public void setWebappURL(String webappURL) {
		this.webappURL = webappURL;
	}
	public List<Element> getFormElements() {
		return formElements;
	}
	public void setFormElements(List<Element> formElements) {
		this.formElements = formElements;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
