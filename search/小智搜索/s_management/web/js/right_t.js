var areaArray = new Array();
areaArray[areaArray.length]=new Array("ysgj","ysgj");
areaArray[areaArray.length]=new Array("cdsb","cdsb");
areaArray[areaArray.length]=new Array("hwsb","hwsb");
areaArray[areaArray.length]=new Array("sfzf","sfzf");
areaArray[areaArray.length]=new Array("qtyw","qtyw");
areaArray[areaArray.length]=new Array("kjds","kjds");

var townArray = new Array();
townArray[townArray.length]=new Array("ysgj","cb","船舶");
townArray[townArray.length]=new Array("ysgj","hkq","航空器");
townArray[townArray.length]=new Array("ysgj","gl","公路");
townArray[townArray.length]=new Array("cdsb","hy","海运");
townArray[townArray.length]=new Array("dsb","ky","空运");
townArray[townArray.length]=new Array("cdsb","gl","公路");
townArray[townArray.length]=new Array("hwsb","hwsb","货物申报");
townArray[townArray.length]=new Array("hwsb","jzsb","集中申报");
townArray[townArray.length]=new Array("hwsb","bgdlwt","报关代理委托");
townArray[townArray.length]=new Array("hwsb","yytg","预约通关");
townArray[townArray.length]=new Array("sfzf","sfzf","税费支付");
townArray[townArray.length]=new Array("qtyw","qtyw","其他业务");
townArray[townArray.length]=new Array("kjds","jksq","进口申报");
townArray[townArray.length]=new Array("kjds","cksb","出口申报");
townArray[townArray.length]=new Array("kjds","ggfw","公共服务");




function setTown(obj1ID,obj2ID){
    var objArea = document.getElementById(obj1ID);
    var objTown = document.getElementById(obj2ID);
    var i;
    var itemArray = null;
    if(objArea.value.length > 0){
        itemArray = new Array();
        for(i=0;i<townArray.length;i++){
            if(townArray[i][0]==objArea.value){itemArray[itemArray.length]=new Array(townArray[i][1],townArray[i][2],townArray[i][3]);}
        }
    }
    for(i = objTown.options.length ; i >= 0 ; i--){
        objTown.options[i] = null;}
    objTown.options[0] = new Option("请选择系统");
    objTown.options[0].value = "";
    if(itemArray != null){
        for(i = 0 ; i < itemArray.length; i++){
            objTown.options[i] = new Option(itemArray[i][1]);
            if(itemArray[i][0] != null){
                objTown.options[i].value = itemArray[i][0];
            }
        }
    }
}




function aaa() {
    // var aaa=document.getElementById("no1");
    // var bbb=document.getElementById("no2");
    // if (aaa.check=true){
    //     document.getElementById('no2').disabled = 'disabled';
    //     document.getElementById('tingzhi').style.display='block';
    //
    // }
    // if (bbb.check=true){
    //     document.getElementById('no1').disabled = 'disabled';
        document.getElementById('lizhi').style.display='block';
    // }

}
function ccc() {
    // document.getElementById('tingzhi').style.display = 'none';
    document.getElementById('lizhi').style.display = 'none';
    document.location.reload();
}

function tijiao(){
    // document.getElementById('tingzhi').style.display='none';
    document.getElementById('lizhi').style.display='none';
    var checkID=[];
    $("input[name='checkbox']").each(function(i){
        checkID[i] = $(this).val();
    });
    var checkStatus=[];
    $("input[name='checkbox1']:checked").each(function(i){
        checkStatus[i] = $(this).val();
    });


    var department = document.getElementById("department").value;
    var system = document.getElementById("system").value;
    var userID=localStorage.getItem('userID');

    $.ajax({
        data:department+','+system+','+checkID+','+userID,
        dataType:'text',
        type:'post',
        async: true,
        url:'http://localhost:8080/SetRightServlet',
        traditional:true,
        success: function(data){
            console.log(data);
            alert(data);
        }

    });
}

