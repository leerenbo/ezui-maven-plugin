package com.datalook.ezui.generate.plugin.model.bean.page;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.datalook.ezui.annotation.declare.EzuiTransparent;
import com.datalook.ezui.annotation.element.EzuiElement.EzuiElementType;
import com.datalook.ezui.annotation.page.EzuiDataGrid;
import com.datalook.ezui.generate.plugin.model.bean.element.Element;
import com.datalook.ezui.generate.plugin.model.bean.element.ElementFactory;

public class DataGrid {
	// ${pages.dataGrid.}
	public File file;
	public String webappURL = "待写";

	public List<Element> conditionElements = new ArrayList<Element>();
	public List<Element> showElements = new ArrayList<Element>();

	public String moduleName;

	public boolean saveabel;
	public boolean getByIdable;
	public boolean updateable;
	public boolean deleteable;
	public boolean downloadExcelable;
	public String deleteMethod = "deletePhysical";

	public String sqlId;
	public boolean uploadExcelable;
	public String uploadExcelURL;
	public File uploadExcelFile;
	public void init(Class clazz) {
		if (deleteable) {
			try {
				if (clazz.getDeclaredField("status") != null) {
					deleteMethod = "deleteByStatus";
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

			Element show = ElementFactory.instance(field, ElementFactory.SHOW);
			if (show != null) {
				showElements.add(show);
			}
		}
	}

	public DataGrid(EzuiDataGrid ezuiDataGrid) {
		moduleName = ezuiDataGrid.moduleName();
		saveabel = ezuiDataGrid.saveabel();
		getByIdable = ezuiDataGrid.getByIdable();
		updateable = ezuiDataGrid.updateable();
		deleteable = ezuiDataGrid.deleteable();
		downloadExcelable = ezuiDataGrid.downloadExcelable();
		uploadExcelable = ezuiDataGrid.uploadExcelable();
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

	public List<Element> getShowElements() {
		return showElements;
	}

	public void setShowElements(List<Element> showElements) {
		this.showElements = showElements;
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

	public boolean isDownloadExcelable() {
		return downloadExcelable;
	}

	public void setDownloadExcelable(boolean downloadExcelable) {
		this.downloadExcelable = downloadExcelable;
	}

	public boolean isUploadExcelable() {
		return uploadExcelable;
	}

	public void setUploadExcelable(boolean uploadExcelable) {
		this.uploadExcelable = uploadExcelable;
	}

	public String getUploadExcelURL() {
		return uploadExcelURL;
	}

	public void setUploadExcelURL(String uploadExcelURL) {
		this.uploadExcelURL = uploadExcelURL;
	}

}
