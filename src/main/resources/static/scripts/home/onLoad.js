let current_city = $("#current_city");

console.log(current_city.val());

$.ajax({
    url: "/home/events/onLoadEvents",
    method: "get",
    contentType: "application/json",
    data: {cityName: current_city.val()},
    success: function(data) {
        for (let i = 0; i < data.length; i++)
            console.log(data[i]);
    }
})