package com.datalook.ezui.generate.plugin.model.bean.element;

import java.lang.reflect.Field;

import com.datalook.ezui.annotation.element.EzuiElement;
import com.datalook.ezui.annotation.element.EzuiElement.EzuiElementType;

public class Element {
	
	public String title;
	public String fieldName;
	public String typeClassName;
	
	public String elementType;

	public Element(Field field, EzuiElement element) {
		title=element.title();
		fieldName=field.getName();
		typeClassName=field.getClass().getSimpleName();
		elementType=element.elementType().name();
	}

	public Element(Field field) {
		title=field.getName();
		fieldName=field.getName();
		typeClassName=field.getClass().getSimpleName();

		if(field.getType().equals(String.class)){
			elementType=EzuiElementType.textbox.name();
		}
		if(field.getType().equals(Integer.class)){
			elementType=EzuiElementType.numberbox.name();
		}
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getTypeClassName() {
		return typeClassName;
	}

	public void setTypeClassName(String typeClassName) {
		this.typeClassName = typeClassName;
	}

	public String getElementType() {
		return elementType;
	}

	public void setElementType(String elementType) {
		this.elementType = elementType;
	}

	
}
