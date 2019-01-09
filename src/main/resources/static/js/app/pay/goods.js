$(function () {
    var $goodsTableForm = $(".goods-table-form");
    var settings = {
        url: ctx + "goods/list",
        pageSize: 10,
        queryParams: function (params) {
            return {
                pageSize: params.limit,
                pageNum: params.offset / params.limit + 1,
                goodsCycle: $goodsTableForm.find("select[name='goodsCycle']").val(),
                vipType: $goodsTableForm.find("select[name='vipType']").val(),
                vipMoney: $goodsTableForm.find("input[name='vipMoney']").val().trim(),
                // businessMoney: $goodsTableForm.find("input[name='businessMoney']").val().trim(),
            };
        },
        columns: [{
            checkbox: true
        }, {
            field: 'goodsId',
            title: '序号',
            width:80
        },{
            field: 'goodsCycle',
            title: '套餐周期',
            width:100

        }, {
            field: 'vipMoney',
            title: '会员价格',
            width:100
        },
        //     {
        //     field: 'businessMoney',
        //     title: '商户会员价格',
        //     width:100
        // },
            {
            field: 'remark',
            title: '备注说明',
            width:120

        }, {
            field: 'createTime',
            title: '创建时间',
            width:200
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


function deleteGoods() {
    var ids = $("#goodsTable").bootstrapTable('getSelections');
    alert("ids:"+ids);
    var ids_arr = "";
    if (!ids.length) {
        $MB.n_warning("请勾选需要删除的商品！");
        return;
    }
    for (var i = 0; i < ids.length; i++) {
        ids_arr += ids[i].id;
        if (i !== (ids.length - 1)) ids_arr += ",";
    }
    $MB.confirm({
        text: "确定删除选中商品？",
        confirmButtonText: "确定删除"
    }, function() {
        $.post(ctx + 'goods/delete', { "ids": ids_arr }, function(r) {
            if (r.code === 0) {
                $MB.n_success(r.msg);
                refresh();
            } else {
                $MB.n_danger(r.msg);
            }
        });
    });
}


