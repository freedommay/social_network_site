<!DOCTYPE html>
<html lang="en">
<#include "head.ftl">
<body>
<#include "navbar.ftl">
<div class="container" style="margin-top: 20px">
    <div class="card">
        <div class="card-header">
        ${curUser.name}粉丝${followerCount}人
        </div>
        <#list followers as f>
        <div class="card-body border-bottom">

            <div class="row">
                <div class="col-sm-2">
                    <img src="${f.user.headURL}">
                </div>
                <div class="col-sm-4">
                    <div>
                        ${f.user.name}
                    </div>
                    <div>
                        <span>${f.followerCount}粉丝/</span>
                        <span>${f.followeeCount}关注/</span>
                        <span>${f.commentCount}回答/</span>
                    </div>
                </div>
                <div class="col-sm-3">
                </div>
                <div class="col-sm-3  text-center">
                    <#if f.followed>
                        <button type="button" class="btn btn-info" onclick="unfollow()" value="${f.user.id}">取消关注</button>
                    <#else>
                        <button type="button" class="btn btn-info" onclick="follow()" value="${f.user.id}">关注</button>
                    </#if>
                </div>
            </div>
        </div>
        </#list>
    </div>
</div>
</body>
</html>