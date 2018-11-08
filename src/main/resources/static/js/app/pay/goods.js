$(function () {
    var $goodsTableForm = $(".goods-table-form");
    var settings = {
        url: ctx + "goods/list",
        pageSize: 10,
        queryParams: function (params) {
            return {
                pageSize: params.limit,
                pageNum: params.offset / params.limit + 1,
                vehicleType: $goodsTableForm.find("select[name='vehicleType']").val(),
                chassisTrademark: $goodsTableForm.find("input[name='chassisTrademark']").val(),
                engineType: $goodsTableForm.find("input[name='engineType']").val().trim(),
                squareQuantity: $goodsTableForm.find("input[name='squareQuantity']").val(),
                remark: $goodsTableForm.find("input[name='remark']").val(),
                manufacturer: $goodsTableForm.find("input[name='manufacturer']").val()
            };
        },
        columns: [{
            checkbox: true
        }, {
            field: 'goodsid',
            title: '序号',
            width:30
        },{
            field: 'goodscycle',
            title: '套餐周期',
            width:50
        }, {
            field: 'goodsmoney',
            title: '价格',
            width:60
        }, {
            field: 'remark',
            title: '备注说明',
            width:120

        }, {
            field: 'createTime',
            title: '创建时间',
            width:80
        }
        ]
    };

    $MB.initTable('goodsTable', settings);
});

function search() {
    $MB.refreshTable('goodsTable');
}

function refresh() {
    $(".goods-table-form")[0].reset();
    $MB.refreshTable('goodsTable');
}

function exportgoodsExcel() {
    $.post(ctx + "goods/excel", $(".goods-table-form").serialize(), function (r) {
        if (r.code === 0) {
            window.location.href = "common/download?fileName=" + r.msg + "&delete=" + true;
        } else {
            $MB.n_warning(r.msg);
        }
    });
}

function exportgoodsCsv() {
    $.post(ctx + "goods/csv", $(".goods-table-form").serialize(), function (r) {
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