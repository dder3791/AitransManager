<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/common.jspf"%>
<title>Insert title here</title>
</head>
<body>

Js获取 table当前行的值
<script language=javascript> 
var selectedTr=null; 
function c1(obj){ 
obj.style.backgroundColor='blue'; //把点到的那一行变希望的颜色; 
if(selectedTr!=null) selectedTr.style.removeAttribute("backgroundColor"); 
if(selectedTr==obj) selectedTr=null;//加上此句，以控制点击变白，再点击反灰 
else selectedTr=obj; 
} 
/*得到选中行的第一列的值*/ 
function check(){ 
if(selectedTr!=null){ 
var str=selectedTr.cells[0].childNodes[0].value; 
document.getElementById("lab").innerHTML=str; 
} 
else{ 
alert("请选择一行"); 
} 
} 
/*删除选中行*/ 
function del(){ 
if(selectedTr!=null){ 
if(confirm("确定要删除吗?")){ 
alert(selectedTr.cells[0].childNodes[0].value); 
var tbody=selectedTr.parentNode; 
tbody.removeChild(selectedTr); 
} 
} 
else{ 
alert("请选择一行"); 
} 
} 
</script> 
单击选中Tr，高亮显示，再单击取消选选中。<input type=button value="选中的是哪一行？" onclick="check()"> <input 
type=button value="删除选中行" onclick="del()"> <input type=button value="增加一行" onclick="add()"> 
<table width="100%" border="1" cellspacing="0" cellpadding="0" id="tab"> 
<tr onclick="c1(this);" bgcolor="#cccccc"> 
<td ><input type="text" value="11">　</td> <td><input type="text" value="12"></td> 
</tr> 
<tr onclick="c1(this);" bgcolor="#e0e0e0"> 
<td ><input type="text" value="21">　</td> <td><input type="text" value="22"></td> 
</tr> 
<tr onclick="c1(this);" bgcolor="#cccccc"> 
<td ><input type="text" value="31">　</td> <td><input type="text" value="32"></td> 
</tr> 
<tr onclick="c1(this);" bgcolor="#e0e0e0"> 
<td ><input type="text" value="41">　</td> <td><input type="text" value="42"></td> 
</tr> 
<tr onclick="c1(this);" bgcolor="#cccccc"> 
<td ><input type="text" value="51">　</td> <td ><input type="text" value="52">　</td> 
</tr> 
</table> 
<label id="lab"></label> 




    <span class="second">  
        <input type="text" name="makeupCo" id="makeupCo" class="makeinp" onfocus="setfocus(this)" oninput="setinput(this);" placeholder="请选择或输入"/>  
        <select name="makeupCoSe" id="typenum" onchange="changeF(this)" size="10" style="display:none;">  
            <option value="1">1</option>  
            <option value="2">2</option>  
            <option value="12333">12323</option>  
            <option value="31">31</option>  
            <option value="1332">1332</option>  
            <option value="412">412</option>  
            <option value="42">42</option>  
            <option value="11">11</option>  
        </select>  
    </span>  
</body>
<script type="text/javascript"> 
   
var TempArr=[];//存储option

$(function(){
    /*先将数据存入数组*/
    $("#typenum option").each(function(index, el) {
        TempArr[index] = $(this).text();
    });
    $(document).bind('click', function(e) {  
        var e = e || window.event; //浏览器兼容性   
        var elem = e.target || e.srcElement;  
        while (elem) { //循环判断至跟节点，防止点击的是div子元素   
            if (elem.id && (elem.id == 'typenum' || elem.id == "makeupCo")) {  
                return;  
            }  
            elem = elem.parentNode;  
        }  
        $('#typenum').css('display', 'none'); //点击的不是div或其子元素   
    });  
})

function changeF(this_) {
    $(this_).prev("input").val($(this_).find("option:selected").text());
    $("#typenum").css({"display":"none"});
}
function setfocus(this_){
    $("#typenum").css({"display":""});
    var select = $("#typenum");
    for(i=0;i<TempArr.length;i++){
        var option = $("<option></option>").text(TempArr[i]);
        select.append(option);
    } 
}

function setinput(this_){
    var select = $("#typenum");
    select.html("");
    for(i=0;i<TempArr.length;i++){
        //若找到以txt的内容开头的，添option
        if(TempArr[i].substring(0,this_.value.length).indexOf(this_.value)==0){
            var option = $("<option></option>").text(TempArr[i]);
            select.append(option);
        }
    }
}
  </script> 
<!-- <body onload="leftTimer('2017-10-24 17:34:7')">

<h2>剩余时间:</h2>
<div id="timer"></div>
</body>
<script type="text/javascript">
function leftTimer(year,month,day,hour,minute,second){ 
 var leftTime = (new Date(year,month-1,day,hour,minute,second)) - (new Date()); //计算剩余的毫秒数 
 var days = parseInt(leftTime / 1000 / 60 / 60 / 24 , 10); //计算剩余的天数 
 var hours = parseInt(leftTime / 1000 / 60 / 60 % 24 , 10); //计算剩余的小时 
 var minutes = parseInt(leftTime / 1000 / 60 % 60, 10);//计算剩余的分钟 
 var seconds = parseInt(leftTime / 1000 % 60, 10);//计算剩余的秒数 
 days = checkTime(days);  
 hours = checkTime(hours); 
 minutes = checkTime(minutes); 
 seconds = checkTime(seconds); 
 setInterval("leftTimer(2017,10,24,12,02,12)",1000); 
 document.getElementById("timer").innerHTML = days+"天" + hours+"小时" + minutes+"分"+seconds+"秒"; 
} 
function checkTime(i){ //将0-9的数字前面加上0，例1变为01 
 if(i<10) 
 { 
  i = "0" + i; 
 } 
 return i; 
} 
</script> -->


</html>