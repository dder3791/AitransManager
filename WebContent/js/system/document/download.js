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
			for (var k = 1; k < oldOptions.length;) {
				oSelect.removeChild(oldOptions[k]);
			}
			
			for (var i = 0; i < data[0].length; i++) {
				$("#path").append("<option  value="+data[1][i]+">"+data[0][i]+ "</option>");
			}
			document.getElementById("newPath").value=value;
			//document.getElementById("newPath").value=data[1][0];
			
		}, "json");
        
	}
	
	function pathChange(){
		var pathValue=document.getElementById("path").value;
		
		document.getElementById("newPath").value=pathValue;
		 $.post("/AitransManager/project/findURL.shtml?url="+pathValue,function(data) {
	        	var oSelect = document.getElementById("path");
				var oldOptions = oSelect.getElementsByTagName("option");
				for (var k = 1; k < oldOptions.length;) {
					oSelect.removeChild(oldOptions[k]);
				}
				
				for (var i = 0; i < data[0].length; i++) {
					$("#path").append("<option  value="+data[1][i]+">"+data[0][i]+ "</option>");
				}
				
			}, "json");
		
	}