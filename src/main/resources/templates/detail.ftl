<!DOCTYPE html>
<html lang="en">
<#include "head.ftl">
<body>
<#include "navbar.ftl">
<div class="container">
    <div class="page-header" style="margin-top: 20px ">
        <div class="row">
            <div class="col-sm-9">
                <h2 style="font-family:宋体, color: #1a1a1a">${question.title}
                </h2></div>
            <div class="col-sm-3">
                <#if user??>
                <#if follow>
                    <button type="button" class="btn btn-light" onclick="unfollowq()" value="${question.id}">取消关注
                    </button>
                <#else>
                    <button type="button" class="btn btn-light" onclick="followq()" value="${question.id}">关注
                    </button>
                </#if>
                </#if>
            </div>
        </div>
    </div>
    <p>${question.content}</p>
    <div>
        <span>共${size}人关注</span>
        <span>
            <#list users as user>
                <img width="50px" height="50px" alt="image" src="${user.headURL}" style="margin-left: 20px"/>
            </#list>
        </span>
    </div>
    <#list vos as vo>
    <hr/>
    <div class="row">
        <div class="col-sm-2 vote">
            <#if (vo.liked > 0) >
            <button type="button" class="btn btn-success" data-id="${vo.comment.id}" value="${vo.comment.id}"
                    onclick="like()" disabled>赞同
            </button><br>
            <#else>
            <button type="button" class="btn btn-success" data-id="${vo.comment.id}" value="${vo.comment.id}"
                    onclick="like()">赞同
            </button><br>
            </#if>
            <span id="count" class="voteCount">${vo.likeCount}<span><br>
            <#if (vo.liked < 0) >
            <button type="button" class="btn btn-danger" data-id="${vo.comment.id}" value="${vo.comment.id}"
                    onclick="dislike()" disabled>反对</button>
            <#else>
            <button type="button" class="btn btn-danger" data-id="${vo.comment.id}" value="${vo.comment.id}"
                    onclick="dislike()">反对</button>
            </#if>
        </div>
        <div class="col-sm-10">
            <div>
                ${vo.user.name}
            </div>
            <div>
                ${vo.comment.content}
            </div>
            <div>
                发布于 ${vo.comment.createdDate?datetime}
            </div>
        </div>
    </div>
    </#list>
    <hr/>
    <form action="/addComment" method="post" id="commentform">
        <input type="hidden" name="questionID" value="${question.id}">
        <div class="form-group">
            <label for="commentarea">评论</label>
            <textarea name="content" class="form-control" id="commentarea" rows="3"></textarea>
            <button type="submit" class="btn btn-primary" style="margin-top:20px">发布</button>
        </div>
    </form>
</div>
</body>
</html>