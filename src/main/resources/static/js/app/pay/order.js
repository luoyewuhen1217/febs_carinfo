$(function () {
    var $orderTableForm = $(".order-table-form");
    var settings = {
        url: ctx + "order/list",
        pageSize: 10,
        queryParams: function (params) {
            return {
                pageSize: params.limit,
                pageNum: params.offset / params.limit + 1,
                orderCode: $orderTableForm.find("select[name='orderCode']").val(),
                userName: $orderTableForm.find("input[name='userName']").val(),
                payMent: $orderTableForm.find("select[name='payMent']").val(),
                payStatus: $orderTableForm.find("select[name='payStatus']").val(),
                rechargeMoney: $orderTableForm.find("input[name='rechargeMoney']").val(),
                rechargeCycle: $orderTableForm.find("input[name='rechargeCycle']").val()
            };
        },
        columns: [{
            checkbox: true
        }, {
            field: 'orderId',
            title: '序号',
            width: 50
        }, {
            field: 'orderCode',
            title: '订单号',
            width: 100
        }, {
            field: 'userName',
            title: '用户名',
            width: 60
        }, {
            field: 'payMent',
            title: '支付方式',
            width: 60

        }, {
            field: 'payStatus',
            title: '支付状态',
            width: 60,
            formatter: function (value, row, index) {
                if (value === '1') return '已支付';
                else if (value === '2') return '未支付';
                else if (value === '3') return '支付失败';
                else return '无';
            }

        }, {
            field: 'rechargeMoney',
            title: '商品价格',
            width: 35

        }, {
            field: 'rechargeCycle',
            title: '充值周期',
            width: 50

        }, {
            field: 'createTime',
            title: '创建时间',
            width: 80
        }, {
            field: 'payTime',
            title: '支付时间',
            width: 80
        }, {
            field: 'expiryTime',
            title: '到期时间',
            width: 80
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
    var span = document.createElement('span');
    span.setAttribute('title', values);
    span.innerHTML = row.remark;
    return span.outerHTML;
}

function paramsMatter1(value, row, index) {
    var values = row.manufacturer;
    var span = document.createElement('span');
    span.setAttribute('title', values);
    span.innerHTML = row.manufacturer;
    return span.outerHTML;
}

function paramsMatter2(value, row, index) {
    var values = row.address;
    var span = document.createElement('span');
    span.setAttribute('title', values);
    span.innerHTML = row.address;
    return span.outerHTML;
}