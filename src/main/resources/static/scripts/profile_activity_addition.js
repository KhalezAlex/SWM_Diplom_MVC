$("#profile_activities").on('change', () => {
    let activity = $("#profile_activities");
    $.ajax({
        url: "/profile/update/activity",
        method: "GET",
        dataType: "html",
        data: {
            profileId: $("#profileId").val(),
            tag: activity.val()},
        success: function(data) {
            alert("Не забыть написать код, чтобы добавлялись теги в параграфы")
            deleteTagFormSelect(data);
        }
    })
})

function deleteTagFormSelect(tag) {
    $('#profile_activities option:contains(' + tag + ')').remove();
}