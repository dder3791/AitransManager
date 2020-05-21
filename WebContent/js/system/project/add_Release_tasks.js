$(function() {
		radioClick();
	 
		$("#cooperativeState").change(function() {
			var cooperativeState = document.getElementById("cooperativeState").value;
			//alert("协同分类"+cooperativeState);
			//清空译员
		var oSelect = document.getElementById("transferId");
		var oldOptions = oSelect.getElementsByTagName("option");
		for (var k = 0; k < oldOptions.length;) {
			oSelect.removeChild(oldOptions[k]);
		}
		
		var ocheck = document.getElementById("transCheck");
		var oldOptions = ocheck.childNodes;
		//alert("原来的子元素"+oldOptions);
		for (var k = 0; k < oldOptions.length;) {
			ocheck.removeChild(oldOptions[k]);
		}
		
		//清空校对员
		var oSelect = document.getElementById("verifierId");
		var oldOptions = oSelect.getElementsByTagName("option");
		for (var k = 0; k < oldOptions.length;) {
			oSelect.removeChild(oldOptions[k]);
		}
		
		var ocheck = document.getElementById("verifiCheck");
		var oldOptions = ocheck.childNodes;
		alert("原来的子元素"+oldOptions);
		for (var k = 0; k < oldOptions.length;) {
			ocheck.removeChild(oldOptions[k]);
		}
		
		//清空审核员
		
		var oSelect = document.getElementById("auditorId");
		var oldOptions = oSelect.getElementsByTagName("option");
		for (var k = 0; k < oldOptions.length;) {
			oSelect.removeChild(oldOptions[k]);
		}
		
		var ocheck = document.getElementById("auditorCheck");
		var oldOptions = ocheck.childNodes;
		//alert("原来的子元素"+oldOptions);
		for (var k = 0; k < oldOptions.length;) {
			ocheck.removeChild(oldOptions[k]);
		}
		
			if(cooperativeState==0){
				//不协同
				$("#tselect").show();
				$("#vselect").show();
				$("#aselect").show();
				$("#transCheck").hide();
				$("#verifiCheck").hide();
				$("#auditorCheck").hide();
			}if(cooperativeState==1){
				//协同
				$("#tselect").hide();
				$("#vselect").hide();
				$("#aselect").hide();
				$("#transCheck").show();
				$("#verifiCheck").show();
				$("#auditorCheck").show();
			}
		});
		//alert("进入js");
		$("#languagePair").change(function() {
			//alert("进入根据语言查找译员");
			var proid = document.getElementById("languagePair").value;
			var domain = document.getElementById("domain").value;
			$.post("/AitransManager/transfer/findtransIdandName.shtml?transid="+proid+"&domain="+domain,
			function(data) {
				var cooperativeState = document.getElementById("cooperativeState").value;
				//alert(cooperativeState);
				if(cooperativeState==0){
					var oSelect = document.getElementById("transferId");
					var oldOptions = oSelect.getElementsByTagName("option");
					for (var k = 0; k < oldOptions.length;) {
						oSelect.removeChild(oldOptions[k]);
					}
					//  alert("date"+data);
					$("#transferId").append("<option value='88'>待定</option>");
					for (var i = 0; i < data.length; i++) {

						$("#transferId").append("<option value="+data[i].id+">"+ data[i].nickname+ "</option>");
						}
					}
				if(cooperativeState==1){
					//获取原来的
					var ocheck = document.getElementById("transCheck");
					var oldOptions = ocheck.childNodes;
					//alert("原来的子元素"+oldOptions);
					for (var k = 0; k < oldOptions.length;) {
						ocheck.removeChild(oldOptions[k]);
					}
						for (var i = 0; i < data.length; i++) {
								//alert("江南");
							 var oin=document.createElement("input");
				        	 oin.setAttribute("type","checkbox");
				        	 oin.setAttribute("name","transferId");
				        	 oin.setAttribute("id","transferId");
				        	 oin.setAttribute("value",data[i].id);				        	 
				        	 document.getElementById("transCheck").appendChild(oin);
				        	 var myText = document.createTextNode(data[i].nickname);
				        	 document.getElementById("transCheck").appendChild(myText);
						}
					}
				}, "json");
			select1();
			select2();
		});
		 
		$("#domain").change(function() {
			//alert("进入根据语言查找译员");
			var proid = document.getElementById("languagePair").value;
			var domain = document.getElementById("domain").value;
			$.post("/AitransManager/transfer/findtransIdandName.shtml?transid="+proid+"&domain="+domain,
			function(data) {
				var cooperativeState = document.getElementById("cooperativeState").value;
				//alert(cooperativeState);
				if(cooperativeState==0){
					var oSelect = document.getElementById("transferId");
					var oldOptions = oSelect.getElementsByTagName("option");
					for (var k = 0; k < oldOptions.length;) {
						oSelect.removeChild(oldOptions[k]);
					}
					//  alert("date"+data);
					$("#transferId").append("<option value='88'>待定</option>");
					for (var i = 0; i < data.length; i++) {

						$("#transferId").append("<option value="+data[i].id+">"+ data[i].nickname+ "</option>");
						}
					}
				if(cooperativeState==1){
					//获取原来的
					var ocheck = document.getElementById("transCheck");
					var oldOptions = ocheck.childNodes;
					//alert("原来的子元素"+oldOptions);
					for (var k = 0; k < oldOptions.length;) {
						ocheck.removeChild(oldOptions[k]);
					}
						for (var i = 0; i < data.length; i++) {
								//alert("江南");
							 var oin=document.createElement("input");
				        	 oin.setAttribute("type","checkbox");
				        	 oin.setAttribute("name","transferId");
				        	 oin.setAttribute("id","transferId");
				        	 oin.setAttribute("value",data[i].id);				        	 
				        	 document.getElementById("transCheck").appendChild(oin);
				        	 
				        	 var myText = document.createTextNode(data[i].nickname);
				        	 document.getElementById("transCheck").appendChild(myText);
						}
					}
				}, "json");
			select1();
			select2();
		});

		$("#processesTypeId").change(
			function() {
				//alert("进入根据项目的流程显示校对员和审核员");
				var proid = document.getElementById("processesTypeId").value;

				if (proid == 1) {
					$("#verifierdiv").hide();
					$("#auditordiv").hide();
					$("#verifierId").prop({"value" : 89});
					$("#auditorId").prop({"value" : 89});
				}
				if (proid == 2) {
					$("#verifierdiv").show();
					$("#auditordiv").hide();
					$("#auditorId").prop({"value" : 89});
					select1();
				}
				if (proid == 3) {
					$("#verifierdiv").show();
					$("#auditordiv").show();
					select1();
					select2();
				}
			});
				

		//根据语言查找校对员
		function select1() {
			var language = document.getElementById("languagePair").value;
			var domain = document.getElementById("domain").value;
			
			$.post("/AitransManager/vierfier/findIdandName.shtml?language="+language+"&domain="+domain,
					function(data) {
				var cooperativeState = document.getElementById("cooperativeState").value;
				//alert(cooperativeState);
				if(cooperativeState==0){
					var oSelect = document.getElementById("verifierId");
					var oldOptions = oSelect.getElementsByTagName("option");
					for (var k = 0; k < oldOptions.length;) {
						oSelect.removeChild(oldOptions[k]);
					}
					//  alert("date"+data);
					$("#verifierId").append("<option value='89'>待定</option>");
					for (var i = 0; i < data.length; i++) {

						$("#verifierId").append("<option value="+data[i].id+">"+ data[i].nickname+ "</option>");
					}
					}
				if(cooperativeState==1){
					//获取原来的
					var ocheck = document.getElementById("verifiCheck");
					var oldOptions = ocheck.childNodes;
					//alert("原来的子元素"+oldOptions);
					for (var k = 0; k < oldOptions.length;) {
						ocheck.removeChild(oldOptions[k]);
					}
						for (var i = 0; i < data.length; i++) {
								//alert("江南");
							 var oin=document.createElement("input");
				        	 oin.setAttribute("type","checkbox");
				        	 oin.setAttribute("name","verifierId");
				        	 oin.setAttribute("id","verifierId");
				        	 oin.setAttribute("value",data[i].id);				        	 
				        	 document.getElementById("verifiCheck").appendChild(oin);
				        	 var myText = document.createTextNode(data[i].nickname);
				        	 document.getElementById("verifiCheck").appendChild(myText);
						}
					}
				}, "json");
		};

		//根据语言查找审核员
		function select2() {
			var language = document.getElementById("languagePair").value;
			var domain = document.getElementById("domain").value;
			
			$.post("/AitransManager/auditor/findIdandName.shtml?language="+language+"&domain="+domain,
					function(data) {

				var cooperativeState = document.getElementById("cooperativeState").value;
				//alert(cooperativeState);
				if(cooperativeState==0){
					var oSelect = document.getElementById("auditorId");
					var oldOptions = oSelect.getElementsByTagName("option");
					for (var k = 0; k < oldOptions.length;) {
						oSelect.removeChild(oldOptions[k]);
					}
					//  alert("date"+data);
					$("#auditorId").append("<option value='89'>待定</option>");
					for (var i = 0; i < data.length; i++) {

						$("#auditorId").append("<option value="+data[i].id+">"+ data[i].nickname+ "</option>");
						}
					}
				if(cooperativeState==1){
					//获取原来的
					var ocheck = document.getElementById("auditorCheck");
					var oldOptions = ocheck.childNodes;
					//alert("原来的子元素"+oldOptions);
					for (var k = 1; k < oldOptions.length;) {
						ocheck.removeChild(oldOptions[k]);
					}
						for (var i = 0; i < data.length; i++) {
								//alert("江南");
							 var oin=document.createElement("input");
				        	 oin.setAttribute("type","checkbox");
				        	 oin.setAttribute("name","auditorId");
				        	 oin.setAttribute("id","auditorId");
				        	 oin.setAttribute("value",data[i].id);				        	 
				        	 document.getElementById("auditorCheck").appendChild(oin);
				        	 var myText = document.createTextNode(data[i].nickname);
				        	 document.getElementById("auditorCheck").appendChild(myText);
						}
					}
						
				}, "json");
		};
				
		//根据客户id,查找客户缩写名称
		$("#cname").change(
				function() {
					var cname = document.getElementById("cname").value;
					if (cname != '' || cname!=null) {
						$.post("/AitransManager/project/findcustomerIdandabbreviation.shtml?customerid="+ cname,function(data) {
							//alert(data);
							$("#reference").prop("value",data[0]);
							var pathValue=document.getElementById("path").value;
							
							//项目名称
							var parjectName=document.getElementById("name").value;
							/*//公司名称
							var cusName=document.getElementById("cname").value;*/
							document.getElementById("url").value=pathValue+"/Aitrans/"+data[1]+"/"+parjectName;
							document.getElementById("cusZH").value=data[1];
						}, "json");
					}
				});
		/*$("#parentPath").change(function(){
			//alert("parent");
			var value='';  
	        var radio = document.getElementsByName("parentPath");  
	        for(var i = 0;i<radio.length;i++)  
	        {  
	            if(radio[i].checked==true)  
	            {value = radio[i].value;  
	            break;}  
	        }  
	        alert(value);  
		});
*/
		$("#btn_add1").click(function(){  
        	 var oin=document.createElement("input");
        	 oin.setAttribute("id","file");
        	 oin.setAttribute("type","file");
        	 oin.setAttribute("name","filename");
        	 oin.setAttribute("style","margin-left: 100px");
        	 document.getElementById("newUpload1").appendChild(oin);  
        	 document.getElementById("btn_del1").removeAttribute("disabled");
        }); 
	});	
	
	function radioClick(){
		var value='';  
        var radio = document.getElementsByName("parentPath");  
        for(var i = 0;i<radio.length;i++)  
        {  
            if(radio[i].checked==true)  
            {value = radio[i].value;  
            break;}  
        }  
        
        
        $.post("/AitransManager/project/findURL.shtml?url="+value,function(data) {
        	var oSelect = document.getElementById("path");
			var oldOptions = oSelect.getElementsByTagName("option");
			for (var k = 0; k < oldOptions.length;) {
				oSelect.removeChild(oldOptions[k]);
			}
			//alert(data[0]);
			//$("#path").append("<option  checked='checked' value="+data[1][0]+">"+data[0][0]+ "</option>");
			for (var i = 1; i < data[0].length; i++) {
				$("#path").append("<option  value="+data[1][i]+">"+data[0][i]+ "</option>");
			}
			
			document.getElementById("url").value=data[1][0];
			
		}, "json");
        
	}
	
	function pathChange(){
		var pathValue=document.getElementById("path").value;
		
		//项目名称
		var parjectName=document.getElementById("name").value;
		//公司名称
		var cusName=document.getElementById("cusZH").value;
		document.getElementById("url").value=pathValue+'/Aitrans/'+cusName+'/'+parjectName;
		 $.post("/AitransManager/project/findURL.shtml?url="+pathValue,function(data) {
	        	var oSelect = document.getElementById("path");
				var oldOptions = oSelect.getElementsByTagName("option");
				for (var k = 0; k < oldOptions.length;) {
					oSelect.removeChild(oldOptions[k]);
				}
				//alert(data[0]);
				//$("#path").append("<option  checked='checked' value="+data[1][0]+">"+data[0][0]+ "</option>");
				for (var i = 0; i < data[0].length; i++) {
					$("#path").append("<option  value="+data[1][i]+">"+data[0][i]+ "</option>");
				}
				
				//document.getElementById("url").value=data[1][0];
				
			}, "json");
		
	}

	function del_1(){  
    	var odel=document.getElementById("newUpload1");
    	   var ofile=odel.lastElementChild;
    	   var name="";
    	   name = ofile.getAttribute("id");
    	   if(name=='file1'){
    		   document.getElementById("btn_del1").setAttribute("disabled","true");
    	   }else{
    		   odel.removeChild(ofile);
    	   }
       } 
	
	
	function projectOnblur(){
		var pathValue=document.getElementById("path").value;
		
		//项目名称
		var parjectName=document.getElementById("name").value;
		//公司名称
		var cusName=document.getElementById("cusZH").value;
		document.getElementById("url").value=pathValue+'/Aitrans/'+cusName+'/'+parjectName;
	}
