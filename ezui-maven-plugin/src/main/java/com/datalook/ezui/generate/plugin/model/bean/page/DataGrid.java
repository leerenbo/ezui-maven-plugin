package com.datalook.ezui.generate.plugin.model.bean.page;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.datalook.ezui.annotation.declare.EzuiTransparent;
import com.datalook.ezui.annotation.page.EzuiDataGrid;
import com.datalook.ezui.generate.plugin.model.bean.declare.Show;
import com.datalook.ezui.generate.plugin.model.bean.declare.ShowFactory;
import com.datalook.ezui.generate.plugin.model.bean.element.Element;
import com.datalook.ezui.generate.plugin.model.bean.element.ElementFactory;

public class DataGrid {
	//${pages.dataGrid.}
	public File file;

	public List<Element> conditionElements = new ArrayList<Element>();
	public List<Show> showFields = new ArrayList<Show>();

	public String moduleName;

	public boolean saveabel;
	public boolean getByIdable;
	public boolean updateable;
	public boolean deleteable;
	public String deleteMethod="deletePhysical";

	public void init(Class clazz) {
		if(deleteable){
			try {
				if(clazz.getDeclaredField("status")!=null){
					deleteMethod="deleteByStatus";
				}
			} catch (NoSuchFieldException e) {
			} catch (SecurityException e) {
			}
		}
		
		Field[] fields = clazz.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			EzuiTransparent ezuiTransparent = field.getAnnotation(EzuiTransparent.class);
			if (ezuiTransparent != null) {
				continue;
			}
			Element eConditon = ElementFactory.instance(field, ElementFactory.CONDITION);
			if (eConditon != null)
				conditionElements.add(eConditon);
			
			Show s = ShowFactory.instance(field);
			if (s != null)
				showFields.add(s);
		}
	}

	public DataGrid(EzuiDataGrid ezuiDataGrid) {
		moduleName = ezuiDataGrid.moduleName();
		saveabel = ezuiDataGrid.saveabel();
		getByIdable = ezuiDataGrid.getByIdable();
		updateable = ezuiDataGrid.updateable();
		deleteable = ezuiDataGrid.deleteable();
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public List<Element> getConditionElements() {
		return conditionElements;
	}

	public void setConditionElements(List<Element> conditionElements) {
		this.conditionElements = conditionElements;
	}

	public List<Show> getShowFields() {
		return showFields;
	}

	public void setShowFields(List<Show> showFields) {
		this.showFields = showFields;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public boolean isSaveabel() {
		return saveabel;
	}

	public void setSaveabel(boolean saveabel) {
		this.saveabel = saveabel;
	}

	public boolean isGetByIdable() {
		return getByIdable;
	}

	public void setGetByIdable(boolean getByIdable) {
		this.getByIdable = getByIdable;
	}

	public boolean isUpdateable() {
		return updateable;
	}

	public void setUpdateable(boolean updateable) {
		this.updateable = updateable;
	}

	public boolean isDeleteable() {
		return deleteable;
	}

	public void setDeleteable(boolean deleteable) {
		this.deleteable = deleteable;
	}

	public String getDeleteMethod() {
		return deleteMethod;
	}

	public void setDeleteMethod(String deleteMethod) {
		this.deleteMethod = deleteMethod;
	}
	
	
}
