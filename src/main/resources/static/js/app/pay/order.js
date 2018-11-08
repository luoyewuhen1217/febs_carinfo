$(function () {
    var $orderTableForm = $(".order-table-form");
    var settings = {
        url: ctx + "order/list",
        pageSize: 10,
        queryParams: function (params) {
            return {
                pageSize: params.limit,
                pageNum: params.offset / params.limit + 1,
                vehicleType: $orderTableForm.find("select[name='vehicleType']").val(),
                chassisTrademark: $orderTableForm.find("input[name='chassisTrademark']").val(),
                engineType: $orderTableForm.find("input[name='engineType']").val().trim(),
                squareQuantity: $orderTableForm.find("input[name='squareQuantity']").val(),
                remark: $orderTableForm.find("input[name='remark']").val(),
                manufacturer: $orderTableForm.find("input[name='manufacturer']").val()
            };
        },
        columns: [{
            checkbox: true
        }, {
            field: 'orderid',
            title: '序号',
            width:50
        },{
            field: 'ordercode',
            title: '订单号',
            width:100
        }, {
            field: 'username',
            title: '用户名',
            width:60
        }

        , {
            field: 'payment',
            title: '支付方式',
            width:60

        }, {
                field: 'paystatus',
                title: '支付状态',
                width:60

            }, {
            field: 'rechargemoney',
            title: '商品价格',
            width:35

        }, {
            field: 'rechargecycle',
            title: '充值周期',
            width:50

        }, {
            field: 'createTime',
            title: '创建时间',
            width:80
        }, {
            field: 'paytime',
            title: '支付时间',
            width:80
        }, {
            field: 'expirytime',
            title: '到期时间',
            width:80
        }
        ]
    };

    $MB.initTable('orderTable', settings);
});

function search() {
    $MB.refreshTable('orderTable');
}

function refresh() {
    $(".order-table-form")[0].reset();
    $MB.refreshTable('orderTable');
}

function exportorderExcel() {
    $.post(ctx + "order/excel", $(".order-table-form").serialize(), function (r) {
        if (r.code === 0) {
            window.location.href = "common/download?fileName=" + r.msg + "&delete=" + true;
        } else {
            $MB.n_warning(r.msg);
        }
    });
}

function exportorderCsv() {
    $.post(ctx + "order/csv", $(".order-table-form").serialize(), function (r) {
        if (r.code === 0) {
            window.location.href = "common/download?fileName=" + r.msg + "&delete=" + true;
        } else {
            $MB.n_warning(r.msg);
        }
    });
}

// 备注
function paramsMatter(value, row, index) {
    var values = row.remark;
    var span=document.createElement('span');
    span.setAttribute('title',values);
    span.innerHTML = row.remark;
    return span.outerHTML;
}

function paramsMatter1(value, row, index) {
    var values = row.manufacturer;
    var span=document.createElement('span');
    span.setAttribute('title',values);
    span.innerHTML = row.manufacturer;
    return span.outerHTML;
}

function paramsMatter2(value, row, index) {
    var values = row.address;
    var span=document.createElement('span');
    span.setAttribute('title',values);
    span.innerHTML = row.address;
    return span.outerHTML;
}