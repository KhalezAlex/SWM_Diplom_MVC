let tagsAmount = document.getElementById("div_tags_list").querySelectorAll("input").length;

$("#profile_activities").on('change', () => {
    let activity = $("#profile_activities");
    if (tagsAmount < 4)
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
                drawNewActivity(data);
                tagsAmount++;
            }
        })
})

function drawNewActivity(activityTag) {
    let input = document.createElement("input");
    input.readOnly = true;
    input.setAttribute("value", activityTag);
    input.style.height = '32px';
    input.style.width = '110px';
    input.style.borderRadius = '9px';
    input.style.marginTop = '10px';
    input.style.marginBottom = '10px';
    input.style.marginLeft = '4px';
    document.getElementById("div_tags_list").appendChild(input);
}


function deleteTagFormSelect(tag) {
    $('#profile_activities option:contains(' + tag + ')').remove();
}