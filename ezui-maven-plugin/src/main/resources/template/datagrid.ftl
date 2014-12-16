<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<%@ include file="${pages.incPath}inc.jsp"%>
<script type="text/javascript">
	var ezgrid;


	var save = function() {
		var dialog = parent.ez.modalDialog({
			title : '添加${pages.dataGrid.moduleName}',
			url : ez.contextPath + '/${pages.form.webappURL}',
			buttons : [ {
				text : '添加',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.submitForm(dialog, ezgrid, parent.$);
				}
			} ]
		});
	};

	var openGetById = function(row) {
		var dialog = parent.ez.modalDialog({
			title : '查看${pages.dataGrid.moduleName}',
			url : ez.contextPath + '/${pages.form.webappURL}?id=' + row.id
		});
	}

	var openUpdate = function(row) {
		var dialog = parent.ez.modalDialog({
			title : '编辑${pages.dataGrid.moduleName}',
			url : ez.contextPath + '/${pages.form.webappURL}?id=' + row.id,
			buttons : [ {
				text : '编辑',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.submitForm(dialog, ezgrid, parent.$);
				}
			} ]
		});
	}

			<#if (pages.trees?size>0)>
				<#list pages.trees as tree>
	<s:if test="@com.datalook.util.base.SecurityUtil@havePermission('/${javas.action.beanName}!${tree.actionMethodName}')">
	var open${tree.actionMethodName?cap_first} = function(row) {
		var dialog = parent.ez.modalDialog({
			title : '${tree.title}',
			url : ez.contextPath + '/${tree.webappURL}?id=' + row.id,
			buttons : [ {
				text : '${tree.buttonText}',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.submitForm(dialog, ezgrid, parent.$);
				}
			} ]
		});
	}
	</s:if>
				</#list>				
			</#if>

			<#if (pages.treegrids?size>0)>
				<#list pages.treegrids as treegrid>
	<s:if test="@com.datalook.util.base.SecurityUtil@havePermission('/${javas.action.beanName}!${treegrid.actionMethodName}')">
	var open${treegrid.actionMethodName?cap_first} = function(row) {
		var dialog = parent.ez.modalDialog({
			title : '${treegrid.title}',
			url : ez.contextPath + '/${treegrid.webappURL}?id=' + row.id,
			buttons : [ {
				text : '${treegrid.buttonText}',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.submitForm(dialog, ezgrid, parent.$);
				}
			} ]
		});
	}
	</s:if>
				</#list>				
			</#if>

	var ${pages.dataGrid.deleteMethod} = function() {
		var rows = ezgrid.datagrid('getChecked');
		parent.$.messager.confirm('询问', '您确定要删除记录？', function(r) {
			if (r) {
				var ids = [];
				for (var i = 0; i < rows.length; i++) {
					ids.push(rows[i].id);
				}
				$.post(ez.contextPath + '/${javas.action.beanName}!${pages.dataGrid.deleteMethod}.action', {
					'ids' : ids.join(',')
				}, function() {
					ezgrid.datagrid('reload');
					ezgrid.datagrid('clearChecked');
				}, 'json');
			}
		});
	};
	var clean = function() {
		$('#searchForm input.easyui-textbox,#searchForm input.easyui-numberbox').textbox('setValue','');
		$('#searchForm input.easyui-datebox').datebox('setValue','');
		$('#searchForm input.easyui-datetimebox').datetimebox('setValue','');
		$('#searchForm input.easyui-combobox').combobox('setValue','');
		$('#searchForm input.easyui-combotree').combotree('setValue','');
		$('#searchForm input.easyui-combogrid').combogrid('setValue','');
		$('#searchForm input.easyui-timespinner').spinner('setValue','');
		ezgrid.datagrid('load',ez.serializeObject($('#searchForm')));
	}
	$(function() {
		ezgrid = $('#ezgrid').datagrid({
			title : '',
			url : ez.contextPath + '/${javas.action.beanName}!datagridByPage.action',
			striped : true,
			rownumbers : true,
			pagination : true,
			checkOnSelect : true,
			selectOnCheck : true,
			singleSelect : false,
			idField : 'id',
			sortName : 'id',
			sortOrder : 'desc',
			pageSize : 50,
			pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
			frozenColumns : [ [ {
				field : 'ck',
				checkbox : true
			}] ],
			columns : [ [ 
			<#list pages.dataGrid.showElements as show>
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
								if(row.${show.fieldName}!=undefined){
									return ez.columnsFomatter(row.${show.fieldName}.${show.insideEzuiHolder.idFieldName},row,'${insideEzuiHolderColumnsElementsShow.location}');
								}
								return "";
							}
							<#else>
							formatter : function(value,row){
								if(row.${show.fieldName} == undefined){
									return "";
								}
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
			] ],
			toolbar : '#eztoolbar',
			onBeforeLoad : function(param) {
				parent.$.messager.progress({
					text : '数据加载中....'
				});
			},
			onLoadSuccess : function(data) {
				$('.iconImg').attr('src', ez.pixel_0);
				parent.$.messager.progress('close');
			}
		});
	});
</script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
	<div id="eztoolbar" style="display: none;">
		<div>
			<form id="searchForm">
				<#if (pages.dataGrid.conditionElements?size>0)>
				<#list pages.dataGrid.conditionElements as condition>
				<#if condition.isHidden>
				<input name="hqland_${condition.fieldName}_dengyu_${condition.typeClassName}" type="hidden" value="${condition.defaultValue!""}"/>
				<#elseif condition.elementType=="combogrid">
				${condition.title}
						<input name="hqland_${condition.fieldName}.${condition.idField}_mohu_${condition.idFieldClass}" class="easyui-combogrid" data-options="
				            panelWidth: 500,
				            idField: '${condition.idField}',
				            textField: '${condition.textField}',
				            url: ez.contextPath + '/${condition.insideEzuiHolder.javas.action.beanName}!noSy_find.action',
				            method: 'get',
				            editable: false,
				            columns: [[
				            	<#list condition.insideEzuiHolder.pages.dataGrid.showElements as insideShow>
				            	<#if insideShow.isHidden>
				            	<#else>			
				            	{field:'${insideShow.fieldName}',title:'${insideShow.title}',width:150},
				            	</#if>
				            	</#list>
				            ]],
				            fitColumns: true
				        " style="width: 80px;"/>
				
				<#elseif condition.elementType=="tree">
				<#elseif condition.elementType=="treegrid">
				<#elseif condition.elementType=="combobox">
				${condition.title}
					<input name="hqland_${condition.fieldName}_mohu_${condition.typeClassName}" class="easyui-${condition.elementType}" data-options="${condition.dataOptions!""}" style="width: 80px;"/>
				<#elseif condition.elementType=="datebox"||condition.elementType=="datetimebox"||condition.elementType=="timespinner">
				从${condition.title}
				<input name="hqland_${condition.fieldName}_dayudengyu_${condition.typeClassName}" class="easyui-${condition.elementType}" data-options="editable:false" style="width: 80px;"/>
				到${condition.title}
				<input name="hqland_${condition.fieldName}_xiaoyudengyu_${condition.typeClassName}" class="easyui-${condition.elementType}" data-options="editable:false" style="width: 80px;"/>
				<#else>
				${condition.title}
				<input name="hqland_${condition.fieldName}_mohu_${condition.typeClassName}" class="easyui-${condition.elementType}" data-options="" style="width: 80px;"/>
				</#if>
				</#list>
				<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-zoom',plain:true" onclick="ezgrid.datagrid('load',ez.serializeObject($('#searchForm')));">查询</a>
				<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-zoom_out',plain:true" onclick="clean();">清空查询</a>
				</#if>
			</form>
		</div>
		<div>
			<#if pages.dataGrid.saveabel>
			<s:if test="@com.datalook.util.base.SecurityUtil@havePermission('/${javas.action.beanName}!save')">
				<a onclick="save();" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-note_add',plain:true">添加</a>
			</s:if>
			</#if>
			
			<#if pages.dataGrid.getByIdable>
			<s:if test="@com.datalook.util.base.SecurityUtil@havePermission('/${javas.action.beanName}!getById')">
				<a onclick="ezgrid.datagrid('ezCheckedInvoke','openGetById');" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-note',plain:true">查看</a>
			</s:if>
			</#if>
			
			<#if pages.dataGrid.updateable>
			<s:if test="@com.datalook.util.base.SecurityUtil@havePermission('/${javas.action.beanName}!update')">
				<a onclick="ezgrid.datagrid('ezCheckedInvoke','openUpdate');" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-note_edit',plain:true">编辑</a>
			</s:if>
			</#if>
			
			<#if (pages.trees?size>0)>
				<#list pages.trees as tree>
			<s:if test="@com.datalook.util.base.SecurityUtil@havePermission('/${javas.action.beanName}!${tree.actionMethodName}')">
				<a onclick="ezgrid.datagrid('ezCheckedInvoke','open${tree.actionMethodName?cap_first}');" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-key',plain:true">${tree.buttonText}</a>
			</s:if>
				</#list>				
			</#if>
			<#if (pages.treegrids?size>0)>
				<#list pages.treegrids as treegrid>
			<s:if test="@com.datalook.util.base.SecurityUtil@havePermission('/${javas.action.beanName}!${treegrid.actionMethodName}')">
				<a onclick="ezgrid.datagrid('ezCheckedInvoke','open${treegrid.actionMethodName?cap_first}');" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-key',plain:true">${treegrid.buttonText}</a>
			</s:if>
				</#list>				
			</#if>
			
			
			<#if pages.dataGrid.deleteable>
			<s:if test="@com.datalook.util.base.SecurityUtil@havePermission('/${javas.action.beanName}!${pages.dataGrid.deleteMethod}')">
				<a onclick="${pages.dataGrid.deleteMethod}();" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-note_delete',plain:true">删除</a>
			</s:if>
			</#if>
			
			
		</div>
	</div>
	<div data-options="region:'center',fit:true,border:false">
		<table id="ezgrid" data-options="fit:true,border:false"></table>
	</div>
</body>
</html>