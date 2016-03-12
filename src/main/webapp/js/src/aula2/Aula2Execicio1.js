function Aula2Execicio1() {
}
Aula2Execicio1.prototype.everyElementsIsNumbers = function (array) {
    return array.every(Aula2Execicio1.isNumber());
};

Aula2Execicio1.prototype.isNumber = function (value) {
    return !isNaN(value);
};
