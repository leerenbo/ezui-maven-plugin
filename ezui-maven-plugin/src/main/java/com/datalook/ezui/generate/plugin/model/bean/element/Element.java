package com.datalook.ezui.generate.plugin.model.bean.element;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.datalook.ezui.annotation.element.EzuiElement;
import com.datalook.ezui.annotation.element.EzuiElement.EzuiElementType;
import com.datalook.ezui.generate.plugin.model.EzuiHolder;
import com.datalook.ezui.generate.plugin.model.bean.java.Relate;
import com.datalook.ezui.generate.plugin.model.bean.page.Tree;
import com.datalook.ezui.generate.plugin.model.bean.page.TreeGrid;
import com.datalook.ezui.generate.plugin.scan.Scanner;
import com.datalook.ezui.generate.plugin.util.GenericsUtils;
import com.datalook.ezui.generate.plugin.util.ReflectUtil;
import com.datalook.ezui.generate.plugin.util.TextUtil;

public class Element {

	public String title;
	public String fieldName;
	public String typeClassName;
	public String location;
	public String formatString;

	public String elementType = EzuiElementType.Default.name();
	public Boolean isHidden = false;
	public String defaultValue = "";
	public Boolean isId = false;
	public EzuiHolder insideEzuiHolder;
	public Field field;

	public String textField;
	public String idField;
	public String idFieldClass;
	public EzuiElement element;

	public DataOption dataOption;
	public String dataOptions;

	public Element(Field field, EzuiElement element) {
		this.element = element;
		this.field = field;
		title = element.title();
		fieldName = field.getName();
		typeClassName = field.getType().getSimpleName();
		elementType = element.elementType().name();
		isHidden = element.isHidden();
		defaultValue = element.defaultValue();
		formatString = element.formatString();
		if (StringUtils.isNotBlank(formatString)) {
			location = field.getDeclaringClass().getName() + "." + fieldName;
			JSONObject json = JSON.parseObject(formatString);
			Set<String> value = json.keySet();
			for (String eachValue : value) {
				json.getString(eachValue);
				TextUtil.addSysDict(location, eachValue, json.getString(eachValue));
			}
		}
		isId = ReflectUtil.isFieldAnnotated(field, Id.class);
		if (StringUtils.isNoneBlank(element.idField(), element.textField())) {
			idField = element.idField();
			textField = element.textField();
		}
	}

	public Element(Field field) {
		this.field = field;
		title = field.getName();
		fieldName = field.getName();
		typeClassName = field.getType().getSimpleName();
		isId = ReflectUtil.isFieldAnnotated(field, Id.class);
	}

	public void deepInit(EzuiHolder ezuiHolder) {
		autoSetInsideEzuiHolder();
		autoSetElementType();
		autoSetDataOption();

		autoSetManyToOne(ezuiHolder);
		autoSetOneToMany(ezuiHolder);
		autoSetManyToMany(ezuiHolder);
		autoSetOneToOne(ezuiHolder);

	}

	private void autoSetDataOption() {
		if (ReflectUtil.isFieldAnnotated(field, Column.class)) {
			dataOption = new DataOption(field);
			if (element != null) {
				dataOption.init(element);
			}
			dataOptions = dataOption.generateDataOptionString();
			if (elementType.equals("combobox")) {
				if (StringUtils.isBlank(dataOptions)) {
					dataOptions = "data:ez.loadSysDict('" + location + "'),editable:false";
				} else {
					dataOptions = "data:ez.loadSysDict('" + location + "')," + dataOptions;
				}
			}
		}
	}

	private void autoSetInsideEzuiHolder() {
		if (Collection.class.isAssignableFrom(field.getType())) {
			insideEzuiHolder = Scanner.getEzuiHolderByClass(GenericsUtils.getCollectionFieldGenricTypeClass(field, Scanner.getUrlClassLoader()));
		} else {
			insideEzuiHolder = Scanner.getEzuiHolderByClass(field.getType());
		}
	}

	private void autoSetManyToMany(EzuiHolder ezuiHolder) {
		if (elementType.equals(EzuiElementType.tree.name())) {
			if (ReflectUtil.isFieldAnnotated(field, ManyToMany.class)) {
				treeInit(ezuiHolder);
			}
		}
		if (elementType.equals(EzuiElementType.treegrid.name())) {
			if (ReflectUtil.isFieldAnnotated(field, ManyToMany.class)) {
				treegridInit(ezuiHolder);
			}
		}
	}

	private void treegridInit(EzuiHolder ezuiHolder) {
		Class relatedClass = GenericsUtils.getCollectionFieldGenricTypeClass(field, Scanner.getUrlClassLoader());
		TreeGrid treeGrid = new TreeGrid();
		try {
			if (ezuiHolder.clazz.getDeclaredField("status") != null) {
				treeGrid.haveStatus = true;
			}
		} catch (NoSuchFieldException e) {
		} catch (SecurityException e) {
		}
		treeGrid.relateEzuiHolder = Scanner.getEzuiHolderByClass(relatedClass);
		treeGrid.relateEzuiHolder.javas.action.noSy_find = true;
		if (element != null && StringUtils.isNoneBlank(element.idField())) {
			treeGrid.idField = element.idField();
		} else {
			treeGrid.idField = treeGrid.relateEzuiHolder.idField.getName();
		}
		if (element != null && StringUtils.isNoneBlank(element.textField())) {
			treeGrid.treeField = element.textField();
		} else {
			treeGrid.treeField = treeGrid.relateEzuiHolder.idField.getName();
		}
		if (element != null && StringUtils.isNotBlank(element.parentField())) {
			treeGrid.parentField = element.parentField();
		} else {
			treeGrid.parentField = "pid";
		}
		if (element != null && StringUtils.isNotBlank(element.title())) {
			treeGrid.title = element.title();
		} else {
			treeGrid.title = fieldName;
		}
		if (element != null && StringUtils.isNotBlank(element.buttonText())) {
			treeGrid.buttonText = element.buttonText();
		} else {
			treeGrid.buttonText = fieldName;
		}
		if (element != null && StringUtils.isNotBlank(element.relatedCollectionName())) {
			treeGrid.relatedCollectionName = element.relatedCollectionName();
		} else {
			Field[] fields = ezuiHolder.clazz.getDeclaredFields();
			for (Field eachField : fields) {
				if (Collection.class.isAssignableFrom(eachField.getType())) {
					if (GenericsUtils.getCollectionFieldGenricTypeClass(eachField, Scanner.getUrlClassLoader()).equals(relatedClass)) {
						treeGrid.relatedCollectionName = eachField.getName();
					}
				}
			}
		}
		treeGrid.actionMethodName = "relate" + ezuiHolder.clazz.getSimpleName() + relatedClass.getSimpleName();
		Relate r = new Relate(ezuiHolder.clazz, relatedClass);
		r.actionName = treeGrid.actionMethodName;
		r.relatedCollectionName = treeGrid.relatedCollectionName;
		ezuiHolder.javas.relates.add(r);

		treeGrid.webappURL = "pages/" + ezuiHolder.pages.pagesPackage.replace("\\", "/") + "/" + StringUtils.uncapitalize(ezuiHolder.clazz.getSimpleName()) + "Relate" + relatedClass.getSimpleName() + "Treegrid.jsp";
		treeGrid.file = new File(ezuiHolder.pages.webappDir + "pages\\" + ezuiHolder.pages.pagesPackage + "\\" + StringUtils.uncapitalize(ezuiHolder.clazz.getSimpleName()) + "Relate" + relatedClass.getSimpleName() + "Treegrid.jsp");
		treeGrid.relatedClassSimpleName = relatedClass.getSimpleName();
		ezuiHolder.pages.treegrids.add(treeGrid);
	}

	private void treeInit(EzuiHolder ezuiHolder) {
		Class relatedClass = GenericsUtils.getCollectionFieldGenricTypeClass(field, Scanner.getUrlClassLoader());
		Tree tree = new Tree();
		try {
			if (ezuiHolder.clazz.getDeclaredField("status") != null) {
				tree.haveStatus = true;
			}
		} catch (NoSuchFieldException e) {
		} catch (SecurityException e) {
		}
		tree.relateEzuiHolder = Scanner.getEzuiHolderByClass(relatedClass);
		tree.relateEzuiHolder.javas.action.noSy_find = true;
		if (element != null && StringUtils.isNoneBlank(element.textField())) {
			tree.treeField = element.textField();
		} else {
			tree.treeField = tree.relateEzuiHolder.idField.getName();
		}
		if (element != null && StringUtils.isNoneBlank(element.idField())) {
			tree.idField = element.idField();
		} else {
			tree.idField = tree.relateEzuiHolder.idField.getName();
		}
		if (element != null && StringUtils.isNotBlank(element.parentField())) {
			tree.parentField = element.parentField();
		} else {
			tree.parentField = "pid";
		}
		if (element != null && StringUtils.isNotBlank(element.title())) {
			tree.title = element.title();
		} else {
			tree.title = fieldName;
		}
		if (element != null && StringUtils.isNotBlank(element.buttonText())) {
			tree.buttonText = element.buttonText();
		} else {
			tree.buttonText = fieldName;
		}
		if (element != null && StringUtils.isNotBlank(element.relatedCollectionName())) {
			tree.relatedCollectionName = element.relatedCollectionName();
		} else {
			Field[] fields = ezuiHolder.clazz.getDeclaredFields();
			for (Field eachField : fields) {
				if (Collection.class.isAssignableFrom(eachField.getType())) {
					if (GenericsUtils.getCollectionFieldGenricTypeClass(eachField, Scanner.getUrlClassLoader()).equals(relatedClass)) {
						tree.relatedCollectionName = eachField.getName();
					}
				}
			}
		}
		tree.actionMethodName = "relate" + ezuiHolder.clazz.getSimpleName() + relatedClass.getSimpleName();
		Relate r = new Relate(ezuiHolder.clazz, relatedClass);
		r.actionName = tree.actionMethodName;
		r.relatedCollectionName = tree.relatedCollectionName;
		ezuiHolder.javas.relates.add(r);

		tree.webappURL = "pages/" + ezuiHolder.pages.pagesPackage.replace("\\", "/") + "/" + StringUtils.uncapitalize(ezuiHolder.clazz.getSimpleName()) + "Relate" + relatedClass.getSimpleName() + "Tree.jsp";
		tree.file = new File(ezuiHolder.pages.webappDir + "pages\\" + ezuiHolder.pages.pagesPackage + "\\" + StringUtils.uncapitalize(ezuiHolder.clazz.getSimpleName()) + "Relate" + relatedClass.getSimpleName() + "Tree.jsp");
		tree.relatedClassSimpleName = relatedClass.getSimpleName();
		ezuiHolder.pages.trees.add(tree);
	}

	private void autoSetElementType() {
		if (element == null || element.elementType() == EzuiElementType.Default) {
			if (field.getType().equals(String.class)) {
				elementType = EzuiElementType.textbox.name();
				if (element != null && StringUtils.isNotBlank(element.formatString())) {
					elementType = EzuiElementType.combobox.name();
				}
			}
			if (field.getType().equals(Integer.class)) {
				elementType = EzuiElementType.numberbox.name();
			}
			if (field.getType().equals(Date.class)) {
				Temporal temporal = ReflectUtil.getAnnotation(field, Temporal.class);
				if (temporal != null) {
					if (temporal.value().equals(TemporalType.DATE)) {
						elementType = EzuiElementType.datebox.name();
					} else if (temporal.value().equals(TemporalType.TIME)) {
						elementType = EzuiElementType.timespinner.name();
					} else {
						elementType = EzuiElementType.datetimebox.name();
					}
				} else {
					elementType = EzuiElementType.datetimebox.name();
				}
			}
			if (Collection.class.isAssignableFrom(field.getType())) {
				if (ReflectUtil.isFieldAnnotated(field, ManyToMany.class)) {
					elementType = EzuiElementType.tree.name();
				}
				if (ReflectUtil.isFieldAnnotated(field, OneToMany.class)) {
					elementType = EzuiElementType.tree.name();
				}
			}
			if (ReflectUtil.isFieldAnnotated(field, ManyToOne.class) && insideEzuiHolder != null) {
				if (insideEzuiHolder.pages.dataGrid != null) {
					elementType = EzuiElementType.combogrid.name();
				}
			}
			if (ReflectUtil.isFieldAnnotated(field, OneToOne.class) && insideEzuiHolder != null) {
				if (insideEzuiHolder.pages.dataGrid != null) {
					elementType = EzuiElementType.combogrid.name();
				}
			}
		}
	}

	private void autoSetOneToMany(EzuiHolder ezuiHolder) {
		if (ReflectUtil.isFieldAnnotated(field, OneToMany.class)) {
			if (elementType.equals(EzuiElementType.tree.name())) {
				treeInit(ezuiHolder);
			}
			if (elementType.equals(EzuiElementType.treegrid.name())) {
				treegridInit(ezuiHolder);
			}
		}
	}

	private void combogridInit(EzuiHolder ezuiHolder) {
		if (elementType.equals(EzuiElementType.combogrid.name())) {
			idFieldClass = ezuiHolder.idField.getType().getSimpleName();
			if (idField != null && textField != null) {
			} else if (insideEzuiHolder.idField != null) {
				idField = insideEzuiHolder.idField.getName();
				textField = insideEzuiHolder.idField.getName();
			} else {
				idField = "manualSet";
				textField = "manualSet";
			}
			insideEzuiHolder.javas.action.noSy_find = true;
			for (int i = 0; i < insideEzuiHolder.pages.dataGrid.showElements.size(); i++) {
				Element e = insideEzuiHolder.pages.dataGrid.showElements.get(i);
				if (e.field.equals(insideEzuiHolder.idField)) {
					// if (e.dataOption.required == false) {
					ezuiHolder.javas.action.beforeSaveIdnullables.add("if(null==data.get" + StringUtils.capitalize(fieldName) + "().get" + StringUtils.capitalize(e.fieldName) + "()){data.set" + StringUtils.capitalize(fieldName) + "(null);}");
					// }
				}
			}
		}
	}

	private void autoSetOneToOne(EzuiHolder ezuiHolder) {
		if (ReflectUtil.isFieldAnnotated(field, OneToOne.class)) {
			combogridInit(ezuiHolder);
		}
	}

	private void autoSetManyToOne(EzuiHolder ezuiHolder) {
		if (ReflectUtil.isFieldAnnotated(field, ManyToOne.class)) {
			combogridInit(ezuiHolder);
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

	public Boolean getIsHidden() {
		return isHidden;
	}

	public void setIsHidden(Boolean isHidden) {
		this.isHidden = isHidden;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public Boolean getIsId() {
		return isId;
	}

	public void setIsId(Boolean isId) {
		this.isId = isId;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getFormatString() {
		return formatString;
	}

	public void setFormatString(String formatString) {
		this.formatString = formatString;
	}

	public EzuiHolder getInsideEzuiHolder() {
		return insideEzuiHolder;
	}

	public void setInsideEzuiHolder(EzuiHolder insideEzuiHolder) {
		this.insideEzuiHolder = insideEzuiHolder;
	}

	public String getTextField() {
		return textField;
	}

	public void setTextField(String textField) {
		this.textField = textField;
	}

	public String getIdField() {
		return idField;
	}

	public void setIdField(String idField) {
		this.idField = idField;
	}

	public String getIdFieldClass() {
		return idFieldClass;
	}

	public void setIdFieldClass(String idFieldClass) {
		this.idFieldClass = idFieldClass;
	}

	public DataOption getDataOption() {
		return dataOption;
	}

	public void setDataOption(DataOption dataOption) {
		this.dataOption = dataOption;
	}

	public String getDataOptions() {
		return dataOptions;
	}

	public void setDataOptions(String dataOptions) {
		this.dataOptions = dataOptions;
	}

}
