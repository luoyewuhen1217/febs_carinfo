function updateGoods() {
    alert("aaa");
    var selected = $("#goodsTable").bootstrapTable('getSelections');
    var selected_length = selected.length;
    if (!selected_length) {
        $MB.n_warning('请勾选需要修改的商品！');
        return;
    }
    if (selected_length > 1) {
        $MB.n_warning('一次只能修改一个商品！');
        return;
    }
    var goodsId = selected[0].goodsId;
    $.post(ctx + "goods/getGoods", {"goodsId": goodsId}, function (r) {
        if (r.code === 0) {
            var $form = $('#goods-add');
            $form.modal();
            var goods = r.msg;
            $("#goods-add-modal-title").html('修改商品');
            $form.find("input[name='goodsCycle']").val(goods.goodsCycle).attr("readonly", true);
            $form.find("input[name='vipMoney']").val(goods.vipMoney);
            $form.find("input[name='businessMoney']").val(goods.businessMoney);
            $form.find("input[name='remark']").val(goods.remark);
            $form.find("input[name='createTime']").val(goods.createTime).attr("readonly", true);;
            $("#goods-add-button").attr("name", "update");
        } else {
            $MB.n_danger(r.msg);
        }
    });
}