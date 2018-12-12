<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container">
        <a href="#" class="navbar-brand"><img src="/image/question-mark.jpg" width="40" height="40" alt=""></a>
        <div class="collapse navbar-collapse" id="navbarNavDropdown">
            <ul class="navbar-nav">
                <li class="nav-item active">
                    <a class="nav-link" href="/index">首页 <span class="sr-only">(current)</span></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/msg/list">消息</a>
                </li>
                <#if user??>
                <li class="nav-item">
                    <a class="nav-link" href="/user/${user.id}/followers">关注我的人</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/user/${user.id}/followees">我关注的人</a>
                </li>
                </#if>
            </ul>
        </div>
        <#if user??>
            <a href="#" style="margin-right: 20px"><img src="${user.headURL}" width="40" height="40" alt=""></a>
            <div class="dropdown">
                <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton"
                        data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    ${user.name}
                </button>
                <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                    <a class="dropdown-item" href="/user/${user.id}">我的主页</a>
                    <a class="dropdown-item" href="/logout">登出</a>
                </div>
            </div>
            <button class="btn btn-info" data-toggle="modal" data-target="#exampleModal" style="margin-left: 25px">提问</button>
        <#else>
            <ul class="navbar-nav mr-auto">
                <li class="nav-item">
                    <a class="nav-link" href="/reglogin">登录/注册</a>
                </li>
            </ul>
        </#if>

    </div>
</nav>
