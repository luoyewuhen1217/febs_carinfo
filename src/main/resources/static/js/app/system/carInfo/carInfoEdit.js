function updateUser() {
    var selected = $("#carInfoTable").bootstrapTable('getSelections');
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
    $.post(ctx + "carInfo/getCarInfo", {"carId": carId}, function (r) {
        if (r.code === 0) {
            var $form = $('#carInfo-add');
            $form.modal();
            var carInfo = r.msg;
            $("#carInfo-add-modal-title").html('修改车辆信息');
            $form.find("input[name='vehicleType']").val(carInfo.vehicleType).attr("readonly", true);
            $form.find("input[name='chassisTrademark']").val(carInfo.chassisTrademark);
            $form.find("input[name='engineType']").val(carInfo.engineType);
            $form.find("input[name='squareQuantity']").val(carInfo.squareQuantity);
            $form.find("input[name='remark']").val(carInfo.remark);
            $form.find("input[name='manufacturer']").val(carInfo.manufacturer);
            $form.find("input[name='number']").val(carInfo.mobile);
            $form.find("input[name='number']").val(carInfo.mobile);
            $form.find("input[name='number']").val(carInfo.mobile);

            var roleArr = [];
            for (var i = 0; i < carInfo.roleIds.length; i++) {
                roleArr.push(carInfo.roleIds[i]);
            }
            $form.find("select[name='rolesSelect']").multipleSelect('setSelects', roleArr);
            $form.find("input[name='roles']").val($form.find("select[name='rolesSelect']").val());
            var $status = $form.find("input[name='status']");
            if (carInfo.status === '1') {
                $status.prop("checked", true);
                $status.parent().next().html('可用');
            } else {
                $status.prop("checked", false);
                $status.parent().next().html('禁用');
            }
            $("input:radio[value='" + carInfo.ssex + "']").prop("checked", true);
            $deptTree.jstree().open_all();
            $deptTree.jstree('select_node', carInfo.deptId, true);
            $("#carInfo-add-button").attr("name", "update");
        } else {
            $MB.n_danger(r.msg);
        }
    });
}