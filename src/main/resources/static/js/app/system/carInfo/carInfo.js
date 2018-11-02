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
                chassisTrademark: $carInfoTableForm.find("input[name='chassisTrademark']").val(),
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
            title: '序号',
            width:50
        }, {
            field: 'vehicleType',
            title: '车辆类型',
            width:120,
            formatter: function (value, row, index) {
                if (value === '0') return '油罐车';
                else if (value === '1') return '供液车';
                else if (value === '2') return '洒水车';
                else if (value === '3') return '冷藏车';
                else if (value === '4') return '吸污车';
                else if (value === '5') return '吸粪车';
                else if (value === '6') return '清洗吸污车';
                else if (value === '7') return '高空作业车';
                else if (value === '8') return '垃圾车';
                else if (value === '9') return '抑尘车';
                else if (value === '10') return '厢式车';
                else if (value === '11') return '平板车';
                else if (value === '12') return '随车吊';
                else if (value === '13') return '搅拌车';
                else if (value === '14') return '粉罐车';
                else if (value === '15') return '防爆车';
                else if (value === '16') return '清障车';
                else if (value === '17') return '气瓶运输车';
                else if (value === '18') return '广告宣传车';
                else if (value === '19') return '售货车';
                else if (value === '20') return '扫路车';
                else if (value === '21') return '洗扫车';
                else if (value === '22') return '吸尘车';
                else if (value === '23') return '路面养护车';
                else if (value === '24') return '护栏清洗车';
                else if (value === '25') return '泵车';
                else if (value === '26') return '救护车';
                else if (value === '27') return '污水净化车';
                else if (value === '28') return '污水处理车';
                else if (value === '29') return '消防车';
                else if (value === '30') return '房车';
                else if (value === '31') return '仓栅车';
                else if (value === '32') return '载货车';
                else if (value === '33') return '升降平台车';
                else if (value === '34') return '自卸车';
                else if (value === '35') return '饲料车';
                else if (value === '36') return '校车';
                else if (value === '37') return '舞台车';
                else if (value === '38') return '轿运车';
                else if (value === '39') return '半挂车';
                else if (value === '40') return '旅居车';
                else return '无';
            }
        }, {
            field: 'chassisTrademark',
            title: '底盘品牌',
            width:100
        }, {
            field: 'engineType',
            title: '发动机型号',
            width:100
        }, {
            field: 'squareQuantity',
            title: '方量/吨位/尺寸',
            width:120

        }, {
            field: 'number',
            title: '数量',
            width:50
        }, {
            field: 'emissionStandard',
            title: '排放标准',
            width:100
        }, {
            field: 'remark',
            title: '备注（轴距，轮胎等）',
            width:400,
            class:'colStyle',
            formatter:paramsMatter
        }, {
            field: 'manufacturer',
            title: '生产厂家',
            width:100,
            class:'colStyle',
            formatter:paramsMatter1
        }, {
            field: 'contacts',
            title: '联系人',
            width:80
        }, {
            field: 'tel',
            title: '联系电话',
            width:120
        }, {
            field: 'address',
            title: '联系地址',
            width:100,
            class:'colStyle',
            formatter:paramsMatter2
        }, {
            field: 'price',
            title: '售价/万',
            width:120,
        }, {
            field: 'isTop',
            title: '是否置顶',
            width:80,
            formatter: function (value) {
                if (value === '1') return '置顶';
                else  return '未置顶';
            }
        }, {
            field: 'dimension',
            title: '箱体尺寸',
            width:100,
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