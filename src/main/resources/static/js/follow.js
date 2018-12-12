function follow(Event) {
    var element = event.currentTarget;
    var userID = element.value;
    data = {};
    data["userID"] = userID;
    $.ajax({
        url: "/followUser",
        type: 'post',
        data: data,
        dataType: 'json',
        success: function (response) {
            window.location.reload();
        }
    })
}

function unfollow() {
    var element = event.currentTarget;
    var userID = element.value;
    data = {};
    data["userID"] = userID;
    $.ajax({
        url: "/unfollowUser",
        type: 'post',
        data: data,
        dataType: 'json',
        success: function (response) {
            window.location.reload();
        }
    })
}

function followq() {
    var element = event.currentTarget;
    var questionID = element.value;
    data = {};
    data["questionID"] = questionID;
    $.ajax({
        url: "/followQuestion",
        type: 'post',
        data: data,
        dataType: 'json',
        success: function (response) {
            window.location.reload();
        }
    })
}

function unfollowq() {
    var element = event.currentTarget;
    var questionID = element.value;
    data = {};
    data["questionID"] = questionID;
    $.ajax({
        url: "/unfollowQuestion",
        type: 'post',
        data: data,
        dataType: 'json',
        success: function (response) {
            window.location.reload();
        }
    })
}
