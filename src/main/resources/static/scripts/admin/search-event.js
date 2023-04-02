let row = document.querySelectorAll("tr")
//по клику на кнопку ищем никнейм
$("#button_search").on('click', () => {
    for (let n = 0; n < row.length; n++){
        //записываем ряды яйчеек в переменную
        let cells = row[n].querySelectorAll("th");
        if(cells[5] !== undefined)
            if(!cells[5].innerHTML.includes($("#input_search").val()) && !cells[5].innerHTML.includes("Initiator"))
                //удаляем ряды которые не равны инпуту
                row[n].remove();
    }
})