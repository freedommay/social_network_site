<!DOCTYPE html>
<html lang="en">
<#include "head.ftl">
<script>
    $(function () {
        var count = 0;
        var pageStart = 0;
        var pageSize = 10;
        getData(pageStart, pageSize);
        $(document).on('click', '.js-load-more', function () {
            count++;
            pageStart = count * pageSize;
            getData(pageStart, pageSize);
        });
    });

    function getData(offset, size) {
        $.ajax(
            {
                type: 'POST',
                url: '/get?offset=' + offset + "&limit=" + size,
                dataType: 'json',
                success: function (response) {
                    var len = response.length;

                    var result = '';
                    for (var i = 0; i < len; i++) {
                        var userId = response[i].objs.user.id;
                        result += '<hr/>' +
                            '<div class="row">' +
                            '<div class="col-sm-8 col-lg-8 col-md-8 col-xl-8"><div class="row"><div class="col-sm-3" align="center">' +
                            '<img width="100px" height="100px" alt="image" src="' + response[i].objs.user.headURL + '"/>';
                        if (response[i].objs.current) {
                            if (userId == response[i].objs.current.id) {
                                result += '<button class="btn btn-light" data-toggle="modal" data-target="#messageModal"' +
                                    ' style="margin-top: 20px" data-id="' + response[i].objs.user.id + '" disabled>发送私信</button>';
                            } else {
                                result += '<button class="btn btn-light" data-toggle="modal" data-target="#messageModal"' +
                                    ' style="margin-top: 20px" data-id="' + response[i].objs.user.headURL + '">发送私信'
                            }
                        }

                        result += '</div><div class="col-sm-9"><p><a href=' + '"/question/' + response[i].objs.question.id + '"' +
                            ' style="font-family: 微软雅黑;font-size: 18px;">' + response[i].objs.question.title +
                            '</a></p>' + '<div class="row"><div class="col-sm-4"><a href="/user/' +response[i].objs.user.id +
                              '">' + response[i].objs.user.name + '</a></div><div class="col-sm-6"><p>' + response[i].objs.question.createdDate +
                            '</p></div></div>' + '<div class="row-fluid"><div class="col-sm-12"><p>' + response[i].objs.question.content +
                                '</p></div></div>' + '<div class="row"><div class="col-sm-4"><p>';
                        if (response[i].objs.current) {
                            if (response[i].objs.followed) {
                                result += '<button type="button" class="btn btn-light" onclick="unfollow()" value="' +
                                    response[i].objs.user.id + '">取消关注</button>';
                            } else {
                                result += '<button type="button" class="btn btn-light" onclick="follow()" value="' +
                                    response[i].objs.user.id + '">关注作者</button>';
                            }
                        }

                        result += '</p></div><div class="col-sm-4"><p>评论数：' + response[i].objs.question.commentCount +
                            '</p></div></div></div></div></div></div>';

                    }
                    $('.js-blog-list').append(result);
                },

                error: function (xhr, type) {
                    alert('Ajax error!');
                }
            }
        )
    }
</script>
<body>
<#include "navbar.ftl">
<div class="container" style="margin-top: 50px">
    <div class="js-blog-list">

    </div>
    <div class="container text-center">
        <button class="btn btn-default js-load-more">加载更多</button>
    </div>
    <br><br>
</div>
<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
     aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">发布</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <input type="text" class="form-control" id="question" placeholder="微博标题">
                </div>
                <div class="form-group">
                    <label for="textarea">微博内容</label>
                    <textarea class="form-control" id="textarea" rows="3" placeholder="不多于140字"></textarea>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" onclick="add()">发布</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="messageModal" tabindex="-1" role="dialog" aria-labelledby="messageModalLabel"
     aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">发送私信</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <input type="hidden" name="id" id="id" value="">
                <div class="form-group">
                    <label for="textarea">内容</label>
                    <textarea class="form-control" id="message" rows="3" placeholder="私信内容"></textarea>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" onclick="send()">发布</button>
            </div>
        </div>
    </div>
</div>
</body>
</html>
