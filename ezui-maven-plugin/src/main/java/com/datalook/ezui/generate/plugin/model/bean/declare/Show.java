package com.datalook.ezui.generate.plugin.model.bean.declare;

import java.lang.reflect.Field;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.datalook.ezui.annotation.declare.EzuiShow;
import com.datalook.ezui.generate.plugin.util.TextUtil;

public class Show {

	public String title;
	public String fieldName;

	public String typeFullClassName;
	public String formatString;

	public Show(Field field, EzuiShow ezuiShow) {
		title = ezuiShow.title();
		fieldName = field.getName();
		formatString = ezuiShow.formatString();
		
		if(StringUtils.isNotBlank(formatString)){
			typeFullClassName = field.getClass().getName();
			JSONObject json=JSON.parseObject(formatString);
			Set<String> value=json.keySet();
			for (String eachValue : value) {
				json.getString(eachValue);
				TextUtil.addSql("insert into SYS_DICT(location,value,text) values('"+typeFullClassName+"','"+eachValue+"','"+json.getString(eachValue)+"');");
			}
		}
	}

	public Show(Field field) {
		title = field.getName();
		fieldName = field.getName();
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

	public String getTypeFullClassName() {
		return typeFullClassName;
	}

	public void setTypeFullClassName(String typeFullClassName) {
		this.typeFullClassName = typeFullClassName;
	}

	public String getFormatString() {
		return formatString;
	}

	public void setFormatString(String formatString) {
		this.formatString = formatString;
	}

	
}
