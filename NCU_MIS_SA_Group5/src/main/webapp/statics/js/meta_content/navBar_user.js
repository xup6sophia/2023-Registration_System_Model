// navBar_user
navBar_user = '<!-- navBar_user -->\
<nav class="navbar navbar-expand-lg bg-secondary px-5" width=50%>\
    <div class="container-fluid">\
        <a class="navbar-brand" href="MainPage.html">\
            <img src="statics/img/logo.png" alt="logo.png" height="50">\
        </a>\
        <ul class="navbar-nav">\
        	<li class="nav-item">\
        		<a class="nav-link" href="MainPage.html">首頁</a>\
        	</li>\
        	<li class="nav-item">\
            	<a class="nav-link" href="index.html">活動主頁</a>\
        	</li>\
        </ul>\
        <ul class="navbar-nav">\
            <li class="nav-item dropdown">\
                <a class="nav-link dropdown-toggle" href="#" id="navbarScrollingDropdown" role="button"\
                    data-bs-toggle="dropdown" aria-expanded="false">\
                    <i class="bi bi-person-circle">會員中心</i>\
                </a>\
        		<ul class="dropdown-menu">\
             	<li><a class="dropdown-item" href="center-basicInfo-view.html">基本資料</a></li>\
             	<li><a class="dropdown-item" href="center-myOrder-browse.html">我的活動</a></li>\
             	<li><a class="dropdown-item" href="collect.html">收藏管理</a></li>\
             	<li>\
                 	<hr class="dropdown-divider">\
             	</li>\
                    <li><a class="dropdown-item" href="login.html">登出</a></li>\
                </ul>\
            </li>\
        </ul>\
    </div>\
</nav>';
document.write(navBar_user);
