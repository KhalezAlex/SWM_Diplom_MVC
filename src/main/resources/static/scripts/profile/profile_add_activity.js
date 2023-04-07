let tagsAmount = document.getElementById("div_tags_list").querySelectorAll("img").length;

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
                data = JSON.parse(data);
                deleteTagFormSelect(data);
                drawNewActivity(data);
                tagsAmount++;
            }
        })
})

function drawNewActivity(activity) {
    let img = document.createElement('img');
    img.setAttribute('src', "/images/activity_pics/" + activity.id + ".png");
    img.setAttribute('class', 'img_tags');
    img.addEventListener('click', function () {tagsDeleteListener(img)});
    document.getElementById("div_tags_list").appendChild(img);
}

function deleteTagFormSelect(activity) {
    $('#profile_activities option:contains(' + activity.name + ')').remove();
}