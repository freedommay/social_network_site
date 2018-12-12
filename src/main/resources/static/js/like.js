function like(Event) {
    var element = event.currentTarget;
    var id = element.value;
    var div = $(element).closest('div.vote');
    data = {};
    data['commentID'] = id;
    $.ajax({
        url: "/like",
        type: 'post',
        data: data,
        dataType: 'json',
        success: function (response) {
            window.location.reload();
        }
    })
}

function dislike(Event) {
    var element = event.currentTarget;
    var id = element.value;
    var div = $(element).closest('div.vote');
    data = {};
    data['commentID'] = id;
    $.ajax({
        url: "/dislike",
        type: 'post',
        data: data,
        dataType: 'json',
        success: function (response) {
            window.location.reload();
        }
    })
}