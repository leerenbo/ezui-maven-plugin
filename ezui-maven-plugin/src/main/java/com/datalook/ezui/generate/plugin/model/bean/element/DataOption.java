package com.datalook.ezui.generate.plugin.model.bean.element;

import java.lang.reflect.Field;

import javax.persistence.Column;

import org.apache.commons.lang3.StringUtils;

import com.datalook.ezui.annotation.element.EzuiDataOptions;
import com.datalook.ezui.annotation.element.EzuiDataOptions.EzuiValid;
import com.datalook.ezui.annotation.element.EzuiElement;
import com.datalook.ezui.generate.plugin.util.ReflectUtil;

public class DataOption {

	public Boolean required = false;
	public String missingMessage = "该输入项为必输项";

	public String validdates;
	public String invalidMessage = "";
	public String url = "";
	// public Boolean unique;

	public String prompt;
	// public String textboxType = "text";
	public int length;
	public Boolean editable;

	public DataOption(Field field) {
		Column c = ReflectUtil.getAnnotation(field, Column.class);
		if (c.nullable())
			required = false;
		length = c.length() / 2;
		// unique = c.unique();
	}

	public void init(EzuiElement element) {
		EzuiDataOptions eds = element.dataOptions();
		required = required || eds.required();
		// textboxType = eds.textBoxType().name();
		// if (eds.textBoxType() == TextboxType.Default) {
		// textboxType = null;
		// }
		editable = eds.editable();
		switch (element.elementType()) {
		case datebox:
		case datetimebox:
		case timespinner:
			editable = false;
			break;
		}
		prompt = eds.prompt();
		invalidMessage = eds.invalidMessage();
		missingMessage = eds.missingMessage();
		StringBuffer sb = new StringBuffer();
		sb.append("validType:{");
		EzuiValid[] vs = eds.validTypes();

		boolean lengthlimit = false;
		for (EzuiValid valid : vs) {
			switch (valid.type()) {
			case email:
			case url:
			case Char:
			case CharAndChinese:
			case CharAndChineseAndNumber:
			case CharAndNumber:
			case CharBig:
			case CharSmall:
			case Chinese:
			case ChineseAndNumber:
				sb.append(valid.type().name());
				sb.append(":[],");
				break;
			case length:
				lengthlimit = true;
				sb.append("length:[");
				sb.append(valid.params()[0]);
				sb.append(",");
				sb.append(valid.params()[1]);
				sb.append("],");
				break;
			case regexp:
			case remote:
				sb.append(valid.type().name());
				sb.append(":['");
				sb.append(valid.params()[0]);
				sb.append("','");
				sb.append(valid.params()[1]);
				sb.append("'],");
				break;
			}
		}
		if (!lengthlimit) {
			switch (element.elementType()) {
			case textbox:
				sb.append("length:[0," + length + "],");
				break;
			case numberbox:
				break;
			}
		}
		if (sb.length() > 11) {
			validdates = (String) sb.subSequence(0, sb.length() - 1) + "}";
		}
	}

	public String generateDataOptionString() {
		StringBuffer sb = new StringBuffer();
		if (required)
			sb.append("required:true,");
		if (!editable)
			sb.append("editable:false,");
		if (StringUtils.isNotBlank(missingMessage))
			sb.append("missingMessage:'" + missingMessage + "',");
		if (StringUtils.isNotBlank(invalidMessage))
			sb.append("invalidMessage:'" + invalidMessage + "',");
		if (StringUtils.isNotBlank(prompt))
			sb.append("prompt:'" + prompt + "',");
		if (StringUtils.isNotBlank(url))
			sb.append("url:'" + url + "',");
		if (StringUtils.isNotBlank(validdates))
			sb.append(validdates + ",");
		return (String) sb.subSequence(0, sb.length() - 1);
	}
}
