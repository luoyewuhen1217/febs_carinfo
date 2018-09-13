$(function () {
    var $carInfoTableForm = $(".carInfo-table-form");
    var settings = {
        url: ctx + "carInfo/list",
        pageSize: 10,
        queryParams: function (params) {
            return {
                pageSize: params.limit,
                pageNum: params.offset / params.limit + 1,
                vehicleType: $carInfoTableForm.find("select[name='vehicleType']").val(),
                chassisTrademark: $carInfoTableForm.find("select[name='chassisTrademark']").val(),
                engineType: $carInfoTableForm.find("input[name='engineType']").val().trim(),
                squareQuantity: $carInfoTableForm.find("input[name='squareQuantity']").val(),
                remark: $carInfoTableForm.find("input[name='remark']").val(),
                manufacturer: $carInfoTableForm.find("input[name='manufacturer']").val()
            };
        },
        columns: [{
            checkbox: true
        }, {
            field: 'carId',
            title: '序号'
        }, {
            field: 'vehicleType',
            title: '车辆类型',
            formatter: function (value, row, index) {
                if (value === '1') return '油罐车';
                else if (value === '2') return '洒水车';
                else if (value === '3') return '冷藏车';
                else if (value === '4') return '吸污车';
                else if (value === '5') return '搅拌车';
                else if (value === '6') return '吸粪车';
                else return '无';
            }
        }, {
            field: 'chassisTrademark',
            title: '底盘品牌',
            formatter: function (value, row, index) {
                if (value === '1') return '东风牌';
                else if (value === '2') return '福田牌';
                else if (value === '3') return '江淮牌';
                else if (value === '4') return '江铃牌';
                else if (value === '5') return '解放牌';
                else if (value === '6') return '陕汽牌';
                else if (value === '7') return '重汽牌';
                else return '无';
            }
        }, {
            field: 'engineType',
            title: '发动机型号'
        }, {
            field: 'squareQuantity',
            title: '方量'
        }, {
            field: 'number',
            title: '数量'
        }, {
            field: 'emissionStandard',
            title: '排放标准',
            formatter: function (value, row, index) {
                if (value === '1') return '国三';
                else if (value === '2') return '国四';
                else if (value === '3') return '国五';
                else if (value === '4') return '国六';
                else return '无';
            }
        }, {
            field: 'remark',
            title: '备注（轴距，轮胎等）'
        }, {
            field: 'crateTime',
            title: '创建时间'
        }, {
            field: 'modifyTime',
            title: '修改时间'
        }, {
            field: 'manufacturer',
            title: '生产厂家'
        }, {
            field: 'contacts',
            title: '联系人'
        }, {
            field: 'tel',
            title: '联系电话'
        }, {
            field: 'address',
            title: '联系地址'
        }, {
            field: 'price',
            title: '售价'
        }
        ]
    };

    $MB.initTable('carInfoTable', settings);
});

function search() {
    $MB.refreshTable('carInfoTable');
}

function refresh() {
    $(".carInfo-table-form")[0].reset();
    $MB.refreshTable('carInfoTable');
}

function exportcarInfoExcel() {
    $.post(ctx + "carInfo/excel", $(".carInfo-table-form").serialize(), function (r) {
        if (r.code === 0) {
            window.location.href = "common/download?fileName=" + r.msg + "&delete=" + true;
        } else {
            $MB.n_warning(r.msg);
        }
    });
}

function exportcarInfoCsv() {
    $.post(ctx + "carInfo/csv", $(".carInfo-table-form").serialize(), function (r) {
        if (r.code === 0) {
            window.location.href = "common/download?fileName=" + r.msg + "&delete=" + true;
        } else {
            $MB.n_warning(r.msg);
        }
    });
}