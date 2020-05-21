$(function() {
		document.oncontextmenu =documentWhichButton;
		
         //阻止事件冒泡
        document.getElementById("tableRight").onclick = function(event) {
          event.stopPropagation();
        };
        /*   for (var i = 0; i < getElementsByClassName("contextMenuItem").length; i ) {
        	  
            getElementsByClassName("contextMenuItem")[i].onclick = function(event) {
              event = event ? event : window.event
              var target = event.srcElement ? event.srcElement : event.targe;
              document.getElementById("contextMenu").style.display = "none";                
            }
          };*/
		findDocument('','');
		$("#documentRight").hide();
		$("#tableRight").hide();
		$("#fileRight").hide();
	})
	function findDocument(name,url){
		
		$.post(rootPath + '/document/findDocuments.shtml?name='+name+'&url='+url,
				function(data) {
					//alert(name+"------------------"+url);
					if(!(name=="" && url=="")){
					var tableElement=document.getElementById("myTable");
					var trElement = tableElement.getElementsByTagName("tbody");
					tableElement.removeChild(trElement[0]);
					}
					for (var k = 0; k < data[0].length; k++) {
						$("#myTable").append(
										"<tr onclick='getRowNo(this)' ><td ondblclick=\"findDocument('"+data[0][k]+"','"+data[2][0]+"')\"><img src='/Aitrans/images/document/dir.png' height='28px' width='17px' alt='' id='imgAlt'><span style='color: black;font-size: 13px'>"
										+ data[0][k]
										+ "</span></td></tr>");

					}
					for (var k = 0; k < data[1].length; k++) {
						$("#myTable").append(
										"<tr onclick='getFileRowNo(this)'><td ondblclick=\"downFile('"+data[0][k]+"','"+data[2][0]+"')\"><img src='/Aitrans/images/document/file.png' height='28px'	width='18px' alt=''><span style='color: black;font-size: 13px'>"
										+ data[1][k]
										+ "</span></td></tr>");
					}
					//alert(data[3][0]);
					document.getElementById("oldurl").value=data[3][0];

					var inputValue=document.getElementById("inputValue").value=data[2][0];

					if("E:/"!=inputValue){
						$("#back").show();
					}else{
						$("#back").hide();
					}
					
				}, "json");
	}
	//页面中的右键
	function documentWhichButton(event)
	{	
		var btnNum = event.button;
		if (btnNum==2)
		{
		//alert("您点击了鼠标右键！");
			$("#documentRight").show();
			//$("#newDir").show();
			//$("#uploadfile").show();
	 		var forRight=document.getElementById("documentRight");
	 		forRight.style.marginLeft=event.clientX+"px"; 
	 		forRight.style.marginTop=event.clientY+"px"; //鼠标的坐标啊
			return false;
			  
		}  
	}
	
	//文件右键
	function fileWhichButton(event)
	{	
		var btnNum = event.button;
		if (btnNum==2)
		{
		//alert("您点击了鼠标右键！");
			
			$("#fileRight").show();
			$("#downFile").show();
	 		$("#renamefile").show();
	 		$("#deletefile").show();
			
	 		$("#lookDir").hide();
			$("#renameDir").hide();
			$("#deleteDir").hide();
			$("#tableRight").hide();
			
			
			$("#newDir").hide();
			$("#uploadfile").hide();
			$("#documentRight").hide();
 			
	 		var forRight=document.getElementById("fileRight");
	 		forRight.style.display="block"; 
	 		forRight.style.marginLeft=event.clientX+"px"; 
	 		forRight.style.marginTop=event.clientY+"px"; //鼠标的坐标啊
			return false;
			  
		}  
	}
	
	//table中的右键
	function whichButton(event)
	{
		var btnNum = event.button;
		if (btnNum==2)
		{
		//alert("您点击了鼠标右键！");
			$("#downFile").hide();
	 		$("#renamefile").hide();
	 		$("#deletefile").hide();
			//$("#fileRight").hide();
			
			$("#newDir").hide();
			$("#uploadfile").hide();
			//$("#documentRight").hide();
			
			$("#lookDir").show();
			$("#renameDir").show();
			$("#deleteDir").show();
			$("#tableRight").show();
			
			
			
	 		var forRight=document.getElementById("tableRight");
	 		forRight.style.display="block"; 
	 		forRight.style.marginLeft=event.clientX+"px"; 
			forRight.style.marginTop=event.clientY+"px"; //鼠标的坐标啊
			return false;
			  
		}  
		/* else if(btnNum==0)
		{
		//alert("您点击了鼠标左键！");
		}
		else if(btnNum==1)
		{
		alert("您点击了鼠标中键！");
		}
		else
		{
		alert("您点击了" + btnNum+ "号键，我不能确定它的名称。");
		} */
	}
	
	document.onclick=function(){//就是为了更形象的模仿啊 
		/*$("#tableRight").show();*/
		$("#lookDir").show();
		$("#renameDir").show();
		$("#deleteDir").show();
		var tableRight=document.getElementById("tableRight");
		tableRight.style.display="none";
		
		
		$("#newDir").show();
		$("#uploadfile").show();	
		var documentRight=document.getElementById("documentRight");
		documentRight.style.display="none";
		
		/*$("#fileRight").show();*/
		$("#downFile").show();
 		$("#renamefile").show();
 		$("#deletefile").show();
		var fileRight=document.getElementById("fileRight");
		fileRight.style.display="none";
		
	}
	
    function block(event) {
        if (window.event) {
          event = window.event;
          event.returnValue = false;
        } else event.preventDefault();
      }
      //兼容IE不支持getElementsByClassName
      function getElementsByClassName(className, root, tagName) {
        if (root) {
          root = typeof root == "string" ? document.getElementById(root) : root;
        } else {
          root = document.body;
        }
        tagName = tagName || "*";
        if (document.getElementsByClassName) { 
          return root.getElementsByClassName(className);
        } else {
          var tag = root.getElementsByTagName(tagName); 
          var tagAll = [];
          for (var i = 0; i < tag.length; i ) {
            for (var j = 0, n = tag[i].className.split(' '); j < n.length; j ) {
              if (n[j] == className) {
                tagAll.push(tag[i]);
                break;
              }
            }
          }
          return tagAll;
        }
      }
      
   function getRowObj(obj)
  	{ 
  		var i = 0;   
  		while(obj.tagName.toLowerCase() != "tr")
  			{    obj = obj.parentNode;    
  			if(obj.tagName.toLowerCase() == "table")
  			return null;   
  			}   
  		return obj;
  	}
  	//文件夹  根据得到的行对象得到所在的行数 添加对应的右键效果
  	function getRowNo(obj)
  	{
  		
  		var trObj = getRowObj(obj);    
  		var trArr = trObj.parentNode.children; 
  		for(var trNo= 0; trNo < trArr.length; trNo++)
  		{  
  				if(trObj == trObj.parentNode.children[trNo])
  				{    
  					//行号
  					//alert(trNo+1);
  					//左键单击改变颜色
  					obj.style.backgroundColor = '#ccc';
  					
  					//alert(spanValue);
  					//为该行添加右击事件
  					obj.oncontextmenu=whichButton;
  					//找到被选中行所对应的值
  					var valueTD=document.getElementById("myTable").rows[trNo].cells[0];
  					//span标签对应值
  					var spanValue=valueTD.getElementsByTagName('span')[0].innerText;
  					//alert(spanValue);
  					//input标签对应值
  					//var inputValue=valueTD.getElementsByTagName('input')[0].value;
  					
  					document.getElementById("spanValue").value=spanValue; 
  					//document.getElementById("inputValue").value=inputValue; 
  					
  				}else{
  					trObj.parentNode.children[trNo].style.backgroundColor='#ffffff';
  				} 
  		}
  	}  
  	
  	
  //文件     根据得到的行对象得到所在的行数并添加右键效果
  	function getFileRowNo(obj)
  	{
  		
  		var trObj = getRowObj(obj);    
  		var trArr = trObj.parentNode.children; 
  		for(var trNo= 0; trNo < trArr.length; trNo++)
  		{  
  				if(trObj == trObj.parentNode.children[trNo])
  				{    
  					//行号
  					//alert(trNo+1);
  					//左键单击改变颜色
  					obj.style.backgroundColor = '#ccc';
  					
  					//alert(spanValue);
  					//为该行添加右击事件
  					obj.oncontextmenu=fileWhichButton;
  					//找到被选中行所对应的值
  					var valueTD=document.getElementById("myTable").rows[trNo].cells[0];
  					//span标签对应值
  					var spanValue=valueTD.getElementsByTagName('span')[0].innerText;
  					//alert(spanValue);
  					//input标签对应值
  					//var inputValue=valueTD.getElementsByTagName('input')[0].value;
  					
  					document.getElementById("spanValue").value=spanValue; 
  					/*document.getElementsByTagName("c:param")[0].value=spanValue;
  					document.getElementsByName("urlname").value=spanValue;
  					document.getElementsByName("path").value=inputValue;*/
  					//document.getElementById("inputValue").value=inputValue; 
  					
  				}else{
  					trObj.parentNode.children[trNo].style.backgroundColor='#ffffff';
  				} 
  		}
  	}