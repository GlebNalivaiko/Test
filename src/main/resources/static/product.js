
    function submit() {
    let title = getTitle();
    let description = getDescription();
    let price = getPrice();
    let discount = getDiscount();
    let characteristic = getCharacteristic();
    let quantityInStock = getQuantityInStock();
    let images = getImages();


    let product = {
    title: title,
    categoryName: 'fruits',
    quantityInStock: quantityInStock,
    description: description,
    price: price,
    discount: discount,
    characteristic: Object.fromEntries(characteristic),
}
    $.ajax({
    url: "admin",
    type: 'POST',
    dataType: 'json',
    data: JSON.stringify(product),
    contentType: 'application/json',
    mimeType: 'application/json',

}).done(function () {
    window.location = "http://localhost:8083/admin"
})
    .fail(function () {
    window.location = "http://localhost:8083/security/authorization"
});
}

    function getTitle() {
    return document.getElementById('title').value.trim();
}

    function getDescription() {
    return document.getElementById('description').value.trim();
}

    function getPrice() {
    return document.getElementById('price').value.trim();
}

    function getDiscount() {
    return document.getElementById('discount').value.trim();
}

    function getQuantityInStock() {
    return document.getElementById('quantityInStock').value.trim();
}

    function getCharacteristic() {
    let map = new Map();
    let children = document.getElementById('characteristic').children;
    for (let i = 0; i < children.length; i++) {
    alert(children[i].children[0].value.trim());
    if (children[i].children[0].value.trim().length !== 0 && children[i].children[2].value.trim().length !== 0) {
    map.set(children[i].children[0].value.trim(), children[i].children[2].value.trim())
}
}
    return map;
}

    function getImages() {

}

    let countOfFields = 1; // Текущее число полей
    let curFieldNameId = 1; // Уникальное значение для атрибута name
    let maxFieldLimit = 10; // Максимальное число возможных полей

    function deleteField(a) {
    if (countOfFields > 1) {
    // Получаем доступ к ДИВу, содержащему поле
    let contDiv = a.parentNode;
    alert(contDiv.id)
    // Удаляем этот ДИВ из DOM-дерева
    contDiv.parentNode.removeChild(contDiv);
    // Уменьшаем значение текущего числа полей
    countOfFields--;
}
    // Возвращаем false, чтобы не было перехода по сслыке
    return false;
}

    function addField() {
    // Проверяем, не достигло ли число полей максимума
    if (countOfFields >= maxFieldLimit) {
    alert("Число полей достигло своего максимума = " + maxFieldLimit);
    return false;
}
    // Увеличиваем текущее значение числа полей
    countOfFields++;
    // Увеличиваем ID
    curFieldNameId++;
    // Создаем элемент ДИВ
    var div = document.createElement("div");
    // Добавляем HTML-контент с пом. свойства innerHTML
    div.innerHTML = "<input type=\"text\"\n" +
    "                       style=\"border: black 1px solid;font-size: 15px;border-radius: 5px;width:300px;height: 30px\"/>\n" +
    "                <a style=\"color:red;\" onclick=\"return deleteField(this)\" href=\"#\">[—]</a>\n" +
    "                <input maxlength=\"10\" type=\"text\"\n" +
    "                       style=\"border: black 1px solid;font-size: 15px;width:300px;border-radius: 5px;height: 30px\"/>\n" +
    "                <a style=\"color:green;\" onclick=\"return addField()\" href=\"#\">[+]</a>"
    // Добавляем новый узел в конец списка полей
    document.getElementById("characteristic").appendChild(div);
    // Возвращаем false, чтобы не было перехода по сслыке
    return false;
}
