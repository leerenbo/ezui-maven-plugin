<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String id = request.getParameter("id");
	if (id == null) {
		id = "";
	}
%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<jsp:include page="${pages.incPath}inc.jsp"></jsp:include>
<script type="text/javascript">
	var submitForm = function($dialog, $grid, $pjq) {
		if ($('form').form('validate')) {
			var url;
			if ($(':input[name="data.id"]').val().length > 0) {
				url = ez.contextPath + '/${javas.action.beanName}!update.action';
			} else {
				url = ez.contextPath + '/${javas.action.beanName}!save.action';
			}
			$('#ezAddAndUpdataform').form('submit', {
				url : url,
				onSubmit : function() {
					// do some check
					// return false to prevent submit;
					return true;
				},
				success : function(result) {
					result = $.parseJSON(result);
					$pjq.messager.alert('提示', result.msg, 'info');
					$grid.datagrid('load');
					$dialog.dialog('destroy');
				}
			});
		}
	};
	$(function() {
		if ($(':input[name="data.id"]').val().length > 0) {
			parent.$.messager.progress({
				text : '数据加载中....'
			});

			$.post(ez.contextPath + '/${javas.action.beanName}!getById.action', {
				'data.id' : $(':input[name="data.id"]').val()
			}, function(result) {
				if (result.id != undefined) {
					var data = ez.formLoadConvert(result);
					if (result.id != undefined) {
						$('#ezAddAndUpdataform').form('load', data);
					}
				}
				parent.$.messager.progress('close');
			}, 'json');
		}
	});
</script>
</head>
<body>
	<form id="ezAddAndUpdataform" method="post" enctype="multipart/form-data" class="form">
		<fieldset>
			<legend>${pages.moduleName}信息</legend>
			<table class="table" style="width: 100%;">
			
				<tr>
	<#assign x = 0>
		<#list pages.form.formElements as element>
			<#if element.isId>
				<input name="data.${element.fieldName}" type="hidden" value="<%=id%>" />
			<#elseif element.isHidden>
				<input name="data.${element.fieldName}" type="hidden" value="${element.defaultValue!""}"/>
			<#elseif element.elementType=="tree">
			<#elseif element.elementType=="treegrid">
				
			<#else>
				<#if x%2==0&&x!=0>
				</tr>
				<tr>
				</#if>
	<#assign x = x+1>
				<th>${element.title}</th>
				<td>
				<#if element.elementType=="combogrid">
				    <input name="data.${element.fieldName}.${element.idField}" class="easyui-combogrid" data-options="
				            panelWidth: 500,
				            idField: '${element.idField}',
				            textField: '${element.textField}',
				            url: ez.contextPath + '/${element.insideEzuiHolder.javas.action.beanName}!noSy_find.action',
				            method: 'get',
				            editable: false,
				            columns: [[
				            	<#list element.insideEzuiHolder.pages.dataGrid.showElements as insideShow>
				            	<#if insideShow.isHidden>
				            	<#else>			
				            	{field:'${insideShow.fieldName}',title:'${insideShow.title}',width:150},
				            	</#if>
				            	</#list>
				            ]],
				            fitColumns: true
				        "/>
				<#else>
					<input name="data.${element.fieldName}" class="easyui-${element.elementType}" data-options="${element.dataOptions!""}"/>
				</#if>
				</td>
			</#if>
		</#list>
				</tr>
			</table>
		</fieldset>
	</form>
</body>
</html>