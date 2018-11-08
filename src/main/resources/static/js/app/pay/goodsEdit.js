function updategoods() {
    var selected = $("#goodsTable").bootstrapTable('getSelections');
    var selected_length = selected.length;
    if (!selected_length) {
        $MB.n_warning('请勾选需要修改的用户！');
        return;
    }
    if (selected_length > 1) {
        $MB.n_warning('一次只能修改一个用户！');
        return;
    }
    var userId = selected[0].userId;
    $.post(ctx + "goods/getgoods", {"carId": carId}, function (r) {
        if (r.code === 0) {
            var $form = $('#goods-add');
            $form.modal();
            var goods = r.msg;
            $("#goods-add-modal-title").html('修改车辆信息');
            $form.find("input[name='vehicleType']").val(goods.vehicleType).attr("readonly", true);
            $form.find("input[name='chassisTrademark']").val(goods.chassisTrademark);
            $form.find("input[name='engineType']").val(goods.engineType);
            $form.find("input[name='squareQuantity']").val(goods.squareQuantity);
            $form.find("input[name='number']").val(goods.number);
            $form.find("input[name='emissionstandard']").val(goods.emissionstandard);
            $form.find("input[name='remark']").val(goods.remark);
            $form.find("input[name='manufacturer']").val(goods.manufacturer);
            $form.find("input[name='contacts']").val(goods.contacts);
            $form.find("input[name='tel']").val(goods.tel);
            $form.find("input[name='number']").val(goods.mobile);

            var roleArr = [];
            for (var i = 0; i < goods.roleIds.length; i++) {
                roleArr.push(goods.roleIds[i]);
            }
            $form.find("select[name='rolesSelect']").multipleSelect('setSelects', roleArr);
            $form.find("input[name='roles']").val($form.find("select[name='rolesSelect']").val());
            var $status = $form.find("input[name='status']");
            if (goods.status === '1') {
                $status.prop("checked", true);
                $status.parent().next().html('可用');
            } else {
                $status.prop("checked", false);
                $status.parent().next().html('禁用');
            }
            $("input:radio[value='" + goods.ssex + "']").prop("checked", true);
            $deptTree.jstree().open_all();
            $deptTree.jstree('select_node', goods.deptId, true);
            $("#goods-add-button").attr("name", "update");
        } else {
            $MB.n_danger(r.msg);
        }
    });
}