var validator;
var $goodsAddForm = $("#goods-add-form");
// var $rolesSelect = $goodsAddForm.find("select[name='rolesSelect']");
// var $roles = $goodsAddForm.find("input[name='roles']");

$(function () {
    // validateRule();
    // initRole();
    // createDeptTree();

    // $("input[name='status']").change(function () {
    //     var checked = $(this).is(":checked");
    //     var $status_label = $("#status");
    //     if (checked) $status_label.html('可用');
    //     else $status_label.html('禁用');
    // });

    $("#goods-add .btn-save").click(function () {
        var name = $(this).attr("name");
        getDept();
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
    validator.resetForm();
    $rolesSelect.multipleSelect('setSelects', []);
    $rolesSelect.multipleSelect("refresh");
    $goodsAddForm.find("input[name='goodsname']").removeAttr("readonly");
    $goodsAddForm.find(".goods_password").show();
    $goodsAddForm.find("input[name='status']").prop("checked", true);
    $("#goods-add-modal-title").html('新增用户');
    $MB.closeAndRestModal("goods-add");

}

// function validateRule() {
//     var icon = "<i class='zmdi zmdi-close-circle zmdi-hc-fw'></i> ";
//     validator = $goodsAddForm.validate({
//         rules: {
//             goodsname: {
//                 required: true,
//                 minlength: 3,
//                 maxlength: 10,
//                 remote: {
//                     url: "goods/checkgoodsName",
//                     type: "get",
//                     dataType: "json",
//                     data: {
//                         goodsname: function () {
//                             return $("input[name='goodsname']").val().trim();
//                         },
//                         oldgoodsname: function () {
//                             return $("input[name='oldgoodsname']").val().trim();
//                         }
//                     }
//                 }
//             },
//             email: {
//                 email: true
//             },
//             roles: {
//                 required: true
//             },
//             mobile: {
//                 checkPhone: true
//             },
//             ssex: {
//                 required: true
//             }
//         },
//         errorPlacement: function (error, element) {
//             if (element.is(":checkbox") || element.is(":radio")) {
//                 error.appendTo(element.parent().parent());
//             } else {
//                 error.insertAfter(element);
//             }
//         },
//         messages: {
//             goodsname: {
//                 required: icon + "请输入用户名",
//                 minlength: icon + "用户名长度3到10个字符",
//                 remote: icon + "用户名已经存在"
//             },
//             roles: icon + "请选择用户角色",
//             email: icon + "邮箱格式不正确",
//             ssex: icon + "请选择性别"
//         }
//     });
// }

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