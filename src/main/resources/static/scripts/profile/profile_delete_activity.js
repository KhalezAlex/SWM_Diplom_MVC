function tagsDeleteListener(id) {
    let tagInput = $("#" + id);
    $.ajax({
        url: "/profile/activity/delete",
        method: "get",
        dataType: "html",
        data: {
            profileId: $("#profileId").val(),
            tag: tagInput.val()},
        success: function(data) {
            tagInput.remove();
            addTagToSelect(data);
        }
    })
}

function addTagToSelect(tag) {
    let option = document.createElement("option");
    option.innerHTML = tag;
    document.getElementById("profile_activities").appendChild(option);
}