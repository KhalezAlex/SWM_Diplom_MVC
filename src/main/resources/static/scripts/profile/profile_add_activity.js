let tagsAmount = document.getElementById("div_tags_list").querySelectorAll("input").length;

$("#profile_activities").on('change', () => {
    if (tagsAmount < 4)
        $.ajax({
            url: "/profile/activity/add",
            method: "GET",
            dataType: "html",
            data: {
                profileId: $("#profileId").val(),
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
    input.setAttribute("value", activityTag);
    input.setAttribute("id", "activity_tag_" + tagsAmount);
    input.style.height = '32px';
    input.style.width = '110px';
    input.style.borderRadius = '9px';
    input.style.marginTop = '10px';
    input.style.marginBottom = '10px';
    input.style.marginLeft = '4px';
    input.addEventListener('click', function() {tagsDeleteListener(input)});
    document.getElementById("div_tags_list").appendChild(input);
}

function deleteTagFormSelect(tag) {
    $('#profile_activities option:contains(' + tag + ')').remove();
}