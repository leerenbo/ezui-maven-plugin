<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String id = request.getParameter("id");
	if (id == null) {
		id = "";
	}
%>
<jsp:include page="${ezuiHolder.pages.form.fileName}"/>
<!DOCTYPE html>
<html>
<head>
<title></title>
<script type="text/javascript">
	var submitForm = function($dialog, $grid, $pjq) {
		var nodes = $('#treegrid').treegrid('getChecked', [ 'checked', 'indeterminate' ]);
		var ids = [];
		for (var i = 0; i < nodes.length; i++) {
			ids.push(nodes[i].id);
		}
		$.post(ez.contextPath + '/${ezuiHolder.javas.action.beanName}!${treegrid.actionMethodName}.action', {
			'data.id' : $(':input[name="data.id"]').val(),
			ids : ids.join(',')
		}, function(result) {
			if (result.success) {
				$dialog.dialog('destroy');
			} else {
				$pjq.messager.alert('提示', result.msg, 'error');
			}
			$pjq.messager.alert('提示', result.msg, 'info');
		}, 'json');
	};
	$(function() {
		parent.$.messager.progress({
			text : '数据加载中....'
		});
		$('#treegrid').treegrid({
			url : ez.contextPath + '/${treegrid.relateEzuiHolder.javas.action.beanName}!noSy_find.action<#if treegrid.haveStatus>?hqland_status_dengyu_String=1</#if>',
			idField : '${treegrid.idField!"id"}',
			treeField : '${treegrid.treeField!"name"}',
			parentField : '${treegrid.parentField!"pid"}',
			checkOnSelect : true,
			selectOnCheck : true,
			checkbox : true,
			rownumbers : true,
			singleSelect : false,
			pagination : false,
			frozenColumns : [ [ {
				field : 'ck',
				checkbox : true
			} ] ],
		    columns:[[
			<#list treegrid.relateEzuiHolder.pages.dataGrid.showElements as show>
			<#if show.isHidden>
			<#elseif show.insideEzuiHolder??&&show.elementType=="combogrid">
				<#if show.insideEzuiHolder.pages.dataGrid??>
					<#list show.insideEzuiHolder.pages.dataGrid.showElements as insideEzuiHolderColumnsElementsShow>
						<#if insideEzuiHolderColumnsElementsShow.isHidden>
						<#elseif insideEzuiHolderColumnsElementsShow.insideEzuiHolder??>
						<#else>
						{
							width : '200',
							title : '${insideEzuiHolderColumnsElementsShow.title}',
							field : '${show.fieldName}.${insideEzuiHolderColumnsElementsShow.fieldName}',
							<#if insideEzuiHolderColumnsElementsShow.location??>
							formatter : function(value,row){
								return ez.columnsFomatter(value,row,'${insideEzuiHolderColumnsElementsShow.location}');
							}
							<#else>
							formatter : function(value,row){
								return row.${show.fieldName}.${insideEzuiHolderColumnsElementsShow.fieldName};
							}
							</#if>
						},
						</#if>
					</#list>
				</#if>
			<#elseif show.elementType=="tree">
			<#elseif show.elementType=="treegrid">
			<#else>
			{
				width : '200',
				title : '${show.title}',
				field : '${show.fieldName}',
				<#if show.location??>
				formatter : function(value,row){
					return ez.columnsFomatter(value,row,'${show.location}');
				}
				</#if>
			},
			</#if>
			</#list>
		    ]],
			onLoadSuccess : function(node, data) {
				$.post(ez.contextPath + '/${ezuiHolder.javas.action.beanName}!noSy_getRelated${treegrid.relatedClassSimpleName}.action', {
					'data.id' : $(':input[name="data.id"]').val()
				}, function(result) {
					if (result) {
						for (var i = 0; i < result.length; i++) {
						$('#treegrid').datagrid('checkRow', result[i].${treegrid.idField!"id"});
						}
					}
					parent.$.messager.progress('close');
				}, 'json');
			}
		});
	});
</script>
</head>
<body>
	<input name="data.id" value="<%=id%>" readonly="readonly" type="hidden" />
	<fieldset>
		<legend>关联${treegrid.relateEzuiHolder.pages.moduleName!""}</legend>
		<ul id="treegrid"></ul>
	</fieldset>
</body>
</html>