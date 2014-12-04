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
			url : ez.contextPath + '/${javas.action.beanName}?id=' + row.id
		});
	}

	var openUpdate = function(row) {
		var dialog = parent.ez.modalDialog({
			title : '编辑${pages.dataGrid.moduleName}'',
			url : ez.contextPath + '/${javas.action.beanName}?id=' + row.id,
			buttons : [ {
				text : '编辑',
				handler : function() {
					dialog.find('iframe').get(0).contentWindow.submitForm(dialog, ezgrid, parent.$);
				}
			} ]
		});
	}

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
			<#list pages.dataGrid.showFields as show>
			{
				width : '200',
				title : '${show.title}',
				field : '${show.fieldName}',
				<#if show.typeFullClassName??>
				formatter : function(value,row){
					return ez.columnsFomatter(value,row,'${show.typeFullClassName}');
				}
				</#if>
			},
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
				${condition.title}
				<input name="data.${condition.fieldName}" class="easyui-${condition.elementType}" data-options="" style="width: 80px;"/>
				</#list>
				<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-zoom',plain:true" onclick="ezgrid.datagrid('load',ez.serializeObject($('#searchForm')));">查询</a>
				<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-zoom_out',plain:true" onclick="$('#searchForm input.easyui-textbox').textbox('setValue','');ezgrid.datagrid('load',{});">清空查询</a>
				</#if>
			</form>
		</div>
		<div>
			<#if pages.dataGrid.saveabel>
			<s:if test="@com.datalook.util.base.SecurityUtil@havePermission('${javas.action.beanName}!save')">
				<a onclick="save();" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-note_add',plain:true">添加</a>
			</s:if>
			</#if>
			
			<#if pages.dataGrid.getByIdable>
			<s:if test="@com.datalook.util.base.SecurityUtil@havePermission('${javas.action.beanName}!getById')">
				<a onclick="ezgrid.datagrid('ezCheckedInvoke','openGetById');" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-note',plain:true">查看</a>
			</s:if>
			</#if>
			
			
			<#if pages.dataGrid.updateable>
			<s:if test="@com.datalook.util.base.SecurityUtil@havePermission('${javas.action.beanName}!update')">
				<a onclick="ezgrid.datagrid('ezCheckedInvoke','openUpdate');" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-note_edit',plain:true">编辑</a>
			</s:if>
			</#if>
			
			<#if pages.dataGrid.deleteable>
			<s:if test="@com.datalook.util.base.SecurityUtil@havePermission('${javas.action.beanName}!${pages.dataGrid.deleteMethod}')">
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