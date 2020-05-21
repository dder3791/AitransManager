var pageii = null;
var grid = null;
$(function() {
	grid = lyGrid({
		pagId : 'paging',
		l_column : [ 
		 {
			colkey : "reference",
			name : "公司案号",
		 },{
			colkey : "tname",
			name : "译员名称",
		 },{
			colkey : "name",
			name : "项目名称",
		 },{
			colkey : "oriLanguage",
			name : "校对语言",
			renderData : function(rowindex, data, rowdata, column) {
				if(data=="EN"){
					return "英语";
				}else if(data=="JP"){
					return "日语";
				}else if(data=="KOR"){
					return "韩语";
				}else if(data=="GER"){
					return "德语";
				}else if(data=="FR"){
					return "法语";
			   }
			}
		 },{
			colkey : "pname",
			name : "校对人员",
		 },{
			colkey : "difficultyT",
			 name : "技术难度",
		 },{
	        colkey : "difficultyL",
	         name : "语言难度",  
         },{
        	 colkey : "comingDate",
        	 name : "交案时间",
         },{
        	 colkey : "completeDate",
        	 name : "完成时间",
         },{
        	 colkey : "score",
        	 name : "得分情况"
         },{
        	 colkey : "problems",
        	 name : "问题描述",
         },{
        	 colkey : "checker",
        	 name : "检查人",
         },{
        	 colkey : "checkDate",
        	 name : "检查日期",
         },{
        	 colkey : "total",
             name : "操作",//当前行号,当前列的数据,当前行数据,列
             renderData : function(rowindex,data,rowdata,column){
            	 if(data<=0){
            		 return "<button type='button' class='btn btn-default btn-sm' onclick='appeal("+rowdata.id+")'>请求申诉</button>"
            	 }else{
            		 return "<button type='button' class='btn btn-default btn-sm active'>等待处理</button>";
            		 
            	 }
     		 
           }
         }
		 ],
		jsonUrl : rootPath + '/transfer/find_my_Evaluate_by_page.shtml',
		checkbox : true,
		serNumber : true
	});
});

function appeal(id){
	pageii = layer.open({
		title : "申诉页面",
		type : 2,
		area : [ "600px", "80%" ],
		content : rootPath + '/transfer/addappeal.shtml?id='+id 
	});
};


