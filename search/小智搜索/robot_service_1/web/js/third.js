    var email = localStorage.getItem("userEmail");
    var firstType = localStorage.getItem("firstType");
    var secondTitle = localStorage.getItem("secondTitle");

//5个问题的ajax请求，得到5个问题的内容
    $(document).ready(function () {
        console.log(email);
        console.log(firstType);
        console.log(secondTitle);
        var html = '';
        $.ajax({
            type: "post",
            contentType: "application/x-www-form-urlencoded;charset=utf-8",
            // url: "http://47.107.64.157:9999/searchByTitle",
            url: "http://localhost:9999/searchByTitle",
            data: "title=" + firstType,
            dataType: "json",
            async: true,
            success: function (data) {
                if (data.length === 0) {
                    html = "暂无数据"
                } else {
                    for (var i = 0; i < data.length; i++) {
                        var ls = eval('(' + data[i] + ')');
                        console.log(ls);
                        html += "<div align='center' id=\"question-type\" class=\"list-group-item\">" +
                            "<a  id='" + ls["id"] + "' href=\"" + ls.content + "\" onclick='saveThirdTitle(this.id)'>" + ls["title"] + "</a>"
                            + "</div>"
                    }
                }
                $("#question-type").html(html);
            }
        })
    });



    //得到输入搜索框的值，点击搜索的Ajax请求，得到搜索结果
    $(document).ready(function () {
        $("#search_btn").click(function () {
            var search__input = document.getElementById("search__input");
            console.log(search__input.value);
            if(search__input.value === ""){
                console.log("text is null");
                $("#search__btn").val('');
            }else {
                var search = '';
                $.ajax({
                    type: "post",
                    contentType: "application/x-www-form-urlencoded",
                    // url: "http://47.107.64.157:9999/searchByContent",
                    url: "http://localhost:9999/searchByContent",
                    dataType: "json",
                    data:"type="+firstType+"&title="+search__input.value,
                    async: true,
                    success: function (data) {
                        if(data.length === 0){
                            search = "暂无数据";
                        }else {
                            for (var i = 0; i < data.length; i++) {
                                // var sea = eval('(' + data[i] + ')');
                                var sea = data[i];
                                console.log(sea);
                                search+= "<div id=\"search-answer\"  >" +
                                    "<a href=\""+sea.content+"\" id=\""+sea.id+"\" onclick='saveThirdTitle(this.id)'  class=\"list-group-item active\" style=\"display: block; text-align: center;\">" +
                                    sea.title+"</a >"+"<div>" + "<div  class=\"list-group-item\">" +
                                    "<p id='text' style=\"padding-top: 2%\">" + sea.content+"</p><br>" +
                                    "</div>" + "</div>" + "</div>";
                            }
                        }
                        $("#search-answer").html(search);
                    }
                });
            }
        });
    });


    function saveThirdTitle(value) {
        console.log(value);
        localStorage.setItem("thirdId",value);
    }






