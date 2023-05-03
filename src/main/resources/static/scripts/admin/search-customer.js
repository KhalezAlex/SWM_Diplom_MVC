let rows = document.querySelectorAll("tr")
//по клику на кнопку ищем никнейм
$("#button_search").on('click', () => {
    for (let n = 0; n < rows.length; n++){
//записываем ряды яйчеек в переменную
        let cells = rows[n].querySelectorAll("th");
        if(cells[1] !== undefined)
            if(!cells[1].innerHTML.includes($("#input_search").val()) && !cells[1].innerHTML.includes("Login"))
//удаляем ряды которые не равны инпуту
                rows[n].remove();
    }
})