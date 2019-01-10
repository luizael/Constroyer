var menuId =  "#menu";
var subMenuClass = ".submenu"; 
var menuAtivo = false;
var abandonarMenu = function(){
    if(!menuAtivo){            
        $(menuId + " > li > ul").fadeOut("fast");
    }   
}
var limparMenu = function(){
     $(menuId + " > li > ul").fadeOut("fast");
}
$(document).ready(function(){
    $(subMenuClass).click(function(){
        limparMenu();
        var cls = $(this).attr("class").split(' ');
        if($("#" + cls[2]).is(":hidden")){
            $("#" + cls[2]).slideDown();
            menuAtivo = true;
        }else{         
            menuAtivo = false;
        }
    })
    $(menuId + " > li > ul").hover("",function(){       
        menuAtivo = false;
    })
    $('body').click(function(){
        abandonarMenu();
    })
})


