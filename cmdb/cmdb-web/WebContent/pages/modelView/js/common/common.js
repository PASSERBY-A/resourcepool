/**
 * 
 */
//动态修改grid宽度，高度
function changeWidthHeight(){
		  $(function(){　　 
				$(window).resize(function(){　　
				$("#metaclass-attr").setGridWidth($(window).width()*0.99);　　
				$("#metaclass-attr").setGridWidth(document.body.clientWidth*0.99);　　
				$("#metaclass-attr").setGridHeight($(window).width()*0.35);　　
				$("#metaclass-attr").setGridHeight(document.body.clientWidth*0.35);　　
				});　　

         });
}

function changeWidthHeightByPara(width,height){
	  $(function(){　　 
			$(window).resize(function(){　　
				$("#metaclass-attr").setGridWidth($(window).width()*width);　　
				$("#metaclass-attr").setGridWidth(document.body.clientWidth*width);　　
				$("#metaclass-attr").setGridHeight($(window).width()*height);　　
				$("#metaclass-attr").setGridHeight(document.body.clientWidth*height);　
			});　　

   });
}



	  
//初始化grid宽高
function initGridWidthHeight(){
		   $("#metaclass-attr").setGridWidth($(window).width()*0.99);　　
			$("#metaclass-attr").setGridWidth(document.body.clientWidth*0.99);　　
			$("#metaclass-attr").setGridHeight($(window).width()*0.35);　　
			$("#metaclass-attr").setGridHeight(document.body.clientWidth*0.35);　　
}

function initGridWidthHeightByPara(width,height){
	   $("#metaclass-attr").setGridWidth($(window).width()*width);　　
		$("#metaclass-attr").setGridWidth(document.body.clientWidth*width);　　
		$("#metaclass-attr").setGridHeight($(window).width()*height);　　
		$("#metaclass-attr").setGridHeight(document.body.clientWidth*height);　　
}




