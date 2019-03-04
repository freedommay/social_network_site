<!DOCTYPE html>
<html lang="en">
<#include "head.ftl">
<body>
<body class="text-center">
<div class="container">
    <form action="/reg/" id="regloginform" method="post" class="form-signin">
        <img class="mb-4" src="/image/logo.jpg" alt="" width="72" height="72">
        <h1 class="h3 mb-3 font-weight-normal">
            <#if msg??>
                ${msg}
            <#else>
                与世界建立连接
            </#if>
        </h1>
        <label for="name" class="sr-only">用户名</label>
        <input type="text" id="name" name="username" class="form-control" placeholder="用户名" required autofocus>
        <label for="inputPassword" class="sr-only">Password</label>
        <input type="password" name="password" id="inputPassword" class="form-control" placeholder="密码" required>
        <div class="checkbox mb-3">
            <label>
                <input type="checkbox" name="rememberme" value="true"> Remember me
            </label>
        </div>
        <#if next??>
        <input type="hidden" name="next" value="${next}"/>
        </#if>
        <div class="row">
            <div class="col-sm-6">
                <button class="btn btn-lg btn-info btn-block" type="submit"
                        onclick="form=document.getElementById('regloginform');form.action='/login/'">登录
                </button>
            </div>
            <div class="col-sm-6">
                <button class="btn btn-lg btn-info btn-block" type="submit"
                        onclick="form=document.getElementById('regloginform');form.action='/reg/'">注册
                </button>
            </div>
        </div>

        <p class="mt-5 mb-3 text-muted">&copy; 2017-2018</p>
    </form>
</div>
</body>
</body>
</html>
