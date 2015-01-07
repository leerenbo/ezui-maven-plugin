<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<jsp:include page="${pages.incPath}inc.jsp"></jsp:include>
<script type="text/javascript">
	var submitForm = function($dialog, $grid, $pjq) {
			$('#uploadForm').form('submit',{
				url : ez.contextPath + '/${javas.action.beanName}!uploadExcel.action',
			    onSubmit: function(){
			        return true;
			    },
			    success:function(result){
					result = $.parseJSON(result);
					$pjq.messager.alert('提示', result.msg, 'info');
					$grid.datagrid('load');
					$dialog.dialog('destroy');
			    }
			});
		}
</script>
</head>
<body>	
	<div>
	<form id="uploadForm" method="post" enctype="multipart/form-data">
	
		<input class="easyui-filebox" name="uploadExcelFile" data-options="prompt:'选择excel文件',buttonText: '选择文件',editable:false" style="width:400px">
        
	</form>
	</div>
</body>
</html>