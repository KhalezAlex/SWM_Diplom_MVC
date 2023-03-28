function tagsDeleteListener(input) {
    $.ajax({
        url: "/profile/activity/delete",
        method: "get",
        dataType: "html",
        data: {
            profileId: $("#input_profile_id").val(),
            tag: input.getAttribute("value").substring(1)},
        success: function(data) {
            input.remove();
            addTagToSelect(data);
            tagsAmount--;
        }
    })
}

function addTagToSelect(tag) {
    let option = document.createElement("option");
    option.innerHTML = tag;
    document.getElementById("profile_activities").appendChild(option);
}