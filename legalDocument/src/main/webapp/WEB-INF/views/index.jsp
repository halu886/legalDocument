<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>法律文书挖掘</title>
<%@ include file="/WEB-INF/views/include/include.jsp"%>
</head>
<body>

	<div class="container">
		<h1>法律文书挖掘</h1>
		<div id="toolbar">
			<div>
				<form id="searchform">
					<fieldset class="search">
						<input type="text" class="box" name="id" id="input"
							class="inputText" placeholder="查询">
						<button class="getBtn" title="get">通过ID查询</button>
						<button class="scanBtn" title="scan">scan扫描</button>
					</fieldset>
				</form>
			</div>
		</div>
		<table id="table" class="table " data-toolbar="#toolbar"
			data-toggle="table" data-search="true" data-show-columns="true" data-show-export="true"  	data-pagination="true">
			<thead>
				<tr>
					<th data-field="id">id</th>
					<th data-field="columnFamily">column Family</th>
					<th data-field="timeTamp">Timetamp</th>
					<th data-field="title">title</th>
					<th data-field="body">body</th>
				</tr>
			</thead>
		</table>
	</div>
</body>
<script type="text/javascript">

var $table = $('#table'),
	$getButton = $('.getBtn'),
	$scanButton=$('.scanBtn');
	
	$(function() {
		 $getButton.click(function(e){
			 e.preventDefault();
				$.ajax({
				    cache : true,
					type : "POST",
					datetype : "json",
					data : $("#searchform").serializeArray(),
					url : "<%=basePath%>test/get.html",
				success : function(data) {
					var dataJson = eval('(' + data + ')');
					if (dataJson.isDo == true) {
						$table.bootstrapTable('load', [dataJson]);
					}
					if (dataJson.isDo == false) {
						alert(dataJson.errorMsg);
					}

				}
			});
		});
		$scanButton.click(function(e){
			 e.preventDefault();
				$.ajax({
				    cache : true,
					type : "POST",
					datetype : "json",
					url : "<%=basePath%>test/scan.html",
					success : function(data) {
						var dataJson = eval('(' + data + ')');
						if (dataJson.isDo == true) {
							$table.bootstrapTable('load', dataJson.cells);
						}
						if (dataJson.isDo == false) {
							alert(dataJson.errorMsg);
						}

					}
			});
		});
	});
</script>
</html>