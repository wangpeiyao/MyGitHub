//生成二维码 
!function(){ 
var uuid = (“#uuid”).val();  
             var content;  
             content = “……….do?uuid=”+uuid;  
             //console.dir(content);(‘.pc_qr_code’).qrcode({ 
render:”canvas”, 
width:200, 
height:200, 
correctLevel:0, 
text:content, 
background:”#ffffff”, 
foreground:”black”, 
src:”/logo.png” 
}); 
setCookie(“sid”, 123, -1*60*60*1000); 
keepPool();//自动循环调用 
}();

function keepPool(){
    var uuid = $("#uuid").val();
    $.get(ctx+"/web/login/pool.do",{uuid:uuid,},function(msg){//如果放入一个不存在的网址怎么办?
        //console.log(msg);
        if(msg.successFlag == '1'){
            $("#result").html("扫码成功");
            setCookie(msg.data.cname, msg.data.cvalue, 3*60*60*1000);
            //alert("将跳转...");
            window.location.href = ctx +"/webstage/login/success.do";
        }else if(msg.successFlag == '0'){
            $("#result").html("该二维码已经失效,请重新获取");
        }else{
            keepPool();
        }

    }); 
}

//设置cookie
function setCookie(cname, cvalue, expireTime) {
 var d = new Date();
 d.setTime(d.getTime() + expireTime);//设置过期时间
 var expires = "expires="+d.toUTCString();
 var path = "path=/"
 document.cookie = cname + "=" + cvalue + "; " + expires + "; " + path;
}