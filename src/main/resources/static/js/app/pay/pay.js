$(function () {
    $.getJSON(
        "goods/listall",
        function (data) {
            //循环获取数据
            var tt = "";
            var numb = 0;
            var css;//默认样式
            $.each(data, function (k, v) {
                css = numb == 0 ? ' active' : '';//第一个默认勾选
                tt += '<li> <a href="javascript:void(0);" class="wshop-membership-63cc6c9f10d6d97511b9768b1c3ca2580' + css
                    + '" data-id="' + v['goodsId']
                    + '"> <span class="price">&yen;' + v['vipMoney']
                    + '</span> <span class="title">' + v['goodsCycle']
                    + '</span> </a> </li>';

                numb = 2;
            });
            $("[class='xh-vip clearfix']").html(tt);
            $('.wshop-membership-63cc6c9f10d6d97511b9768b1c3ca2580').click(function () {
                $('.wshop-membership-63cc6c9f10d6d97511b9768b1c3ca2580.active').removeClass('active');
                $(this).addClass('active');

                $(document).trigger('wshop_63cc6c9f10d6d97511b9768b1c3ca2580_on_amount_change');
            });

            $(document).bind('wshop_form_63cc6c9f10d6d97511b9768b1c3ca2580_submit', function (e, data) {
                data.post_id = $('.wshop-membership-63cc6c9f10d6d97511b9768b1c3ca2580.active').attr('data-id');
            });

        }
    );

});


//当前订单号
var orderCode = 100;
var timer = null;

//支付后轮询订单
function getOrders() {
    // http://127.0.0.1:8000/order/getOrderByCode?orderCode=2018111070999
    var orderstate = 0;
    $.getJSON(
        "order/getOrderByCode?orderCode=" + orderCode,
        function (data) {
            //循环获取数据

            $.each(data, function (k, v) {
                console.log(data);
                orderstate = v["payStatus"];
                // alert("orderstate:" + orderstate);
                if (orderstate == "1") {
                    //停止查订单状态
                    clearInterval(timer);
                    closeDiv();
                    alert("恭喜你充值会员支付成功！");
                    $("#request-process-patent").html("订单号：" + orderCode + "<br>订单状态：已支付");
                }
            });


        }
    );


}


//打开支付层
function divShow(srcUrl, orderCodeNow) {
    orderCode = orderCodeNow;//轮询当前订单号

    $("#payIframe").attr("src", srcUrl);
    //div 支付存
    $("#payindex").show();
    //轮询查看订单状态
    timer = setInterval(getOrders, 2000);// 注意函数名没有引号和括弧！
}

//关闭支付层
$("#linkClose").click(function () {
    closeDiv();
});

//关闭层
function closeDiv() {
    $("#payIframe").attr("src", "");
//div 支付存
    $("#payindex").hide();
}


$("#btn-pay-button-63cc6c9f10d6d97511b9768b1c3ca2580").click(function () {
    $("#request-process-patent").html("正在提交数据，请勿关闭当前窗口...");


    var payment_method = $("input[name='payment_method-63cc6c9f10d6d97511b9768b1c3ca2580']:checked").val()
    var json_data = {
        "payment_method": payment_method,
        "data_id": $("[class='wshop-membership-63cc6c9f10d6d97511b9768b1c3ca2580 active']").attr("data-id")
    };
    $.ajax({
        url: 'order/addnew',
        type: 'post',
        data: JSON.stringify(json_data),
        contentType: 'application/json;charset=utf-8',   //中文需要加上charset=utf-8才正确
        dataType: "json",
        success: function (message) {

            console.debug(message);
            $("#request-process-patent").html(message.msg);
            if (message > 0) {
                alert("请求已提交！我们会尽快与您取得联系");
            }
            /*
            * <div class="modal fade show" id="payindex" data-keyboard="false" data-backdrop="static" tabindex="-1" style="display: block;">
    <div style="
    position: fixed;
    border: 1 solid;
    background: floralwhite;
    top: 0px;
    left: 0px;
    height: 38px;
    width: 100%;
">
        <span style="
    position: absolute;
    left: 0px;
    font-size: 24px;
">会员支付收银台</span>
        <span style="
    position: absolute;
    right: 0px;
    font-size: 24px;
"><a>关闭</a></span>
    </div>
    <iframe style="width: 100%;height: 100%;position: fixed;left: 0;top: 38px;" src="http://localhost:8000/order/wechat/20190104205525294"></iframe></div>
            *
            * */
            if (payment_method == "alipay") { // 支付宝支付
                //如果浏览器拦截这个可以用a来除非
                //$('#aopen').attr('href','http://keleyi.com');
                //$("#aopen")[0].click();
                //http://localhost:8000 不写这个也可以正常跳转
                window.open("/order/" + payment_method + "/" + message.msg);
                orderCode = message.msg;//轮询当前订单号
                //轮询查看订单状态
                timer = setInterval(getOrders, 2000);// 注意函数名没有引号和括弧！ 两秒轮询一次
            } else if (payment_method == "wechat") { // 微信支付
                var urls = "http://localhost:8000/order/" + payment_method + "/" + message.msg;
                divShow(urls, message.msg);
            }
            //

            //$("body").append('<div class="modal fade show" id="user-add" data-keyboard="false" data-backdrop="static" tabindex="-1" style="display: block;"><iframe  style="width: 100%;    height: 100%;    position: fixed;    left: 0;    top: 38px;" src="'+urls+'"></iframe></div>');
        },
        error: function (message) {
            $("#request-process-patent").html("提交数据失败！");
        }
    });


});


function search() {
    var areaId = $('#areaId').text();
    if (areaId.length) {
        $.post(ctx + "weather/query", {"areaId": areaId}, function (r) {
            if (r.code === 0) {
                var data = JSON.parse(r.msg);
                if (data.code == 200) {
                    var countyName = data.value[0].city;
                    var weathers = data.value[0].weathers;
                    var day_c = [];
                    var night_c = [];
                    var dateArr = [];
                    for (var i = 0; i < weathers.length; i++) {
                        if (i === weathers.length - 1) {
                            day_c.unshift(parseFloat(weathers[i].temp_day_c));
                            night_c.unshift(parseFloat(weathers[i].temp_night_c));
                            dateArr.unshift(weathers[i].date.split("-")[1] + "-" + weathers[i].date.split("-")[2]);
                        } else {
                            day_c.push(parseFloat(weathers[i].temp_day_c));
                            night_c.push(parseFloat(weathers[i].temp_night_c));
                            dateArr.push(weathers[i].date.split("-")[1] + "-" + weathers[i].date.split("-")[2]);
                        }
                    }
                    var weather3HoursDetailsInfos = data.value[0].weatherDetailsInfo.weather3HoursDetailsInfos;
                    var publishTime = data.value[0].weatherDetailsInfo.publishTime;
                    var timeArr = [];
                    var hours_c = [];
                    for (var i = 0; i < weather3HoursDetailsInfos.length; i++) {
                        var time = weather3HoursDetailsInfos[i].endTime.split(" ")[1].split(":");
                        hours_c.push(parseFloat(weather3HoursDetailsInfos[i].highestTemperature));
                        timeArr.push(time[0] + ":" + time[1]);
                    }
                    var chart = Highcharts.chart('container1', {
                        chart: {
                            type: 'line'
                        },
                        title: {
                            text: countyName + '七天气温图',
                            style: {
                                'font-size': '1.1rem',
                                'color': '#888'
                            }
                        },
                        xAxis: {
                            categories: dateArr
                        },
                        yAxis: {
                            title: {
                                text: '°C',
                                align: 'high',
                                rotation: 0
                            }
                        },
                        exporting: {
                            enabled: false
                        },
                        plotOptions: {
                            spline: {
                                dataLabels: {
                                    enabled: true
                                },
                                enableMouseTracking: true
                            }
                        },
                        credits: {
                            enabled: false
                        },
                        series: [{
                            type: 'spline',
                            color: '#FFC77F',
                            name: '高温',
                            data: day_c
                        }, {
                            type: 'spline',
                            color: '#C5F0A4',
                            name: '低温',
                            data: night_c
                        }]
                    });

                    var chart = Highcharts.chart('container2', {
                        chart: {
                            type: 'line'
                        },
                        title: {
                            text: countyName + '未来气温细节',
                            style: {
                                'font-size': '1.1rem',
                                'color': '#888'
                            }
                        },
                        xAxis: {
                            categories: timeArr
                        },
                        yAxis: {
                            title: {
                                text: '°C',
                                align: 'high',
                                rotation: 0
                            }
                        },
                        exporting: {
                            enabled: false
                        },
                        plotOptions: {
                            spline: {
                                dataLabels: {
                                    enabled: true
                                },
                                enableMouseTracking: true
                            }
                        },
                        credits: {
                            enabled: false
                        },
                        series: [{
                            type: 'spline',
                            color: '#A1D9FF',
                            name: '气温',
                            data: hours_c
                        }]
                    });

                    var realtime = data.value[0].realtime;
                    var detailHtml = '<div class="card-header">' +
                        '<h2 class="card-title">当前天气</h2>' +
                        '</div>' +
                        '<div class="card-block">' +
                        '<ul class="icon-list">' +
                        '<li>天气：' + realtime.weather + '</li>' +
                        '<li>风向：' + realtime.wd + '</li>' +
                        '<li>风力大小：' + realtime.ws + '</li>' +
                        '<li>温度：' + realtime.temp + '℃</li>' +
                        '<li>体感温度：' + realtime.sendibleTemp + '℃</li>' +
                        '<li>空气湿度百分比：' + realtime.sd + '%</li>' +
                        '<li>更新时间：' + realtime.time + '</li>' +
                        '</ul>' +
                        '</div>';
                    $("#container3").html("").append(detailHtml);

                    var weathersHtml = '<div class="card-header">' +
                        '<h2 class="card-title">未来天气</h2>' +
                        '</div>' +
                        '<div class="card-block">' +
                        '<ul class="icon-list">' +
                        '<li style="width:100%">' + weathers[6].date + '【' + weathers[6].week + '】：日出时间---' + weathers[6].sun_rise_time + '      日落时间---' + weathers[6].sun_down_time + '      天气---' + weathers[6].weather + ' </li>' +
                        '<li style="width:100%">' + weathers[0].date + '【' + weathers[0].week + '】：日出时间---' + weathers[0].sun_rise_time + '      日落时间---' + weathers[0].sun_down_time + '      天气---' + weathers[0].weather + ' </li>' +
                        '<li style="width:100%">' + weathers[1].date + '【' + weathers[1].week + '】：日出时间---' + weathers[1].sun_rise_time + '      日落时间---' + weathers[1].sun_down_time + '      天气---' + weathers[1].weather + ' </li>' +
                        '<li style="width:100%">' + weathers[2].date + '【' + weathers[2].week + '】：日出时间---' + weathers[2].sun_rise_time + '      日落时间---' + weathers[2].sun_down_time + '      天气---' + weathers[2].weather + ' </li>' +
                        '<li style="width:100%">' + weathers[3].date + '【' + weathers[3].week + '】：日出时间---' + weathers[3].sun_rise_time + '      日落时间---' + weathers[3].sun_down_time + '      天气---' + weathers[3].weather + ' </li>' +
                        '<li style="width:100%">' + weathers[4].date + '【' + weathers[4].week + '】：日出时间---' + weathers[4].sun_rise_time + '      日落时间---' + weathers[4].sun_down_time + '      天气---' + weathers[4].weather + ' </li>' +
                        '<li style="width:100%">' + weathers[5].date + '【' + weathers[5].week + '】：日出时间---' + weathers[5].sun_rise_time + '      日落时间---' + weathers[5].sun_down_time + '      天气---' + weathers[5].weather + ' </li>' +
                        '</ul>' +
                        '</div>';
                    $("#container4").html("").append(weathersHtml);


                    var indexes = data.value[0].indexes;
                    var indexesHtml = '<div class="card-header">' +
                        '<h2 class="card-title">生活指数</h2>' +
                        '</div>' +
                        '<div class="card-block">' +
                        '<ul class="icon-list">' +
                        '<li style="width:100%">化妆指数：' + indexes[0].content + '</li>' +
                        '<li style="width:100%">感冒指数：' + indexes[1].content + '</li>' +
                        '<li style="width:100%">洗车指数：' + indexes[2].content + '</li>' +
                        '<li style="width:100%">穿衣指数：' + indexes[3].content + '</li>' +
                        '<li style="width:100%">运动指数：' + indexes[5].content + '</li>' +
                        '<li style="width:100%">紫外线强度指数：' + indexes[4].content + '</li>' +
                        '</ul>' +
                        '</div>';
                    $("#container5").html("").append(indexesHtml);

                    var alarms = data.value[0].alarms;
                    $("#container6").html("");
                    if (alarms.length) {
                        alarms = alarms[0];
                        var alarmsHtml = '<div class="card-header">' +
                            '<h2 class="card-title">预警信息</h2>' +
                            '</div>' +
                            '<div class="card-block">' +
                            '<ul class="icon-list">' +
                            '<li style="width:100%">预警标题：' + alarms.alarmDesc + '</li>' +
                            '<li style="width:100%">预警类型：' + alarms.alarmTypeDesc + '</li>' +
                            '<li style="width:100%">发布时间：' + alarms.publishTime + '</li>' +
                            '<li style="width:100%">预防措施：' + alarms.precaution + '</li>' +
                            '<li style="width:100%">预警详情：' + alarms.alarmContent + '</li>' +
                            '</ul>' +
                            '</div>';
                        $("#container6").append(alarmsHtml);
                    }
                } else {
                    $MB.n_danger(data.message);
                }
            } else {
                $MB.n_danger(r.msg);
            }
        });
    } else {
        $MB.n_warning("请输入城市！");
    }

}