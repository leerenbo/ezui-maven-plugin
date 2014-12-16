package com.datalook.ezui.generate.plugin.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;

public class ReflectUtil {

	@SuppressWarnings("unchecked")
	public static boolean isFieldAnnotated(Field field, Class annotation) {
		Object o = field.getAnnotation(annotation);
		if (o != null) {
			return true;
		}
		Method m = MethodUtils.getAccessibleMethod(field.getDeclaringClass(), "get" + StringUtils.capitalize(field.getName()));
		if (m != null) {
			o = m.getAnnotation(annotation);
			if (o != null) {
				return true;
			}
		}
		return false;
	}

	public static <T extends Annotation> T getAnnotation(Field field, Class<T> annotation) {
		T t = null;
		t = field.getAnnotation(annotation);
		if (t != null) {
			return t;
		}
		Method m = MethodUtils.getAccessibleMethod(field.getDeclaringClass(), "get" + StringUtils.capitalize(field.getName()));
		if (m != null) {
			t = m.getAnnotation(annotation);
			if (t != null) {
				return t;
			}
		}
		return null;
	}
}
