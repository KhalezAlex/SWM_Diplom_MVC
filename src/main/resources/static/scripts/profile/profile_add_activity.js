let tagsAmount = document.getElementById("div_tags_list").querySelectorAll("input").length;

$("#profile_activities").on('change', () => {
    if (tagsAmount < 4)
        $.ajax({
            url: "/profile/activity/add",
            method: "GET",
            dataType: "html",
            data: {
                profileId: $("#input_profile_id").val(),
                tag: $("#profile_activities").val()},
            success: function(data) {
                deleteTagFormSelect(data);
                drawNewActivity(data);
                tagsAmount++;
            }
        })
})

function drawNewActivity(activityTag) {
    let input = document.createElement("input");
    input.readOnly = true;
    input.setAttribute("value", '#' + activityTag);
    input.setAttribute("id", "activity_tag_" + tagsAmount);
    input.addEventListener('click', function() {tagsDeleteListener(input)});
    document.getElementById("div_tags_list").appendChild(input);
}

function deleteTagFormSelect(tag) {
    $('#profile_activities option:contains(' + tag + ')').remove();
}