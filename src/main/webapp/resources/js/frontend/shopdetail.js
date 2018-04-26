$(function() {
	var loading = false;
	var maxItems = 20;
	var pageSize = 3;

	var listUrl = '/o2o/frontend/listproductsbyshop';

	var pageNum = 1;
	var shopId = getQueryString('shopId');
	var productCategoryId = '';
	var productName = '';

	var searchDivUrl = '/o2o/frontend/listshopdetailpageinfo?shopId='
			+ shopId;
	//渲染出店铺的基本信息以及商品列表
	getSearchDivData();
	//预先加载店铺的10条信息
	addItems(pageSize,pageNum);
	function getSearchDivData() {
		var url = searchDivUrl;
		$
				.getJSON(
						url,
						function(data) {
							if (data.success) {
								var shop = data.shop;
								$('#shop-cover-pic').attr('src', shop.shopImg);
								$('#shop-update-time').html(
										new Date(shop.lastEditTime)
												.Format("yyyy-MM-dd"));
								$('#shop-name').html(shop.shopName);
								$('#shop-desc').html(shop.shopDesc);
								$('#shop-addr').html(shop.shopAddr);
								$('#shop-phone').html(shop.phone);

								var productCategoryList = data.productCategoryList;
								var html = '';
								productCategoryList
										.map(function(item, index) {
											html += '<a href="#" class="button" data-product-search-id='
													+ item.productCategoryId
													+ '>'
													+ item.productCategoryName
													+ '</a>';
										});
								$('#shopdetail-button-div').html(html);
							}
						});
	}
	getSearchDivData();

	function addItems(pageSize, pageIndex) {
		// 生成新条目的HTML
		var url = listUrl + '?' + 'pageIndex=' + pageIndex + '&pageSize='
				+ pageSize + '&productCategoryId=' + productCategoryId
				+ '&productName=' + productName + '&shopId=' + shopId;
		loading = true;
		$.getJSON(url, function(data) {
			if (data.success) {
				maxItems = data.count;
				var html = '';
				data.productList.map(function(item, index) {
					html += '' + '<div class="card" data-product-id='
							+ item.productId + '>'
							+ '<div class="card-header">' + item.productName
							+ '</div>' + '<div class="card-content">'
							+ '<div class="list-block media-list">' + '<ul>'
							+ '<li class="item-content">'
							+ '<div class="item-media">' + '<img src="'
							+ item.imgAddr + '" width="44">' + '</div>'
							+ '<div class="item-inner">'
							+ '<div class="item-subtitle">' + item.productDesc
							+ '</div>' + '</div>' + '</li>' + '</ul>'
							+ '</div>' + '</div>' + '<div class="card-footer">'
							+ '<p class="color-gray">'
							+ new Date(item.lastEditTime).Format("yyyy-MM-dd")
							+ '更新</p>' + '<span>点击查看</span>' + '</div>'
							+ '</div>';
				});
				$('.list-div').append(html);
				var total = $('.list-div .card').length;
				if (total >= maxItems) {
					// 加载完毕，则注销无限加载事件，以防不必要的加载
					$.detachInfiniteScroll($('.infinite-scroll'));
					// 删除加载提示符
					$('.infinite-scroll-preloader').remove();
				}
				pageNum += 1;
				loading = false;
				$.refreshScroller();
			}
		});
	}

	// addItems(pageSize, pageNum);
	//下滑屏幕自动进行分页搜索
	$(document).on('infinite', '.infinite-scroll-bottom', function() {
		if (loading)
			return;
		addItems(pageSize, pageNum);
	});

	//选择新的商品类别后，重置页码，清空原先的商品列表，重新按照新的类别去查询
	$('#shopdetail-button-div').on(
			'click',
			'.button',
			function(e) {
				productCategoryId = e.target.dataset.productSearchId;
				if (productCategoryId) {
					if ($(e.target).hasClass('button-fill')) {
						$(e.target).removeClass('button-fill');
						productCategoryId = '';
					} else {
						$(e.target).addClass('button-fill').siblings()
								.removeClass('button-fill');
					}
					$('.list-div').empty();
					pageNum = 1;
					addItems(pageSize, pageNum);
				}
			});

	//点击卡片进入该商品下的详情页
	$('.list-div')
			.on(
					'click',
					'.card',
					function(e) {
						var productId = e.currentTarget.dataset.productId;
						window.location.href = '/o2o/frontend/productdetail?productId='
								+ productId;
					});
	//需要查询的商品名字发生变化后，重置页码，清空原先的商品列表，重新进行查询
	$('#search').on('input', function(e) {
		productName = e.target.value;
		$('.list-div').empty();
		pageNum = 1;
		addItems(pageSize, pageNum);
	});

	//点击打卡侧栏
	$('#me').click(function() {
		$.openPanel('#panel-left-demo');
	});
	$.init();
});
