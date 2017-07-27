IbaUtil = function(){}

IbaUtil.countPerPage = 10;

IbaUtil.requestAjax = function(type, url, data, callback){
	$.ajax({
		type : type,
		url : url,
		processData: false,
		data : data,
		cache : false,
		beforeSend:function(xhr){
		},
		error : function(xor, status, error){
			alert(error);
		},
		success : function(data, status){
			if (typeof callback === 'function') {
				callback(data);
			}
		}
	});
}

IbaUtil.ajax = function(url, async, contentType, type, data, dataType, successCallback, errorCallback){
	$.ajax({
		url : url,                  // request
		async : async,              // request
		contentType : contentType,  // request
		type : type,                // request
		data : data,                // request
		dataType : dataType,        // response
		success : function(result, status, xhr){
			if (typeof successCallback === 'function') {
				successCallback.call(this, result);
			}
		},
		error : function(xhr, status, error){
			if (typeof errorCallback === 'function') {
				errorCallback.call(this);
			}
			console.log(JSON.stringify(xhr) + '\n' + status + '\n' + error);
			if (xhr.status === 403 || xhr.status === '403') {
				alert('권한이 없습니다.\n메인페이지로 이동합니다.');
				location.href = globalContextPath + '/main.do';
			}
		},
	});
}

IbaUtil.jsonAjax = function(url, data, successCallback){
	IbaUtil.ajax(url, true, 'application/json', 'post', JSON.stringify(data), 'json', successCallback);
}

IbaUtil.formAjax = function(url, type, data, successCallback){
	IbaUtil.ajax(url, true, 'application/x-www-form-urlencoded', type, data, 'json', successCallback);
}