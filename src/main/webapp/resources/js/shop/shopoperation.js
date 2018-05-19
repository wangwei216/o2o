
/*这个是补充shopoperation的js响应事件，也就是把后台的数据都给
* 设置到前台的页面中去*/

$('#submit').click(function () {

    var shop = {};
    shop.shopName = $('#shop-name').val();
    shop.shopAddr = $('#shop-addr').val();
    shop.phone = $('#shop-phone').val();
    shop.shopDesc = $('#shop-desc').val();

    shop.shopCategory = {
        shopCategoryId:$('#shop-category').find('option').not(function () {
            return !this.selected();
        }).date('id')

};

    shop.area = {
        areaId:$('#area').find('option').not(function () {
            return !this.selected();
        }).date('id')
};

    var  shopImg = $('#shop-img')[0].file(0);
    var  formData = new formData();
    formData.append('shopImg',shopImg);
    formData.append('shopStr',JSON.stringify(shop));
    var verifyCodeActual = $('#j-captcha').val();
    if (!verifyCodeActual ){
        $.toast('请输入验证码');
        return;
    }
   /* formData.append('verifyCodeActual',verifyCodeActual);
    $.ajax({
        url:registerShopUrl,
        type:'POST',
        data:formData,
        contentType:false,
        processData:false,
        cache:false,
       success:function (data) {
           if(data.success){
                $.toast('提交成功');
           }
           else {
               $.toast('提交失败');
           }
           $('#captcha-img').click();
       )};
    )}
*/


});