let select_city = $("#select_city");

console.log(select_city.val());

$.ajax({
    url: "/home/events/onLoadEvents",
    method: "get",
    contentType: "application/json",
    data: {cityName: select_city.val()},
    success: function(data) {
        for (let i = 0; i < data.length; i++)
            console.log(data[i]);
    }
})