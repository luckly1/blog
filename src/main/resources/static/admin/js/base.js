/**
 *  Tale全局函数对象   var tale = new $.tale();
 */
$.extend({
    tale: function () {
    }
});

/**
 * 成功弹框
 * @param options
 */
$.tale.prototype.alertOk = function (options) {
    options = options.length ? {text:options.text} : ( options || {} );
    options.title = options.title || '操作成功';
    options.reload = options.reload || false;
    options.text = options.text;
    options.path = options.path;
    options.showCancelButton = false;
    options.showCloseButton = false;
    options.type = 'success';
    options.timer = 2000;
    this.alertBox(options);
    //刷新页面
    if (options.reload) {
        window.location.href=options.path;
    }

};

/**
 * 弹出成功
 * @param text
 */
$.tale.prototype.alertOkAndReload = function (text,path) {  //提示信息，跳转路径
    var obj = new Object();
    obj.text=text;
    obj.reload=true;
    obj.path=path;
    this.alertOk(obj)      //新增reload  true or false
};

/**
 * 警告弹框
 * @param options
 */
$.tale.prototype.alertWarn = function (options) {
    options = options.length ? {text:options} : ( options || {} );
    options.title = options.title || '警告信息';
    options.text = options.text;
    options.timer = 3000;
    options.type = 'warning';
    this.alertBox(options);
};

/**
 * 询问确认弹框，这里会传入then函数进来
 * @param options
 */
$.tale.prototype.alertConfirm = function (options) {
    options = options || {};
    options.title = options.title || '确定要删除吗？';
    options.text = options.text;
    options.showCancelButton = true;
    options.type = 'warning';
    this.alertBox(options);
};

/**
 * 错误提示
 * @param options
 */
$.tale.prototype.alertError = function (options) {
    options = options.length ? {text:options} : ( options || {} );
    options.title = options.title || '错误信息';
    options.text = options.text;
    options.type = 'error';
    this.alertBox(options);
};

/**
 * 公共弹框
 * @param options
 */
$.tale.prototype.alertBox = function (options) {
    swal({
        title: options.title,
        text: options.text,
        type: options.type,
        showCancelButton: options.showCancelButton,
        confirmButtonColor: options.confirmButtonColor || '#3085d6',
        confirmButtonText: options.confirmButtonText || '确定',
        cancelButtonText: options.cancelButtonText || '取消',
        closeOnConfirm: true,
        closeOnCancel: true,
        timer:options.timer || 9999
       //  title: options.title,
       //  text: options.text,
       //  type: options.type,
       // // timer: options.timer || 9999,
       //  showCloseButton: options.showCloseButton,
       //  showCancelButton: options.showCancelButton,
       //  showLoaderOnConfirm: options.showLoaderOnConfirm || false,
       //  confirmButtonColor: options.confirmButtonColor || '#3085d6',
       //  cancelButtonColor: options.cancelButtonColor || '#d33',
       //  confirmButtonText: options.confirmButtonText || '确定',
       //  cancelButtonText: options.cancelButtonText || '取消'
    },function (e) {    //点取消会传入false   点确定传入true
        if (e){
            options.then && options.then(e);
        }
    });

};

/**
 * 全局post函数
 *
 * @param options   参数
 */
$.tale.prototype.post = function (options) {
    var self = this;
    $.ajax({
        type: 'POST',
        url: options.url,
        data: options.data || {},
        async: options.async || false,
        dataType: 'json',
        success: function (result) {
            self.hideLoading();
            options.success && options.success(result);
        },
        error: function () {}
    });
};

/**
 * 显示动画
 */
$.tale.prototype.showLoading = function () {
    if ($('#tale-loading').length == 0) {
        $('body').append('<div id="tale-loading"></div>');
    }
    $('#tale-loading').show();
};

/**
 * 隐藏动画
 */
$.tale.prototype.hideLoading = function () {
    $('#tale-loading') && $('#tale-loading').hide();
};