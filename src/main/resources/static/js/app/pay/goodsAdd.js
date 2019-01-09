var validator;
var $goodsAddForm = $("#goods-add-form");
// var $rolesSelect = $goodsAddForm.find("select[name='rolesSelect']");
// var $roles = $goodsAddForm.find("input[name='roles']");

$(function () {
    validateRule();
    // initRole();
    // createDeptTree();

    $("#goods-add .btn-save").click(function () {
        var name = $(this).attr("name");
        // getDept();
        var validator = $goodsAddForm.validate();
        var flag = validator.form();
        if (flag) {
            if (name === "save") {
                $.post(ctx + "goods/add", $goodsAddForm.serialize(), function (r) {
                    if (r.code === 0) {
                        closeModal();
                        $MB.n_success(r.msg);
                        $MB.refreshTable("goodsTable");
                    } else $MB.n_danger(r.msg);
                });
            }
            if (name === "update") {
                $.post(ctx + "goods/update", $goodsAddForm.serialize(), function (r) {
                    if (r.code === 0) {
                        closeModal();
                        $MB.n_success(r.msg);
                        $MB.refreshTable("goodsTable");
                    } else $MB.n_danger(r.msg);
                });
            }
        }
    });

    $("#goods-add .btn-close").click(function () {
        closeModal();
    });

});

function closeModal() {
    $("#goods-add-button").attr("name", "save");
    // validator.resetForm();
    // $rolesSelect.multipleSelect('setSelects', []);
    // $rolesSelect.multipleSelect("refresh");
    $goodsAddForm.find("input[name='goodsId']").removeAttr("readonly");
    $goodsAddForm.find("input[name='goodsCycle']").removeAttr("readonly");
    $goodsAddForm.find("input[name='vipMoney']").show();
    $goodsAddForm.find("input[name='remark']").prop("checked", true);
    $("#goods-add-modal-title").html('新增商品');
    $MB.closeAndRestModal("goods-add");

}

function validateRule() {
    // var icon = "<i class='zmdi zmdi-close-circle zmdi-hc-fw'></i> ";
    // validator = $goodsAddForm.validate({
    //     rules: {
    //         goodsCycle: {
    //             required: true,
    //             minlength: 1,
    //             maxlength: 20,
    //             remote: {
    //                 url: "goods/checkGoodsName",
    //                 type: "get",
    //                 dataType: "json",
    //                 data: {
    //                     goodsCycle: function () {
    //                         return $("input[name='goodsCycle']").val().trim();
    //                     },
    //                     oldGoodsCycle: function () {
    //                         return $("input[name='oldGoodsCycle']").val().trim();
    //                     }
    //                 }
    //             }
    //         }
    //     },
    //     // errorPlacement: function (error, element) {
    //     //     if (element.is(":checkbox") || element.is(":radio")) {
    //     //         error.appendTo(element.parent().parent());
    //     //     } else {
    //     //         error.insertAfter(element);
    //     //     }
    //     // },
    //     messages: {
    //         goodsCycle: {
    //             required: icon + "请输入套餐周期",
    //             minlength: icon + "套餐周期格式：1天/1个月/3个月/6个月/1年/3年/5年",
    //             remote: icon + "套餐已经存在"
    //         }
    //     }
    // });
}

// function initRole() {
//     $.post(ctx + "role/list", {}, function (r) {
//         var data = r.rows;
//         var option = "";
//         for (var i = 0; i < data.length; i++) {
//             option += "<option value='" + data[i].roleId + "'>" + data[i].roleName + "</option>"
//         }
//         $rolesSelect.html("").append(option);
//         var options = {
//             selectAllText: '所有角色',
//             allSelected: '所有角色',
//             width: '100%',
//             onClose: function () {
//                 $roles.val($rolesSelect.val());
//                 validator.element("input[name='roles']");
//             }
//         };
//
//         $rolesSelect.multipleSelect(options);
//     });
// }

// function createDeptTree() {
//     $.post(ctx + "dept/tree", {}, function (r) {
//         if (r.code === 0) {
//             var data = r.msg;
//             $('#deptTree').jstree({
//                 "core": {
//                     'data': data.children,
//                     'multiple': false // 取消多选
//                 },
//                 "state": {
//                     "disabled": true
//                 },
//                 "checkbox": {
//                     "three_state": false // 取消选择父节点后选中所有子节点
//                 },
//                 "plugins": ["wholerow", "checkbox"]
//             });
//         } else {
//             $MB.n_danger(r.msg);
//         }
//     })
// }

// function getDept() {
//     var ref = $('#deptTree').jstree(true);
//     $("[name='deptId']").val(ref.get_selected()[0]);
// }